package hoanglv.fpoly.petshop.DTO;

public class Ward {
    private String wardCode;
    private int districtID;
    private String wardName;

    public Ward(String wardCode, int districtID, String wardName) {
        this.wardCode = wardCode;
        this.districtID = districtID;
        this.wardName = wardName;
    }

    public Ward() {
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
}
