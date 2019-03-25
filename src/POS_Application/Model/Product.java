package POS_Application.Model;

public class Product {
    private String UPC;
    private int quantity;
    private String name;
    private float price;

    public Product(String UPC, int quantity, String name, float price){
        this.UPC = UPC;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public String getUPC(){return this.UPC;}

    public int getQuantity(){return this.quantity;}

    public String getName(){return this.name;}

    public float getPrice(){return this.price;}

}
