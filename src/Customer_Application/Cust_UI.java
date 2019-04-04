package Customer_Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Cust_UI extends Application implements Runnable{


    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField search = new TextField();
        Button submit = new Button("Submit");
        HBox searchBar = new HBox();
        searchBar.getChildren().addAll(search, submit);

        //get top 5 products and their details

        VBox outerVB = new VBox();
        outerVB.getChildren().addAll(searchBar);
        Scene scene = new Scene(outerVB, 700, 500);

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
