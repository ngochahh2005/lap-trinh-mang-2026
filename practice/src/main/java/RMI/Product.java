package RMI;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 20151107L;
    private String id, code;
    private double importPrice, exportPrice;

    public Product() {}

    public Product(String id, String code, double importPrice, double exportPrice) {
        this.id = id;
        this.code = code;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
    }

    public void updateCode() {
        this.code = this.code.toUpperCase();
    }

    public void updateExportPrice() {
        exportPrice = importPrice * 1.2;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", importPrice=" + importPrice +
                ", exportPrice=" + exportPrice +
                '}';
    }
}
