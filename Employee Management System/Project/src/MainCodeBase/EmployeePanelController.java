package MainCodeBase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class EmployeePanelController implements Initializable {

    @FXML
    private Button prsn_info_btn;

    @FXML
    private Button edit_dtls_btn;

    @FXML
    private Button gnt_pay_btn;

    @FXML
    private Button chk_dept_info_btn;

    @FXML
    private Button loan_info_btn;

    @FXML
    private Button prsn_logindet_btn;

    @FXML
    private Text id_txt;

    @FXML
    private Button logoutBt;

    @FXML
    private Text time;

    int index =-1;
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String curr_id;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = SqlConnect.ConnectDB();
        curr_id  = LoginController.id;
        try {
            ps = conn.prepareStatement("SELECT emp_name FROM employee,user WHERE user.emp_id = " +curr_id+ " AND user.emp_id = employee.emp_id ");
            rs = ps.executeQuery();
            if(rs.next()){
                id_txt.setText(rs.getString(1));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clock();
    }

    public void clock(){
        Thread ck= new Thread(){
            public void run(){
                try {
                    while(true) {
                        Calendar cal = new GregorianCalendar();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);

                        int sec = cal.get(Calendar.SECOND);
                        int min = cal.get(Calendar.MINUTE);
                        int hr = cal.get(Calendar.HOUR);

                        if(min<10 && sec<10)
                            time.setText("Date: " + day + "/" + month+1 + "/" + year + "      \nTime:" + hr + ":0" + min + ":0" + sec);
                        else if(min<10)
                            time.setText("Date: " + day + "/" + month+1 + "/" + year + "      \nTime:" + hr + ":0" + min + ":" + sec);
                        else if(sec<10)
                            time.setText("Date: " + day + "/" + month+1 + "/" + year + "      \nTime:" + hr + ":" + min + ":0" + sec);
                        else
                            time.setText("Date: " + day + "/" + month+1 + "/" + year + "      \nTime:" + hr + ":" + min + ":" + sec);

                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ck.setDaemon(true);
        ck.start();
    }

    public void viewInfoBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePersonalInfo.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)prsn_info_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void loginDetailBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditLoginDetails.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)prsn_logindet_btn.getScene().getWindow();
        window.setScene(new Scene(root, 900,600));
        window.setResizable(false);
    }

    public void editPersonalBtCode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPersonalInfo.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)edit_dtls_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void viewLoanBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeLoanPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)loan_info_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void viewEmpDptDeets() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FromEmployeePanelDepartmentInfo.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)chk_dept_info_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void GeneratePayslip() throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePayslipPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)gnt_pay_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,650));
        window.setResizable(false);
    }

    public void setLogoutBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }
}
