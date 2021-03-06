package DBAdmin;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBAdminView {
    //arraylist in case we want to add passwords, we can change it to primitive if we want
    ArrayList<String[]> userPass;
    DBAdminController myDBC;

    //public DBAdminView (SQLExecutor givenSQLE){
    public DBAdminView (DBAdminController DBC){
        myDBC = DBC;
        userPass = new ArrayList<String[]>();
        String userPass1[] = {"Staples","CSCI320"};
        userPass.add(userPass1);
    }

    //could constructor call start so it starts automaticly?
    public int start(){
        System.out.println("\nWelcome to the Staples Data Base Administrator console\n");
        System.out.println("Entering security protocol...\n");
        //security check
        Security mySecurity = new Security(userPass);
        int check = mySecurity.securityCheckpoint();
        if(check == 1){
            System.out.println("Exiting database...");
        }else{
            System.out.println("Accessing database...\n");
            System.out.println("Directly input SQL queries here.\n" +
                    "Type \"Help\" for a list of useful queries and info.\n" +
                    "Type \"Quit\" to close the database.\n");
            String input = "";
            Scanner sc = new Scanner(System.in);
            boolean done =false;
            int cols;
            String titles = "";
            String row = "";
            String format="";
            ArrayList<Integer> colWidths;
            int maxW = 0,thisW=0;
            while(!done){
                input = sc.nextLine();
                if(input.equalsIgnoreCase("Quit")){
                    done = true;
                }else if (input.equalsIgnoreCase("Help")) {
                    System.out.println("-Make sure your SQL statements have no line breaks.\n");
                    System.out.println("-Type \"Quit\" to close this view and the database.\n");
                    System.out.println("-To get the top 20 selling products at a particular store,\n" +
                            "Try the following statement, which queries store 1075:\n" +
                            "\"with storeSalesProduct (product_name,UPC,quantity, storeID) as (select product_name,product.UPC, saleitem.sale_quantity, sale.store_id from product natural join saleitem natural join sale where sale.store_id=1075) select UPC,Product_name, storeID,sum(quantity) as total_quantity from storeSalesProduct group by UPC order by total_quantity desc limit 20;\"\n");
                    System.out.println("To get the top 20 selling products in a particular state,\n" +
                            "Try the followinf statement, which queries New York State:\n" +
                            "\"with storeSalesProduct (product_name,UPC,quantity, storeID,state) as (select product_name,product.UPC, saleitem.sale_quantity, sale.store_id, state from product natural join saleitem natural join sale natural join store where state = 'NY') select UPC,Product_name, storeID,sum(quantity) as total_quantity, state from storeSalesProduct group by UPC order by total_quantity desc limit 20;\"\n");
                    System.out.println("To get the top 5 stores by sales, Try the following statement:\n" +
                            "\"with A (product_name,UPC,quantity, storeID,price,Sale_ID,year) as (select product_name,product.UPC, sale_quantity, sale.store_id,price, sale.Sale_ID,year from product natural join saleitem natural join sale where year = 2019) select storeID,sum(quantity*price) as sales from A group by storeID order by sales desc limit 5;\"\n");

                }else{
                    //send input to controller
                    System.out.println("Sending input \n\"" + input + "\"\nto the database...");
                    try {
                        ResultSet result = myDBC.getResult(input);
                        if (result != null && result.first()){
                            //put the below line in the else above
                            //result.first();
                            ResultSetMetaData rsmd = result.getMetaData();
                            cols = rsmd.getColumnCount();

                            colWidths = new ArrayList<>();
                            //System.out.println(cols);
                            for( int c = 1; c <= cols; c++ ){
                                maxW = rsmd.getColumnName(c).length();
                                result.first();
                                while(!result.isAfterLast()){
                                    if(result.getObject(c) != null) {
                                        thisW = result.getObject(c).toString().length();

                                    }
                                    //thisW = result.getObject(c).toString().length();
                                    //System.out.println(result.getObject(c).toString());
                                    if(thisW > maxW){
                                        maxW = thisW;
                                    }
                                    result.next();
                                }
                                colWidths.add(maxW);
                            }
                            //colWidths.forEach((n) -> System.out.print(n + "  "));
                            System.out.println();
                            result.first();

                            for (int i = 1; i <= cols; i++) {
                                //need to format these strings but for now its ok
                                //System.out.print(rsmd.getColumnName(i));
                                //below i can use the max length of a col name if its larger than 15
                                format = "|%-"+(2+colWidths.get(i-1))+"s|";
                                titles+=String.format(format,rsmd.getColumnName(i));
                            }
                            System.out.print(titles);
                            System.out.println();
                            while (!result.isAfterLast()) {
                                for (int i = 1; i <= cols; i++) {
                                    //System.out.print(" | " + result.getObject(i).toString() + " | ");
                                    //System.out.println(result.getObject(i).toString());
                                    format = "|%-"+(2+ colWidths.get(i-1))+"s|";
                                    if(result.getObject(i)!=null){
                                        row+=String.format(format,result.getObject(i).toString());
                                    }else{
                                        row+=String.format(format,"null");
                                    }
                                    //row+=String.format(format,result.getObject(i).toString());
                                }
                                System.out.print(row);
                                System.out.println();
                                row="";
                                result.next();
                            }
//                            for (int i = 1; i <= cols; i++) {
//                                //System.out.print(" | " + result.getObject(i).toString() + " | ");
//                                row+=String.format("|%-20s|",result.getObject(i).toString());
//                            }
//                            System.out.print(row);
                            //System.out.println();
                            row="";
                            System.out.println("\nEnd of query results...");
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }
                titles="";
            }

        }
        //System.out.println(userPass.get(0)[0]+" "+userPass.get(0)[1]);
        return 0;
    }
}