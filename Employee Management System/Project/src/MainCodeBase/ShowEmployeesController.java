package MainCodeBase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.javafx.image.IntPixelGetter;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ShowEmployeesController implements Initializable {

    //buttons
    @FXML
    private Button allEmpBt;
    @FXML
    private Button mgrBt;
    @FXML
    private Button adminBt;
    @FXML
    private Button empBt;
    @FXML
    private Button retBt;
    @FXML
    private Button updateEmpBt;
    @FXML
    private Button addEmpBt;
    @FXML
    private Button delEmpBt;



    //editable text fields
    @FXML
    private TextField idTxt;
    @FXML
    private TextField  ssnTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField  dobTxt;
    @FXML
    private TextField joinDateTxt;
    @FXML
    private TextField ageTxt;
    @FXML
    private TextField mgrIDTxt;
    @FXML
    private TextField gradeIdTxt;
    @FXML
    private TextField deptIdTxt;

    @FXML
    private Button gnt_emp_list_btn;

    @FXML
    private DatePicker JD_CLNDR;

    @FXML
    private DatePicker DOB_CLNDR;


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
    private ComboBox<String> login_priv_cbx;

    @FXML
    private ComboBox<String> deptBox;

    @FXML
    private ComboBox<String> gradeBox;

    @FXML
    private ComboBox<String> mgrBox;








    ObservableList<EmpDeets> listEm;
    String deptIdFromCmbBox;
    String gradeIdFromCmbBox;
    String mgrIdFromCmbBox;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    String status;
    String priv;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_priv_cbx.getItems().addAll("Employee","Manager","Admin");
        try {
            fillDeptComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        showAllEmployee();

    }

    public void showAllAdmin(){

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

        listEm = SqlConnect.getAdminDetails("AD");
        view.setItems(listEm);
    }

    public void showAllManager(){

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

        listEm = SqlConnect.getManagerDetails("MGR");
        view.setItems(listEm);
    }

    public void showOnlyEmployee(){

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

        listEm = SqlConnect.getOnlyEmployeeDetail("EMP");
        view.setItems(listEm);
    }

    public void showAllEmployee(){

        updateTableView();
    }

    public void returnFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)retBt.getScene().getWindow();
        window.setScene(new Scene(root, 960,612));
        window.setResizable(false);
    }

    public void setMgrIDGradeIDCmbBox() throws SQLException {

        conn = (Connection) SqlConnect.ConnectDB();


        //Selecting ManagerID
        String sql = "SELECT e.emp_id FROM employee e, gradescale g WHERE e.emp_name = '"+mgrBox.getValue().toString()+"' AND e.dept_id = "+deptIdFromCmbBox+" AND e.dept_id = g.dept_id AND g.grade_name = '"+gradeBox.getValue().toString()+"'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        if(rs.next())
            mgrIdFromCmbBox = rs.getString(1);
        else
            mgrIdFromCmbBox = "NULL";


        System.out.println("MGR ID COMBO BOX: " + mgrIdFromCmbBox);

        if(deptIdFromCmbBox == "NULL")
            sql = "SELECT g.grade_id FROM gradescale g WHERE g.dept_id IS NULL AND  g.grade_name = \""+gradeBox.getValue().toString()+"\"";
        else
            sql = "SELECT g.grade_id FROM gradescale g WHERE g.dept_id = "+deptIdFromCmbBox+" AND  g.grade_name = '"+gradeBox.getValue().toString()+"'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        if(rs.next())
            gradeIdFromCmbBox = rs.getString(1);
        else
            gradeIdFromCmbBox = "NULL";

        System.out.println("GRADE ID COMBO BOX: " + gradeIdFromCmbBox);
        System.out.println("GRADE NAME COMBO BOX: " + gradeBox.getValue().toString());

        conn.close();


    }

    public void addEmp() throws SQLException {

        setMgrIDGradeIDCmbBox();
        int numbers = 0;

        System.out.println("Login box: "+login_priv_cbx.getValue().toString()+"    GradeBox: "+gradeBox.getValue().toString());

        if(!login_priv_cbx.getValue().toString().equals(gradeBox.getValue().toString())){
            JOptionPane.showMessageDialog(null, "Grade Scale and Login Privilege Do not Match!");
            return;
        }

        conn = (Connection) SqlConnect.ConnectDB();

        String sql = "INSERT INTO employee (emp_id, SSN, emp_name, address, email, phone, DOB, joining_date, age, mgr_emp_id, grade_id, dept_id) VALUES \n" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try{
            status = login_priv_cbx.getValue();
            if(!status.equals("Manager") && !status.equals("Employee") && !status.equals("Admin")){
                JOptionPane.showMessageDialog(null,"Select at least select 1 privilege for login credentials");
            }else{
                ps = conn.prepareStatement(sql);

                ps.setString(1, idTxt.getText());
                ps.setString(2, ssnTxt.getText());
                ps.setString(3, nameTxt.getText());
                ps.setString(4, addressTxt.getText());
                ps.setString(5, emailTxt.getText());
                ps.setString(6, phoneTxt.getText());
                LocalDate ld = DOB_CLNDR.getValue();
                ps.setString(7,ld.toString());
                LocalDate jd = JD_CLNDR.getValue();
                ps.setString(8, jd.toString());
                ps.setString(9, ageTxt.getText());
                if(mgrIdFromCmbBox == "NULL")
                    ps.setString(10, null);
                else
                    ps.setString(10, mgrIdFromCmbBox);
                ps.setString(11, gradeIdFromCmbBox);

                /*if(deptIdTxt.getText().isEmpty())
                    ps.setString(12, null);
                else
                    ps.setString(12, deptIdTxt.getText());*/
                if(deptIdFromCmbBox == "NULL")
                    ps.setString(12, null);
                else
                    ps.setString(12, deptIdFromCmbBox);

                ps.execute();
                JOptionPane.showMessageDialog(null, "Employee " +nameTxt.getText()+" Added to Database");

                // making changes for user login credentials

                if(status=="Employee"){
                    priv = "EMP";
                }else if(status=="Admin"){
                    priv = "AD";

                }else if(status=="Manager"){
                    priv = "MGR";
                }
                Random rand = new Random();
                numbers = rand.nextInt(999999);
                String userSql = "INSERT INTO user (emp_id, username, password, privelege) VALUES ("+idTxt.getText()+","+"\""+emailTxt.getText()+"\""+","+"\""+numbers+"\""+","+"\""+priv+"\")";
                ps = conn.prepareStatement(userSql);
                ps.execute();
                JOptionPane.showMessageDialog(null,"Your Credentials. Username: "+emailTxt.getText()+"  Password: "+numbers);
                updateTableView();

                String dedSql = "INSERT INTO deduction(emp_id, loan, numleaves, timesLate) VALUES (?,?,?,?)";

                try{
                    ps = conn.prepareStatement(dedSql);

                    ps.setString(1,idTxt.getText());
                    ps.setString(2,"0");
                    ps.setString(3,"0");
                    ps.setString(4,"0");

                    ps.execute();

                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        conn.close();


        //EMAIL
        {

            String to = emailTxt.getText();
            String from = "daniadamwolfgang@gmail.com";
            String host = "smtp.gmail.com";

            String user = "daniadamwolfgang@gmail.com";
            String pass = "TrecoolART123!";

            String subject = "Login Details";

            String yPass = String.valueOf(numbers);
            String yUser = emailTxt.getText().toString();
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
                m.setText("Username: "+yUser+"\nPassword: "+yPass);


                Transport.send(m);
                System.out.println("Email Sent!");
            }catch(MessagingException e){
                e.printStackTrace();

            }



        }
    }

    void updateTableView(){

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
    void getSelected(){
        index =  view.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        else{
            idTxt.setText(col_id.getCellData(index).toString());
            ssnTxt.setText(col_ssn.getCellData(index).toString());
            nameTxt.setText(col_name.getCellData(index).toString());
            addressTxt.setText(col_addr.getCellData(index).toString());
            emailTxt.setText(col_email.getCellData(index).toString());
            phoneTxt.setText(col_ph.getCellData(index).toString());
            //dobTxt.setText(col_db.getCellData(index).toString());
            DOB_CLNDR.setValue(LocalDate.parse(col_db.getCellData(index).toString()));
            JD_CLNDR.setValue(LocalDate.parse(col_join.getCellData(index).toString()));
            //joinDateTxt.setText(col_join.getCellData(index).toString());
            ageTxt.setText(col_age.getCellData(index).toString());

            if(col_mgrID.getCellData(index).toString().equals("0")) {
                mgrIDTxt.setText("");
                mgrIDTxt.setPromptText("No Manager!");
               // mgrBox.setPromptText("No Manager");
            }
            else
                mgrIDTxt.setText(col_mgrID.getCellData(index).toString());

            gradeIdTxt.setText(col_gradeID.getCellData(index).toString());

            if(col_deptID.getCellData(index).toString().equals("0")) {
                deptIdTxt.setText("");
                deptIdTxt.setPromptText("No Department!");
               // deptBox.setPromptText("No Department");
            }
            else
            deptIdTxt.setText(col_deptID.getCellData(index).toString());

        }

    }

    public void updateEmp() throws SQLException {

        setMgrIDGradeIDCmbBox();

        if(!login_priv_cbx.getValue().toString().equals(gradeBox.getValue().toString())){
            JOptionPane.showMessageDialog(null, "Grade Scale and Login Privilege Do not Match!");
            return;
        }
            try{
                status = login_priv_cbx.getValue();
                if(!status.equals("Manager") && !status.equals("Employee") && !status.equals("Admin")){
                    JOptionPane.showMessageDialog(null,"Select at least select 1 privilege for login credentials");

                    return;
                }

                conn = (Connection) SqlConnect.ConnectDB();
               // String deptIDs;
                String mgrIDs;

                String ids = idTxt.getText();
                String ssns = ssnTxt.getText();
                String names = nameTxt.getText();
                String addresss = addressTxt.getText();
                String emails = emailTxt.getText();
                String phones = phoneTxt.getText();
                LocalDate ld = DOB_CLNDR.getValue();
                LocalDate jd = JD_CLNDR.getValue();
                String dobs = ld.toString();
                String joinDates = jd.toString();
                String ages = ageTxt.getText();

                /*if(mgrIdFromCmbBox == "NULL")
                    mgrIDs = "NULL";
                else
                    mgrIDs= mgrIdFromCmbBox;*/

               // String gradeIDs = gradeIdTxt.getText();

               /* if(deptIdTxt.getText().isEmpty())
                    deptIDs = "NULL";
                else
                    deptIDs = deptIdTxt.getText();*/

        String sql = "UPDATE employee\n" +
         "SET emp_id = "+ids+", SSN = "+ssns+", emp_name ='"+names+"', address='"+addresss+"', email='"+emails+"', phone='"+phones+"', DOB='"+dobs+"', \n" +
         "joining_date= '"+joinDates+"', age = '"+ages  +"', mgr_emp_id = "+mgrIdFromCmbBox+", grade_id= '"+gradeIdFromCmbBox+"', dept_id = "+deptIdFromCmbBox+" \n" +
         "WHERE emp_id = '"+ids+"'";

                ps = conn.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(null, "Details of "+names+" has been updated!");
                updateTableView();


                if(status.equals("Employee")){
                    sql = "UPDATE user SET privelege = 'EMP' WHERE emp_id ="+idTxt.getText();
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, nameTxt.getText()+" is now only an Employee");

                }
                else if(status.equals("Manager")){
                    sql = "UPDATE user SET privelege = 'MGR' WHERE emp_id ="+idTxt.getText();
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, nameTxt.getText()+" is now only a Manager");

                }
                else if(status.equals("Admin")){
                    sql = "UPDATE user SET privelege = 'AD' WHERE emp_id ="+idTxt.getText();
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, nameTxt.getText()+" is now only an Admin");

                }



            }catch (Exception e){
                //JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
            }

            conn.close();
    }

    public void deleteEmp() throws SQLException {
        conn = (Connection) SqlConnect.ConnectDB();
        String sql;

        try{
            if(idTxt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Select an ID to Delete!");
                conn.close();
                return;
            }


            sql = "DELETE FROM employee where emp_id = ?";
            ps= conn.prepareStatement(sql);
            ps.setString(1,idTxt.getText());
            String name = nameTxt.getText();
            ps.execute();

            JOptionPane.showMessageDialog(null,"Employee "+name+ " has been Deleted!");
            updateTableView();

            String ids = idTxt.getText();
            sql = "UPDATE employee SET mgr_emp_id = NULL WHERE mgr_emp_id = "+ids;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Managers of Employees has been Updated");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        conn.close();
    }

    public void GenerateEmpList() throws FileNotFoundException, DocumentException, SQLException {
        conn = SqlConnect.ConnectDB();

        JFileChooser dialog = new JFileChooser();
        dialog.setSelectedFile(new File("Employees Report.pdf"));
        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult==JFileChooser.APPROVE_OPTION) {
            String filePath = dialog.getSelectedFile().getPath();
            try {
                ps = conn.prepareStatement("select e.emp_id,e.ssn,e.emp_name,e.age,e.address,e.email,e.phone,e.joining_date,d.dept_name,g.grade_name from employee e, department d,gradescale g where e.dept_id=d.dept_id and e.grade_id=g.grade_id");
                rs = ps.executeQuery();

                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath ));
                PdfPTable table = new PdfPTable(10);
                myDocument.open();

                float[] columnWidths = new float[] {3,5,7,3,9,9,7,6,7,7};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100); //set table width to 100%

                myDocument.add(new Paragraph("Employees List",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD )));
                LocalDate date = LocalDate.now();
                myDocument.add(new Paragraph(date.toString()));
                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                table.addCell(new PdfPCell(new Paragraph("ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("SSN",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Name",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Age",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Address",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Email",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Phone",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Grade",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Department", FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Joining Date",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));

                while(rs.next()){
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(7),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(10),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(9), FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(8),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                }

                myDocument.add(table);
                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                myDocument.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Employee Report generated successfully");
                alert.showAndWait();

                //JOptionPane.showMessageDialog(null,"Report was successfully generated");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {
                try{
                    rs.close();
                    ps.close();

                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }

        }
        conn.close();
    }


    public void fillDeptComboBox() throws SQLException {
        conn = SqlConnect.ConnectDB();
        String sql = "SELECT dept_name FROM department";
        String dept;
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        ObservableList<String> deptList = FXCollections.observableArrayList();
        deptList.add("NULL");
        while(rs.next()){
            dept = rs.getString(1);
            deptList.add(dept);
        }
        deptBox.setItems(deptList);
        conn.close();

    }



    public void setManagerTxt() throws SQLException {
       String deptName= deptBox.getSelectionModel().getSelectedItem();
       conn = SqlConnect.ConnectDB();





       String  sql = "SELECT dept_id FROM department WHERE dept_name ='"+deptName+"'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        if(rs.next()) {
            deptIdFromCmbBox = rs.getString(1);
            System.out.println(deptIdFromCmbBox);
        }
        else
            deptIdFromCmbBox = "NULL";


        if(deptBox.getValue().toString() == "NULL") {
            deptIdFromCmbBox = "NULL";
            sql = "SELECT DISTINCT grade_name FROM gradescale g, department d WHERE g.dept_id IS "+deptIdFromCmbBox;
        }
        else
            sql = "SELECT grade_name FROM gradescale g, department d WHERE d.dept_id = g.dept_id AND d.dept_id = "+deptIdFromCmbBox;

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        String grd = "";
        ObservableList<String> grdList = FXCollections.observableArrayList();

        while(rs.next()){
             if(deptIdFromCmbBox == "NULL") {           //ADMIN--EMPLOYEE
                 grd = rs.getString(1);
                 grdList.add(grd);
             }
             else{
                 if(mgrBox.getSelectionModel().isEmpty()) {
                     grd = " ";
                     grdList.add("Manager");
                     grdList.add("Employee");
                     break;
                 }
                 else {
                     grd = " ";
                     grdList.add("Employee");
                     break;
                 }
             }

        }
        if(grd == "")
            grdList.add("NULL");

        gradeBox.setItems(grdList);




         sql = "SELECT e.emp_name FROM employee e, department d, user u WHERE d.dept_id = "+deptIdFromCmbBox+" AND d.dept_id = e.dept_id AND e.emp_id = u.emp_id AND u.privelege = 'MGR'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        String mgr;
        ObservableList<String> mgrList = FXCollections.observableArrayList();


        if (rs.next()) {
            mgr = rs.getString(1);
        } else mgr = "Set as Manager";

        if(deptBox.getValue().toString() == "NULL")
        mgr = "NULL";

        mgrList.add(mgr);
        mgrBox.setItems(mgrList);



        conn.close();
    }
}
