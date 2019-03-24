package POS_Application;

import java.util.Scanner;
import MainPk.SQLExecutor;
import POS_Application.Model.Cart;
import POS_Application.Model.Product;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Vendor_UI
{
    private SQLExecutor executor;

    public static void main(String[] args)
    {
        boolean exit = false;
        while(!exit) {
            Scanner sc = new Scanner(System.in);
            int choice = 0;

            System.out.println("Please enter an option: ");
            System.out.println("1) Make a reorder request");
            System.out.println("2) Fill a reorder request");
            System.out.println("3) Exit");
            choice = sc.nextInt();


            switch (choice) {
                case 1:
                    boolean requestMade = make_reorder_request();
                    if(!requestMade)
                        System.out.println("Request not made. Something went wrong.");
                    break;
                case 2:
                    boolean requestFilled = fill_reorder_request();
                    if(!requestFilled)
                        System.out.println("Request not filled. Something went wrong.");
                    break;
                case 3:
                    //Should exit to the start UI
                    exit = true;
                    break;
            }
        }
    }

    public static boolean make_reorder_request() { //This should be done automatically when a product is low
        Scanner sc = new Scanner(System.in);
        executor = new SQLExecutor();
        ResultSet rs = null;
        String request_id; //Retrieve the previous from the database and increase by 1
        int lastRequestID = -1;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(cal.getTime());
        String vendor_id;
        String store_id;
        String upc;
        String quantity;
        StringBuilder sb;

        //get next reorder request number
        String currentID = "select top 1 * from reorder_request by request_id desc";

        rs = sqlExecutor.executeQuery(currentID);
        try{
            lastRequestID = rs.getInt(2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        lastRequestID++;
        request_id = lastRequestID;

        System.out.println("REORDER REQUEST\n-------------------");
        System.out.println("Enter your vendor name:");
        vendor_id = sc.next();

        System.out.println("Enter the Store_ID to make the delivery to:");
        store_id = sc.next();

        System.out.println("Enter the UPC of the product to deliver:");
        upc = sc.next();

        System.out.println("Enter the quantity of the product to be refilled:");
        quantity = sc.next();

        //Need to grab the next request id from the database and create a date and time

        sb = new StringBuilder();
        sb.append("insert into reorder_request values("
                +request_id
                + ", "
                + date
                + ", "
                + time
                + ", "
                + vendor_id
                + ", "
                + store_id
                + ", "
                + "false"
                + ", "
                + upc
                + ", "
                + quantity
                + ");");
        executor.executeQuery(sb.toString());
        return true;
    }
    public boolean fill_reorder_request()
    {
        executor = new SQLExecutor();
        ResultSet rs = null;
        String upc = "";
        int quantity = -1;
        String oldQuantity = "";
        String reorder_id = "";
        System.out.println("Retrieving reorder requests from the database.");
        String grabRequests = "select * from reorder_request where delivered = 'false'");
        rs = executor.executeQuery(grabRequests);
        rs.next();

        //Grabbing all important information
        upc = rs.getString(7);
        quantity = Integer.parseInt(rs.getString(8));
        reorder_id = rs.getString(1);

        String grabOldQuantity = "select quantity from Product where upc = '" + upc + "'";
        rs = executor.executeQuery(grabOldQuantity);
        quantity += Integer.parseInt(rs.next().getString(1));

        //Updating the product table
        String updateProduct = "update product set quantity = '" + quantity.toString() + "' where upc = '" + upc + "'";
        rs = executor.executeQuery(updateProduct);

        //Updating the reorder_request table
        String updateReorderRequest = "update reorder_request set delivered = 'true' where reorder_id = '" + reorder_id + "'";
        rs = executor.executeQuery(updateReorderRequest);
        return true;
    }

}