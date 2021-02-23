package MainCodeBase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeController {


    @FXML
    private Button editBt, payslipBt, deptBt, loanBt, logoutBt;

    public void setLogoutBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }
}
