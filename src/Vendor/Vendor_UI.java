package Vendor;

import java.time.format.DateTimeFormatter;
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

    public Vendor_UI(SQLExecutor sqlExecutor)
    {
        super();
        executor = sqlExecutor;
        executor.startConnection("sa", "");
    }

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
                    exit = true; //Just here for the repeating menu
                    break;
            }
        }
        System.exit(0); //Kills the program after exiting
    }

    public static boolean make_reorder_request() { //This should be done automatically when a product is low
        Scanner sc = new Scanner(System.in);
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
        String currentID = "select top 1 * from reorder_request order by request_id desc";

        rs = executor.executeQuery(currentID);
        try{
            rs.next();
            lastRequestID = rs.getInt(1);
            lastRequestID++;
            request_id = lastRequestID;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        //Interface with the vendor
        System.out.println("REORDER REQUEST\n-------------------");
        System.out.println("Enter your vendor ID:");
        vendor_id = sc.next();

        System.out.println("Enter the Store_ID to make the delivery to:");
        store_id = sc.next();

        System.out.println("Enter the UPC of the product to deliver:");
        upc = sc.next();

        System.out.println("Enter the quantity of the product to be refilled:");
        quantity = sc.next();

        //Updating the reorder_request table
        sb = new StringBuilder();
        sb.append("insert into reorder_request values('"
                + request_id
                + "', '"
                + date
                + "', '"
                + time
                + "', '"
                + vendor_id
                + "', '"
                + store_id
                + "', '"
                + "false"
                + "', '"
                + upc
                + "', '"
                + quantity
                + "');");
        executor.executeQuery(sb.toString());


        //Updating the reorder_item table
        sb = new StringBuilder();
        sb.append("insert into reorder_item values('"
                + upc
                + "', '"
                + quantity
                + "', '"
                + request_id
                + "');");
        executor.executeQuery(sb.toString());
        return true;
    }
    public static boolean fill_reorder_request()
    {
        ResultSet rs;
        ResultSet upcResult;
        ResultSet quantityResultOld;
        String upc;
        int oldQuantity;
        int quantity;
        System.out.println("Retrieving reorder requests from the database.");
        try{
            String currentID = "select * from reorder_request order by request_id desc";
            rs = executor.executeQuery(currentID);
            rs.next();

            while(!rs.isAfterLast())
            {
                //If the reorder request has a delivery status of TRUE, move on
                if(rs.getString(6).equals("TRUE"))
                {
                    rs.next();
                    continue;
                }
                System.out.println("Filling request"); //Fills any undelivered requests

                //Filling each product one by one
                String getUPC = "select * from reorder_item where request_id = '" + rs.getInt(1) + "'";
                upcResult = executor.executeQuery(getUPC);
                upcResult.next();
                upc = upcResult.getString(1);

                quantity = upcResult.getInt(2);

                //Grabbing the quantity from the reorder_item table to add to the new quantity
                String addQuantity = "select * from product where upc = '" + upc + "'";
                quantityResultOld = executor.executeQuery(addQuantity);
                quantityResultOld.next();
                oldQuantity = quantityResultOld.getInt(9);
                quantity += oldQuantity;

                //Updating the product table
                String updateProduct = "update product set quantity = '" + quantity + "' where upc = '" + upc + "'";
                executor.executeQuery(updateProduct);

                //Updating the reorder_request table
                String updateReorderRequest = "update reorder_request set delivered = 'true' where request_id = '" + rs.getInt(1) + "'";
                executor.executeQuery(updateReorderRequest);
                rs.next();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finished();
        return true;
    }
    public static void finished()
    {
        System.out.println("Finished filling requests.");
    }

}