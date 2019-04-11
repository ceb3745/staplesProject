package Customer_Application;

import MainPk.SQLExecutor;
import POS_Application.Model.Product;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

    //heightF, widthF, depthF, raVB, cfVB
    public ArrayList<VBox> furnitureSearch(String height, String width, String depth, String ra, String cf){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from furniture natural join product");
        boolean heightB = (!height.equals(""));
        boolean widthB = (!width.equals(""));
        boolean depthB = (!depth.equals(""));
        boolean raB = (!ra.equals(""));
        boolean cfB = (!cf.equals(""));
        if(heightB || widthB || depthB || raB || cfB){
            sb.append(" where ");
            if(!height.equals("")){
                sb.append("height<'" + height + "' ");
            }
            if(!width.equals("")){
                if(heightB){sb.append(" AND ");}
                sb.append("width<'" + width + "' ");
            }
            if(!depth.equals("")){
                if(heightB || widthB){sb.append(" AND ");}
                sb.append("depth<'" + depth + "' ");
            }
            if(!ra.equals("")){
                if(heightB || widthB || depthB){sb.append(" AND ");}
                sb.append("requires_assembly='" + ra + "' ");
            }
            if(!cf.equals("")){
                if(heightB || widthB || depthB || raB){sb.append(" AND ");}
                sb.append("color_family='" + cf + "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> laptopSearch(String ram, String os, String processor, String hhd, String ss, String ts, String dt, String audio){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from laptop natural join computer natural join product");
        boolean ramB = (!ram.equals(""));
        boolean osB = (!os.equals(""));
        boolean processorB = (!processor.equals(""));
        boolean hhdB = (!hhd.equals(""));
        boolean ssB = (!ss.equals(""));
        boolean tsB = (!ts.equals(""));
        boolean dtB = (!dt.equals(""));
        boolean audioB = (!audio.equals(""));
        if(ramB || osB || processorB || hhdB || ssB || tsB || dtB || audioB){
            sb.append(" where ");
            if(!ram.equals("")){
                sb.append("ram='" + ram + "' ");
            }
            if(!os.equals("")){
                if(ramB){sb.append(" AND ");}
                sb.append("os='" + os + "' ");
            }
            if(!processor.equals("")){
                if(osB || ramB){sb.append(" AND ");}
                sb.append("processor='" + processor+ "' ");
            }
            if(!hhd.equals("")){
                if(hhdB || ramB || osB){sb.append(" AND ");}
                sb.append("ssd='" + hhd+ "' ");
            }
            if(!ss.equals("")){
                if(hhdB || ramB || osB || processorB){sb.append(" AND ");}
                sb.append("screen_size='" + ss + "' ");
            }
            if(!ts.equals("")){
                if(hhdB || ramB || osB || processorB || ssB){sb.append(" AND ");}
                sb.append("touchscreen='" + ts + "' ");
            }
            if(!dt.equals("")){
                if(hhdB || ramB || osB || processorB || ssB || tsB){sb.append(" AND ");}
                sb.append("displaytype='" + dt + "' ");
            }
            if(!audio.equals("")){
                if(hhdB || ramB || osB || processorB || ssB || tsB || dtB){sb.append(" AND ");}
                sb.append("audio='" + audio + "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> desktopSearch(String ram, String os, String processor, String hhd, String height, String width, String depth, String od){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from desktop natural join computer natural join product");
        boolean ramB = (!ram.equals(""));
        boolean osB = (!os.equals(""));
        boolean processorB = (!processor.equals(""));
        boolean hhdB = (!hhd.equals(""));
        boolean ssB = (!height.equals(""));
        boolean tsB = (!width.equals(""));
        boolean dtB = (!depth.equals(""));
        boolean audioB = (!od.equals(""));
        if(ramB || osB || processorB || hhdB || ssB || tsB || dtB || audioB){
            sb.append(" where ");
            if(!ram.equals("")){
                sb.append("ram='" + ram + "' ");
            }
            if(!os.equals("")){
                if(ramB){sb.append(" AND ");}
                sb.append("os='" + os + "' ");
            }
            if(!processor.equals("")){
                if(osB || ramB){sb.append(" AND ");}
                sb.append("processor='" + processor+ "' ");
            }
            if(!hhd.equals("")){
                if(hhdB || ramB || osB){sb.append(" AND ");}
                sb.append("ssd='" + hhd+ "' ");
            }
            if(!height.equals("")){
                if(hhdB || ramB || osB || processorB){sb.append(" AND ");}
                sb.append("height<'" + height + "' ");
            }
            if(!width.equals("")){
                if(hhdB || ramB || osB || processorB || ssB){sb.append(" AND ");}
                sb.append("width<'" + width + "' ");
            }
            if(!depth.equals("")){
                if(hhdB || ramB || osB || processorB || ssB || tsB){sb.append(" AND ");}
                sb.append("depth<'" + depth + "' ");
            }
            if(!od.equals("")){
                if(hhdB || ramB || osB || processorB || ssB || tsB || dtB){sb.append(" AND ");}
                sb.append("optical_drive='" + od + "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> printerSearch(String typeOfPrinter, String wirelessReady, String printTechnology, String mobileCapability, String outputType){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from printer natural join product");
        boolean typeOfPrinterB = (!typeOfPrinter.equals(""));
        boolean wirelessReadyB = (!wirelessReady.equals(""));
        boolean printTechnologyB = (!printTechnology.equals(""));
        boolean mobileCapabilityB = (!mobileCapability.equals(""));
        boolean outputTypeB = (!outputType.equals(""));
        if(typeOfPrinterB || wirelessReadyB || printTechnologyB || mobileCapabilityB || outputTypeB){
            sb.append(" where ");
            if(!typeOfPrinter.equals("")){
                sb.append("type_of_printer='" + typeOfPrinter + "' ");
            }
            if(!wirelessReady.equals("")){
                if(typeOfPrinterB){sb.append(" AND ");}
                sb.append("wireless_ready='" + wirelessReady + "' ");
            }
            if(!printTechnology.equals("")){
                if(wirelessReadyB || typeOfPrinterB){sb.append(" AND ");}
                sb.append("print_technology='" + printTechnology + "' ");
            }
            if(!mobileCapability.equals("")){
                if(printTechnologyB || typeOfPrinterB || wirelessReadyB){sb.append(" AND ");}
                sb.append("mobile_capability='" + mobileCapability + "' ");
            }
            if(!outputType.equals("")){
                if(printTechnologyB || typeOfPrinterB || wirelessReadyB || mobileCapabilityB){sb.append(" AND ");}
                sb.append("output_type='" + outputType + "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    //itcVB, ittVB, psVB, ytVB
    public ArrayList<VBox> inktonerSearch(String color, String technology, String packSize, String yieldType){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from ink_toner natural join product");
        boolean typeOfPrinterB = (!color.equals(""));
        boolean wirelessReadyB = (!technology.equals(""));
        boolean printTechnologyB = (!packSize.equals(""));
        boolean mobileCapabilityB = (!yieldType.equals(""));
        if(typeOfPrinterB || wirelessReadyB || printTechnologyB || mobileCapabilityB){
            sb.append(" where ");
            if(!color.equals("")){
                sb.append("color='" + color + "' ");
            }
            if(!technology.equals("")){
                if(typeOfPrinterB){sb.append(" AND ");}
                sb.append("supply_type='" + technology + "' ");
            }
            if(!packSize.equals("")){
                if(wirelessReadyB || typeOfPrinterB){sb.append(" AND ");}
                sb.append("pack_size='" + packSize + "' ");
            }
            if(!yieldType.equals("")){
                if(printTechnologyB || typeOfPrinterB || wirelessReadyB){sb.append(" AND ");}
                sb.append("yield_type='" + yieldType + "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> paperSearch(String sheetDimension, String paperWeight, String paperColor, String packQuantity){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from paper natural join product");
        boolean typeOfPrinterB = (!sheetDimension.equals(""));
        boolean wirelessReadyB = (!paperWeight.equals(""));
        boolean printTechnologyB = (!paperColor.equals(""));
        boolean mobileCapabilityB = (!packSize.equals(""));
        if(typeOfPrinterB || wirelessReadyB || printTechnologyB || mobileCapabilityB){
            sb.append(" where ");
            if(!sheetDimension.equals("")){
                sb.append("sheet_dimension='" + sheetDimension + "' ");
            }
            if(!paperWeight.equals("")){
                if(typeOfPrinterB){sb.append(" AND ");}
                sb.append("paper_weight='" + paperWeight + "' ");
            }
            if(!paperColor.equals("")){
                if(wirelessReadyB || typeOfPrinterB){sb.append(" AND ");}
                sb.append("paper_color='" + paperColor + "' ");
            }
            if(!packSize.equals("")){
                if(printTechnologyB || typeOfPrinterB || wirelessReadyB){sb.append(" AND ");}
                sb.append("pack_quantity='" + packSize + "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> computerSearch(String ram, String os, String processor, String hhd){
        sb = new StringBuilder();
        sb.append("select product.UPC, Product_name from computer natural join product");
        boolean ramB = (!ram.equals(""));
        boolean osB = (!os.equals(""));
        boolean processorB = (!processor.equals(""));
        boolean hhdB = (!hhd.equals(""));
        if(ramB || osB || processorB || hhdB){
            sb.append(" where ");
            if(!ram.equals("")){
                sb.append("ram='" + ram + "' ");
            }
            if(!os.equals("")){
                if(ramB){sb.append(" AND ");}
                sb.append("os='" + os + "' ");
            }
            if(!processor.equals("")){
                if(osB || ramB){sb.append(" AND ");}
                sb.append("processor='" + processor+ "' ");
            }
            if(!hhd.equals("")){
                if(hhdB || ramB || osB){sb.append(" AND ");}
                sb.append("ssd='" + hhd+ "' ");
            }
        }
        sb.append(";");
        String query = sb.toString();

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public CustomerControl(SQLExecutor sqlExecutor){
        this.sqlExecutor = sqlExecutor;
        sqlExecutor.startConnection("sa", "");
    }

    public ArrayList<VBox> getTop5Products(){
        String query = "with storeSalesProduct (product_name,UPC,quantity, storeID) as\n" +
                "(select product_name,product.UPC, saleitem.sale_quantity, sale.store_id from\n" +
                "product natural join saleitem natural join sale)\n" +
                "select UPC,Product_name, storeID,sum(quantity) as total_quantity from storeSalesProduct group by UPC " +
                "order by total_quantity desc limit 5";
        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            for(int i=0; i<5; i++){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public String getDetails(String UPC){
        String query = "select * from product where UPC='" + UPC + "';";
        ResultSet rs;
        String details = "";
        try{
            rs = sqlExecutor.executeQuery(query);
            rs.next();
            details = rs.getString(4);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
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

    public ArrayList<VBox> search(String searchTerm){
        String searchTermNew = "";
        if(searchTerm.length() != 0){
            searchTermNew = Character.toUpperCase(searchTerm.charAt(0)) + searchTerm.substring(1, searchTerm.length());
        }
        String query = "with storeSalesProduct (product_name,UPC,quantity, storeID) as\n" +
                "(select product_name,product.UPC, saleitem.sale_quantity, sale.store_id from\n" +
                "product natural join saleitem natural join sale)\n" +
                "select UPC,Product_name, storeID,sum(quantity) as total_quantity from storeSalesProduct " +
                "where product_name like " + "\'%" + searchTermNew + "%\'" +
                "group by UPC order by total_quantity desc limit 20;";

        ResultSet rs;
        ArrayList<VBox> arr = new ArrayList<>();
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            rs.next();
            if(!rs.first()){
                searchName(arr, searchTerm);
                return arr;
            }
            while(!rs.isAfterLast()){
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
            if(arr.size() < 20){
                searchName(arr, searchTerm);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> searchName(ArrayList<VBox> arr, String searchTerm){
        String searchTermNew = Character.toUpperCase(searchTerm.charAt(0)) + searchTerm.substring(1, searchTerm.length());
        String previousQuery = "with storeSalesProduct (product_name,UPC,quantity, storeID) as\n" +
                "(select product_name,product.UPC, saleitem.sale_quantity, sale.store_id from\n" +
                "product natural join saleitem natural join sale)\n" +
                "select product_name as total_quantity from storeSalesProduct " +
                "where product_name like " + "\'%" + searchTermNew + "%\'" +
                "group by UPC order by total_quantity desc limit 20";
        String query = "(select * from product where product_name like " + "\'%" + searchTermNew + "%\' and product_name NOT IN ("+ previousQuery + "));";

        ResultSet rs;
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            if(!rs.first()){
                searchDetails(arr, searchTerm);
                return arr;
            }
            rs.next();
            while(!rs.isAfterLast()){
                if(arr.size() == 20){
                    return arr;
                }
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
            if(arr.size() < 20){
                searchDetails(arr, searchTerm);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public ArrayList<VBox> searchDetails(ArrayList<VBox> arr, String searchTerm){
        String searchTermNew = Character.toUpperCase(searchTerm.charAt(0)) + searchTerm.substring(1, searchTerm.length());
        String previousQuery = "with storeSalesProduct (product_name,UPC,quantity, storeID) as\n" +
                "(select product_name,product.UPC, saleitem.sale_quantity, sale.store_id from\n" +
                "product natural join saleitem natural join sale)\n" +
                "select product_name as total_quantity from storeSalesProduct " +
                "where product_name like " + "\'%" + searchTermNew + "%\'" +
                "group by UPC order by total_quantity desc limit 20";
        String query = "(select * from product where details like " + "\'%" + searchTerm + "%\' and product_name NOT IN ("+ previousQuery + "));";

        ResultSet rs;
        try{
            String productName;
            String UPC;
            String details;

            rs = sqlExecutor.executeQuery(query);
            if(!rs.first()){
                return arr;
            }
            rs.next();
            while(!rs.isAfterLast()){
                if(arr.size() == 20){
                    return arr;
                }
                productName = rs.getString(2);
                UPC = rs.getString(1);
                details = getDetails(UPC);
                VBox innerVB = new VBox();
                Label productNameLabel = new Label(productName);
                productNameLabel.setStyle("-fx-font-size: 20px;");
                Label upcLabel = new Label(UPC);
                Label detailsLabel = new Label(details);
                detailsLabel.setStyle("-fx-padding: 10px;");
                upcLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 0px 0px 0px 10px;");
                innerVB.getChildren().addAll(productNameLabel, detailsLabel, upcLabel);
                innerVB.setStyle("-fx-background-color: lightgray; -fx-border-width: 3px; -fx-padding: 10px 0px 10px 0px;");
                rs.next();

                arr.add(innerVB);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

}
