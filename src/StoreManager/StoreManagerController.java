package StoreManager;

import MainPk.SQLExecutor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class StoreManagerController {

    StoreManagerView myView;
    SQLExecutor mySQLE;

    public StoreManagerController(SQLExecutor executor){
        mySQLE = executor;
        myView = new StoreManagerView(this);
    }

    public void startUp(){
        myView.start();
    }

    public ResultSet getResult(String str){
        //the SQLE method has the same name as the actual execute method in sql javadb
        ResultSet result = mySQLE.executeQuery(str);
//        if(result == null){
//            //something went wrong
//            //i need to know if the SQLEx handels the exeption or if i should add another handler
//        }else return result;
        //ResultSetMetaData rsmd = result.getMetaData();
        return result;




    }

}