package POS_Application;

import MainPk.SQLExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Client Control for POS system
 */
public class ClientControl {

    Connection conn;
    int store_id;
    StringBuilder sb;
    SQLExecutor sqlExecutor;

    public ClientControl(SQLExecutor sqlExecutor){
        this.sqlExecutor = sqlExecutor;
    }

    /**
     *
     * @param store_id
     */
    public void setStoreId(int store_id){
        this.store_id = store_id;
    }

    /**
     *
     * @param conn
     */
    public void setConnection(Connection conn){
        this.conn = conn;
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
        if(conn == null){
            return null;
        }
        sb = new StringBuilder();
        ResultSet rs;
        sb.append("insert into member values("
                +fn
                + ", "
                + ln
                + ", "
                + phone_number
                + ", "
                + member_type
                + ", "
                + email
                + ");");
        try{
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            return rs;
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Store_ID,Sale_ID,Member_ID,Payment_Type,Card_Number,Time,Month,Day,Year
    /**
     *
     * @param upc
     * @param quantity
     * @param card_number
     * @param member_id
     * @param payment_type
     * @param time
     * @param month
     * @param day
     * @param year
     */
    public void addSale(int upc, int quantity, int card_number, int member_id, String payment_type, String time, int month, int day, int year){
        if(conn == null){
            return;
        }
        ResultSet rs = null;
        sb = new StringBuilder();
        int lastSaleIndex = -1;

        //get next sale number
        String init = "select top 1 * from sale by sale_id desc";

        try{
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(init);
            lastSaleIndex = rs.getInt(2);
            lastSaleIndex++;
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //add sale to sale table
        sb.append("insert into sale values("
                    + store_id
                    + ", "
                    + lastSaleIndex
                    + ", "
                    + member_id
                    + ", "
                    + payment_type
                    + ", "
                    + card_number
                    + ", "
                    + time
                    + ", "
                    + month
                    + ", "
                    + day
                    + ", "
                    + year
                    + ");");

        try{
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sb.toString());
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //add sale item to sale item table
        sb = new StringBuilder();
        sb.append("insert into saleitem values("
                    + upc
                    + ", "
                    + lastSaleIndex
                    + ", "
                    + quantity
                    + ");");

        try{
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sb.toString());
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //check valid store_num
    /**
     *
     * @param store_num
     * @return
     */
    public boolean findStore(int store_num){
        if(conn == null){
            return false;
        }
        String query = "select store_id from store where store_id = " + store_num;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.getInt(1) != store_num){
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
