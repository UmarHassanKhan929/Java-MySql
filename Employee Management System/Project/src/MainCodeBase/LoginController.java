package MainCodeBase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView ;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;




public class LoginController implements Initializable {

    public static String id = "1";
    public static String priv = "EMP";

    @FXML
    private Button loginBt;

    @FXML
    private Button resetBt;

    @FXML
    private PasswordField pass_field_btn;

    @FXML
    private TextField userText;

    @FXML
    private ComboBox<String> status_drp_btn;

    int index =-1;
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        status_drp_btn.getItems().addAll("Employee","Manager","Admin");
    }




    public void loginBtCode(){
        try{
            conn =  SqlConnect.ConnectDB();
            String status = status_drp_btn.getValue();
            if(status=="Employee"){
                ps = conn.prepareStatement("SELECT * FROM user WHERE user.username = "+"\""+userText.getText()+"\""+" AND user.password = "+"\""+pass_field_btn.getText()+"\""+" AND privelege = \"EMP\"");
                rs = ps.executeQuery();

                if(rs.next()){
                    id=rs.getString("emp_id");
                    priv = "EMP";
                    //==================
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();
                    //===================
                    //JOptionPane.showMessageDialog(null,"Login Successful");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
                    Parent root = loader.load();
                    Stage window = (Stage)loginBt.getScene().getWindow();
                    window.setScene(new Scene(root, 950,600));
                    window.setResizable(false);
                }else{
                    //System.out.println("record  not found");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Credentials, Try Again");
                    alert.showAndWait();
                    //JOptionPane.showMessageDialog(null,"Incorrect Credentials, Login Failed, Try Again");
                }
            }else if(status=="Admin"){
                ps = conn.prepareStatement("SELECT * FROM user WHERE user.username = "+"\""+userText.getText()+"\""+" AND user.password = "+"\""+pass_field_btn.getText()+"\""+" AND privelege = \"AD\"");
                rs = ps.executeQuery();

                if(rs.next()){
                    id=rs.getString("emp_id");
                    priv = "AD";
                    //JOptionPane.showMessageDialog(null,"Login Successful");
                    //==================
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();
                    //===================
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
                    Parent root = loader.load();
                    Stage window = (Stage)loginBt.getScene().getWindow();
                    window.setScene(new Scene(root, 960,612));
                    window.setResizable(false);
                }else{
                    //System.out.println("record  not found");
                    //JOptionPane.showMessageDialog(null,"Incorrent Credentials, Login Failed, Try Again");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Credentials, Try Again");
                    alert.showAndWait();
                }
            }else if(status=="Manager"){
                ps = conn.prepareStatement("SELECT * FROM user WHERE user.username = "+"\""+userText.getText()+"\""+" AND user.password = "+"\""+pass_field_btn.getText()+"\""+" AND privelege = \"MGR\"");
                rs = ps.executeQuery();

                if(rs.next()){
                    id=rs.getString("emp_id");
                    priv = "MGR";
                    //==================
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();
                    //===================
                    //JOptionPane.showMessageDialog(null,"Login Successful");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
                    Parent root = loader.load();
                    Stage window = (Stage)loginBt.getScene().getWindow();
                    window.setScene(new Scene(root, 950,600));
                    window.setResizable(false);
                }else{
                    //System.out.println("record  not found");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Credentials, Try Again");
                    alert.showAndWait();
                    //JOptionPane.showMessageDialog(null,"Incorrent Credentials, Login Failed, Try Again");
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error ");
                alert.setHeaderText(null);
                alert.setContentText("Are you ze imposta ?");
                alert.showAndWait();
                //JOptionPane.showMessageDialog(null,"Are you sure ze IMPOSTA ???");
            }
            conn.close();
        }catch(Exception e){
            //System.out.println("query wrong");
            //e.printStackTrace();
            //JOptionPane.showMessageDialog(null,e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

    }
    public void ClearTextFieldsInLogin(){
        userText.clear();
        pass_field_btn.clear();
    }

    static void returnBtFunc(Button bt) throws IOException {
        FXMLLoader loader;
        boolean adm = false;
        if(LoginController.priv.equals("AD")) {
            loader = new FXMLLoader(LoginController.class.getResource("AdminPanel.fxml"));
            adm = true;
        }
        else if (LoginController.priv.equals("MGR"))
            return;
        else if(LoginController.priv.equals("EMP"))
            loader = new FXMLLoader(LoginController.class.getResource("EmployeePanel.fxml"));
        else
            loader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)bt.getScene().getWindow();
        if(adm)
            window.setScene(new Scene(root, 960,612));
        else
            window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

}
