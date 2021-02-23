package MainCodeBase;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminLoanManageController implements Initializable {

    @FXML
    private TextField search_emp_ssn;

    @FXML
    private Button search_emp_btn;

    @FXML
    private TextField emp_id_fld;

    @FXML
    private TextField emp_name_fld;

    @FXML
    private Button return_loan_btn;

    @FXML
    private TextField return_loan_emp;

    @FXML
    private TextField emp_ssn_fld;

    @FXML
    private TextField emp_email_fld;

    @FXML
    private TextField emp_phone_fld;

    @FXML
    private TextField emp_age_fld;

    @FXML
    private TextField emp_curr_loan_fld;

    @FXML
    private TextField emp_dpt_fld;

    @FXML
    private Button Generate_loan_rp_btn;

    @FXML
    private Button go_back_btn;

    String ssn;
    int index =-1;
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    String ID;

    public void SearchEmpSSN(){
        ssn  = search_emp_ssn.getText();
        if(ssn.isEmpty()){
            JOptionPane.showMessageDialog(null,"Are ya dum dum ?");
        }else{
            conn = SqlConnect.ConnectDB();
            try {
                ps = conn.prepareStatement("SELECT e.emp_id,e.emp_name,e.ssn,e.email,e.phone,e.age,dpt.dept_name,ded.loan FROM employee e, deduction ded, department dpt WHERE e.ssn = "+ssn+" AND e.emp_id = ded.emp_id AND e.dept_id=dpt.dept_id");
                rs = ps.executeQuery();

                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Employee found");
                    emp_id_fld.setText(rs.getString(1));
                    emp_name_fld.setText(rs.getString(2));
                    emp_ssn_fld.setText(rs.getString(3));
                    emp_email_fld.setText(rs.getString(4));
                    emp_phone_fld.setText(rs.getString(5));
                    emp_age_fld.setText(rs.getString(6));
                    emp_curr_loan_fld.setText(rs.getString(8));
                    emp_dpt_fld.setText(rs.getString(7));
                    ID = rs.getString(1);
                    //ssn = rs.getString(3);
                }else{
                    JOptionPane.showMessageDialog(null,"Employee not found, Check again");

                }
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }

    public void LoanReturnMethod(){
        String loan = return_loan_emp.getText();
        if(loan.isEmpty()){
            JOptionPane.showMessageDialog(null,"invlaid arguments");
        }else if(Integer.parseInt(loan)<0){
            conn = SqlConnect.ConnectDB();
            try {
                ps = conn.prepareStatement("UPDATE DEDUCTION SET LOAN = loan "+loan+" WHERE EMP_ID = "+ID);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"Loan returned");
                ps = conn.prepareStatement("SELECT e.emp_id,e.emp_name,e.ssn,e.email,e.phone,e.age,dpt.dept_name,ded.loan FROM employee e, deduction ded, department dpt WHERE e.ssn = "+ssn+" AND e.emp_id = ded.emp_id AND e.dept_id=dpt.dept_id");
                rs = ps.executeQuery();
                if(rs.next()){
                    emp_id_fld.setText(rs.getString(1));
                    emp_name_fld.setText(rs.getString(2));
                    emp_ssn_fld.setText(rs.getString(3));
                    emp_email_fld.setText(rs.getString(4));
                    emp_phone_fld.setText(rs.getString(5));
                    emp_age_fld.setText(rs.getString(6));
                    emp_curr_loan_fld.setText(rs.getString(8));
                    emp_dpt_fld.setText(rs.getString(7));
                }
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null,"invlaid arguments");
        }
    }

    public void GeneratePendingLoadReport() throws SQLException, FileNotFoundException, DocumentException {
        JFileChooser dialog = new JFileChooser();
        dialog.setSelectedFile(new File("Employees Report.pdf"));
        int dialogResult = dialog.showSaveDialog(null);
        if (dialogResult==JFileChooser.APPROVE_OPTION) {
            String filePath = dialog.getSelectedFile().getPath();
            conn = SqlConnect.ConnectDB();
            ps = conn.prepareStatement("SELECT e.ssn,e.emp_name,e.phone,de.dept_name,g.grade_name,d.loan FROM employee e, deduction d,department de, gradescale g where e.emp_id=d.emp_id and e.dept_id=de.dept_id and e.grade_id=g.grade_id");
            rs = ps.executeQuery();
            Document myDocument = new Document();
            PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath ));
            PdfPTable table = new PdfPTable(6);
            myDocument.open();

            float[] columnWidths = new float[] {3,7,7,7,7,5};
            table.setWidths(columnWidths);
            table.setWidthPercentage(100); //set table width to 100%

            myDocument.add(new Paragraph("Pending Loans", FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD )));
            LocalDate date = LocalDate.now();
            myDocument.add(new Paragraph(date.toString()));

            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

            table.addCell(new PdfPCell(new Paragraph("SSN",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("Name",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("Phone",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("Department",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("Grade",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));
            table.addCell(new PdfPCell(new Paragraph("Loan",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD))));

            int net=0;
            while(rs.next()){
                table.addCell(new PdfPCell(new Paragraph(rs.getString(1),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph(rs.getString(2),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph(rs.getString(3),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph(rs.getString(4),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph(rs.getString(5),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                table.addCell(new PdfPCell(new Paragraph(rs.getString(6),FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD))));
                net+=Integer.parseInt(rs.getString(6));
            }

            myDocument.add(table);
            myDocument.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            Paragraph pp = new Paragraph("Total Loan pending to be collected : "+net+" PKR", FontFactory.getFont(FontFactory.TIMES_BOLD,20, Font.BOLD ));
            pp.setAlignment(Element.ALIGN_RIGHT);
            myDocument.add(pp);

            myDocument.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Loan RP generated successfully");
            alert.showAndWait();

            rs.close();
            ps.close();
            conn.close();
            //JOptionPane.showMessageDialog(null,"Report was successfully generated");
        }
    }

    public void GoBackBt() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)go_back_btn.getScene().getWindow();
        window.setScene(new Scene(root, 950,600));
        window.setResizable(false);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emp_id_fld.setEditable(false);
        emp_name_fld.setEditable(false);
        emp_ssn_fld.setEditable(false);
        emp_email_fld.setEditable(false);
        emp_phone_fld.setEditable(false);
        emp_age_fld.setEditable(false);
        emp_curr_loan_fld.setEditable(false);
        emp_dpt_fld.setEditable(false);
    }
}
