package MainCodeBase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeLoanPanelController implements Initializable {
    @FXML
    private Button go_back_btn;

    @FXML
    private TextField curr_loan_fld;

    @FXML
    private TextField req_loan_fld;

    @FXML
    private Button cnfrm_load_req_btn;

    int index =-1;
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String ID = LoginController.id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        curr_loan_fld.setEditable(false);
        conn = SqlConnect.ConnectDB();
        try {
            ps = conn.prepareStatement("SELECT * FROM deduction d WHERE d.emp_id ="+ID);
            rs = ps.executeQuery();
            if(rs.next()){
                curr_loan_fld.setText(rs.getString(2));
            }else{
                ps = conn.prepareStatement("INSERT INTO deduction(emp_id,loan) values ("+ID+",0)");
                ps.executeUpdate();
            }

            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void RequestLoanCnfrm(){
        if(req_loan_fld.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"I said NO HANKY PANKY");
        }else{
            conn = SqlConnect.ConnectDB();
            int loan = Integer.parseInt(req_loan_fld.getText());
            if(loan<0){
                JOptionPane.showMessageDialog(null,"I said NO HANKY PANKY");
            }else{
                Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Sure want the loan ?");
                Optional<ButtonType> checksum = alert.showAndWait();
                if(checksum.get() == ButtonType.OK){

                    try {
                        //String ss = String.valueOf(loan);
                        ps = conn.prepareStatement("UPDATE DEDUCTION SET LOAN = loan + "+loan+" WHERE EMP_ID = "+ID);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Loan requested");
                        ps = conn.prepareStatement("SELECT * FROM deduction d WHERE d.emp_id ="+ID);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            curr_loan_fld.setText(rs.getString(2));
                        }
                        conn.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }



    }

    public void GoBackBt() throws IOException, SQLException {
        conn = SqlConnect.ConnectDB();
        ps = conn.prepareStatement("select u.privelege from employee e, user u where e.emp_id=u.emp_id and e.emp_id = "+"\""+ID+"\"");
        rs = ps.executeQuery();
        if(rs.next()){
            if(rs.getString(1).equals("MGR")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
                Parent root = loader.load();
                Stage window = (Stage)go_back_btn.getScene().getWindow();
                window.setScene(new Scene(root, 950,600));
                window.setResizable(false);
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
                Parent root = loader.load();
                Stage window = (Stage)go_back_btn.getScene().getWindow();
                window.setScene(new Scene(root, 950,600));
                window.setResizable(false);
            }
        }


    }

}
