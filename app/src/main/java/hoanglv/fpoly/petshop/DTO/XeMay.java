package hoanglv.fpoly.petshop.DTO;


import java.io.Serializable;

public class XeMay implements Serializable {

    private String _id;
    private String ten_xe;
    private String mau_sac;
    private long gia_ban;
    private String mo_ta;
    private String hinh_anh;

    public XeMay() {
    }

    public XeMay(String ten_xe, String mau_sac, long gia_ban, String mo_ta, String hinh_anh) {
        this.ten_xe = ten_xe;
        this.mau_sac = mau_sac;
        this.gia_ban = gia_ban;
        this.mo_ta = mo_ta;
        this.hinh_anh = hinh_anh;
    }

    public String get_id() {
        return _id;
    }

    public String getTenXe() {
        return ten_xe;
    }

    public void setTenXe(String ten_xe) {
        this.ten_xe = ten_xe;
    }

    public String getMauSac() {
        return mau_sac;
    }

    public void setMauSac(String mau_sac) {
        this.mau_sac = mau_sac;
    }

    public long getGiaBan() {
        return gia_ban;
    }

    public void setGiaBan(long gia_ban) {
        this.gia_ban = gia_ban;
    }

    public String getMoTa() {
        return mo_ta;
    }

    public void setMoTa(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getHinhAnh() {
        return hinh_anh;
    }

    public void setHinhAnh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }
}
