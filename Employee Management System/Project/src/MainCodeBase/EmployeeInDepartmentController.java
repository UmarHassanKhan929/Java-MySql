package MainCodeBase;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.jar.JarOutputStream;

public class EmployeeInDepartmentController implements Initializable {

    @FXML
    private Button retBt;

    @FXML
    private TableView<EmpDeets> view;

    @FXML
    private TableColumn<EmpDeets, Integer> col_id;

    @FXML
    private TableColumn<EmpDeets, String> col_name;

    @FXML
    private TableColumn<EmpDeets, String > col_addr;

    @FXML
    private TableColumn<EmpDeets, String > col_email;

    @FXML
    private TableColumn<EmpDeets, String > col_ph;

    @FXML
    private TableColumn<EmpDeets, String > col_join;

    @FXML
    private TableColumn<EmpDeets, Integer> col_age;

    @FXML
    private TableColumn<EmpDeets, Integer> col_mgrID;

    @FXML
    private TableColumn<EmpDeets, Integer> col_gradeID;

    @FXML
    private TableColumn<EmpDeets, Integer> col_deptID;

    @FXML
    private TableView<EmpDeets> view1;

    @FXML
    private TableColumn<EmpDeets, Integer> col_id1;

    @FXML
    private TableColumn<EmpDeets, String> col_name1;

    @FXML
    private TableColumn<EmpDeets, String > col_addr1;

    @FXML
    private TableColumn<EmpDeets, String> col_email1;

    @FXML
    private TableColumn<EmpDeets, String> col_ph1;

    @FXML
    private TableColumn<EmpDeets, String> col_join1;

    @FXML
    private TableColumn<EmpDeets, Integer> col_age1;

    @FXML
    private TableColumn<EmpDeets, Integer> col_mgrID1;

    @FXML
    private TableColumn<EmpDeets, Integer> col_gradeID1;

    @FXML
    private TableColumn<EmpDeets,   Integer> col_deptID1;

    @FXML
    private TextField nameTxtN;

    @FXML
    private TextField idTxtN;

    @FXML
    private TextField emailTxtN;

    @FXML
    private TextField phTxtN;

    @FXML
    private TextField ageTxtN;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField phTxt;

    @FXML
    private TextField ageTxt;

    @FXML
    private Button removeBt;

    @FXML
    private Button addBt;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    ObservableList<EmpDeets> currEm;
    ObservableList<EmpDeets> noDeptEm;

    String status;
    String priv;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTables();
    }


    public void getSelectedView(){
        index =  view.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        else{
            idTxt.setText(col_id.getCellData(index).toString());
            nameTxt.setText(col_name.getCellData(index).toString());
            emailTxt.setText(col_email.getCellData(index).toString());
            phTxt.setText(col_ph.getCellData(index).toString());
            ageTxt.setText(col_age.getCellData(index).toString());
        }
    }

    public void getSelectedView1(){
        index =  view1.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        else{
            idTxtN.setText(col_id1.getCellData(index).toString());
            nameTxtN.setText(col_name1.getCellData(index).toString());
            emailTxtN.setText(col_email1.getCellData(index).toString());
            phTxtN.setText(col_ph1.getCellData(index).toString());
            ageTxtN.setText(col_age1.getCellData(index).toString());
        }
    }

    public void returnFunc() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)retBt.getScene().getWindow();
        window.setScene(new Scene(root, 987,600));
        window.setResizable(false);
    }


    public void fillTables(){
        col_id.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("name"));
        col_addr.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("address"));
        col_email.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("email"));
        col_ph.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("phone"));
        col_join.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("joinDate"));
        col_age.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("age"));
        col_mgrID.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("mgrId"));
        col_gradeID.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("gradeId"));
        col_deptID.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("deptId"));

        currEm = SqlConnect.getOnlyEmployeeInDepartmentDetail();
        view.setItems(currEm);

        col_id1.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("id"));
        col_name1.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("name"));
        col_addr1.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("address"));
        col_email1.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("email"));
        col_ph1.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("phone"));
        col_join1.setCellValueFactory(new PropertyValueFactory<EmpDeets, String>("joinDate"));
        col_age1.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("age"));
        col_mgrID1.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("mgrId"));
        col_gradeID1.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("gradeId"));
        col_deptID1.setCellValueFactory(new PropertyValueFactory<EmpDeets, Integer>("deptId"));

        noDeptEm = SqlConnect.getOnlyEmployeeInNoDepartmentDetail();
        view1.setItems(noDeptEm);

    }

    public void removeBtFunc(){

        if(idTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "ID Field is Empty!");
            return;
        }

        try{

            conn = SqlConnect.ConnectDB();

            String eid = idTxt.getText();
            String sql = "UPDATE employee SET dept_ID  = NULL WHERE emp_id ="+eid;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            sql = "UPDATE employee SET mgr_emp_id = NULL WHERE emp_id ="+eid;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employee has been removed from Department!");
            fillTables();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addEmpBtFunc(){

        int newGrade = 0;

        if(idTxtN.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "ID Field is Empty!");
            return;
        }

        try{

            conn = SqlConnect.ConnectDB();




            String deptid = "SELECT dept_id FROM employee WHERE emp_id = "+LoginController.id;

            ps = conn.prepareStatement(deptid);
            rs = ps.executeQuery();
            if(rs.next())
                deptid = rs.getString(1);


            String sql = "SELECT g.grade_id, g.grade_name FROM gradescale g, employee e WHERE e.emp_id = "+LoginController.id+" AND e.dept_id = g.dept_id AND g.grade_name = \"Employee\"\n";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
                newGrade = rs.getInt(1);

            String eid = idTxtN.getText();
             sql = "UPDATE employee SET dept_ID  = "+deptid+" WHERE emp_id ="+eid;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            sql = "UPDATE employee SET mgr_emp_id = "+LoginController.id +" WHERE emp_id ="+eid;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            sql = "UPDATE employee SET grade_id = "+newGrade +" WHERE emp_id ="+eid;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employee has been added to Department!");
            fillTables();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
