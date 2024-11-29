package hoanglv.fpoly.petshop.DTO;

public class BillDetails {
    private String _id;
    private String id_bill;
    private String id_pet;

    public BillDetails(String _id, String id_bill, String id_pet) {
        this._id = _id;
        this.id_bill = id_bill;
        this.id_pet = id_pet;
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
}
