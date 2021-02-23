package MainCodeBase;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
public class SqlConnect {

    Connection MyCon = null;
    public static Connection ConnectDB(){
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeePayroll", "root", "nicky420");
            //JOptionPane.showMessageDialog(null,"Establishing Connection. ");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }

    public static ObservableList<EmpDeets> getEmployeeDetails(){

        Connection con = ConnectDB();
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("select * from employee e");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<EmpDeets> getAdminDetails(String pp){

        Connection con = ConnectDB();
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("select * from employee e, user u where e.emp_id=u.emp_id and u.privelege = "+"\""+pp+"\"");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public static ObservableList<EmpDeets> getManagerDetails(String pp){

        Connection con = ConnectDB();
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("select * from employee e, user u where e.emp_id=u.emp_id and u.privelege = "+"\""+pp+"\"");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<EmpDeets> getOnlyEmployeeDetail(String pp){

        Connection con = ConnectDB();
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("select * from employee e, user u where e.emp_id=u.emp_id and u.privelege = "+"\""+pp+"\"");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<DeptDeets> getDeptDetail(){

        Connection con = ConnectDB();
        ObservableList<DeptDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM department");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new DeptDeets(rs.getInt("dept_id"),rs.getString("dept_name")));

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<GradeScaleDeets> getGradeScaleDetail(){

        Connection con = ConnectDB();
        ObservableList<GradeScaleDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM gradescale");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new GradeScaleDeets(rs.getInt("grade_id"),rs.getInt("dept_id"), rs.getInt("basepay"), rs.getInt("travelAllo"), rs.getInt("medicAllo"),rs.getInt("houseRentAllo"), rs.getString("grade_name") ));

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<UserDeets> getUsersDetail(){

        Connection con = ConnectDB();
        ObservableList<UserDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new UserDeets(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5)));

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<DeductionDeets> getDeductionDetail(){

        Connection con = ConnectDB();
        ObservableList<DeductionDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM deduction");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new DeductionDeets(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<EmpDeets> getOnlyEmployeeInDepartmentDetail(){

        Connection con = ConnectDB();
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE grade_id = 3 AND mgr_emp_id = "+LoginController.id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public static ObservableList<EmpDeets> getOnlyEmployeeInNoDepartmentDetail(){

        Connection con = ConnectDB();
        ObservableList<EmpDeets> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM employee e, user u WHERE  e.emp_id = u.emp_id AND u.privelege = \"EMP\" AND e.mgr_emp_id IS NULL");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                list.add(new EmpDeets(rs.getInt("emp_id"),rs.getInt("SSN"),rs.getInt("age"),rs.getInt("mgr_emp_id"), rs.getInt("grade_id"), rs.getInt("dept_id"), rs.getString("emp_name"),rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("DOB"), rs.getString("joining_date")) );

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }



//    //EXPECTED TO BE CHANGED
//    public static ObservableList<Users> getDatabase(){
//        Connection MyConn = ConnectDB();
//        ObservableList<Users> list = FXCollections.observableArrayList();
//
//        try{
//            PreparedStatement ps = MyConn.prepareStatement("SELECT * FROM soup");
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()){
//                list.add(new Users(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3)));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
}
