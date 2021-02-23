package MainCodeBase;

public class Users {
    int id;
    String name,major;
    public Users(int id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
}
