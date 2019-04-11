package Trigger;

import org.h2.api.Trigger;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * How to setup the database for this trigger:
 *
 * /////Create the trigger: CREATE TRIGGER my_trigger BEFORE UPDATE ON product FOR EACH ROW CALL "Trigger.ReorderTrigger"
 *
 * my version :
 * create the trigger: CREATE TRIGGER my_trigger2 after insert ON saleitem FOR EACH ROW CALL "Trigger.SaleDecrementTrigger"
 *
 *
 */
public class SaleDecrementTrigger implements Trigger {

    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {

    }

    @Override
    public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
        Statement sqlStatement = connection.createStatement();
        String q = "update product set quantity = quantity - "+ newRow[2] + " where upc = " + newRow[0];
        sqlStatement.execute((q));
        //maybe check if null of something??



        //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        //Date date = new Date(System.currentTimeMillis());
        //String time = timeFormat.format(date);

        //Integer currQuantity = (Integer) newRow[8];

//        if(currQuantity < 5) {
//            String vendorQuery = "SELECT VENDOR_ID FROM (SELECT * FROM PRODUCT NATURAL JOIN BRAND " +
//                    "NATURAL JOIN VENDOR_BRAND NATURAL JOIN VENDOR WHERE UPC = "
//                    + newRow[0] + ")";
//            System.out.println(vendorQuery);
//
//            Integer vendorID =
//                    (Integer) sqlStatement.executeQuery(vendorQuery)
//                            .getInt(1);
//
//            sqlStatement.execute("INSERT INTO reorder_request VALUES(CURRENT_DATE, " + time + ", " +
//                    vendorID + ", " + newRow[4] + ", FALSE, 5");
//        }
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public void remove() throws SQLException {}
}
