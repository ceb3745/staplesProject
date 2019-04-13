package POS_Application;

import MainPk.SQLExecutor;
import POS_Application.Model.Cart;
import POS_Application.Model.Product;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class POS_UI extends Application implements Runnable {
    public int store_id = -1;
    public int member_id = -99;
    public boolean finalize = false;
    boolean cash = false;
    boolean credit = false;
    boolean saleDone = false;
    private static ClientControl cc;
    Cart myCart = new Cart();

    public POS_UI(){
        super();
    }

    public POS_UI(SQLExecutor executor) {
        super();
        cc = new ClientControl(executor);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        if(saleDone){
            Label staplesTitle = new Label("Staples");
            staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
            staplesTitle.setPrefWidth(700);
            VBox outerVB = new VBox();
            outerVB.setAlignment(Pos.TOP_CENTER);
            Label labelStoreNum = new Label("Store Number: " + String.valueOf(store_id));
            Label memberlabel = new Label("Member ID: " + String.valueOf(member_id));
            HBox tophb = new HBox();
            tophb.setAlignment(Pos.TOP_LEFT);
            tophb.getChildren().addAll(labelStoreNum, memberlabel);

            Label cart = new Label(myCart.listProducts() + "\n Total: " + myCart.getTotal(cc.getTeacher(member_id)));
            HBox product = new HBox();

            Label prompt = new Label("Sale Complete!");
            Button nextSale = new Button("Next Sale");
            product.getChildren().addAll(prompt, nextSale);

            nextSale.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    primaryStage.hide();
                    store_id = -1;
                    member_id = -99;
                    finalize = false;
                    cash = false;
                    credit = false;
                    myCart = new Cart();
                    saleDone = false;
                    start(new Stage());
                }
            });

            VBox vBox = new VBox();
            vBox.getChildren().addAll(cart, product);
            BorderPane bp = new BorderPane();
            bp.setCenter(vBox);

            outerVB.getChildren().addAll(staplesTitle, tophb, bp);
            Scene scene = new Scene(outerVB, 700, 500);

            primaryStage.setTitle("Staples POS Systems");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else {
            if (store_id == -1) {
                Label label1 = new Label("What is your Store Number?");
                Label staplesTitle = new Label("Staples");
                staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
                staplesTitle.setPrefWidth(700);
                VBox outerVB = new VBox();
                outerVB.setAlignment(Pos.TOP_CENTER);
                BorderPane root = new BorderPane();
                TextField textField = new TextField();


                outerVB.getChildren().addAll(staplesTitle, root);
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);
                VBox topvb = new VBox();
                topvb.getChildren().addAll(staplesTitle);
                label1.setStyle("-fx-padding: 10px;");
                vb.setSpacing(10);
                vb.setStyle("-fx-padding: 50px;");
                Button button = new Button("Submit");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String storenum = textField.getText();
                        if (storenum.equals("")) {
                            VBox vb = (VBox) root.getCenter();
                            Label errorField = new Label("Please enter a Store Number.");
                            errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");
                            vb.getChildren().add(1, errorField);
                            return;
                        }
                        //should confirm store num exists
                        store_id = Integer.parseInt(storenum);
                        if (cc.findStore(store_id) == false) {
                            VBox vb = (VBox) root.getCenter();
                            Label errorField = new Label("Please enter a VALID Store Number.");
                            errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");
                            vb.getChildren().add(1, errorField);
                            return;
                        }
                        primaryStage.hide();
                        start(new Stage());
                    }
                });

                vb.getChildren().addAll(label1, textField, button);


                root.setCenter(vb);
                root.setTop(topvb);

                Scene scene = new Scene(outerVB, 700, 500);

                primaryStage.setTitle("Staples POS Systems");
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                if (member_id == -99) {
                    Label staplesTitle = new Label("Staples");
                    staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
                    staplesTitle.setPrefWidth(700);
                    VBox outerVB = new VBox();
                    outerVB.setAlignment(Pos.TOP_CENTER);
                    Label labelStoreNum = new Label("Store Number: " + String.valueOf(store_id));
                    VBox topvb = new VBox();
                    topvb.setAlignment(Pos.TOP_LEFT);
                    topvb.getChildren().addAll(labelStoreNum);

                    BorderPane bp = new BorderPane();
                    Label lb = new Label("Are You a Member?");
                    Button yes = new Button("Yes");

                    Button no = new Button("No");


                    Button yes2 = new Button("Yes");

                    Button no2 = new Button("No");

                    //fn, ln, phone #, member_id, member_type, email
                    Label fnLBL = new Label("First Name: ");
                    TextField fn = new TextField();
                    HBox fnHB = new HBox();
                    fnHB.getChildren().addAll(fnLBL, fn);

                    Label lnLBL = new Label("Last Name: ");
                    TextField ln = new TextField();
                    HBox lnHB = new HBox();
                    lnHB.getChildren().addAll(lnLBL, ln);

                    Label pnLBL = new Label("Phone Number ex. (XXX)XXX-XXXX : ");
                    TextField pn = new TextField();
                    HBox pnHB = new HBox();
                    pnHB.getChildren().addAll(pnLBL, pn);

                    ToggleGroup mt = new ToggleGroup();
                    Label mtLBL = new Label("Member Type: ");
                    RadioButton teacher = new RadioButton("Teacher");
                    RadioButton regular = new RadioButton("Regular");
                    teacher.setToggleGroup(mt);
                    regular.setToggleGroup(mt);
                    VBox mtVB = new VBox();
                    mtVB.getChildren().addAll(mtLBL, teacher, regular);

                    Label emailLBL = new Label("Email ex. me@gmail.com : ");
                    TextField email = new TextField();
                    HBox emailHB = new HBox();
                    emailHB.getChildren().addAll(emailLBL, email);

                    Button memberSubmit = new Button("Submit");


                    VBox newMember = new VBox();
                    newMember.getChildren().addAll(fnHB, lnHB, pnHB, mtVB, emailHB, memberSubmit);

                    VBox vbox = new VBox();
                    vbox.getChildren().addAll(lb, yes, no);
                    bp.setCenter(vbox);
                    Button submit = new Button("Submit");
                    TextField input = new TextField();

                    yes.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            vbox.getChildren().removeAll(yes, no);
                            lb.setText("What is your Member ID?");
                            vbox.getChildren().addAll(input, submit);
                        }
                    });

                    no.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            vbox.getChildren().removeAll(yes, no);
                            lb.setText("Do You Want to Become a Member?");

                            vbox.getChildren().addAll(yes2, no2);
                        }
                    });

                    no2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            vbox.getChildren().removeAll(yes, no);
                            member_id = -1;
                            primaryStage.hide();
                            start(new Stage());
                        }
                    });

                    yes2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            vbox.getChildren().removeAll(yes2, no2);
                            lb.setText("Please enter your member info: ");
                            vbox.getChildren().addAll(newMember);
                        }
                    });

                    memberSubmit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            RadioButton rmt = (RadioButton) mt.getSelectedToggle();
                            if(fn.getText().equals("")){
                                Label errorField = new Label("Bad First Name Entry.");
                                errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");

                                vbox.getChildren().add(0, errorField);
                            }
                            else if(ln.getText().equals("")){
                                Label errorField = new Label("Bad Last Name Entry.");
                                errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");

                                vbox.getChildren().add(0, errorField);
                            }
                            else if(pn.getText().equals("")){
                                Label errorField = new Label("Bad Phone Number Entry.");
                                errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");

                                vbox.getChildren().add(0, errorField);
                            }
                            else if(rmt == null ){
                                Label errorField = new Label("Please select a member type.");
                                errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");

                                vbox.getChildren().add(0, errorField);
                            }
                            else if(email.getText().equals("")){
                                Label errorField = new Label("Bad Email Entry.");
                                errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");

                                vbox.getChildren().add(0, errorField);
                            }else{
                                vbox.getChildren().remove(0);
                                member_id = cc.becomeAMember(fn.getText(), ln.getText(), pn.getText(), rmt.getText(), email.getText());
                                vbox.getChildren().removeAll(newMember);
                                primaryStage.hide();
                                start(new Stage());
                            }

                        }
                    });

                    submit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (input.getText().equals("") || !cc.findMember(Integer.parseInt(input.getText()))) {
                                Label error = new Label("Please enter a VALID member ID");
                                error.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");
                                vbox.getChildren().add(1, error);
                                return;
                            }
                            member_id = Integer.parseInt(input.getText());
                            primaryStage.hide();
                            start(new Stage());

                        }
                    });

                    outerVB.getChildren().addAll(staplesTitle, topvb, bp);
                    Scene scene = new Scene(outerVB, 700, 500);

                    primaryStage.setTitle("Staples POS Systems");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } else {
                    if (!finalize) {
                        myCart.setMember_id(member_id);
                        Label staplesTitle = new Label("Staples");
                        staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
                        staplesTitle.setPrefWidth(700);
                        VBox outerVB = new VBox();
                        outerVB.setAlignment(Pos.TOP_CENTER);
                        Label labelStoreNum = new Label("Store Number: " + String.valueOf(store_id));
                        Label memberlabel = new Label("Member ID: " + String.valueOf(member_id));
                        HBox tophb = new HBox();
                        tophb.setAlignment(Pos.TOP_LEFT);
                        tophb.getChildren().addAll(labelStoreNum, memberlabel);


                        BorderPane bp = new BorderPane();
                        Label cart = new Label(myCart.listProducts());
                        HBox product = new HBox();
                        Label prompt = new Label("UPC: ");
                        TextField textField = new TextField();
                        Button submit = new Button("Submit");
                        product.getChildren().addAll(prompt, textField, submit);
                        VBox innervbox = new VBox();
                        innervbox.getChildren().addAll(cart, product);
                        bp.setCenter(innervbox);
                        Button completeSale = new Button("Complete Sale");
                        bp.setBottom(completeSale);
                        completeSale.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (myCart.listProducts() == null) {
                                    Label error = new Label("please enter a UPC, sale NULL");
                                    error.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");
                                    bp.setTop(error);
                                    return;
                                }
                                finalize = true;
                                primaryStage.hide();
                                start(new Stage());
                            }
                        });

                        submit.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (!textField.getText().equals("") && cc.confirmProduct(textField.getText())) {
                                    myCart.addItem(cc.getProduct(textField.getText()));
                                    primaryStage.hide();
                                    start(new Stage());
                                    return;

                                }
                                Label error = new Label("product number INVALID please enter new one");
                                error.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");
                                bp.setTop(error);

                            }
                        });

                        //setup bp for POS

                        outerVB.getChildren().addAll(staplesTitle, tophb, bp);
                        Scene scene = new Scene(outerVB, 700, 500);

                        primaryStage.setTitle("Staples POS Systems");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } else {
                        if (credit != true && cash != true) {
                            myCart.setMember_id(member_id);
                            Label staplesTitle = new Label("Staples");
                            staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
                            staplesTitle.setPrefWidth(700);
                            VBox outerVB = new VBox();
                            outerVB.setAlignment(Pos.TOP_CENTER);
                            Label labelStoreNum = new Label("Store Number: " + String.valueOf(store_id));
                            Label memberlabel = new Label("Member ID: " + String.valueOf(member_id));
                            HBox tophb = new HBox();
                            tophb.setAlignment(Pos.TOP_LEFT);
                            tophb.getChildren().addAll(labelStoreNum, memberlabel);

                            Label cart = new Label(myCart.listProducts() + "\n Total: " + myCart.getTotal(cc.getTeacher(member_id)));
                            HBox product = new HBox();

                            Button creditCard = new Button("Credit Card");
                            Button cashB = new Button("Cash");
                            product.getChildren().addAll(creditCard, cashB);

                            creditCard.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    //enter card screen
                                    credit = true;
                                    primaryStage.hide();
                                    start(new Stage());
                                }
                            });

                            cashB.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    // enter cash screen
                                    cash = true;
                                    primaryStage.hide();
                                    start(new Stage());
                                }
                            });

                            VBox vBox = new VBox();
                            vBox.getChildren().addAll(cart, product);
                            BorderPane bp = new BorderPane();
                            bp.setCenter(vBox);

                            outerVB.getChildren().addAll(staplesTitle, tophb, bp);
                            Scene scene = new Scene(outerVB, 700, 500);

                            primaryStage.setTitle("Staples POS Systems");
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        } else if (credit) {
                            Label staplesTitle = new Label("Staples");
                            staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
                            staplesTitle.setPrefWidth(700);
                            VBox outerVB = new VBox();
                            outerVB.setAlignment(Pos.TOP_CENTER);
                            Label labelStoreNum = new Label("Store Number: " + String.valueOf(store_id));
                            Label memberlabel = new Label("Member ID: " + String.valueOf(member_id));
                            HBox tophb = new HBox();
                            tophb.setAlignment(Pos.TOP_LEFT);
                            tophb.getChildren().addAll(labelStoreNum, memberlabel);

                            Label cart = new Label(myCart.listProducts() + "\n Total: " + myCart.getTotal(cc.getTeacher(member_id)));
                            HBox product = new HBox();

                            Label creditCardNo = new Label("Card Number: ");
                            TextField creditCardNum = new TextField();
                            Button submit = new Button("Submit Sale");
                            product.getChildren().addAll(creditCardNo, creditCardNum, submit);

                            submit.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
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

                                    cc.addSale(creditCardNum.getText(), member_id, "Card", time, Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
                                    for (int i = 0; i < myCart.getCartSize(); i++) {
                                        Product temp = myCart.getProduct(i);
                                        cc.addItem(temp.getUPC(), temp.getQuantity());
                                    }

                                    saleDone = true;
                                    primaryStage.hide();
                                    start(new Stage());
                                }
                            });

                            VBox vBox = new VBox();
                            vBox.getChildren().addAll(cart, product);
                            BorderPane bp = new BorderPane();
                            bp.setCenter(vBox);

                            outerVB.getChildren().addAll(staplesTitle, tophb, bp);
                            Scene scene = new Scene(outerVB, 700, 500);

                            primaryStage.setTitle("Staples POS Systems");
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        } else {
                            Label staplesTitle = new Label("Staples");
                            staplesTitle.setStyle("-fx-alignment: center; -fx-font-size: 190%; -fx-padding: 10px");
                            staplesTitle.setPrefWidth(700);
                            VBox outerVB = new VBox();
                            outerVB.setAlignment(Pos.TOP_CENTER);
                            Label labelStoreNum = new Label("Store Number: " + String.valueOf(store_id));
                            Label memberlabel = new Label("Member ID: " + String.valueOf(member_id));
                            HBox tophb = new HBox();
                            tophb.setAlignment(Pos.TOP_LEFT);
                            tophb.getChildren().addAll(labelStoreNum, memberlabel);

                            Label cart = new Label(myCart.listProducts() + "\n Total: " + myCart.getTotal(cc.getTeacher(member_id)));
                            HBox product = new HBox();

                            Label cashAmount = new Label("Cash Amount: ");
                            TextField cashNum = new TextField();
                            Button submit = new Button("Submit Sale");
                            product.getChildren().addAll(cashAmount, cashNum, submit);

                            VBox vBox = new VBox();
                            vBox.getChildren().addAll(cart, product);
                            BorderPane bp = new BorderPane();
                            bp.setCenter(vBox);

                            submit.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    float total = myCart.getTotal(cc.getTeacher(member_id));
                                    if (total > Float.parseFloat(cashNum.getText())) {
                                        Label errorField = new Label("Please give more cash than the given total.");
                                        errorField.setStyle("-fx-text-fill: red; -fx-pref-height: 20px");
                                        vBox.getChildren().add(1, errorField);
                                        return;
                                    }
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

                                    cc.addSale(-1 + "", member_id, "Card", time, Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
                                    for (int i = 0; i < myCart.getCartSize(); i++) {

                                        Product temp = myCart.getProduct(i);
                                        System.out.println(temp.getUPC());
                                        cc.addItem(temp.getUPC(), temp.getQuantity());
                                    }

                                    product.getChildren().removeAll(cashAmount, cashNum, submit);
                                    Label totalNum = new Label("Total: " + total + " \n");
                                    Label change = new Label("Change: " + (Float.parseFloat(cashNum.getText()) - total));
                                    product.getChildren().addAll(totalNum, change);

                                    saleDone = true;
                                    primaryStage.hide();
                                    start(new Stage());

                                }
                            });

                            outerVB.getChildren().addAll(staplesTitle, tophb, bp);
                            Scene scene = new Scene(outerVB, 700, 500);

                            primaryStage.setTitle("Staples POS Systems");
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void run() {
        main(null);
    }
}
