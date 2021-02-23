package MainCodeBase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditLoginDetailController implements Initializable {

    @FXML
    private TextField userTxt;
    @FXML
    private PasswordField pass1Txt;
    @FXML
    private PasswordField pass2Txt;

    @FXML
    private TextField currPassTxt;

    @FXML
    private TextField show_name_fld;

    @FXML
    private Button retBt;
    @FXML
    private Button updateBt;

    Connection conn = SqlConnect.ConnectDB();
    ResultSet rs=null;
    PreparedStatement ps=null;
    String curr_id = LoginController.id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateBt.setVisible(false);
        conn = SqlConnect.ConnectDB();
        show_name_fld.setEditable(false);
        try {
            ps = conn.prepareStatement("Select emp_name from employee e where emp_id = "+curr_id);
            rs = ps.executeQuery();
            if(rs.next()){
                show_name_fld.setText(rs.getString(1));
            }

            conn.close();
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

    }

    public void updateBtFunc() throws ClassNotFoundException, SQLException {
        if(userTxt.getText().isEmpty() || pass1Txt.getText().isEmpty() || pass2Txt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("One or more Text Fields are Empty!");
            alert.showAndWait();
            return;
        }

        String uname = userTxt.getText();
        String p1 = pass1Txt.getText();
        String p2 = pass2Txt.getText();



        if(p1.length()<6){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Enter Password with at least 6 characters!");
            alert.showAndWait();
            return;
        }

        if(!(p1.equals(p2))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Passwords Do Not Match!");
            alert.showAndWait();
            return;
        }

        //  Class.forName("com.mysql.jdbc.Driver");
        // Connection MyConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeepayroll", "root", "Trecool123!");

        Connection MyConn = SqlConnect.ConnectDB();

        try{
            Statement ps = MyConn.createStatement();
            ps.executeUpdate("UPDATE user\n" +
                    "SET username = '"+uname+"' , password = '"+p1+"' WHERE emp_id ="+curr_id) ;

            JOptionPane.showMessageDialog(null, "Data Updated!");
            MyConn.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

    }

    public void returnBtFunc() throws IOException {

        LoginController.returnBtFunc(retBt);

    }

    public void currPassBtFunc() throws SQLException {

        String pass = currPassTxt.getText();



        String sql = "SELECT * FROM user WHERE password ='"+pass+"' AND emp_id = "+curr_id;

        conn = SqlConnect.ConnectDB();
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        if(rs.next()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Enter New Login Credentials");
            alert.showAndWait();
            setNewPass();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("Enter Correct Password");
            alert.showAndWait();
        }

        conn.close();
    }

    public void setNewPass(){
        userTxt.setEditable(true);
        pass1Txt.setEditable(true);
        pass2Txt.setEditable(true);
        updateBt.setVisible(true);
    }


}
