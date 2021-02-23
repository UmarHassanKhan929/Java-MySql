package MainCodeBase;

public class EmpDeets {


    int id,ssn,age,mgrId,gradeId,deptId;
    String name,address,email,phone,dob,joinDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMgrId() {
        return mgrId;
    }

    public void setMgrId(int mgrId) {
        this.mgrId = mgrId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }


    public EmpDeets(int id, int ssn, int age, int mgrId, int gradeId, int deptId, String name, String address, String email, String phone, String dob, String joinDate) {
        this.id = id;
        this.ssn = ssn;
        this.age = age;
        this.mgrId = mgrId;
        this.gradeId = gradeId;
        this.deptId = deptId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.joinDate = joinDate;
    }


}
