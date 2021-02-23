package MainCodeBase;


import com.lowagie.text.*;
import com.lowagie.text.Font;
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

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class EmployeePayslipPanelController implements Initializable {
    @FXML
    private Button go_back_btn;

    @FXML
    private ComboBox<String> slct_m_box;

    @FXML
    private Button search_ps_btn;
    @FXML
    private TextField slct_y_fld;

    @FXML
    private TableView<PayslipPanelEmpDeets> emp_ps_infos;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, Integer> col_psid;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, String> col_name;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, String> col_month;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, String> col_year;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, String> col_dt_gnr;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, Integer> col_allowance;

    @FXML
    private TableColumn<PayslipPanelEmpDeets, Integer> col_netpay;

    @FXML
    private TextField name_fld;

    @FXML
    private TextField ssn_fld;

    @FXML
    private TextField dpt_name_fld;

    @FXML
    private TextField curr_loan_fld;

    @FXML
    private Button gen_ps_btn;

    @FXML
    private TextField psid_fld;

    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String empID = LoginController.id;
    String gradeID = "";
    String deptID = "";
    String month;
    ObservableList<PayslipPanelEmpDeets> listM;
    ObservableList<PayslipPanelEmpDeets> listC;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_fld.setEditable(false);
        ssn_fld.setEditable(false);
        dpt_name_fld.setEditable(false);
        curr_loan_fld.setEditable(false);
        psid_fld.setEditable(false);
        slct_m_box.getItems().addAll("JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER");
        LocalDate currentdate = LocalDate.now();
        slct_y_fld.setText(String.valueOf(currentdate.getYear()));
        conn = SqlConnect.ConnectDB();
        try {
            ps = conn.prepareStatement("SELECT e.grade_id,e.dept_id FROM employee e WHERE e.emp_id ="+empID);
            rs = ps.executeQuery();
            if(rs.next()){
                gradeID = rs.getString(1);
                if(rs.getString(2) != null)
                    deptID = rs.getString(2);
                System.out.println("Emp ID is "+empID+", Grade ID is "+gradeID+",Department ID is "+deptID);
            }
            col_psid.setCellValueFactory(new PropertyValueFactory<>("payID"));
            col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            col_month.setCellValueFactory(new PropertyValueFactory<>("Month"));
            col_year.setCellValueFactory(new PropertyValueFactory<>("Year"));
            col_dt_gnr.setCellValueFactory(new PropertyValueFactory<>("date_generated"));
            col_allowance.setCellValueFactory(new PropertyValueFactory<>("Allowance"));
            col_netpay.setCellValueFactory(new PropertyValueFactory<>("netpay"));

           if(deptID == "")
               listM = getPayslipDetailsOfCurrentEmpWithNoDept(Integer.parseInt(empID),Integer.parseInt(gradeID));
           else
            listM = getPayslipDetailsOfCurrentEmp(Integer.parseInt(empID),Integer.parseInt(deptID),Integer.parseInt(gradeID));
            emp_ps_infos.setItems(listM);

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void SearchPayslip(){

        month = slct_m_box.getValue();
        if(!(month.isEmpty()) && !(slct_y_fld.getText()).isEmpty()){
            //conn = SqlConnect.ConnectDB();
            if(deptID == "")
                listC = getPayslipDetailsOfCurrentEmpOfCurrentMonthWithNoDept(Integer.parseInt(empID),Integer.parseInt(gradeID),month,slct_y_fld.getText());
            else
                listC = getPayslipDetailsOfCurrentEmpOfCurrentMonth(Integer.parseInt(empID),Integer.parseInt(deptID),Integer.parseInt(gradeID),month,slct_y_fld.getText());
            emp_ps_infos.setItems(listC);
            conn = SqlConnect.ConnectDB();
            try {

                if(deptID == "")
                    ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan,ded.numleaves,ded.timesLate, p.pay_description, p.bonus, p.month, p.year, p.date_generated,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay,e.ssn  " +
                            "from employee e " +
                            "inner join gradescale g on g.grade_id = e.grade_id " +
                            "left join payslip p on p.emp_id=e.emp_id " +
                            "left join department d on e.dept_id=d.dept_id " +
                            "left join deduction ded on e.emp_id=ded.emp_id " +
                            "where e.emp_id = "+empID+"  and e.grade_id="+Integer.parseInt(gradeID)+"  and e.dept_id IS NULL and p.month = "+"\""+month+"\"and p.year = "+"\""+slct_y_fld.getText()+"\"");
                else {

                    ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan,ded.numleaves,ded.timesLate, p.pay_description, p.bonus, p.month, p.year, p.date_generated,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay,e.ssn  " +
                            "from employee e " +
                            "inner join gradescale g on g.grade_id = e.grade_id " +
                            "left join payslip p on p.emp_id=e.emp_id " +
                            "left join department d on e.dept_id=d.dept_id " +
                            "left join deduction ded on e.emp_id=ded.emp_id " +
                            "where e.emp_id = " + empID + "  and e.grade_id=" + Integer.parseInt(gradeID) + "  and e.dept_id = "+Integer.parseInt(deptID)+" and p.month = " + "\"" + month + "\"and p.year = " + "\"" + slct_y_fld.getText() + "\"");
                }
                rs = ps.executeQuery();
                if(rs.next()){
                    name_fld.setText(rs.getString(2));
                    ssn_fld.setText(rs.getString(17));
                    dpt_name_fld.setText(rs.getString(5));
                    curr_loan_fld.setText(rs.getString(7));
                    psid_fld.setText(rs.getString(1));
                }

                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void GenerateSlipPDF() throws IOException, DocumentException {
        if(!(month.isEmpty()) && !(slct_y_fld.getText()).isEmpty()){
            conn = SqlConnect.ConnectDB();
            JFileChooser dialog = new JFileChooser();
            Random rand = new Random();
            String name = String.valueOf(rand.nextInt(100));
            dialog.setSelectedFile(new File(name+".pdf"));
            int diagRes = dialog.showSaveDialog(null);
            if(diagRes==JFileChooser.APPROVE_OPTION){

                try {
                    String filePath = dialog.getSelectedFile().getPath();
                    JOptionPane.showMessageDialog(null,filePath);
                    OutputStream fos = new FileOutputStream(new File(filePath));

                    Document document = new Document();

                    PdfWriter.getInstance(document, fos);

                    document.open();

                    //FONT SIZE
                    Font heading =  FontFactory.getFont(FontFactory.TIMES_BOLD, 28f);
                    Font subheading =  FontFactory.getFont(FontFactory.TIMES_BOLD, 24f);
                    LocalDate date = LocalDate.now();
                    Paragraph timestamp = new Paragraph(String.valueOf(date.now()));
                    timestamp.setAlignment(Element.ALIGN_RIGHT);
                    document.add(timestamp);
                    Paragraph title = new Paragraph("Pay Slip", heading);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    Paragraph line = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------");
                    document.add(line);

                    if(deptID == "")
                        ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan,ded.numleaves,ded.timesLate, p.pay_description, p.bonus, p.month, p.year, p.date_generated,g.houseRentAllo,g.medicAllo,g.travelAllo,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance,g.basepay, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay,e.ssn ,((ded.numleaves*100)+(ded.timesLate*50)) as cutoff,((g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus)-((ded.numleaves*100)+(ded.timesLate*50))) as TotalEarns  " +
                                "from employee e " +
                                "inner join gradescale g on g.grade_id = e.grade_id " +
                                "left join payslip p on p.emp_id=e.emp_id  " +
                                "left join department d on e.dept_id=d.dept_id " +
                                "left join deduction ded on e.emp_id=ded.emp_id " +
                                "where e.emp_id = "+empID+"  and e.grade_id="+Integer.parseInt(gradeID)+"  and e.dept_id IS NULL and p.month = "+"\""+month+"\"and p.year = "+"\""+slct_y_fld.getText()+"\"");

                    else {
                        ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan,ded.numleaves,ded.timesLate, p.pay_description, p.bonus, p.month, p.year, p.date_generated,g.houseRentAllo,g.medicAllo,g.travelAllo,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance,g.basepay, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay,e.ssn ,((ded.numleaves*100)+(ded.timesLate*50)) as cutoff,((g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus)-((ded.numleaves*100)+(ded.timesLate*50))) as TotalEarns  " +
                                "from employee e " +
                                "inner join gradescale g on g.grade_id = e.grade_id " +
                                "left join payslip p on p.emp_id=e.emp_id  " +
                                "left join department d on e.dept_id=d.dept_id " +
                                "left join deduction ded on e.emp_id=ded.emp_id " +
                                "where e.emp_id = " + empID + "  and e.grade_id=" + Integer.parseInt(gradeID) + "  and e.dept_id = " + Integer.parseInt(deptID) + " and p.month = " + "\"" + month + "\"and p.year = " + "\"" + slct_y_fld.getText() + "\"");
                    }
                    rs = ps.executeQuery();
                    if(rs.next()){
                        Paragraph desp = new Paragraph(rs.getString(10)+" Current Payslip is generated for the MONTH of "+month+", and YEAR "+slct_y_fld.getText(),FontFactory.getFont(FontFactory.TIMES,16f));
                        desp.setAlignment(Element.ALIGN_CENTER);
                        document.add(desp);
                        Paragraph empTitle = new Paragraph("Employee Details",subheading);
                        document.add(empTitle);
                        document.add(new Paragraph("Name of the Employee : "+rs.getString(2),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("SSN : "+rs.getString(21),FontFactory.getFont(FontFactory.TIMES,14f)));
                        if(deptID == "")
                            document.add(new Paragraph("Department : None",FontFactory.getFont(FontFactory.TIMES,14f)));
                        else
                            document.add(new Paragraph("Department : "+rs.getString(5),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Grade Scale: "+rs.getString(6),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(line);
                        Paragraph sal = new Paragraph("Salary Details",subheading);
                        document.add(sal);
                        document.add(new Paragraph("Base Pay : PKR "+rs.getString(19),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("House Rental Allowance : PKR "+rs.getString(15),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Medical Allowance : PKR "+rs.getString(16),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Travel Allowance : PKR "+rs.getString(17),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Total Allowance : PKR "+rs.getString(18),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Bonus : PKR "+rs.getString(11),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Net Pay : PKR "+rs.getString(20),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(line);
                        Paragraph deded = new Paragraph("Deductions",subheading);
                        document.add(deded);
                        document.add(new Paragraph("No of Days late : "+rs.getString(9)+" Days",FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("No of Absent Days : "+rs.getString(8)+" Days",FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Current Loan taken : PKR "+rs.getString(7),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Cuttings : PKR "+rs.getString(22),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(line);
                        Paragraph chk = new Paragraph("Checkout",subheading);
                        document.add(chk);
                        document.add(new Paragraph("Payslip Generated on: "+rs.getString(14),FontFactory.getFont(FontFactory.TIMES,14f)));
                        document.add(new Paragraph("Total Earnings: PKR "+rs.getString(23),FontFactory.getFont(FontFactory.TIMES,18f)));
                        document.add(line);

                        document.close(); //close document
                        fos.close();

                    }else{
                        JOptionPane.showMessageDialog(null,"Invalid Instruction");
                    }

                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
             }


        }else{
            JOptionPane.showMessageDialog(null,"first serach your payslip in left panel");
        }

    }


    public ObservableList<PayslipPanelEmpDeets> getPayslipDetailsOfCurrentEmp(int eid,int did, int gid){

        System.out.println("Here");
        conn = SqlConnect.ConnectDB();

        if(eid == 3)
            System.out.println("eid");
        if(did == 1)
            System.out.println("dept");
        if(gid == 2)
            System.out.println("grade");
        ObservableList<PayslipPanelEmpDeets> list = FXCollections.observableArrayList();
        try{
            ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan, p.pay_description, p.bonus, p.month, p.year, p.date_generated,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay, e.ssn " +
                    "from employee e " +
                    "inner join gradescale g on g.grade_id = e.grade_id " +
                    "left join payslip p on p.emp_id=e.emp_id " +
                    "left join department d on e.dept_id=d.dept_id " +
                    "left join deduction ded on e.emp_id=ded.emp_id " +
                    "where e.emp_id = "+eid+"  and e.grade_id="+gid+"  and e.dept_id = "+did+" order by p.pay_id desc");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new PayslipPanelEmpDeets(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(10),rs.getString(11),rs.getString(12),Integer.parseInt(rs.getString(13)),Integer.parseInt(rs.getString(14))));
            }
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        return list;
    }

    public ObservableList<PayslipPanelEmpDeets> getPayslipDetailsOfCurrentEmpWithNoDept(int eid,int gid){
        conn = SqlConnect.ConnectDB();
        ObservableList<PayslipPanelEmpDeets> list = FXCollections.observableArrayList();
        try{
            ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan, p.pay_description, p.bonus, p.month, p.year, p.date_generated,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay, e.ssn " +
                    "from employee e " +
                    "inner join gradescale g on g.grade_id = e.grade_id " +
                    "left join payslip p on p.emp_id=e.emp_id " +
                    "left join department d on e.dept_id=d.dept_id " +
                    "left join deduction ded on e.emp_id=ded.emp_id " +
                    "where e.emp_id = "+eid+"  and e.grade_id="+gid+"  and e.dept_id IS NULL order by p.pay_id desc");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new PayslipPanelEmpDeets(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(10),rs.getString(11),rs.getString(12),Integer.parseInt(rs.getString(13)),Integer.parseInt(rs.getString(14))));
            }
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        return list;
    }

    public ObservableList<PayslipPanelEmpDeets> getPayslipDetailsOfCurrentEmpOfCurrentMonth(int eid, int did, int gid, String m, String y){
        conn = SqlConnect.ConnectDB();
        ObservableList<PayslipPanelEmpDeets> list = FXCollections.observableArrayList();
        try{
            ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan, p.pay_description, p.bonus, p.month, p.year, p.date_generated,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay, e.ssn " +
                    "from employee e " +
                    "inner join gradescale g on g.grade_id = e.grade_id " +
                    "left join payslip p on p.emp_id=e.emp_id " +
                    "left join department d on e.dept_id=d.dept_id " +
                    "left join deduction ded on e.emp_id=ded.emp_id " +
                    "where e.emp_id = "+eid+"  and e.grade_id="+gid+"  and e.dept_id = "+did+" and p.month = "+"\""+m+"\"and p.year = "+"\""+y+"\"");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new PayslipPanelEmpDeets(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(10),rs.getString(11),rs.getString(12),Integer.parseInt(rs.getString(13)),Integer.parseInt(rs.getString(14))));
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<PayslipPanelEmpDeets> getPayslipDetailsOfCurrentEmpOfCurrentMonthWithNoDept(int eid,int gid, String m, String y){
        conn = SqlConnect.ConnectDB();
        ObservableList<PayslipPanelEmpDeets> list = FXCollections.observableArrayList();
        try{

            ps = conn.prepareStatement("select p.pay_id, e.emp_name, e.age, e.phone, d.dept_name, g.grade_name, ded.loan, p.pay_description, p.bonus, p.month, p.year, p.date_generated,(g.houseRentAllo+g.medicAllo+g.travelAllo) AS allowance, (g.basepay+g.houseRentAllo+g.medicAllo+g.travelAllo+p.bonus) as netpay, e.ssn " +
                    "from employee e " +
                    "inner join gradescale g on g.grade_id = e.grade_id " +
                    "left join payslip p on p.emp_id=e.emp_id " +
                    "left join department d on e.dept_id=d.dept_id " +
                    "left join deduction ded on e.emp_id=ded.emp_id " +
                    "where e.emp_id = "+eid+"  and e.grade_id="+gid+"  and e.dept_id IS NULL and p.month = "+"\""+m+"\"and p.year = "+"\""+y+"\"");
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new PayslipPanelEmpDeets(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(10),rs.getString(11),rs.getString(12),Integer.parseInt(rs.getString(13)),Integer.parseInt(rs.getString(14))));
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void GoBack() throws IOException, SQLException {
        conn = SqlConnect.ConnectDB();
        ps = conn.prepareStatement("select u.privelege from employee e, user u where e.emp_id=u.emp_id and e.emp_id = "+"\""+empID+"\"");
        rs = ps.executeQuery();
        if(rs.next()){
            if(rs.getString(1).equals("MGR")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
                Parent root = loader.load();
                Stage window = (Stage)go_back_btn.getScene().getWindow();
                window.setScene(new Scene(root, 950,600));
                window.setResizable(false);
            }else if(rs.getString(1).equals("EMP")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
                Parent root = loader.load();
                Stage window = (Stage)go_back_btn.getScene().getWindow();
                window.setScene(new Scene(root, 950,600));
                window.setResizable(false);
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
                Parent root = loader.load();
                Stage window = (Stage)go_back_btn.getScene().getWindow();
                window.setScene(new Scene(root, 960,612));
                window.setResizable(false);
            }
        }
    }
}
