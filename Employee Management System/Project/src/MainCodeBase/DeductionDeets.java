package MainCodeBase;

public class DeductionDeets {

    public DeductionDeets(int eid, int loan, int leave, int late) {
        this.eid = eid;
        this.loan = loan;
        this.leave = leave;
        this.late = late;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    int eid,loan,leave,late;
}
