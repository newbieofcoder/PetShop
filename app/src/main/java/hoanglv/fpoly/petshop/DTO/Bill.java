package hoanglv.fpoly.petshop.DTO;

import java.sql.Date;

public class Bill {
    private String _id;
    private Date date;
    private String email;

    public Bill() {
    }

    public Bill(String _id, Date date, String email) {
        this._id = _id;
        this.date = date;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
