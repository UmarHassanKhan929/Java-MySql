package MainCodeBase;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DeductionTableController implements Initializable {

    @FXML
    private Button retBt;

    @FXML
    private TableView<DeductionDeets> deductionTable;

    @FXML
    private TableColumn<DeductionDeets, Integer> eidCol;

    @FXML
    private TableColumn<DeductionDeets, Integer> loanCol;

    @FXML
    private TableColumn<DeductionDeets, Integer> leaveCol;

    @FXML
    private TableColumn<DeductionDeets, Integer> lateCol;


    @FXML
    void getSelection(MouseEvent event) {

    }

    ObservableList<DeductionDeets> listEm;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public void retBtFunc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage window = (Stage)retBt.getScene().getWindow();
        window.setScene(new Scene(root, 987,600));
        window.setResizable(false);

    }

    public void showDeductionDetails(){
        eidCol.setCellValueFactory(new PropertyValueFactory<DeductionDeets, Integer>("eid"));
        loanCol.setCellValueFactory(new PropertyValueFactory<DeductionDeets, Integer>("loan"));
        leaveCol.setCellValueFactory(new PropertyValueFactory<DeductionDeets, Integer>("leave"));
        lateCol.setCellValueFactory(new PropertyValueFactory<DeductionDeets, Integer>("late"));

        listEm = SqlConnect.getDeductionDetail();
        deductionTable.setItems(listEm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showDeductionDetails();
    }
}
