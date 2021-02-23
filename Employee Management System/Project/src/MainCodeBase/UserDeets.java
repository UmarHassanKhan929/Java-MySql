package MainCodeBase;

public class UserDeets {

    public UserDeets(int userId, int empId, String username, String pass, String priv) {
        this.userId = userId;
        this.empId = empId;
        this.username = username;
        this.pass = pass;
        this.priv = priv;
    }

    int userId, empId;
    String username;
    String pass;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    String priv;
}
