
import java.sql.*;

public class Main {
    private Connection conn;

    public Main() {
    }

    public void createConnection(String location, String user, String password) {
        try {
            Class.forName("org.h2.Driver");
            this.conn = DriverManager.getConnection(location, user, password);
        } catch (ClassNotFoundException | SQLException var5) {
            var5.printStackTrace();
        }

    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Main demo = new Main();
        String location = "jdbc:h2:./staplesProject";
        String user = "sa";
        String password = "";
        demo.createConnection(location, user, password);


        StringBuilder sb = new StringBuilder();

        /**
         * Start the select query
         */
        //sb.append("SELECT ");

        /**
         * If we gave no columns just give them all to us
         *
         * other wise add the columns to the query
         * adding a comma top seperate
         */
        //sb.append("* ");

        /**
         * Tells it which table to get the data from
         */
        //sb.append("FROM PAPER ");

        /**
         * close with semi-colon
         */
        //sb.append(";");

        sb.append("with storeSalesProduct (product_name,UPC,quantity, storeID) as \n" +
                "(select product_name,product.UPC, saleitem.quantity, sale.store_id from\n" +
                "product natural join saleitem natural join sale where sale.store_id=1075)\n" +
                "select UPC,Product_name, storeID,sum(quantity) as total_quantity from storeSalesProduct group by UPC order by total_quantity limit 20;\n");


        //Print it out to verify it made it right
        System.out.println("Query: " + sb.toString());
        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = demo.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sb.toString());

            while(result.next()){
                System.out.println("UPC: " + result.getString(1)
                                    + "\nName: " + result.getString(2)
                                    + "\nStore Number: " + result.getString(3)
                                    + "\nQuantity: " + result.getString(4) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
