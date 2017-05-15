package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        DBManager.getAll();
        DBManager.insert(new User("2015011206", "Wengpeijun", "111111"));
        DBManager.getAll();
        DBManager.update(new User("2015011191","Yiyi", "222222"));
        DBManager.delete("Yiyi");
        DBManager.getAll();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
