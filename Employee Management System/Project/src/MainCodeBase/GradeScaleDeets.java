package MainCodeBase;

public class GradeScaleDeets {

    int gID, dId, base, travel,medic, house;
    String gName;

    public GradeScaleDeets(int gID, int dId, int base, int travel, int medic, int house, String gName) {
        this.gID = gID;
        this.dId = dId;
        this.base = base;
        this.travel = travel;
        this.medic = medic;
        this.house = house;
        this.gName = gName;
    }



    public int getGID() {
        return gID;
    }

    public void setGID(int gID) {
        this.gID = gID;
    }

    public int getDId() {
        return dId;
    }

    public void setDId(int dId) {
        this.dId = dId;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getTravel() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
    }

    public int getMedic() {
        return medic;
    }

    public void setMedic(int medic) {
        this.medic = medic;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }


}
