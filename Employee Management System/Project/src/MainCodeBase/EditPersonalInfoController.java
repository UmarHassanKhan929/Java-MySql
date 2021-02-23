package MainCodeBase;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditPersonalInfoController implements Initializable {


    @FXML
    Button retBt;
    @FXML
    Button updateBt;
    @FXML
    Button showDetailBt;


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

    //non editable text fields
    @FXML
    private TextField NidTxt;
    @FXML
    private TextField  NssnTxt;
    @FXML
    private TextField NnameTxt;
    @FXML
    private TextField NaddressTxt;
    @FXML
    private TextField NemailTxt;
    @FXML
    private TextField NphoneTxt;
    @FXML
    private TextField  NdobTxt;
    @FXML
    private TextField NjoinDateTxt;
    @FXML
    private TextField NageTxt;
    @FXML
    private TextField NmgrIDTxt;
    @FXML
    private TextField NgradeIdTxt;
    @FXML
    private TextField NdeptIdTxt;


    int index =-1;
    Connection MyConn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String curr_id;
    String priv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        curr_id  = LoginController.id;
        priv = LoginController.priv;
        if(priv.equals("AD"))
            System.out.println("Admin");
        else {

            //Employee and Manager can only update certain fields
            System.out.println("Manager Or Employee");
            idTxt.setEditable(false);
            ssnTxt.setEditable(false);
            nameTxt.setEditable(false);
            dobTxt.setEditable(false);
            joinDateTxt.setEditable(false);
            ageTxt.setEditable(false);
            mgrIDTxt.setEditable(false);
            gradeIdTxt.setEditable(false);
            deptIdTxt.setEditable(false);

            idTxt.setPromptText("---");
            ssnTxt.setPromptText("---");
            nameTxt.setPromptText("---");
            dobTxt.setPromptText("---");
            joinDateTxt.setPromptText("---");
            ageTxt.setPromptText("---");
            mgrIDTxt.setPromptText("---");
            gradeIdTxt.setPromptText("---");
            deptIdTxt.setPromptText("---");


        }

        try {
            setShowDetailBtFunc();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void setShowDetailBtFunc() throws SQLException, ClassNotFoundException {

//        Class.forName("com.mysql.jdbc.Driver");
//        Connection MyConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeepayroll", "root", "Trecool123!");
        MyConn = SqlConnect.ConnectDB();



        try{
            ps = MyConn.prepareStatement("SELECT * FROM employee e WHERE e.emp_id ="+ curr_id);
            rs = ps.executeQuery();
            if(rs.next()){
                NidTxt.setText(rs.getString(1));
                NssnTxt.setText(rs.getString(2));
                NnameTxt.setText(rs.getString(3));
                NaddressTxt.setText(rs.getString(4));
                NemailTxt.setText(rs.getString(5));
                NphoneTxt.setText(rs.getString(6));
                NdobTxt.setText(rs.getString(7));
                NjoinDateTxt.setText(rs.getString(8));
                NageTxt.setText(rs.getString(9));
                NmgrIDTxt.setText(rs.getString(10));
                NgradeIdTxt.setText(rs.getString(11));
                NdeptIdTxt.setText(rs.getString(12));

            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
        MyConn.close();
    }

    public void updateBtFunc() throws SQLException, ClassNotFoundException {

        if(priv.equals("AD"))
            adminUpdate();
        else
            mgrEmpUpdate();


    }
    //    G11/3, St101, Flat 39C Apartment 2
//    giggaman@yahoo.mail
//      030092929292
    public void adminUpdate()  throws SQLException, ClassNotFoundException {
        if(idTxt.getText().isEmpty() || ssnTxt.getText().isEmpty() || nameTxt.getText().isEmpty()
                ||addressTxt.getText().isEmpty()||emailTxt.getText().isEmpty()||phoneTxt.getText().isEmpty()
                ||dobTxt.getText().isEmpty()||joinDateTxt.getText().isEmpty()||ageTxt.getText().isEmpty()||
                gradeIdTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "One or more necessary fields are empty! Cannot Update");
            return;
        }




        MyConn = SqlConnect.ConnectDB();

        int id;
        if(idTxt.getText().isEmpty())
            id = 1;
        else
            id= Integer.parseInt(idTxt.getText());


        int ssn;
        if(ssnTxt.getText().isEmpty())
            ssn = 0;
        else
            ssn= Integer.parseInt(ssnTxt.getText());

        String name = nameTxt.getText();
        String addr = addressTxt.getText();
        String email = emailTxt.getText();
        String ph = phoneTxt.getText();
        String dob = dobTxt.getText();
        String joinDate = joinDateTxt.getText();

        int age;
        if(ageTxt.getText().isEmpty())
            age = 0;
        else
            age = Integer.parseInt(ageTxt.getText());

        int mgID;
        if(mgrIDTxt.getText().isEmpty())
            mgID = 1;
        else
            mgID= Integer.parseInt(mgrIDTxt.getText());

        int gID;
        if(gradeIdTxt.getText().isEmpty())
            gID = 3;
        else
            gID= Integer.parseInt(gradeIdTxt.getText());


        int dId;
        if(deptIdTxt.getText().isEmpty())
            dId = 1;
        else
            dId= Integer.parseInt(deptIdTxt.getText());

        try{
            Statement ps = MyConn.createStatement();
            ps.executeUpdate("UPDATE employee\n" +
                    "SET emp_id = "+id+", SSN = "+ssn+", emp_name = '"+name+"', email = '"+email+"', phone = '"+ph+"',\n" +
                    "    dob = '"+dob+"', joining_date = '"+joinDate+"', age = "+age+", mgr_emp_id = "+mgID+", grade_id = "+gID+", dept_id = "+dId+"\n" +
                    "WHERE emp_id = "+curr_id+";");

            JOptionPane.showMessageDialog(null, "Data Updated!");
            //updateBtFunc();
            MyConn.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

    }

    public void mgrEmpUpdate()  throws SQLException, ClassNotFoundException{

        if(addressTxt.getText().isEmpty() || emailTxt.getText().isEmpty() || phoneTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "One or more necessary fields are empty! Cannot Update");
            return;
        }


        String addr = addressTxt.getText();
        String email = emailTxt.getText();
        String ph = phoneTxt.getText();

        //Class.forName("com.mysql.jdbc.Driver");
        MyConn = SqlConnect.ConnectDB();
        //DriverManager.getConnection("jdbc:mysql://localhost:3306/employeepayroll", "root", "Trecool123!");

        try{
            ps = MyConn.prepareStatement("UPDATE employee\n" +
                    "SET address ='"+addr +"', email = '"+email+"' , phone = '"+ph+"'"+
                    "WHERE emp_id = "+curr_id+";");

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated!");
            // updateBtFunc();
            MyConn.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

    }

    public void returnBtFunc() throws IOException {
        if(priv=="EMP"){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
            Parent root = loader.load();
            Stage window = (Stage)retBt.getScene().getWindow();
            window.setScene(new Scene(root, 950,600));
            window.setResizable(false);
        }else if(priv=="MGR"){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
            Parent root = loader.load();
            Stage window = (Stage)retBt.getScene().getWindow();
            window.setScene(new Scene(root, 950,600));
            window.setResizable(false);
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
            Parent root = loader.load();
            Stage window = (Stage)retBt.getScene().getWindow();
            window.setScene(new Scene(root, 950,600));
            window.setResizable(false);
        }

    }


}
