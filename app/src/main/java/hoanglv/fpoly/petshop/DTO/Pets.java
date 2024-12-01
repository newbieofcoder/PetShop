package hoanglv.fpoly.petshop.DTO;


import java.io.Serializable;

public class Pets implements Serializable {

    private String _id;
    private String name;
    private String description;
    private long price;
    private String species;

    public Pets() {
    }

    public Pets(String name, String description, long price, String species) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.species = species;
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
