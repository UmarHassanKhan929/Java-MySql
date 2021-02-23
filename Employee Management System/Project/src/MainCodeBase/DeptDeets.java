package MainCodeBase;

public class DeptDeets {

    int deptIdDeet;
    String deptNameDeet;

    public int getDeptIdDeet(){ return deptIdDeet; }
    public void setDeptIdDeet(int deptIdDeet){this.deptIdDeet = deptIdDeet;}

    public String getDeptNameDeet(){ return deptNameDeet;}
    public void setDeptNameDeet(){this.deptNameDeet = deptNameDeet;}

    public DeptDeets(int deptIdDeet, String deptNameDeet){

        this.deptIdDeet = deptIdDeet;
        this.deptNameDeet = deptNameDeet;

    }

}
