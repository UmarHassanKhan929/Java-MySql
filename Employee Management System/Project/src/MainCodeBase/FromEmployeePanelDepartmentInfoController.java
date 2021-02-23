package MainCodeBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FromEmployeePanelDepartmentInfoController implements Initializable {
    @FXML
    private Button go_back_btn;

    @FXML
    private TextField name_fld;


    @FXML
    private TextField dpt_id_fld;

    @FXML
    private TextField dpt_name_fld;

    @FXML
    private TableView<DeptEmpDeets> dpt_emps;

    @FXML
    private TableColumn<DeptEmpDeets, String > col_name;

    @FXML
    private TableColumn<DeptEmpDeets, Integer> col_age;

    @FXML
    private TableColumn<DeptEmpDeets, Integer> col_email;

    @FXML
    private TableColumn<DeptEmpDeets, Integer> col_ph;

    ObservableList<DeptEmpDeets> listM;

    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String curr_id;
    int dept_id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = SqlConnect.ConnectDB();
        curr_id = LoginController.id;
        try {
            ps = conn.prepareStatement("select e.emp_name,d.dept_id,d.dept_name from employee e, department d where e.dept_id = d.dept_id and e.emp_id ="+curr_id);
            rs = ps.executeQuery();
            if(rs.next()){
                name_fld.setText(rs.getString(1));
                dpt_id_fld.setText(rs.getString(2));
                dpt_name_fld.setText(rs.getString(3));
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dept_id = Integer.parseInt(dpt_id_fld.getText());

        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("Age"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col_ph.setCellValueFactory(new PropertyValueFactory<>("Phone"));


        listM = getDeptData(dept_id);


        dpt_emps.setItems(listM);


    }

    public ObservableList<DeptEmpDeets> getDeptData(int did){
        conn = SqlConnect.ConnectDB();
        ObservableList<DeptEmpDeets> list = FXCollections.observableArrayList();
        try {
            ps = conn.prepareStatement("SELECT e.emp_name,e.age,e.email,e.phone from employee e,department d where e.dept_id = d.dept_id and d.dept_id = "+did);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new DeptEmpDeets(rs.getString(1),(rs.getString(3)),(rs.getString(4)),Integer.parseInt(rs.getString(2))));
            }
            conn.close();
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        return list;
    }



    public void GoBackBt() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)go_back_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

}
