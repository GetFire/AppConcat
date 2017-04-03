package entity;

/**
 * This class is represent source-data in niece form
 */
public class DataModel {
    private String one;
    private  String model;
    private String name;
    private String relatedProduct;

    public DataModel(String one, String two, String three, String four) {
        this.one = one;
        this.model = two;
        this.name = three;
        this.relatedProduct = four;
    }

    public DataModel(String one, String two, String three) {
        this.one = one;
        this.model = two;
        this.name = three;
        this.relatedProduct ="";
    }

    public DataModel() {
    }

    public String toString() {

        return "DataModel{" +
                "one='" + one + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", relatedProduct='" + relatedProduct + '\'' +
                '}';
    }

    public String getOne() {
        return one;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getRelatedProduct() {
        return relatedProduct;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelatedProduct(String relatedProduct) {
        this.relatedProduct = relatedProduct;
    }
}