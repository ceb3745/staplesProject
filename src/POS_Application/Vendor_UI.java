package POS_Application;

import java.util.Scanner;
/*
* TODO:
* Handle filling reorder requests
* Handle queries to the database for authentification
* Handle queries to make a reorder request
* Handle queries to mark reorder requests filled
* Handle queries to update the product count
 */
public class Vendor_UI
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to make a reorder request?");
        System.out.println("1) Yes\n2) No");
        String input = new String();
        boolean reorder_status = false;

        input = sc.nextLine();
        while(!input.equals("1") && !input.equals("2"))
        {
            System.out.println("Invalid input, enter either 1 or 2.");
            input = sc.nextLine();
        }

        switch(Integer.parseInt(input))
        {
            case 1:
                reorder_status = make_reorder_request();
                break;
            case 2:
                //do some other shit
        }

    }
    public static boolean make_reorder_request() {
        Scanner sc = new Scanner(System.in);
        String request_id; //Retrieve the previous from the database and increase by 1
        String date;
        String time;
        String vendor_id;
        String store_id;
        String upc;

        System.out.println("REORDER REQUEST\n-------------------");
        System.out.println("Enter your vendor name:");

        vendor_id = sc.nextLine(); //Check the database for a matching vendor_id and replace


        System.out.println("Enter the Store_ID to make the delivery to:");

        store_id = sc.nextLine();//Check the database for the existence of the store_id

        System.out.println("Enter the UPC of the product to deliver:");

        upc = sc.nextLine(); //Check the database for the existence of the UPC

        //Send the query to insert a reorder request
        return false;
    }
    public boolean fill_reorder_request()
    {
        return true;
    }

}