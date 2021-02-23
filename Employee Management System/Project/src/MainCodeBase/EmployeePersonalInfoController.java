package MainCodeBase;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeePersonalInfoController implements Initializable {
    @FXML
    private Button logoutBt;

    @FXML
    private TextField show_name_fld;

    @FXML
    private TextField show_EMPID_fld;

    @FXML
    private TextField show_address_fld;

    @FXML
    private TextField show_email_fld;

    @FXML
    private TextField show_phone_fld;

    @FXML
    private TextField show_dob_fld;

    @FXML
    private TextField show_jngdate_fld;

    @FXML
    private TextField show_age_fld;


    int index =-1;
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String curr_id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        curr_id = LoginController.id;
        show_name_fld.setEditable(false);
        show_EMPID_fld.setEditable(false);
        show_address_fld.setEditable(false);
        show_email_fld.setEditable(false);
        show_phone_fld.setEditable(false);
        show_dob_fld.setEditable(false);
        show_jngdate_fld.setEditable(false);
        show_age_fld.setEditable(false);

        conn = SqlConnect.ConnectDB();
        try {
            ps = conn.prepareStatement("SELECT * FROM employee e WHERE e.emp_id ="+ curr_id);
            rs = ps.executeQuery();
            if(rs.next()){
                show_name_fld.setText(rs.getString(3));
                show_EMPID_fld.setText(rs.getString(2));
                show_address_fld.setText(rs.getString(4));
                show_email_fld.setText(rs.getString(5));
                show_phone_fld.setText(rs.getString(6));
                show_dob_fld.setText(rs.getString(7));
                show_jngdate_fld.setText(rs.getString(8));
                show_age_fld.setText(rs.getString(9));
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void GoBackBt() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)logoutBt.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }
}
