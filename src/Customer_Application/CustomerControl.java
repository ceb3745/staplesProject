package Customer_Application;

import MainPk.SQLExecutor;
import POS_Application.Model.Product;
import javafx.scene.layout.HBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Customer Control for Online system
 */
public class CustomerControl {

    int store_id;
    StringBuilder sb;
    SQLExecutor sqlExecutor;
    int curr_sale_id;
    String dept_name = "";
    String sub_dept_name = "";
    int upper_price = -1;
    int lower_price = -1;
    //COMPUTERS
    //8, 16, or 24
    int ram = -1;
    //Windows 10, Windows 9, Windows 8, mac OSX
    String os = "";
    //AMD Ryzen 3, Intel i5, Intel i7, Intel i3, Intel UHD, Intel Pentium
    String processor = "";
    //1TB, 700GB, 650GB, 500GB, 450GB, 320GB
    String hhd = "";

    //LAPTOPS
    //14", 15.6", 17.3"
    String screen_size = "";
    //Yes, No
    String touchscreen = "";
    //LCD, LED backlight
    String display_type = "";
    //Stereo Speakers, Integrated High Definition Audio
    String audio = "";

    //DESKTOPS
    float height_desktop = -1;
    float width_desktop = -1;
    float depth_desktop = -1;
    //0, 1
    int opticalDrive = -1;

    //FURNITURE
    float height = -1;
    float width = -1;
    float depth = -1;
    //0, 1
    int requiresAssembly = -1;
    //Fuscia, Mauv, Goldenrod, Purple, Turquoise, Aquamarine, Maroon, Blue, Khaki, Crimson, Green, Yellow, Orange
    //Violet, Pink, Puce, Indigo, Teal, Red
    String colorFamily = "";

    //INK TONER
    //Magenta, CMY, Black, Cyan, CMY+B, Yellow
    String color = "";
    //Toner, Ink
    String supplyType = "";
    //XL, Med, Large, Small
    String packSize = "";
    //Standard, High
    String yieldType = "";

    //PAPER
    //8.5X11, 8.5X11.7, 8X11, 8.5X10.5, 9X11.7, 9X11, 9X10.5
    String sheetDimension = "";
    //60, 16, 20, 32, 28, 50
    String paperWeight = "";
    //96 White, Bright Variety, 92 White, 98 White,
    String paperColor = "";
    //50, 100, 150
    int packQuantity = -1;



    public CustomerControl(SQLExecutor sqlExecutor){
        this.sqlExecutor = sqlExecutor;
        sqlExecutor.startConnection("sa", "");
    }

    public ArrayList<HBox> getTop5Products(){
        String query = "select * from saleitem";
        return null;
    }

    //add a department deliminator
    public void addDeptName(String dept_name){
        this.dept_name = dept_name;
    }

    //add price range delim
    public void addPriceRange(int upper_price, int lower_price){
        this.upper_price = upper_price;
        this.lower_price = lower_price;
    }

    public boolean setSubDeptName(String sub_dept_name){
        String query = "select * from sub_department natural join department where department_name='"
                + dept_name + "' AND sub_dept_name= '" + sub_dept_name + "' ;";

        ResultSet rs;
        try{
            rs = sqlExecutor.executeQuery(query);
            if(!rs.first()){
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        this.sub_dept_name = sub_dept_name;
        return true;
    }

}
