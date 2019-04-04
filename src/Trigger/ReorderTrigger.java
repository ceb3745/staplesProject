package Trigger;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * CREATE TRIGGER my_trigger BEFORE UPDATE ON product FOR EACH ROW CALL "java.util.ArrayList"
 */
public class ReorderTrigger implements Trigger {

    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {

    }

    @Override
    public void fire(Connection connection, Object[] objects, Object[] objects1) throws SQLException {
        System.out.println("TRIGGER WORKS BOI");
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public void remove() throws SQLException {

    }
}
