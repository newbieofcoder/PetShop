package hoanglv.fpoly.petshop.DTO;


public class Pets {

    private String _id;
    private String name;
    private String description;
    private long price;
    private String url;

    public Pets(String name, String description, long price, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
