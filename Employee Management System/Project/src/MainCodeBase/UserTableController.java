package MainCodeBase;

import com.sun.javafx.image.IntPixelGetter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class UserTableController implements Initializable {

    @FXML
    private Button retBt;

    @FXML
    private TableView<UserDeets> userTable;

    @FXML
    private TableColumn<UserDeets, Integer> uIdCol;

    @FXML
    private TableColumn<UserDeets, Integer> eIdCol;

    @FXML
    private TableColumn<UserDeets, String> userCol;

    @FXML
    private TableColumn<UserDeets, String> passCol;

    @FXML
    private TableColumn<UserDeets, String> privCol;



    ObservableList<UserDeets> listEm;

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

        public void showUserDetails(){
            uIdCol.setCellValueFactory(new PropertyValueFactory<UserDeets, Integer>("userId"));
            eIdCol.setCellValueFactory(new PropertyValueFactory<UserDeets, Integer>("empId"));
            userCol.setCellValueFactory(new PropertyValueFactory<UserDeets, String>("username"));
            passCol.setCellValueFactory(new PropertyValueFactory<UserDeets, String>("pass"));
            privCol.setCellValueFactory(new PropertyValueFactory<UserDeets, String>("priv"));


            listEm = SqlConnect.getUsersDetail();
            userTable.setItems(listEm);
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUserDetails();
    }

    public void getSelection(){

    }
}
