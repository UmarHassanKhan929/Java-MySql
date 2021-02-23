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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class ManagerPanelController implements Initializable {

    @FXML
    private Text time;
    @FXML
    private Button logoutBt;

    @FXML
    private Button edit_prsnl_info;

    @FXML
    private Button gnrt_ps_btn;

    @FXML
    private Button loan_btn;

    @FXML
    private Button chk_dept_btn;

    @FXML
    private Button mng_emp_btn;

    public void CheckDepartmentInfo()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerDepartmentInfo.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)chk_dept_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void ManageEmployeesInDept() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeInDepartment.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)mng_emp_btn.getScene().getWindow();
        window.setScene(new Scene(root, 988,640));
        window.setResizable(false);
    }
    public void EditPersonalInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPersonalInfo.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)edit_prsnl_info.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void RequestLoan() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeLoanPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)loan_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    public void GeneratePayslip() throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePayslipPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)gnrt_ps_btn.getScene().getWindow();
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

    @Override
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
}
