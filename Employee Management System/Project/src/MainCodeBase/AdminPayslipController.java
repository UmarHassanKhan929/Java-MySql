package MainCodeBase;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class AdminPayslipController implements Initializable {

    //Table stuff
    @FXML
    private TableView<EmpDeets> view;

    @FXML
    private TableColumn<EmpDeets, Integer> col_id;

    @FXML
    private TableColumn<EmpDeets,Integer> col_ssn;

    @FXML
    private TableColumn<EmpDeets, String> col_name;

    @FXML
    private TableColumn<EmpDeets, String> col_addr;

    @FXML
    private TableColumn<EmpDeets, String> col_email;

    @FXML
    private TableColumn<EmpDeets, String> col_ph;

    @FXML
    private TableColumn<EmpDeets, String> col_db;

    @FXML
    private TableColumn<EmpDeets, String> col_join;

    @FXML
    private TableColumn<EmpDeets, Integer> col_age;

    @FXML
    private TableColumn<EmpDeets, Integer> col_mgrID;

    @FXML
    private TableColumn<EmpDeets, Integer> col_gradeID;

    @FXML
    private TableColumn<EmpDeets, Integer> col_deptID;

    @FXML
    private TableView<PayslipPanelEmpDeets> view1;



    @FXML
    private Button go_back_btn;
    @FXML
    private Button resetBt;


    @FXML
    private ComboBox<String> slct_m_box;


    @FXML
    private TextField nameTxt;

    @FXML
    private TextField ssnTxt;

    @FXML
    private TextField deptTxt;

    @FXML
    private TextField loanTxt;

    @FXML
    private Button gen_ps_btn;

    @FXML
    private TextField idTxt1; //payslip ID text field

    @FXML
    private TextField idTxt; //employee ID text field

    @FXML
    private TextField slct_y_fld;

    @FXML
    private TextField leavesTxt;

    @FXML
    private TextField lateTxt;

    @FXML
    private TextField bonusTxt;

    @FXML
    private TextField name_fld1;

    @FXML
    private TextField descTxt;

    @FXML
    private ComboBox<String> select_monthly_rp_btn;

    @FXML
    private TextField monthlyyear_rp_fld;

    @FXML
    private TextField yearly_rp_fld;


    @FXML
    private Button generate_month_rp_btn;

    @FXML
    private Button generate_year_rp_btn;

    ObservableList<EmpDeets> listEm;
    ObservableList<PayslipPanelEmpDeets> listC;

    int index = -1;
    Connection conn = SqlConnect.ConnectDB();
    String empID = LoginController.id;
    String gradeID;
    String deptID;
    ResultSet rs = null;
    PreparedStatement ps = null;

    String status;
    String priv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            findMaxPayslipID();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        slct_m_box.getItems().addAll("JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER");
        select_monthly_rp_btn.getItems().addAll("JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER");

        showEmployee();
    }

    public void addPayslip() throws SQLException {

        String sql= "INSERT INTO payslip (pay_id, pay_description, date_generated, bonus, month, year, emp_id) VALUES \n" +
                "(?, ?, ?, ?, ?, ?, ?)";

        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String Date = dtf.format(now);


            conn = SqlConnect.ConnectDB();
            ps = conn.prepareStatement(sql);

                ps.setString(1, idTxt1.getText());
                ps.setString(2,descTxt.getText());
                ps.setString(3,Date);
                ps.setString(4,bonusTxt.getText());
                ps.setString(5,slct_m_box.getValue());
                ps.setString(6,slct_y_fld.getText());
                ps.setString(7,idTxt.getText());

                ps.execute();
                JOptionPane.showMessageDialog(null, "Payslip For " +nameTxt.getText()+" has been Created");
                findMaxPayslipID();



            //EMAIL
            conn = SqlConnect.ConnectDB();
                String email = "";
                sql = "SELECT email FROM employee WHERE emp_id ="+idTxt.getText().toString();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if(rs.next())
                    email = rs.getString(1);

                LocalDate time = LocalDate.now();
                String month = time.getMonth().toString();
                int year = time.getYear();


                String to = email;
                String from = "daniadamwolfgang@gmail.com";
                String host = "smtp.gmail.com";

                String user = "daniadamwolfgang@gmail.com";
                String pass = "TrecoolART123!";

                String subject = "Payslip of "+month+"/"+year;


                Properties props =  System.getProperties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smpt.port", "587");

                Session sess = Session.getDefaultInstance(props, new javax.mail.Authenticator(){

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });

                try {

                    MimeMessage m = new MimeMessage(sess);
                    m.setFrom(new InternetAddress(from));
                    m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
                    m.setSubject(subject);



                    m.setText("Payslip for the month of "+month+" and year "+year+" has been generated.\nYou can access your payslip from the payslip panel.");


                    Transport.send(m);
                    System.out.println("Email Sent!");
                }catch(MessagingException e){
                    e.printStackTrace();

                }




            conn.close();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e);
        }

    }

    public void findMaxPayslipID() throws SQLException {
        conn = SqlConnect.ConnectDB();
        ps = conn.prepareStatement("SELECT MAX(pay_id) FROM payslip");
        rs = ps.executeQuery();
        if(rs.next()) {
            int maxPlus1 = rs.getInt(1);
            maxPlus1++;
            idTxt1.setText(String.valueOf(maxPlus1));
        }
        conn.close();
    }

    public void showEmployee(){
        col_id.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("id"));
        col_ssn.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("ssn"));
        col_name.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("name"));
        col_addr.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("address"));
        col_email.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("email"));
        col_ph.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("phone"));
        col_db.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("dob"));
        col_join.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("joinDate"));
        col_age.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("age"));
        col_mgrID.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("mgrId"));
        col_gradeID.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("gradeId"));
        col_deptID.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("deptId"));

        listEm = SqlConnect.getEmployeeDetails();
        view.setItems(listEm);
    }

    @FXML
    void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)go_back_btn.getScene().getWindow();
        window.setScene(new Scene(root, 960,612));
        window.setResizable(false);
    }



    String month;
    public void searchPayslipOfMonth(){

        month = slct_m_box.getValue();

        String month = slct_m_box.getValue();
        if(!(month.isEmpty()) && !(slct_y_fld.getText()).isEmpty()) {
            listEm = getPayslipDetailsOfCurrentEmpOfCurrentMonth( month, slct_y_fld.getText());
            view.setItems(listEm);

        }
    }

    public void SearchPayslip(){
        month = slct_m_box.getValue();


        index =  view.getSelectionModel().getSelectedIndex();
        String id = col_id.getCellData(index).toString();

            try {
                conn = SqlConnect.ConnectDB();
                ps = conn.prepareStatement("SELECT * FROM empPayslip WHERE emp_id = "+id+" GROUP BY emp_id");
                rs = ps.executeQuery();
                if(rs.next()){


                    idTxt.setText(rs.getString(2));
                    nameTxt.setText(rs.getString(3));
                    name_fld1.setText(rs.getString(7)); //GRADE NAME
                    deptTxt.setText(rs.getString(6));
                    ssnTxt.setText(rs.getString(18));
                    loanTxt.setText(rs.getString(8));
                    leavesTxt.setText(rs.getString(9));
                    lateTxt.setText(rs.getString(10));
                    bonusTxt.setText(rs.getString(12));

                }

                conn.close();
            } catch (SQLException throwables) {
               JOptionPane.showMessageDialog(null, throwables);
            }

    }

    public ObservableList<EmpDeets> getPayslipDetailsOfCurrentEmpOfCurrentMonth(String m, String y){
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();
        try{
            conn = SqlConnect.ConnectDB();
            ps = conn.prepareStatement("select *,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay \n" +
                    "from employee e\n" +
                    "inner join gradescale g on g.grade_id = e.grade_id\n" +
                    "left join payslip p on p.emp_id=e.emp_id \n" +
                    "left join department d on e.dept_id=d.dept_id\n" +
                    "left join deduction ded on e.emp_id=ded.emp_id\n" +
                    "WHERE month = '"+m+"' AND year = '"+y+"' "
                    );

            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @FXML
    void getSelected() {

    }

    public void GenerateMonthlyReport() throws SQLException, FileNotFoundException, DocumentException{
        month = select_monthly_rp_btn.getValue();
        String yy = monthlyyear_rp_fld.getText();
        if(!(month.isEmpty() && yy.isEmpty())){
            JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(new File("Employees Report.pdf"));
            int dialogResult = dialog.showSaveDialog(null);
            if (dialogResult==JFileChooser.APPROVE_OPTION) {
                String filePath = dialog.getSelectedFile().getPath();
                conn = SqlConnect.ConnectDB();
                ps = conn.prepareStatement("select *,(p.bonus+g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo) as total from employee e,payslip p,gradescale g where e.emp_id=p.emp_id and e.grade_id=g.grade_id and p.month = "+"\""+month+"\""+" and p.year = "+"\""+yy+"\""+" group by p.pay_id");
                rs = ps.executeQuery();
                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath ));
                PdfPTable table = new PdfPTable(10);
                myDocument.open();

                float[] columnWidths = new float[] {3,5,7,7,5,5,5,5,5,5};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100); //set table width to 100%

                myDocument.add(new Paragraph("Monthly Pay Report for "+ month+" and Year "+yy, FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD )));
                LocalDate date = LocalDate.now();
                myDocument.add(new Paragraph(date.toString()));

                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                table.addCell(new PdfPCell(new Paragraph("PAY ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("SSN",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Name",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Date Generated",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Bonus",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Base Pay",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Medical Allowance",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Travel Allowance",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("HR Allowance", FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Net Pay",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                int net=0;
                while(rs.next()){
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(13),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(15),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(16),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(23),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(25),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(24),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(26), FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(27),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    net+=Integer.parseInt(rs.getString(27));
                }

                myDocument.add(table);
                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                Paragraph pp = new Paragraph("Total money Spent in "+month+": "+net+" PKR", FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD ));
                pp.setAlignment(Element.ALIGN_RIGHT);
                myDocument.add(pp);

                myDocument.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("PAY RP generated successfully");
                alert.showAndWait();

                rs.close();
                ps.close();
                conn.close();
                //JOptionPane.showMessageDialog(null,"Report was successfully generated");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("NO WAY HO-SAE");
            alert.setHeaderText(null);
            alert.setContentText("Enter year for its report..");
            alert.showAndWait();
        }
    }
    public void GenerateYearlyReport() throws SQLException, FileNotFoundException, DocumentException {
        String year = yearly_rp_fld.getText();
        if(!(yearly_rp_fld.getText()).isEmpty()){
            JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(new File("Employees Report.pdf"));
            int dialogResult = dialog.showSaveDialog(null);
            if (dialogResult==JFileChooser.APPROVE_OPTION) {
                String filePath = dialog.getSelectedFile().getPath();
                conn = SqlConnect.ConnectDB();
                ps = conn.prepareStatement("select *,(p.bonus+g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo) as total from employee e,payslip p,gradescale g where e.emp_id=p.emp_id and e.grade_id=g.grade_id and p.year = "+"\""+year+"\""+" group by p.pay_id order by p.pay_id desc");
                rs = ps.executeQuery();
                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath ));
                PdfPTable table = new PdfPTable(10);
                myDocument.open();

                float[] columnWidths = new float[] {3,5,7,7,5,5,5,5,5,5};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100); //set table width to 100%

                myDocument.add(new Paragraph("Yearly Pay Report for "+year, FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD )));
                LocalDate date = LocalDate.now();
                myDocument.add(new Paragraph(date.toString()));

                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                table.addCell(new PdfPCell(new Paragraph("PAY ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("SSN",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Name",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Date Generated",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Bonus",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Base Pay",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Medical Allowance",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Travel Allowance",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("HR Allowance", FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Net Pay",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
                int net=0;
                while(rs.next()){
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(13),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(15),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(16),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(23),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(25),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(24),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(26), FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(27),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    net+=Integer.parseInt(rs.getString(27));
                }

                myDocument.add(table);
                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                Paragraph pp = new Paragraph("Total money Spent: "+net+" PKR", FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD ));
                pp.setAlignment(Element.ALIGN_RIGHT);
                myDocument.add(pp);

                myDocument.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("PAY RP generated successfully");
                alert.showAndWait();

                rs.close();
                ps.close();
                conn.close();
                //JOptionPane.showMessageDialog(null,"Report was successfully generated");
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("NO WAY HO-SAE");
            alert.setHeaderText(null);
            alert.setContentText("Enter year for its report..");
            alert.showAndWait();
        }

    }

}
