package hoanglv.fpoly.petshop.DTO;

import java.io.Serializable;
import java.sql.Date;

public class Bill implements Serializable {
    private String _id;
    private String date;
    private String email;

    public Bill() {
    }

    public Bill(String date, String email) {
        this.date = date;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
