package POS_Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class POS_UI extends Application {
    public int store_id = -1;
    ClientControl cc = new ClientControl();

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
            outerVB.getChildren().addAll(staplesTitle, topvb, bp);
            Scene scene = new Scene(outerVB, 700, 500);

            primaryStage.setTitle("Staples POS Systems");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
