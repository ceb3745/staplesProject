package POS_Application;

import MainPk.SQLExecutor;
import POS_Application.Model.Product;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Client Control for POS system
 */
public class ClientControl {

    int store_id;
    StringBuilder sb;
    SQLExecutor sqlExecutor;
    int curr_sale_id;

    public ClientControl(SQLExecutor sqlExecutor){
        this.sqlExecutor = sqlExecutor;
        sqlExecutor.startConnection("sa", "");
    }

    /**
     *
     * @param store_id
     */
    public void setStoreId(int store_id){
        this.store_id = store_id;
    }

    //First_Name,Last_Name,Phone_Number,Member_ID,Member_Type,Email
    /**
     *
     * @param fn
     * @param ln
     * @param phone_number
     * @param member_type
     * @param email
     * @return
     */
    public ResultSet becomeAMember(String fn, String ln, String phone_number, String member_type, String email){
        sb = new StringBuilder();
        ResultSet rs;
        sb.append("insert into member values('"
                +fn
                + "', '"
                + ln
                + "', '"
                + phone_number
                + "', '"
                + member_type
                + "', '"
                + email
                + "');");
        sqlExecutor.executeQuery(sb.toString());
        return null;
    }

    //Store_ID,Sale_ID,Member_ID,Payment_Type,Card_Number,Time,Month,Day,Year
    /**
     *
     * @param card_number
     * @param member_id
     * @param payment_type
     * @param time
     * @param month
     * @param day
     * @param year
     */
    public void addSale(String card_number, int member_id, String payment_type, String time, int month, int day, int year){
        ResultSet rs = null;
        sb = new StringBuilder();
        int lastSaleIndex = -1;

        //get next sale number
        String init = "select top 1 * from sale order by sale_id desc";

        rs = sqlExecutor.executeQuery(init);
        try{
            rs.next();
            lastSaleIndex = rs.getInt(2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        lastSaleIndex++;

        curr_sale_id = lastSaleIndex;

        //add sale to sale table
        sb.append("insert into sale values('"
                    + store_id
                    + "', '"
                    + lastSaleIndex
                    + "', '"
                    + member_id
                    + "', '"
                    + payment_type
                    + "', '"
                    + card_number
                    + "', '"
                    + time
                    + "', '"
                    + month
                    + "', '"
                    + day
                    + "', '"
                    + year
                    + "');");

        sqlExecutor.executeQuery(sb.toString());

    }



    //add sale_item to existing sale
    public void addItem(String upc, int quantity){
        ResultSet rs = null;

        String query = "insert into saleitem values('" + upc +"', '" + curr_sale_id + "', '"+ quantity + "');";
        sqlExecutor.executeQuery(query);
    }


    //check valid store_num
    /**
     *
     * @param store_num
     * @return
     */
    public boolean findStore(int store_num){
        String query = "select * from store where store_id = " + store_num + ";";
        ResultSet rs;
        try{
            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(rs.getInt(1) != store_num){
                return false;
            }
            store_id = store_num;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean findMember(int member_id){
        String query = "select * from member where member_id = " + member_id + ";";
        ResultSet rs;
        try{
            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(rs.getInt(4) != member_id){
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean confirmProduct(String upc){
        String query = "select * from product where upc=" + upc + ";";
        ResultSet rs;
        try{
            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.getString(1).equals(upc)){
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public Product getProduct(String upc){
        String query = "select * from product where upc=" + upc + ";";
        ResultSet rs;
        try{
            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.getString(1).equals(upc)){
                return null;
            }
            return new Product(rs.getString(1), 1, rs.getString(2), rs.getFloat(7));
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public float getProductPrice(String upc){
        String query = "select * from product where upc=" + upc + ";";
        ResultSet rs;
        try{
            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.getString(1).equals(upc)){
                return -1;
            }
            return rs.getFloat(7);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
