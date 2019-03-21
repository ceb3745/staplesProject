package POS_Application.Model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;

    public Cart(){
        products = new ArrayList<Product>();
    }

    public void addItem(Product prod){
        products.add(prod);
    }

    public String listProducts(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<products.size(); i++){
            sb.append("Name: ");
            sb.append(products.get(i).getName());
            sb.append(" ");
            sb.append("Quantity: ");
            sb.append(products.get(i).getQuantity());
            sb.append(" \n");
        }
        return sb.toString();
    }

}
