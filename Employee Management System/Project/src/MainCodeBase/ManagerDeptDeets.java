package MainCodeBase;

public class ManagerDeptDeets {
    String name,email,phone;
    int age,numleaves,timeslate,ID;

    public ManagerDeptDeets(String name,int age, String email, String phone,  int numleaves, int timeslate,int ID) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.numleaves = numleaves;
        this.timeslate = timeslate;
        this.ID=ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumleaves() {
        return numleaves;
    }

    public void setNumleaves(int numleaves) {
        this.numleaves = numleaves;
    }

    public int getTimeslate() {
        return timeslate;
    }

    public void setTimeslate(int timeslate) {
        this.timeslate = timeslate;
    }
}
