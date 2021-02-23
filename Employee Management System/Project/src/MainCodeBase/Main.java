package MainCodeBase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Payroll Management System");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("https://media.discordapp.net/attachments/602449315780100107/802539248535994388/employee.png"));
        primaryStage.setScene(new Scene(root, 950, 600));
        //primaryStage.setScene(new Scene(root, 988,640));
        primaryStage.show();
    }

    public static void main(String[] args) {
        LocalDate currentdate = LocalDate.now();
        int currentDay = currentdate.getDayOfMonth();
        if(currentDay == 22) {
            try {
                Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeePayroll", "root", "nicky420");
                PreparedStatement ps;
                ps = mycon.prepareStatement("set sql_safe_updates = 0;");
                ps.execute();
                ps = mycon.prepareStatement("update deduction set numleaves=0, timeslate = 0");
                ps.executeUpdate();
                mycon.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            System.out.println(currentDay);
        }
        launch(args);
    }
}