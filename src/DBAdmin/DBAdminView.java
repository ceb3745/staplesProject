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
        int check = 0;//mySecurity.securityCheckpoint();
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
            while(!done){
                input = sc.nextLine();
                if(input.equalsIgnoreCase("Quit")){
                    done = true;
                }else if (input.equalsIgnoreCase("Help")) {
                    System.out.println("helpfull message");
                }else{
                    //send input to controller
                    System.out.println("sending input \n\"" + input + "\"\nto the database...");
                    try {
                        ResultSet result = myDBC.getResult(input);
                        if (result != null){
                            //put the below line in the else above
                            result.next();
                            ResultSetMetaData rsmd = result.getMetaData();
                            cols = rsmd.getColumnCount();
                            for (int i = 1; i < cols + 1; i++) {
                                //need to format these strings but for now its ok
                                //System.out.print(rsmd.getColumnName(i));
                                //below i can use the max length of a col name if its larger than 15
                                titles+=String.format("|%-20s|",rsmd.getColumnName(i));
                            }
                            System.out.print(titles);
                            System.out.println();
                            while (!result.isLast()) {
                                for (int i = 1; i < cols + 1; i++) {
                                    //System.out.print(" | " + result.getObject(i).toString() + " | ");
                                    row+=String.format("|%-20s|",result.getObject(i).toString());
                                }
                                System.out.print(row);
                                System.out.println();
                                row="";
                                result.next();
                            }
                            for (int i = 1; i < cols + 1; i++) {
                                //System.out.print(" | " + result.getObject(i).toString() + " | ");
                                row+=String.format("|%-20s|",result.getObject(i).toString());
                            }
                            System.out.print(row);
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