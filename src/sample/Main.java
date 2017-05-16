package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("在线聊天室");

        // ======================================================
        // 登录页面
        // ======================================================
        GridPane gridOfLogin = new GridPane();
        gridOfLogin.setAlignment(Pos.CENTER);
        // 设置各个控件间的水平距离
        gridOfLogin.setHgap(10);
        // 设置各个控件间的垂直距离
        gridOfLogin.setVgap(10);
        // 设置各个控件间的内边距
        gridOfLogin.setPadding(new Insets(10, 10, 0, 10));

        Label nameLabel = new Label("学号:");
        gridOfLogin.add(nameLabel, 0, 0);

        TextField nameTxt = new TextField();
        nameTxt.setPromptText("请输入学号");
        nameTxt.setPrefSize(100, 20);
        gridOfLogin.add(nameTxt, 0, 1);

        Label pwLabel = new Label("密码:");
        gridOfLogin.add(pwLabel, 0, 2);

        PasswordField pwTxt = new PasswordField();
        pwTxt.setPromptText("请输入密码");
        pwTxt.setPrefSize(100, 20);
        gridOfLogin.add(pwTxt, 0, 3);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        gridOfLogin.add(hbox, 0, 4);

        Button signupBtn = new Button("注册");
        signupBtn.setOnAction(event -> {
            GridPane gridOfSignup = new GridPane();
            gridOfSignup.setAlignment(Pos.CENTER);
            // 设置各个控件间的水平距离
            gridOfSignup.setHgap(10);
            // 设置各个控件间的垂直距离
            gridOfSignup.setVgap(10);
            // 设置各个控件间的内边距
            gridOfSignup.setPadding(new Insets(10, 10, 0, 10));

            Label name = new Label("学号:");
            gridOfSignup.add(name, 0, 0);

            TextField nameT = new TextField();
            nameT.setPromptText("请输入学号");
            nameT.setPrefSize(100, 20);
            gridOfSignup.add(nameT, 0, 1);

            Label trueName = new Label("姓名:");
            gridOfSignup.add(trueName, 0, 2);

            TextField trueNameT = new TextField();
            trueNameT.setPromptText("请输入姓名");
            trueNameT.setPrefSize(100, 20);
            gridOfSignup.add(trueNameT, 0, 3);

            Label pw = new Label("密码:");
            gridOfSignup.add(pw, 0, 4);

            TextField pwT = new TextField();
            pwT.setPromptText("请输入密码");
            pwT.setPrefSize(100, 20);
            gridOfSignup.add(pwT, 0, 5);

            Button sureBtn = new Button("确定");
            gridOfSignup.add(sureBtn, 0, 6);
            sureBtn.setOnAction(event1 ->  {
                String Uno = nameT.getText();
                String Uname = trueNameT.getText();
                String Upw = pwT.getText();
                int row = DBManager.insert(new User(Uno, Uname, Upw));
                if (row != 0) {
                    Stage YESview  = new Stage();

                    VBox vbox = new VBox();
                    vbox.setAlignment(Pos.CENTER);
                    Label yesLabel = new Label("注册成功");
                    Button yesBtn = new Button("确定");
                    yesBtn.setOnAction(event2 -> {
                        YESview.close();
                    });
                    vbox.getChildren().addAll(yesLabel, yesBtn);

                    YESview.setScene(new Scene(vbox, 100, 100));
                    YESview.show();
                }

            });

            primaryStage.setScene(new Scene(gridOfSignup, 300, 275));
        });

        Button loginBtn = new Button("登录");
        loginBtn.setOnAction(event -> {
            String Uno = nameTxt.getText();
            String Upw = pwTxt.getText();
            boolean isLogin =  DBManager.search(new User(Uno, Upw));
            if (isLogin) {
                Stage YESview  = new Stage();

                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                Label yesLabel = new Label("登录成功");
                Button sureBtn = new Button("确定");
                sureBtn.setOnAction(event1 -> {
                    YESview.close();
                });

                vbox.getChildren().addAll(yesLabel, sureBtn);

                YESview.setScene(new Scene(vbox, 100, 100));
                YESview.show();
            }
        });

        hbox.getChildren().addAll(signupBtn, loginBtn);

        primaryStage.setScene(new Scene(gridOfLogin, 300, 275));
        primaryStage.show();

//        DBManager.getAll();
//        DBManager.insert(new User("2015011191", "Sunyifan", "111111"));
//        DBManager.getAll();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
