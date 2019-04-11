package Customer_Application;

import MainPk.SQLExecutor;
import POS_Application.ClientControl;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.ArrayList;


public class Cust_UI extends Application implements Runnable{

    private static CustomerControl cc;

    public Cust_UI() {
        super();
    }

    public Cust_UI(SQLExecutor executor) {
        super();
        this.cc = new CustomerControl(executor);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollBar sc = new ScrollBar();
        Group root = new Group();
        TextField search = new TextField();
        search.setStyle("-fx-min-width: 500px;");
        Button submit = new Button("Submit");
        submit.setStyle("-fx-min-width: 80px;");
        Button filters = new Button("filters");
        filters.setStyle("-fx-min-width: 80px;");
        HBox searchBar = new HBox();
        searchBar.getChildren().addAll(search, submit, filters);

        //get top 5 products and their details

        VBox outerVB = new VBox();
        ArrayList<VBox> productsHBoxes = cc.getTop5Products();
        VBox innerVB = new VBox();
        for(int i=0; i<productsHBoxes.size(); i++){
            innerVB.getChildren().addAll(productsHBoxes.get(i));
        }
        outerVB.getChildren().addAll(searchBar, innerVB);
        root.getChildren().addAll( outerVB, sc);
        Scene scene = new Scene(root, 700, 500);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                innerVB.getChildren().remove(0,innerVB.getChildren().size());
                ArrayList<VBox> productsHBoxes = cc.search(search.getText());
                if(productsHBoxes == null || productsHBoxes.size() == 0){
                    Label errorLbl = new Label("no products found");
                    innerVB.getChildren().addAll(errorLbl);
                    return;
                }
                for(int i=0; i<productsHBoxes.size(); i++){
                    innerVB.getChildren().addAll(productsHBoxes.get(i));
                }
            }
        });


        filters.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.hide();
                root.getChildren().remove(0);
                VBox filtersVB = new VBox();

                //ram toggles
                Label ramLBL = new Label("Choose RAM: ");
                ToggleGroup ram = new ToggleGroup();
                RadioButton ramOne = new RadioButton("8");
                ramOne.setToggleGroup(ram);
                RadioButton ramTwo = new RadioButton("16");
                ramTwo.setToggleGroup(ram);
                RadioButton ramThree = new RadioButton("24");
                ramThree.setToggleGroup(ram);
                Button clearRam = new Button("Clear");
                VBox ramVB = new VBox();
                ramVB.getChildren().addAll(ramLBL, ramOne, ramTwo, ramThree);

                //os
                //Windows 10, Windows 9, Windows 8, mac OSX

                Label osLBL = new Label("Choose OS: ");
                ToggleGroup os = new ToggleGroup();
                RadioButton osOne = new RadioButton("Windows 10");
                osOne.setToggleGroup(os);
                RadioButton osTwo = new RadioButton("Windows 9");
                osTwo.setToggleGroup(os);
                RadioButton osThree = new RadioButton("Windows 8");
                osThree.setToggleGroup(os);
                RadioButton osFour = new RadioButton("mac OSX");
                osFour.setToggleGroup(os);
                VBox osVB = new VBox();
                osVB.getChildren().addAll(osLBL, osOne, osTwo, osThree, osFour);

                //processor
                //AMD Ryzen 3, Intel i5, Intel i7, Intel i3, Intel UHD, Intel Pentium
                Label processorLBL = new Label("Choose Processor: ");
                ToggleGroup processor = new ToggleGroup();
                RadioButton processorOne = new RadioButton("AMD Ryzen 3");
                processorOne.setToggleGroup(processor);
                RadioButton processorTwo = new RadioButton("Intel i5");
                processorTwo.setToggleGroup(processor);
                RadioButton processorThree = new RadioButton("Intel i7");
                processorThree.setToggleGroup(processor);
                RadioButton processorFour = new RadioButton("Intel i3");
                processorFour.setToggleGroup(processor);
                RadioButton processorFive = new RadioButton("Intel UHD");
                processorFive.setToggleGroup(processor);
                RadioButton processorSix = new RadioButton("Intel Pentium");
                processorSix.setToggleGroup(processor);
                VBox processorVB = new VBox();
                processorVB.getChildren().addAll(processorLBL, processorOne, processorTwo, processorThree, processorFour, processorFive, processorSix);

                //ssd
                //1TB, 700GB, 650GB, 500GB, 450GB, 320GB
                Label hhdLBL = new Label("Choose HHD: ");
                ToggleGroup hhd = new ToggleGroup();
                RadioButton hhdOne = new RadioButton("1TB");
                hhdOne.setToggleGroup(hhd);
                RadioButton hhdTwo = new RadioButton("700GB");
                hhdTwo.setToggleGroup(hhd);
                RadioButton hhdThree = new RadioButton("650GB");
                hhdThree.setToggleGroup(hhd);
                RadioButton hhdFour = new RadioButton("500GB");
                hhdFour.setToggleGroup(hhd);
                RadioButton hhdFive = new RadioButton("450GB");
                hhdFive.setToggleGroup(hhd);
                RadioButton hhdSix = new RadioButton("320GB");
                hhdSix.setToggleGroup(hhd);
                VBox hhdVB = new VBox();
                hhdVB.getChildren().addAll(hhdLBL, hhdOne, hhdTwo, hhdThree, hhdFour, hhdFive, hhdSix);

                //screensize
                //14", 15.6", 17.3"
                Label ssLBL = new Label("Choose Screen Size: ");
                ToggleGroup ss = new ToggleGroup();
                RadioButton ssOne = new RadioButton("14\"");
                ssOne.setToggleGroup(ss);
                RadioButton ssTwo = new RadioButton("15.6\"");
                ssTwo.setToggleGroup(ss);
                RadioButton ssThree = new RadioButton("17.3\"");
                ssThree.setToggleGroup(ss);
                VBox ssVB = new VBox();
                ssVB.getChildren().addAll(ssLBL, ssOne, ssTwo, ssThree);

                //touchsreen
                //Yes, No
                Label tsLBL = new Label("Choose Touch Screen: ");
                ToggleGroup ts = new ToggleGroup();
                RadioButton tsOne = new RadioButton("Yes");
                tsOne.setToggleGroup(ts);
                RadioButton tsTwo = new RadioButton("No");
                tsTwo.setToggleGroup(ts);
                VBox tsVB = new VBox();
                tsVB.getChildren().addAll(tsLBL, tsOne, tsTwo);

                //display type
                //LCD, LED backlight
                Label displayLBL = new Label("Choose Display Type: ");
                ToggleGroup display = new ToggleGroup();
                RadioButton displayOne = new RadioButton("LCD");
                displayOne.setToggleGroup(display);
                RadioButton displayTwo = new RadioButton("LED backlight");
                displayTwo.setToggleGroup(display);
                VBox displayVB = new VBox();
                displayVB.getChildren().addAll(displayLBL, displayOne, displayTwo);

                //audio
                //Stereo Speakers, Integrated High Definition Audio
                Label audioLBL = new Label("Choose Audio Type: ");
                ToggleGroup audio = new ToggleGroup();
                RadioButton audioOne = new RadioButton("Stereo Speakers");
                audioOne.setToggleGroup(audio);
                RadioButton audioTwo = new RadioButton("Integrated High Definition Audio");
                audioTwo.setToggleGroup(audio);
                VBox audioVB = new VBox();
                audioVB.getChildren().addAll(audioLBL, audioOne, audioTwo);

                //DESKTOPS
                //height
                Label desktopHeight = new Label("Maximum Desktop Height: ");
                TextField desktopHeightInput = new TextField();
                HBox heightD = new HBox();
                heightD.getChildren().addAll(desktopHeight, desktopHeightInput);
                //width
                Label desktopWidth = new Label("Maximum Desktop Width: ");
                TextField desktopWidthInput = new TextField();
                HBox widthD = new HBox();
                widthD.getChildren().addAll(desktopWidth, desktopWidthInput);
                //depth
                Label desktopDepth = new Label("Maximum Desktop Depth: ");
                TextField desktopDepthInput = new TextField();
                HBox depthD = new HBox();
                depthD.getChildren().addAll(desktopDepth, desktopDepthInput);

                //optical drive
                Label odLBL = new Label("Optical Drive: ");
                ToggleGroup od = new ToggleGroup();
                RadioButton odOne = new RadioButton("1");
                odOne.setToggleGroup(od);
                RadioButton odTwo = new RadioButton("0");
                odTwo.setToggleGroup(od);
                VBox odVB = new VBox();
                odVB.getChildren().addAll(odLBL,odOne, odTwo);

                //FURNITURE
                //height
                Label furnitureHeight = new Label("Maximum Furniture Height: ");
                TextField furnitureHeightInput = new TextField();
                HBox heightF = new HBox();
                heightF.getChildren().addAll(furnitureHeight, furnitureHeightInput);
                //width
                Label furnitureWidth = new Label("Maximum Furniture Width: ");
                TextField furnitureWidthInput = new TextField();
                HBox widthF = new HBox();
                widthF.getChildren().addAll(furnitureWidth, furnitureWidthInput);
                //depth
                Label furnitureDepth = new Label("Maximum Furniture Depth: ");
                TextField furnitureDepthInput = new TextField();
                HBox depthF = new HBox();
                depthF.getChildren().addAll(furnitureDepth, furnitureDepthInput);

                //requiresAssembly
                Label raLBL = new Label("Requires Assembly: ");
                ToggleGroup ra = new ToggleGroup();
                RadioButton raOne = new RadioButton("1");
                raOne.setToggleGroup(ra);
                RadioButton raTwo = new RadioButton("0");
                raTwo.setToggleGroup(ra);
                VBox raVB = new VBox();
                raVB.getChildren().addAll(raLBL,raOne, raTwo);

                //colorFamily
                //Fuscia, Mauv, Goldenrod, Purple, Turquoise, Aquamarine, Maroon, Blue, Khaki, Crimson, Green,
                Label cfLBL = new Label("Color Family: ");
                ToggleGroup cf = new ToggleGroup();
                RadioButton cfOne = new RadioButton("Fuscia");
                cfOne.setToggleGroup(cf);
                RadioButton cfTwo = new RadioButton("Mauv");
                cfTwo.setToggleGroup(cf);
                RadioButton cfThree = new RadioButton("Goldrenrod");
                cfThree.setToggleGroup(cf);
                RadioButton cfFour = new RadioButton("Purple");
                cfFour.setToggleGroup(cf);
                RadioButton cfFive = new RadioButton("Turquoise");
                cfFive.setToggleGroup(cf);
                RadioButton cfSix = new RadioButton("Aquamarine");
                cfSix.setToggleGroup(cf);
                RadioButton cfSeven = new RadioButton("Maroon");
                cfSeven.setToggleGroup(cf);
                RadioButton cfEight = new RadioButton("Blue");
                cfEight.setToggleGroup(cf);
                RadioButton cfNine = new RadioButton("Khaki");
                cfNine.setToggleGroup(cf);
                RadioButton cfTen = new RadioButton("Crimson");
                cfTen.setToggleGroup(cf);
                //Yellow, Orange
                //Violet, Pink, Puce, Indigo, Teal, Red
                RadioButton cfEleven = new RadioButton("Green");
                cfEleven.setToggleGroup(cf);
                RadioButton cfTwelve = new RadioButton("Yellow");
                cfTwelve.setToggleGroup(cf);
                RadioButton cfThirteen = new RadioButton("Orange");
                cfThirteen.setToggleGroup(cf);
                RadioButton cfFourteen = new RadioButton("Violet");
                cfFourteen.setToggleGroup(cf);
                RadioButton cfFifteen = new RadioButton("Pink");
                cfFifteen.setToggleGroup(cf);
                RadioButton cfSixteen = new RadioButton("Puce");
                cfSixteen.setToggleGroup(cf);
                RadioButton cfSeventeen = new RadioButton("Indigo");
                cfSeventeen.setToggleGroup(cf);
                RadioButton cfEighteen = new RadioButton("Teal");
                cfEighteen.setToggleGroup(cf);
                RadioButton cfNineteen = new RadioButton("Red");
                cfNineteen.setToggleGroup(cf);
                VBox cfVB = new VBox();
                cfVB.getChildren().addAll(cfLBL, cfOne, cfTwo, cfThree, cfFour, cfFive, cfSix, cfSeven, cfEight,
                        cfNine, cfTen, cfEleven, cfTwelve, cfThirteen, cfFourteen, cfFifteen, cfSixteen,
                        cfSeventeen, cfEighteen, cfNineteen);

                //INKTONER color
                //Magenta, CMY, Black, Cyan, CMY+B, Yellow
                Label itcLBL = new Label("Select Ink/Toner Color: ");
                ToggleGroup itc = new ToggleGroup();
                RadioButton itcOne = new RadioButton("Magenta");
                itcOne.setToggleGroup(itc);
                RadioButton itcTwo = new RadioButton("CMY");
                itcTwo.setToggleGroup(itc);
                RadioButton itcThree = new RadioButton("Black");
                itcThree.setToggleGroup(itc);
                RadioButton itcFour = new RadioButton("Cyan");
                itcFour.setToggleGroup(itc);
                RadioButton itcFive = new RadioButton("CMY+B");
                itcFive.setToggleGroup(itc);
                RadioButton itcSix = new RadioButton("Yellow");
                itcSix.setToggleGroup(itc);
                VBox itcVB = new VBox();
                itcVB.getChildren().addAll(itcLBL, itcOne, itcTwo, itcThree, itcFour, itcFive, itcSix);

                //type
                Label ittLBL = new Label("Select Ink/Toner: ");
                ToggleGroup itt = new ToggleGroup();
                RadioButton ittOne = new RadioButton("Ink");
                ittOne.setToggleGroup(itt);
                RadioButton ittTwo = new RadioButton("Toner");
                ittTwo.setToggleGroup(itt);
                VBox ittVB = new VBox();
                ittVB.getChildren().addAll(ittLBL, ittOne, ittTwo);

                //packSize
                //XL, Med, Large, Small
                Label psLBL = new Label("Select Pack Size: ");
                ToggleGroup ps = new ToggleGroup();
                RadioButton psOne = new RadioButton("XL");
                psOne.setToggleGroup(ps);
                RadioButton psTwo = new RadioButton("Med");
                psTwo.setToggleGroup(ps);
                RadioButton psThree = new RadioButton("Large");
                psThree.setToggleGroup(ps);
                RadioButton psFour = new RadioButton("Small");
                psFour.setToggleGroup(ps);
                VBox psVB = new VBox();
                psVB.getChildren().addAll(psLBL, psOne, psTwo, psThree, psFour);

                //yield type
                //Standard, High
                Label ytLBL = new Label("Select Yield Type: ");
                ToggleGroup yt = new ToggleGroup();
                RadioButton ytOne = new RadioButton("Standard");
                ytOne.setToggleGroup(yt);
                RadioButton ytTwo = new RadioButton("High");
                ytTwo.setToggleGroup(yt);
                VBox ytVB = new VBox();
                ytVB.getChildren().addAll(ytLBL, ytOne, ytTwo);

                //paper
                //sheet dim
                //8.5X11, 8.5X11.7, 8X11, 8.5X10.5, 9X11.7, 9X11, 9X10.5
                Label sdLBL = new Label("Select Sheet Dimension: ");
                ToggleGroup sd = new ToggleGroup();
                RadioButton sdOne = new RadioButton("8.5Xll");
                sdOne.setToggleGroup(sd);
                RadioButton sdTwo = new RadioButton("8.5X11.7");
                sdTwo.setToggleGroup(sd);
                RadioButton sdThree = new RadioButton("8X11");
                sdThree.setToggleGroup(sd);
                RadioButton sdFour = new RadioButton("8.5X10.5");
                sdFour.setToggleGroup(sd);
                RadioButton sdFive = new RadioButton("9X11.7");
                sdFive.setToggleGroup(sd);
                RadioButton sdSix = new RadioButton("9X11");
                sdSix.setToggleGroup(sd);
                RadioButton sdSeven = new RadioButton("9X10.5");
                sdSeven.setToggleGroup(sd);
                VBox sdVB = new VBox();
                sdVB.getChildren().addAll(sdLBL, sdOne, sdTwo, sdThree, sdFour, sdFive, sdSix, sdSeven);

                //paper weight
                //60, 16, 20, 32, 28, 50
                Label pwLBL = new Label("Select Paper Weight: ");
                ToggleGroup pw = new ToggleGroup();
                RadioButton pwOne = new RadioButton("60");
                pwOne.setToggleGroup(pw);
                RadioButton pwTwo = new RadioButton("16");
                pwTwo.setToggleGroup(pw);
                RadioButton pwThree = new RadioButton("20");
                pwThree.setToggleGroup(pw);
                RadioButton pwFour = new RadioButton("32");
                pwFour.setToggleGroup(pw);
                RadioButton pwFive = new RadioButton("28");
                pwFive.setToggleGroup(pw);
                RadioButton pwSix = new RadioButton("50");
                pwSix.setToggleGroup(pw);
                VBox pwVB = new VBox();
                pwVB.getChildren().addAll(pwLBL, pwOne, pwTwo, pwThree, pwFour, pwFive, pwSix);

                //paper color
                //96 White, Bright Variety, 92 White, 98 White,
                Label pcLBL = new Label("Select Paper Color: ");
                ToggleGroup pc = new ToggleGroup();
                RadioButton pcOne = new RadioButton("96 White");
                pcOne.setToggleGroup(pc);
                RadioButton pcTwo = new RadioButton("Bright Valley");
                pcTwo.setToggleGroup(pc);
                RadioButton pcThree = new RadioButton("92 White");
                pcThree.setToggleGroup(pc);
                RadioButton pcFour = new RadioButton("98 White");
                pcFour.setToggleGroup(pc);
                VBox pcVB = new VBox();
                pcVB.getChildren().addAll(pcLBL, pcOne, pcTwo, pcThree, pcFour);

                //pack quantity
                //50, 100, 150
                Label pqLBL = new Label("Select Paper Quantity: ");
                ToggleGroup pq = new ToggleGroup();
                RadioButton pqOne = new RadioButton("50");
                pqOne.setToggleGroup(pq);
                RadioButton pqTwo = new RadioButton("100");
                pqTwo.setToggleGroup(pq);
                RadioButton pqThree = new RadioButton("150");
                pqThree.setToggleGroup(pq);
                VBox pqVB = new VBox();
                pqVB.getChildren().addAll(pqLBL, pqOne, pqTwo, pqThree);

                //PRINTER
                //Inkjet, LaserJet
                Label ptLBL = new Label("Select Printer Type: ");
                ToggleGroup pt = new ToggleGroup();
                RadioButton ptOne = new RadioButton("Inkjet");
                ptOne.setToggleGroup(pt);
                RadioButton ptTwo = new RadioButton("LaserJet");
                ptTwo.setToggleGroup(pt);
                VBox ptVB = new VBox();
                ptVB.getChildren().addAll(ptLBL, ptOne, ptTwo);

                //wireless ready
                //Yes, No
                Label wrLBL = new Label("Select Wireless Ready: ");
                ToggleGroup wr = new ToggleGroup();
                RadioButton wrOne = new RadioButton("Yes");
                wrOne.setToggleGroup(wr);
                RadioButton wrTwo = new RadioButton("No");
                wrTwo.setToggleGroup(wr);
                VBox wrVB = new VBox();
                wrVB.getChildren().addAll(wrLBL, wrOne, wrTwo);

                //print technology
                //Business, Personal, Small Business
                Label ptechLBL = new Label("Select Print Technology: ");
                ToggleGroup ptech = new ToggleGroup();
                RadioButton ptechOne = new RadioButton("Business");
                ptechOne.setToggleGroup(ptech);
                RadioButton ptechTwo = new RadioButton("Personal");
                ptechTwo.setToggleGroup(ptech);
                RadioButton ptechThree = new RadioButton("Small Business");
                ptechThree.setToggleGroup(ptech);
                VBox ptechVB = new VBox();
                ptechVB.getChildren().addAll(ptechLBL, ptechOne, ptechTwo, ptechThree);

                //mobile compatability
                //Yes, No
                Label mcLBL = new Label("Select Mobile Compatability: ");
                ToggleGroup mc = new ToggleGroup();
                RadioButton mcOne = new RadioButton("Yes");
                mcOne.setToggleGroup(mc);
                RadioButton mcTwo = new RadioButton("No");
                mcTwo.setToggleGroup(mc);
                VBox mcVB = new VBox();
                mcVB.getChildren().addAll(mcLBL, mcOne, mcTwo);

                //outputType
                //monochrome, color
                Label otLBL = new Label("Select Output Type: ");
                ToggleGroup ot = new ToggleGroup();
                RadioButton otOne = new RadioButton("monochrome");
                otOne.setToggleGroup(ot);
                RadioButton otTwo = new RadioButton("color");
                otTwo.setToggleGroup(ot);
                VBox otVB = new VBox();
                otVB.getChildren().addAll(otLBL, otOne, otTwo);

                Button computers = new Button("Computers");
                Button computerSubmit = new Button("Submit");
                Button desktops = new Button("Desktops");
                Button desktopSubmit = new Button("Submit");
                Button laptops = new Button("Laptops");
                Button laptopSubmit = new Button("Submit");
                Button printers = new Button("Printers");
                Button printerSubmit = new Button("Submit");
                Button furniture = new Button("Furniture");
                Button furnitureSubmit = new Button("Submit");
                Button inktoner = new Button("Ink and Toner");
                Button inktonerSubmit = new Button("Submit");
                Button paper = new Button("Paper");
                Button paperSubmit = new Button("Submit");

                /*
                filtersVB.getChildren().addAll(ramVB, osVB, processorVB, hhdVB, ssVB, tsVB, displayVB, audioVB, heightD, widthD, depthD,
                        odVB, heightF, widthF, depthF, raVB, cfVB, itcVB, ittVB, psVB, ytVB, sdVB, pwVB, pcVB, pqVB, ptVB, wrVB, ptechVB,
                        mcVB, otVB);
                */
                filtersVB.getChildren().addAll(computers, printers, furniture, inktoner, paper);
                computers.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(0, filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll(ramVB, osVB, processorVB, hhdVB, desktops, laptops, computerSubmit);
                    }
                });

                desktops.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(filtersVB.getChildren().size() - 3 , filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll( heightD, widthD, depthD, odVB, desktopSubmit);

                    }
                });

                laptops.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(filtersVB.getChildren().size() - 3 , filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll( ssVB, tsVB, displayVB, audioVB, laptopSubmit);
                    }
                });

                printers.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(0, filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll(ptVB, wrVB, ptechVB, mcVB, otVB, printerSubmit);
                    }
                });

                furniture.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(0, filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll( heightF, widthF, depthF, raVB, cfVB, furnitureSubmit);
                    }
                });

                inktoner.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(0, filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll( itcVB, ittVB, psVB, ytVB, inktonerSubmit);
                    }
                });

                paper.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        filtersVB.getChildren().remove(0, filtersVB.getChildren().size());
                        filtersVB.getChildren().addAll( sdVB, pwVB, pcVB, pqVB, paperSubmit);
                    }
                });

                //search computers
                computerSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String ramS = "";
                        String osS = "";
                        String processorS = "";
                        String hhdS = "";

                        RadioButton selectedram = (RadioButton) ram.getSelectedToggle();
                        if(selectedram != null){
                            ramS = selectedram.getText();
                        }
                        RadioButton selectedos = (RadioButton) os.getSelectedToggle();
                        if(selectedos != null){
                            osS = selectedos.getText();
                        }
                        RadioButton selectedprocessor = (RadioButton) processor.getSelectedToggle();
                        if(selectedprocessor != null){
                            processorS = selectedprocessor.getText();
                        }
                        RadioButton selectedhhd = (RadioButton) hhd.getSelectedToggle();
                        if(selectedhhd != null){
                            hhdS = selectedhhd.getText();
                        }

                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.computerSearch(ramS, osS, processorS, hhdS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);

                    }
                });

                //heightF, widthF, depthF, raVB, cfVB
                furnitureSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String heightFS = "";
                        String widthFS = "";
                        String depthFS = "";
                        String raS = "";
                        String cfS = "";

                        TextField selectedheightF = (TextField)heightF.getChildren().get(1);
                        if(selectedheightF != null){
                            heightFS = selectedheightF.getText();
                        }
                        TextField selectedwidthF = (TextField)widthF.getChildren().get(1);
                        if(selectedwidthF != null){
                            widthFS = selectedwidthF.getText();
                        }
                        TextField selecteddepthF = (TextField)depthF.getChildren().get(1);
                        if(selecteddepthF != null){
                            depthFS = selecteddepthF.getText();
                        }
                        RadioButton selectedra = (RadioButton) ra.getSelectedToggle();
                        if(selectedra != null){
                            raS = selectedra.getText();
                        }
                        RadioButton selectedcf = (RadioButton) cf.getSelectedToggle();
                        if(selectedcf != null){
                            cfS = selectedcf.getText();
                        }

                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.furnitureSearch(heightFS, widthFS, depthFS, raS, cfS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);
                    }
                });

                //sdVB, pwVB, pcVB, pqVB
                paperSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String sdS = "";
                        String pwS = "";
                        String pcS = "";
                        String pqS = "";

                        RadioButton selectedsd = (RadioButton) sd.getSelectedToggle();
                        if(selectedsd != null){
                            sdS = selectedsd.getText();
                        }
                        RadioButton selectedpw = (RadioButton) pw.getSelectedToggle();
                        if(selectedpw != null){
                            pwS = selectedpw.getText();
                        }
                        RadioButton selectedpc = (RadioButton) pc.getSelectedToggle();
                        if(selectedpc != null){
                            pcS = selectedpc.getText();
                        }
                        RadioButton selectedpq = (RadioButton) pq.getSelectedToggle();
                        if(selectedpq != null){
                            pqS = selectedpq.getText();
                        }

                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.paperSearch(sdS, pwS, pcS, pqS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);
                    }
                });

                //itcVB, ittVB, psVB, ytVB
                inktonerSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String sdS = "";
                        String pwS = "";
                        String pcS = "";
                        String pqS = "";

                        RadioButton selectedsd = (RadioButton) itc.getSelectedToggle();
                        if(selectedsd != null){
                            sdS = selectedsd.getText();
                        }
                        RadioButton selectedpw = (RadioButton) itt.getSelectedToggle();
                        if(selectedpw != null){
                            pwS = selectedpw.getText();
                        }
                        RadioButton selectedpc = (RadioButton) ps.getSelectedToggle();
                        if(selectedpc != null){
                            pcS = selectedpc.getText();
                        }
                        RadioButton selectedpq = (RadioButton) yt.getSelectedToggle();
                        if(selectedpq != null){
                            pqS = selectedpq.getText();
                        }

                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.inktonerSearch(sdS, pwS, pcS, pqS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);
                    }
                });



                //laptopSubmit
                laptopSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String ramS = "";
                        String osS = "";
                        String processorS = "";
                        String hhdS = "";
                        String screensizeS = "";
                        String touchscreenS = "";
                        String displaytypeS = "";
                        String audioS = "";

                        RadioButton selectedram = (RadioButton) ram.getSelectedToggle();
                        if(selectedram != null){
                            ramS = selectedram.getText();
                        }
                        RadioButton selectedos = (RadioButton) os.getSelectedToggle();
                        if(selectedos != null){
                            osS = selectedos.getText();
                        }
                        RadioButton selectedprocessor = (RadioButton) processor.getSelectedToggle();
                        if(selectedprocessor != null){
                            processorS = selectedprocessor.getText();
                        }
                        RadioButton selectedhhd = (RadioButton) hhd.getSelectedToggle();
                        if(selectedhhd != null){
                            hhdS = selectedhhd.getText();
                        }
                        RadioButton selectedss = (RadioButton) ss.getSelectedToggle();
                        if(selectedss != null){
                            screensizeS = selectedss.getText();
                        }
                        RadioButton selectedts = (RadioButton) ts.getSelectedToggle();
                        if(selectedts != null){
                            touchscreenS = selectedts.getText();
                        }
                        RadioButton selecteddt = (RadioButton) display.getSelectedToggle();
                        if(selecteddt != null){
                            displaytypeS = selecteddt.getText();
                        }
                        RadioButton selectedaudio = (RadioButton) audio.getSelectedToggle();
                        if(selectedaudio != null){
                            audioS = selectedaudio.getText();
                        }

                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.laptopSearch(ramS, osS, processorS, hhdS, screensizeS, touchscreenS, displaytypeS, audioS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);
                    }
                });

                //desktopSubmit
                desktopSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String ramS = "";
                        String osS = "";
                        String processorS = "";
                        String hhdS = "";
                        String heightS = "";
                        String widthS = "";
                        String depthS = "";
                        String odS = "";

                        RadioButton selectedram = (RadioButton) ram.getSelectedToggle();
                        if(selectedram != null){
                            ramS = selectedram.getText();
                        }
                        RadioButton selectedos = (RadioButton) os.getSelectedToggle();
                        if(selectedos != null){
                            osS = selectedos.getText();
                        }
                        RadioButton selectedprocessor = (RadioButton) processor.getSelectedToggle();
                        if(selectedprocessor != null){
                            processorS = selectedprocessor.getText();
                        }
                        RadioButton selectedhhd = (RadioButton) hhd.getSelectedToggle();
                        if(selectedhhd != null){
                            hhdS = selectedhhd.getText();
                        }
                        TextField heightT = (TextField)heightD.getChildren().get(1);
                        if(heightT != null){
                            heightS = heightT.getText();
                        }
                        TextField widthT = (TextField)widthD.getChildren().get(1);
                        if(widthT != null){
                            widthS = widthT.getText();
                        }
                        TextField depthT = (TextField)depthD.getChildren().get(1);
                        if(depthT != null){
                            depthS = depthT.getText();
                        }
                        RadioButton selectedod = (RadioButton) od.getSelectedToggle();
                        if(selectedod != null){
                            odS = selectedod.getText();
                        }

                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.desktopSearch(ramS, osS, processorS, hhdS, heightS, widthS, depthS, odS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);
                    }
                });

                printerSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        root.getChildren().remove(0, root.getChildren().size());
                        String ptS = "";
                        String wrS = "";
                        String ptechS = "";
                        String mcS = "";
                        String otS = "";

                        RadioButton selectpt = (RadioButton) pt.getSelectedToggle();
                        if(selectpt != null){
                            ptS = selectpt.getText();
                        }
                        RadioButton selectedwr = (RadioButton) wr.getSelectedToggle();
                        if(selectedwr != null){
                            wrS = selectedwr.getText();
                        }
                        RadioButton selectedptech = (RadioButton) ptech.getSelectedToggle();
                        if(selectedptech != null){
                            ptechS = selectedptech.getText();
                        }
                        RadioButton selectedmc = (RadioButton) mc.getSelectedToggle();
                        if(selectedmc != null){
                            mcS = selectedmc.getText();
                        }
                        RadioButton selectot = (RadioButton) ot.getSelectedToggle();
                        if(selectot != null){
                            otS = selectot.getText();
                        }


                        innerVB.getChildren().remove(0,innerVB.getChildren().size());
                        ArrayList<VBox> productsHBoxes = cc.printerSearch(ptS, wrS, ptechS, mcS, otS);
                        if(productsHBoxes == null || productsHBoxes.size() == 0){
                            Label errorLbl = new Label("no products found");
                            innerVB.getChildren().addAll(errorLbl);
                            return;
                        }
                        for(int i=0; i<productsHBoxes.size(); i++){
                            innerVB.getChildren().addAll(productsHBoxes.get(i));
                        }
                        root.getChildren().addAll( outerVB, sc);
                    }
                });


                root.getChildren().addAll(filtersVB);
                primaryStage.setTitle("Staples POS Systems: Filters");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        });

        //scrollbarstuff
        sc.setLayoutX(scene.getWidth()-(sc.getWidth()));
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setStyle("-fx-block-increment: 50; -fx-unit-increment: 10;");
        sc.setPrefHeight(500);
        sc.setMax(1000);

        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                outerVB.setLayoutY(-new_val.doubleValue());
            }
        });

        primaryStage.setTitle("Staples POS Systems");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void run() {
        main(null);
    }
}
