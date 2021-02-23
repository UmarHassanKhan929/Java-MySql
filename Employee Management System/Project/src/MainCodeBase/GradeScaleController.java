package MainCodeBase;

//import com.mysql.jdbc.Connection;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class GradeScaleController implements Initializable {

    @FXML
    private Button retBt;

    @FXML
    private TextField gIDTxt;

    @FXML
    private Button updateDeptBt;

    @FXML
    private Button addGradeBt;

    @FXML
    private Button delGradeBt;

    @FXML
    private TextField gNameTxt;

    @FXML
    private TableView<GradeScaleDeets> gradeTable;

    @FXML
    private TableColumn<GradeScaleDeets, Integer> tableId;

    @FXML
    private TableColumn<GradeScaleDeets, String> tableGrade;

    @FXML
    private TableColumn<GradeScaleDeets, Integer> tableDID;

    @FXML
    private TableColumn<GradeScaleDeets, Integer> tableBase;

    @FXML
    private TableColumn<GradeScaleDeets, Integer> tableTravel;

    @FXML
    private TableColumn<GradeScaleDeets, Integer> tableMedic;

    @FXML
    private TableColumn<GradeScaleDeets, Integer> TableHouse;

    @FXML
    private TextField dIDTxt;

    @FXML
    private TextField baseTxt;

    @FXML
    private TextField travelTxt;

    @FXML
    private TextField medicalTxt;

    @FXML
    private TextField houseTxt;

    @FXML
    private ComboBox<String> deptBox;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    String deptIdCmbBox = "none";

    ObservableList<GradeScaleDeets> listEm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateGrade();
        try {
            fillComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void fillComboBox() throws SQLException {
        conn = SqlConnect.ConnectDB();
        String sql = "SELECT dept_name FROM department";
        String dept;
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        ObservableList<String> deptList = FXCollections.observableArrayList();
        deptList.add("NULL");
        while(rs.next()){
            dept = rs.getString(1);
            deptList.add(dept);
        }
        deptBox.setItems(deptList);
        conn.close();
    }

    public void setDeptID() throws SQLException {
        conn = SqlConnect.ConnectDB();

            String sql = "SELECT dept_id FROM department WHERE dept_name = '"+deptBox.getValue().toString()+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if(rs.next())
                dIDTxt.setText(rs.getString(1));


        conn.close();
    }

    @FXML
    void addGrade() {

        if(checkFields()){
            JOptionPane.showMessageDialog(null, "Necessary fields are empty!");
            return;
        }

        conn = (Connection) SqlConnect.ConnectDB();
        String sql= "INSERT INTO gradescale (grade_id, grade_name, dept_id, basepay, travelAllo, medicAllo, houseRentAllo) VALUES \n" +
                "(?, ?,?, ?,?, ?,?)";



        try{

            ps = conn.prepareStatement(sql);

            ps.setString(1,gIDTxt.getText());
            ps.setString(2,gNameTxt.getText());
            ps.setString(3,dIDTxt.getText());
            ps.setString(4,baseTxt.getText());
            ps.setString(5,travelTxt.getText());
            ps.setString(6,medicalTxt.getText());
            ps.setString(7, houseTxt.getText());

            ps.execute();
            JOptionPane.showMessageDialog(null, "Department "+gNameTxt.getText()+" has been added!");
            updateGrade();
            conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

    }

    @FXML
    void delGrade() {

        conn = (Connection) SqlConnect.ConnectDB();
        String sql = "DELETE FROM gradescale where grade_id = ?";

        try{

            ps= conn.prepareStatement(sql);
            ps.setString(1,gIDTxt.getText());
            String name = gNameTxt.getText();
            ps.execute();

            JOptionPane.showMessageDialog(null,"Grade Scale "+name+ " has been deleted!");
            updateGrade();

            conn.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

    }

    @FXML
    void getSelection() {

        index =  gradeTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        else{
            gIDTxt.setText(tableId.getCellData(index).toString());
            gNameTxt.setText(tableGrade.getCellData(index).toString());
            dIDTxt.setText(tableDID.getCellData(index).toString());
            baseTxt.setText(tableBase.getCellData(index).toString());
            travelTxt.setText(tableTravel.getCellData(index).toString());
            medicalTxt.setText(tableMedic.getCellData(index).toString());
            houseTxt.setText(TableHouse.getCellData(index).toString());
            deptBox.getSelectionModel().select(Integer.parseInt(dIDTxt.getText()));
        }

    }

    @FXML
    void retBtFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)retBt.getScene().getWindow();
        window.setScene(new Scene(root, 960,612));
        window.setResizable(false);

    }

    @FXML
    void updateGradeSQL(){

        try{

            if(checkFields()) {
                JOptionPane.showMessageDialog(null, "Necessary fields are empty!");
                return;
            }

            conn = (Connection) SqlConnect.ConnectDB();
            String gradeIDs = gIDTxt.getText();
            String gradeNames = gNameTxt.getText();
            String deptIDs = dIDTxt.getText();
            String baseS = baseTxt.getText();
            String travelS = travelTxt.getText();
            String medicS = medicalTxt.getText();
            String houseS = houseTxt.getText();



            String sql = "UPDATE gradescale SET grade_id = "+gradeIDs+", grade_name = '"+gradeNames+"', dept_id = "+deptIDs+", basepay = "+baseS+", travelAllo = "+travelS+", medicAllo = "+medicS+" , houseRentAllo = "+houseS+" WHERE grade_id ="+gradeIDs;

            ps = conn.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Details of "+gradeNames+" has been updated!");
            updateGrade();
            conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }


    //Updates text fields...not SQL values
    @FXML
    void updateGrade() {

        tableId.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, Integer>("gID"));
        tableGrade.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, String>("gName"));
        tableDID.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, Integer>("dId"));
        tableBase.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, Integer>("base"));
        tableTravel.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, Integer>("travel"));
        tableMedic.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, Integer>("medic"));
        TableHouse.setCellValueFactory(new PropertyValueFactory<GradeScaleDeets, Integer>("house"));



        listEm = SqlConnect.getGradeScaleDetail();
        gradeTable.setItems(listEm);

    }



    public boolean checkFields(){

        return ((gIDTxt.getText().isEmpty() || gNameTxt.getText().isEmpty() || dIDTxt.getText().isEmpty()
        || baseTxt.getText().isEmpty() || travelTxt.getText().isEmpty() || medicalTxt.getText().isEmpty() || houseTxt.getText().isEmpty()));

    }
}
