package hoanglv.fpoly.petshop.DTO;

public class District {
    private int districtID;
    private int provinceID;
    private String districtName;

    public District(int districtID, int provinceID, String districtName) {
        this.districtID = districtID;
        this.provinceID = provinceID;
        this.districtName = districtName;
    }

    public District() {
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
