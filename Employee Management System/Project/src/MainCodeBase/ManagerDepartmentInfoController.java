package MainCodeBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerDepartmentInfoController implements Initializable {
    @FXML
    private Button go_back_btn;
    @FXML
    private TextField name_fld;

    @FXML
    private TextField dpt_id_fld;

    @FXML
    private TextField dpt_name_fld;

    @FXML
    private TableView<ManagerDeptDeets> dpt_emps;

    @FXML
    private TableColumn<ManagerDeptDeets, String> col_name;

    @FXML
    private TableColumn<ManagerDeptDeets, Integer> col_age;

    @FXML
    private TableColumn<ManagerDeptDeets, String> col_email;

    @FXML
    private TableColumn<ManagerDeptDeets, String> col_ph;
    @FXML
    private TableColumn<ManagerDeptDeets, Integer> col_ad;

    @FXML
    private TableColumn<ManagerDeptDeets, Integer> col_lc;
    @FXML
    private TableColumn<ManagerDeptDeets, Integer> col_id;

    @FXML
    private TextField search_name_fld;

    @FXML
    private TextField curr_emp_name_fld;

    @FXML
    private TextField curr_emp_absent_days_fld;

    @FXML
    private TextField curr_emp_late_fld;

    @FXML
    private Button curr_emp_upd_btn;

    int index =-1;
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    int empID = Integer.parseInt(LoginController.id);
    int deptId;
    int selectedEmpID;
    String gradeID;
    String month;

    ObservableList<ManagerDeptDeets> listM;
    ObservableList<ManagerDeptDeets> listFiltered;

    public void GoBackBt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)go_back_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_fld.setEditable(false);
        dpt_id_fld.setEditable(false);
        dpt_name_fld.setEditable(false);

        conn = SqlConnect.ConnectDB();
        try {
            ps = conn.prepareStatement("select e.dept_id from employee e where e.emp_id = "+empID);
            rs = ps.executeQuery();
            if(rs.next()){
                deptId=Integer.parseInt(rs.getString(1));
            }

            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            col_ph.setCellValueFactory(new PropertyValueFactory<>("phone"));
            col_ad.setCellValueFactory(new PropertyValueFactory<>("numleaves"));
            col_lc.setCellValueFactory(new PropertyValueFactory<>("timeslate"));
            col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));

            listM = ShowEmployees();
            dpt_emps.setItems(listM);
            conn = SqlConnect.ConnectDB();
            ps = conn.prepareStatement("select * from employee e,department d where e.dept_id = d.dept_id and e.emp_id="+empID);
            rs = ps.executeQuery();
            if(rs.next()){
                name_fld.setText(rs.getString(3));
                dpt_id_fld.setText(rs.getString(12));
                dpt_name_fld.setText(rs.getString(14));
            }

            search_user();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public ObservableList<ManagerDeptDeets> ShowEmployees(){
        conn = SqlConnect.ConnectDB();
        ObservableList<ManagerDeptDeets> list = FXCollections.observableArrayList();
        try{
            ps = conn.prepareStatement("select e.emp_name,e.age,e.email,e.phone,ded.numleaves,ded.timesLate,e.emp_id from employee e,deduction ded where e.emp_id=ded.emp_id and e.dept_id = "+deptId);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new ManagerDeptDeets(rs.getString(1),Integer.parseInt(rs.getString(2)),rs.getString(3),rs.getString(4),Integer.parseInt(rs.getString(5)),Integer.parseInt(rs.getString(6)),Integer.parseInt(rs.getString(7))));
            }
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        return list;
    }

    @FXML
    void getSelected (MouseEvent event){
        index = dpt_emps.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        curr_emp_name_fld.setText(col_name.getCellData(index));
        curr_emp_absent_days_fld.setText(col_ad.getCellData(index).toString());
        curr_emp_late_fld.setText(col_lc.getCellData(index).toString());
        selectedEmpID = Integer.parseInt(col_id.getCellData(index).toString());
    }


    @FXML
    void search_user() {
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_ph.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_ad.setCellValueFactory(new PropertyValueFactory<>("numleaves"));
        col_lc.setCellValueFactory(new PropertyValueFactory<>("timeslate"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));

        listFiltered = ShowEmployees();
        dpt_emps.setItems(listFiltered);
        FilteredList<ManagerDeptDeets> filteredData = new FilteredList<>(listFiltered, b -> true);
        search_name_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches username
                }
                else
                    return false; // Does not match.
            });
        });
        SortedList<ManagerDeptDeets> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(dpt_emps.comparatorProperty());
        dpt_emps.setItems(sortedData);
    }

    public void UpdateDeductionDays() throws SQLException {
        conn = SqlConnect.ConnectDB();
        ps = conn.prepareStatement("update deduction set numleaves = numleaves + "+Integer.parseInt(curr_emp_absent_days_fld.getText())+", timeslate = timeslate + "+Integer.parseInt(curr_emp_late_fld.getText())+" where emp_id = "+selectedEmpID);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Updated!");
        conn.close();
    }


}
