package DBAdmin;

import MainPk.SQLExecutor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DBAdminController {

    DBAdminView myView;
    SQLExecutor mySQLE;

    public DBAdminController(){

        mySQLE = new SQLExecutor();
        myView = new DBAdminView(this);
        mySQLE.startConnection("sa","");

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