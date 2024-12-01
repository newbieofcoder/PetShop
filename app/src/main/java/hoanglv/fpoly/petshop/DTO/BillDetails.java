package hoanglv.fpoly.petshop.DTO;

import java.io.Serializable;

public class BillDetails implements Serializable {
    private String _id;
    private String id_bill;
    private String id_pet;
    private long quantity;

    public BillDetails(String id_bill, String id_pet, long quantity) {
        this.id_bill = id_bill;
        this.id_pet = id_pet;
        this.quantity = quantity;
    }

    public BillDetails() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_bill() {
        return id_bill;
    }

    public void setId_bill(String id_bill) {
        this.id_bill = id_bill;
    }

    public String getId_pet() {
        return id_pet;
    }

    public void setId_pet(String id_pet) {
        this.id_pet = id_pet;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
