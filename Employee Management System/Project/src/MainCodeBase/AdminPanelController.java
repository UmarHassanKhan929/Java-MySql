package MainCodeBase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    @FXML
    private Button logoutBt;

    @FXML
    Button editPersonalBt;

    @FXML
    Button viewEmployeeBt;

    @FXML
    Button genPaySlipBt;

    @FXML
    Button viewDeptBt;

    @FXML
    Button viewGradeScaleBt;
    @FXML
    private Button emp_loan_info_btn;

    @FXML
    private Button upt_login_btn;

    @FXML
    private Button genPersonalPayslipBt;

    @FXML
    private Text time;

    @FXML
    private MenuBar menBar;

    @FXML
    private Menu tables;

    @FXML
    private MenuItem userMen;

    @FXML
    private MenuItem employeeMen;

    @FXML
    private MenuItem deptMen;

    @FXML
    private MenuItem gradeMen;

    @FXML
    private MenuItem payslipMen;

    @FXML
    private MenuItem deductionMen;

    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    public void editPersonalBtCode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPersonalInfo.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)editPersonalBt.getScene().getWindow();
        window.setScene(new Scene(root, 957,603));
        window.setResizable(false);
    }

    public void setLogoutBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void loginDetailBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditLoginDetails.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)upt_login_btn.getScene().getWindow();
        window.setScene(new Scene(root, 908,604));
        window.setResizable(false);
    }

    public void setViewEmployeeBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowEmployees.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 992,638));
        window.setResizable(false);
    }

    public void viewDeptBtFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDepartment.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 601,603));
        window.setResizable(false);
    }

    public void viewGradeScaleBtFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GradeScale.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 708,603));
        window.setResizable(false);

    }
    public void LoanManager() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLoanManage.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)emp_loan_info_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void genPaySlipBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPayslip.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)genPaySlipBt.getScene().getWindow();
        window.setScene(new Scene(root, 950,700));
        window.setResizable(false);
    }

    public void genPersonalPayslipBtFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePayslipPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)genPaySlipBt.getScene().getWindow();
        window.setScene(new Scene(root, 950,650));
        window.setResizable(false);

    }

    public void viewUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserTable.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)genPaySlipBt.getScene().getWindow();
        window.setScene(new Scene(root, 595,600));
        window.setResizable(false);
    }

    public void viewDeductionTable() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeductionTable.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)genPaySlipBt.getScene().getWindow();
        window.setScene(new Scene(root, 595,600));
        window.setResizable(false);
    }

}
