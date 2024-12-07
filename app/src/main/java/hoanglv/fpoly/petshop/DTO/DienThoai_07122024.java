package hoanglv.fpoly.petshop.DTO;


import java.io.Serializable;

public class DienThoai_07122024 implements Serializable {

    private String _id;
    private String ten_dienthoai_ph56584;
    private String ngay_nhap_ph56584;
    private String mo_ta_ph56584;
    private String hinh_anh_ph56584;
    private long trang_thai_ph56584;

    public DienThoai_07122024() {
    }

    public DienThoai_07122024(String ten_dienthoai, String ngay_nhap, long trang_thai, String mo_ta, String hinh_anh) {
        this.ten_dienthoai_ph56584 = ten_dienthoai;
        this.ngay_nhap_ph56584 = ngay_nhap;
        this.trang_thai_ph56584 = trang_thai;
        this.mo_ta_ph56584 = mo_ta;
        this.hinh_anh_ph56584 = hinh_anh;
    }

    public String get_id() {
        return _id;
    }

    public String getTenDienThoai() {
        return ten_dienthoai_ph56584;
    }

    public void setTenDienThoai(String ten_dienthoai) {
        this.ten_dienthoai_ph56584 = ten_dienthoai;
    }

    public String getNgayNhap() {
        return ngay_nhap_ph56584;
    }

    public void setNgayNhap(String ngay_nhap) {
        this.ngay_nhap_ph56584 = ngay_nhap;
    }

    public long getTrangThai() {
        return trang_thai_ph56584;
    }

    public void setTrangThai(long trang_thai) {
        this.trang_thai_ph56584 = trang_thai;
    }

    public String getMoTa() {
        return mo_ta_ph56584;
    }

    public void setMoTa(String mo_ta) {
        this.mo_ta_ph56584 = mo_ta;
    }

    public String getHinhAnh() {
        return hinh_anh_ph56584;
    }

    public void setHinhAnh(String hinh_anh) {
        this.hinh_anh_ph56584 = hinh_anh;
    }
}
