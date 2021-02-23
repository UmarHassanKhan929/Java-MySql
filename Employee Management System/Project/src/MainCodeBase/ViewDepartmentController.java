package MainCodeBase;

//import com.mysql.jdbc.Connection;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewDepartmentController implements Initializable {

    @FXML
    private Button retBt;

    @FXML
    private TextField deptIDTxt;

    @FXML
    private Button updateDeptBt;

    @FXML
    private Button addDeptBt;

    @FXML
    private Button delDeptBt;

    @FXML
    private TextField deptNameTxt;

    @FXML
    private Button gnt_dpt_info_btn;


    @FXML
    private TableView<DeptDeets> deptTable;

    @FXML
    private TableColumn<DeptDeets, Integer> tableId;

    @FXML
    private TableColumn<DeptDeets, String> tableName;

    ObservableList<DeptDeets> listEm;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public void showDeptDetails(){

        tableId.setCellValueFactory(new PropertyValueFactory<DeptDeets, Integer>("deptIdDeet"));
        tableName.setCellValueFactory(new PropertyValueFactory<DeptDeets, String>("deptNameDeet"));


        listEm = SqlConnect.getDeptDetail();
        deptTable.setItems(listEm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showDeptDetails();
    }

    public void addDept(){

        if(deptIDTxt.getText().isEmpty() || deptNameTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necessary fields are empty!");
            return;
        }

        conn = (Connection) SqlConnect.ConnectDB();
        String sql= "INSERT INTO department (dept_id, dept_name) VALUES \n" +
                "(?, ?)";

        try{

            ps = conn.prepareStatement(sql);

            ps.setString(1, deptIDTxt.getText());
            ps.setString(2, deptNameTxt.getText());

            ps.execute();
            JOptionPane.showMessageDialog(null, "Department "+deptNameTxt.getText()+" has been added!");
            showDeptDetails();
            conn.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

    }


    public void delDept(){
        conn = (Connection) SqlConnect.ConnectDB();
        String sql = "DELETE FROM department where dept_id = ?";

        try{

            ps= conn.prepareStatement(sql);
            ps.setString(1,deptIDTxt.getText());
            String name = deptNameTxt.getText();
            ps.execute();

            JOptionPane.showMessageDialog(null,"Employee "+name+ " has been deleted!");
            showDeptDetails();

            conn.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }


    }

    public void updateDept(){


        try{

            if(deptIDTxt.getText().isEmpty() || deptNameTxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Necessary fields are empty!");
                return;
            }

            conn = (Connection) SqlConnect.ConnectDB();
            String deptIDs = deptIDTxt.getText();
            String deptNames = deptNameTxt.getText();

            String sql = "UPDATE department SET dept_id = "+deptIDs+", dept_name = '"+deptNames+"' WHERE dept_id ="+deptIDs;

            ps = conn.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Details of "+deptNames+" has been updated!");
            showDeptDetails();
            conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void getSelection(){
        index =  deptTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        else{
            deptIDTxt.setText(tableId.getCellData(index).toString());
            deptNameTxt.setText(tableName.getCellData(index).toString());
        }
    }

    public void GenerateDeptInfoBTN() throws FileNotFoundException, DocumentException {
        conn = SqlConnect.ConnectDB();

        JFileChooser dialog = new JFileChooser();
        dialog.setSelectedFile(new File("Departments Information.pdf"));
        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult==JFileChooser.APPROVE_OPTION) {
            String filePath = dialog.getSelectedFile().getPath();
            try {
                ps = conn.prepareStatement("select d.dept_name, count(*) from department d, employee e where e.dept_id=d.dept_id group by e.dept_id");
                rs = ps.executeQuery();

                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath ));
                PdfPTable table = new PdfPTable(2);
                myDocument.open();

                float[] columnWidths = new float[] {7,5};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100); //set table width to 100%

                myDocument.add(new Paragraph("Employee Count in Department", FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD )));
                LocalDate date = LocalDate.now();
                myDocument.add(new Paragraph(date.toString()));
                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                table.addCell(new PdfPCell(new Paragraph("Department Name",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph("Employees",FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));


                while(rs.next()){
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                    table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                }

                myDocument.add(table);
                myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                myDocument.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Department Report generated successfully");
                alert.showAndWait();

                //JOptionPane.showMessageDialog(null,"Report was successfully generated");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {
                try{
                    rs.close();
                    ps.close();
                    conn.close();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }

        }


    }

    public void retBtFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)retBt.getScene().getWindow();
        window.setScene(new Scene(root, 960,612));
        window.setResizable(false);

    }
}
