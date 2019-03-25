package POS_Application.Model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;
    private int member_id = -1;
    private int sale_id = -1;

    public Cart(){
        products = new ArrayList<Product>();
    }

    public void addItem(Product prod){
        products.add(prod);
    }

    public void setMember_id(int member_id){
        this.member_id = member_id;
    }

    public int getSale_id(){
        return sale_id;
    }

    public void setSale_id(int sale_id){
        this.sale_id = sale_id;
    }

    public String listProducts(){
        StringBuilder sb = new StringBuilder();
        if(products.size() == 0){
            return null;
        }
        for(int i=0; i<products.size(); i++){
            sb.append("Name: ");
            sb.append(products.get(i).getName());
            sb.append(" ");
            sb.append("Quantity: ");
            sb.append(products.get(i).getQuantity());
            sb.append(" \n");
            sb.append("Price: ");
            sb.append(products.get(i).getPrice());
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getCartSize(){
        return products.size();
    }

    public Product getProduct(int index){
        return products.get(index);
    }

    public float getTotal(){
        float total = 0;
        for(int i=0; i<products.size(); i++){
            total += products.get(i).getPrice();
        }
        return total;
    }

}
