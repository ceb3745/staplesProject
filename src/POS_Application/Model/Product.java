package POS_Application.Model;

public class Product {
    private String UPC;
    private int quantity;
    private String name;

    public Product(String UPC, int quantity, String name){
        this.UPC = UPC;
        this.quantity = quantity;
        this.name = name;
    }

    public String getUPC(){return this.UPC;}

    public int getQuantity(){return this.quantity;}

    public String getName(){return this.name;}

}
