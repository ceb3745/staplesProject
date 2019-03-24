package Vendor;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import MainPk.SQLExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Vendor_UI
{
    private static SQLExecutor executor;

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
        ResultSet rs;
        int request_id = 0; //Retrieve the previous from the database and increase by 1
        int lastRequestID;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
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

        rs = executor.executeQuery(currentID);
        try{
            lastRequestID = rs.getInt(2);
            lastRequestID++;
            request_id = lastRequestID;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        //Interface with the vendor
        System.out.println("REORDER REQUEST\n-------------------");
        System.out.println("Enter your vendor name:");
        vendor_id = sc.next();

        System.out.println("Enter the Store_ID to make the delivery to:");
        store_id = sc.next();

        System.out.println("Enter the UPC of the product to deliver:");
        upc = sc.next();

        System.out.println("Enter the quantity of the product to be refilled:");
        quantity = sc.next();

        //Updating the reorder_request table
        sb = new StringBuilder();
        sb.append("insert into reorder_request values("
                + request_id
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
                + ");");
        executor.executeQuery(sb.toString());


        //Updating the reorder_item table
        sb = new StringBuilder();
        sb.append("insert into reorder_item values("
                + upc
                + ", "
                + quantity
                + ", "
                + request_id
                + ");");
        executor.executeQuery(sb.toString());
        return true;
    }
    public static boolean fill_reorder_request()
    {
        executor = new SQLExecutor();
        ResultSet rs;
        String upc;
        int oldQuantity;
        int quantity;
        String lastRequestID;
        String[] request_id; //an array to handle all of the undelivered reorder_requests
        System.out.println("Retrieving reorder requests from the database.");
        String grabRequests = "select * from reorder_request where delivered = 'false'";
        rs = executor.executeQuery(grabRequests);
        try{
            String currentID = "select top 1 * from reorder_request by request_id desc";
            rs = executor.executeQuery(currentID);
            lastRequestID = rs.getString(2);
            request_id = Arrays.asList(rs.getArray(1)).toArray(new String[Integer.parseInt(lastRequestID)]);

            int counter = Integer.parseInt(lastRequestID); //will count down until all of the unfilled requests are filled
            while(counter > 0)
            {
                //Filling each product one by one
                String getUPC = "select upc from request_item where request_id = '" + request_id[counter] + "'";
                rs = executor.executeQuery(getUPC);
                rs.next();
                upc = rs.getString(1);

                //Grabbing the quantity from the product table to update
                String grabOldQuantity = "select quantity from Product where upc = '" + upc + "'";
                rs = executor.executeQuery(grabOldQuantity);
                rs.next();
                oldQuantity = Integer.parseInt(rs.getString(1));

                //Grabbing the quantity from the reorder_item table to add to the new quantity
                String addQuantity = "select quantity from reorder_item where upc = '" + upc + "'";
                rs = executor.executeQuery(addQuantity);
                rs.next();
                quantity = Integer.parseInt(rs.getString(1));
                quantity += oldQuantity;

                //Updating the product table
                String updateProduct = "update product set quantity = '" + quantity + "' where upc = '" + upc + "'";
                rs = executor.executeQuery(updateProduct);

                //Updating the reorder_request table
                String updateReorderRequest = "update reorder_request set delivered = 'true' where reorder_id = '" + request_id[counter] + "'";
                rs = executor.executeQuery(updateReorderRequest);

                counter--;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}