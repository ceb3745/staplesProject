package POS_Application.Model;

public class Product {
    private int UPC;
    private int quantity;
    private String name;

    public Product(int UPC, int quantity, String name){
        this.UPC = UPC;
        this.quantity = quantity;
        this.name = name;
    }

    public int getUPC(){return this.UPC;}

    public int getQuantity(){return this.quantity;}

    public String getName(){return this.name;}

}
