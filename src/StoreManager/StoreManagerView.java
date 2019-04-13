package StoreManager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StoreManagerView {
    //arraylist in case we want to add passwords, we can change it to primitive if we want
    StoreManagerController mySMC;

    //public DBAdminView (SQLExecutor givenSQLE){
    public StoreManagerView (StoreManagerController SMC){
        mySMC = SMC;
    }

    public int getStoreNumber(){
        int number;
        String input;
        boolean done = false;
        Scanner sc1 = new Scanner(System.in);
        while(!done){
            System.out.println("Enter Store Number: (\"Quit\" to exit the program)");
            input = sc1.nextLine();
            if(input.equalsIgnoreCase("Quit")){
                done = true;
                return -1;
            }else{
                try {
                    number = Integer.parseInt(input);
                    try{
                        ResultSet result = mySMC.getResult("select store_id from store where store_id = " + number);
                        if(!result.first()){
                            System.out.println("This store is not in the database, try another number or type \"Quir\" to exit");
                        }else{
                            return number;
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                } catch (NumberFormatException | NullPointerException nfe) {
                    System.out.println("Thats not a store number, please try again.");
                }


//                number = Integer.parseInt(input);
//                try{
//                  ResultSet result = mySMC.getResult("select store_id from store where store_id = " + number);
//                  if(!result.first()){
//                      System.out.println("This store is not in the database, try another number or type \"Quir\" to exit");
//                  }else{
//                        return number;
//                    }
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }

            }
        }
        return -1;
    }

    private void fancyPrintResults(String input){
        int cols;
        String titles = "";
        String row = "";
        String format="";
        ArrayList<Integer> colWidths;
        int maxW = 0,thisW=0;
        //send input to controller
       // System.out.println("Sending input \n\"" + input + "\"\nto the database...");
        try {
            ResultSet result = mySMC.getResult(input);
            if (result != null && result.first()){
                //result.first();
                ResultSetMetaData rsmd = result.getMetaData();
                cols = rsmd.getColumnCount();
                colWidths = new ArrayList<>();
                for( int c = 1; c <= cols; c++ ){
                    maxW = rsmd.getColumnName(c).length();
                    result.first();
                    while(!result.isAfterLast()){
                        if(result.getObject(c) != null) {
                            thisW = result.getObject(c).toString().length();

                        }


                        //thisW = result.getObject(c).toString().length();
                        if(thisW > maxW){
                            maxW = thisW;
                        }
                        result.next();
                    }
                    colWidths.add(maxW);
                }
                System.out.println();
                result.first();
                for (int i = 1; i <= cols; i++) {
                    format = "|%-"+(2+colWidths.get(i-1))+"s|";
                    titles+=String.format(format,rsmd.getColumnName(i));
                }
                System.out.print(titles);
                System.out.println();
                while (!result.isAfterLast()) {
                    for (int i = 1; i <= cols; i++) {
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
                row="";
                //System.out.println("\nEnd of query results...\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println();
    }



    //could constructor call start so it starts automaticly?
    public int start(){
        System.out.println("\nWelcome to the Staples Store Manager console\n");
        int storeNumber = getStoreNumber();
        int check = storeNumber;
        if(check == -1){
            System.out.println("Exiting database...");
        }else{
            System.out.println("Accessing database...\n");

            //most recent sale
            //select * from sale order by (year,month,day,time) desc limit 1

            //total sale day
            //select sum(price * sale_quantity) from saleitem natural join product natural join sale where day = 1 and month = 2 and year = 2019

            //totas sale week
            //select sum(price * sale_quantity) from saleitem natural join product natural join sale where month = 2 and year = 2019

            //top selling product
            //select product.product_name, product.upc, sum(sale_quantity) as total from sale natural join product natural join saleitem group by product.upc order by total desc limit 1

            //Top selling by department
            //select product.product_name, product.upc, sum(sale_quantity) as total from sale natural join product natural join saleitem natural join sub_department natural join department where department.department_id = 1 group by product.upc order by total desc limit 1



            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String year = yearFormat.format(date);
            String month = monthFormat.format(date);
            String day = dayFormat.format(date);
            String time = timeFormat.format(date);

            //System.out.println(year + " " + month + " " + day + " " + time);

            String input = "";
            Scanner sc = new Scanner(System.in);
            boolean done =false;
//            int cols;
//            String titles = "";
//            String row = "";
//            String format="";
//            ArrayList<Integer> colWidths;
//            int maxW = 0,thisW=0;

            System.out.println("Here is your store summary:\n");
            System.out.println("Most recent sale:");
            fancyPrintResults("select * from sale where store_id = "+storeNumber+" order by (year,month,day,time) desc limit 1");
            //change this to generic later so it actualy gives the today info
            System.out.println("Total sales today (2/1/2019):");
            fancyPrintResults("select sum(price * sale_quantity) as Total_Daily_Sale from saleitem natural join product natural join (select * from sale where store_id = "+storeNumber+") where day = 1 and month = 2 and year = 2019;");
            System.out.println("Total sales this week :");
            fancyPrintResults("select sum(price * sale_quantity) as Total_Weekly_Sale from saleitem natural join product natural join sale where sale.store_id = "+storeNumber+" and month = 2 and year = 2019");
            System.out.println("Top selling product by count:");
            fancyPrintResults("select product.product_name, product.upc, sum(sale_quantity) as total from sale natural join product natural join saleitem where sale.store_id = "+storeNumber+" group by product.upc order by total desc limit 1");
            System.out.println("Top selling product by count by department: ");
            //fancyPrintResults("select product.product_name, product.upc, sum(sale_quantity) as total from sale natural join product natural join saleitem natural join sub_department natural join department where department.department_id = 1 and sale.store_id = "+storeNumber+"group by product.upc order by total desc limit 1");
            fancyPrintResults("select S.sdid, sub_dept_name, upc, total from S natural join (select S.sdid, max(total) as max_total from S group by S.sdid) natural join sub_department where total = max_total and S.sdid=sd_id");

            System.out.println("For more detailed inquiries, please contact your data base administrator. ");

//            while(!done){
//                input = sc.nextLine();
//                if(input.equalsIgnoreCase("Quit")){
//                    done = true;
//                }else if (input.equalsIgnoreCase("Help")) {
//                    System.out.println("-Make sure your SQL statements have no line breaks.\n");
//                    System.out.println("-Type \"Quit\" to close this view and the database.\n");
//                    System.out.println("-To get the top 20 selling products at a particular store,\n" +
//                            "Try the following statement, which queries store 1075:\n" +
//                            "\"with storeSalesProduct (product_name,UPC,quantity, storeID) as (select product_name,product.UPC, saleitem.sale_quantity, sale.store_id from product natural join saleitem natural join sale where sale.store_id=1075) select UPC,Product_name, storeID,sum(quantity) as total_quantity from storeSalesProduct group by UPC order by total_quantity desc limit 20;\"\n");
//                    System.out.println("To get the top 20 selling products in a particular state,\n" +
//                            "Try the followinf statement, which queries New York State:\n" +
//                            "\"with storeSalesProduct (product_name,UPC,quantity, storeID,state) as (select product_name,product.UPC, saleitem.sale_quantity, sale.store_id, state from product natural join saleitem natural join sale natural join store where state = 'NY') select UPC,Product_name, storeID,sum(quantity) as total_quantity, state from storeSalesProduct group by UPC order by total_quantity desc limit 20;\"\n");
//                    System.out.println("To get the top 5 stores by sales, Try the following statement:\n" +
//                            "\"with A (product_name,UPC,quantity, storeID,price,Sale_ID,year) as (select product_name,product.UPC, sale_quantity, sale.store_id,price, sale.Sale_ID,year from product natural join saleitem natural join sale where year = 2019) select storeID,sum(quantity*price) as sales from A group by storeID order by sales desc limit 5;\"\n");
//
//                }else{
//
////                    //send input to controller
////                    System.out.println("Sending input \n\"" + input + "\"\nto the database...");
////                    try {
////                        ResultSet result = mySMC.getResult(input);
////                        if (result != null){
////                            result.first();
////                            ResultSetMetaData rsmd = result.getMetaData();
////                            cols = rsmd.getColumnCount();
////                            colWidths = new ArrayList<>();
////                            for( int c = 1; c <= cols; c++ ){
////                                maxW = rsmd.getColumnName(c).length();
////                                result.first();
////                                while(!result.isAfterLast()){
////                                    thisW = result.getObject(c).toString().length();
////                                    if(thisW > maxW){
////                                        maxW = thisW;
////                                    }
////                                    result.next();
////                                }
////                                colWidths.add(maxW);
////                            }
////                            System.out.println();
////                            result.first();
////                            for (int i = 1; i <= cols; i++) {
////                                format = "|%-"+(2+colWidths.get(i-1))+"s|";
////                                titles+=String.format(format,rsmd.getColumnName(i));
////                            }
////                            System.out.print(titles);
////                            System.out.println();
////                            while (!result.isAfterLast()) {
////                                for (int i = 1; i <= cols; i++) {
////                                    //System.out.print(" | " + result.getObject(i).toString() + " | ");
////                                    //System.out.println(result.getObject(i).toString());
////                                    format = "|%-"+(2+ colWidths.get(i-1))+"s|";
////                                    row+=String.format(format,result.getObject(i).toString());
////                                }
////                                System.out.print(row);
////                                System.out.println();
////                                row="";
////                                result.next();
////                            }
////                            row="";
////                            System.out.println("\nEnd of query results...");
////                        }
////                    }catch (SQLException e){
////                        e.printStackTrace();
////                    }
//
//                }
//                //titles="";
//            }

        }
        //System.out.println(userPass.get(0)[0]+" "+userPass.get(0)[1]);
        return 0;
    }
}