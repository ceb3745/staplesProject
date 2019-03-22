package POS_Application;

import MainPk.SQLExecutor;
import POS_Application.Model.Cart;
import POS_Application.Model.Product;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

public class POS_UI extends Application implements Runnable {
    public int store_id = -1;
    public int member_id = -99;
    public boolean finalize = false;
    private ClientControl cc = new ClientControl(new SQLExecutor());
    Cart myCart = new Cart();

    public POS_UI() {
        super();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        if(store_id == -1) {
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
                    if(cc.findStore(store_id) == false){
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
        }else{
            if(member_id == -99) {
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
                        member_id = -1;
                        primaryStage.hide();
                        start(new Stage());
                    }
                });

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cc.findMember(Integer.parseInt(input.getText()))) {
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

                no.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        vbox.getChildren().removeAll(lb, yes, no);
                    }
                });

                outerVB.getChildren().addAll(staplesTitle, topvb, bp);
                Scene scene = new Scene(outerVB, 700, 500);

                primaryStage.setTitle("Staples POS Systems");
                primaryStage.setScene(scene);
                primaryStage.show();
            }else{
                if(!finalize) {
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
                            if (cc.confirmProduct(textField.getText())) {
                                myCart.addItem(cc.getProduct(textField.getText()));
                                System.out.println("here");
                                primaryStage.hide();
                                start(new Stage());

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
                }
                else{
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

                    Label cart = new Label(myCart.listProducts());
                    HBox product = new HBox();
                    Label prompt = new Label("Credit Card: ");
                    TextField textField = new TextField();
                    Button submit = new Button("Submit");
                    product.getChildren().addAll(prompt, textField, submit);
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(cart, product);
                    BorderPane bp = new BorderPane();
                    bp.setCenter(vBox);

                    textField.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

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

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void run() {
        main(null);
    }
}
