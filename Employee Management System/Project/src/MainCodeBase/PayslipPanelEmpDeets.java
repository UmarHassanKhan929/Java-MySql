package MainCodeBase;

public class PayslipPanelEmpDeets {
    int payID,allowance,netpay;
    String name,month,year,date_generated;

    public PayslipPanelEmpDeets(int payID, String name, String month, String year, String date_generated, int allowance, int netpay) {
        this.payID = payID;
        this.allowance = allowance;
        this.netpay = netpay;
        this.name = name;
        this.month = month;
        this.year = year;
        this.date_generated = date_generated;
    }

    public int getPayID() {
        return payID;
    }

    public void setPayID(int payID) {
        this.payID = payID;
    }

    public int getAllowance() {
        return allowance;
    }

    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    public int getNetpay() {
        return netpay;
    }

    public void setNetpay(int netpay) {
        this.netpay = netpay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate_generated() {
        return date_generated;
    }

    public void setDate_generated(String date_generated) {
        this.date_generated = date_generated;
    }
}
