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

    // 系统默认重载了开始窗体的方法
    @Override
    public void start(Stage primaryStage) throws Exception{
        // 设置主界面窗体标题
        primaryStage.setTitle("在线聊天室");

        // ======================================================
        // 登录页面
        // ======================================================
        GridPane gridOfLogin = new GridPane();
        // 居中显示
        gridOfLogin.setAlignment(Pos.CENTER);
        // 设置各个控件间的水平距离
        gridOfLogin.setHgap(10);
        // 设置各个控件间的垂直距离
        gridOfLogin.setVgap(10);
        // 设置各个控件间的内边距
        gridOfLogin.setPadding(new Insets(10, 10, 0, 10));

        // 学号标签
        Label nameLabel = new Label("学号:");
        gridOfLogin.add(nameLabel, 0, 0);

        // 学号文本框
        TextField nameTxt = new TextField();
        nameTxt.setPromptText("请输入学号");
        nameTxt.setPrefSize(100, 20);
        gridOfLogin.add(nameTxt, 0, 1);

        // 密码标签
        Label pwLabel = new Label("密码:");
        gridOfLogin.add(pwLabel, 0, 2);

        // 密码文本框
        PasswordField pwTxt = new PasswordField();
        pwTxt.setPromptText("请输入密码");
        pwTxt.setPrefSize(100, 20);
        gridOfLogin.add(pwTxt, 0, 3);

        // 水平布局
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        gridOfLogin.add(hbox, 0, 4);

        Button signupBtn = new Button("注册");
        signupBtn.setOnAction(event -> {
            // ======================================================
            // 注册页面
            // ======================================================

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
            // 设置未输入内容时需要显示的文本
            nameT.setPromptText("请输入学号");
            nameT.setPrefSize(100, 20);
            gridOfSignup.add(nameT, 0, 1);

            Label trueName = new Label("姓名:");
            gridOfSignup.add(trueName, 0, 2);

            TextField trueNameT = new TextField();
            // 设置未输入内容时需要显示的文本
            trueNameT.setPromptText("请输入姓名");
            trueNameT.setPrefSize(100, 20);
            gridOfSignup.add(trueNameT, 0, 3);

            Label pw = new Label("密码:");
            gridOfSignup.add(pw, 0, 4);

            TextField pwT = new TextField();
            // 设置未输入内容时需要显示的文本
            pwT.setPromptText("请输入密码");
            pwT.setPrefSize(100, 20);
            gridOfSignup.add(pwT, 0, 5);

            Button sureBtn = new Button("确定");
            gridOfSignup.add(sureBtn, 0, 6);
            sureBtn.setOnAction(event1 ->  {
                // 获取到文本框中输入的内容
                String Uno = nameT.getText();
                String Uname = trueNameT.getText();
                String Upw = pwT.getText();
                // 获取到数据库中受插入影响的列数
                int row = DBManager.insert(new User(Uno, Uname, Upw));
                // 如果受影响的列数不为0,则说明插入成功
                if (row != 0) {
                    Stage YESview  = new Stage();

                    // 垂直布局
                    VBox vbox = new VBox();
                    // 居中显示
                    vbox.setAlignment(Pos.CENTER);
                    Label yesLabel = new Label("注册成功");
                    Button yesBtn = new Button("确定");
                    yesBtn.setOnAction(event2 -> {
                        // 点击按钮后关闭名为YESview的窗体
                        YESview.close();
                    });
                    vbox.getChildren().addAll(yesLabel, yesBtn);

                    YESview.setScene(new Scene(vbox, 100, 100));
                    YESview.show();
                }

            });
            // 切换主界面为注册界面
            primaryStage.setScene(new Scene(gridOfSignup, 300, 275));
        });

        // 创建登录按钮
        Button loginBtn = new Button("登录");
        loginBtn.setOnAction(event -> {
            String Uno = nameTxt.getText();
            String Upw = pwTxt.getText();
            boolean isLogin =  DBManager.search(new User(Uno, Upw));
            if (isLogin) {
                Stage YESview  = new Stage();

                // 垂直布局
                VBox vbox = new VBox();
                // 居中显示
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

        // 添加控件到水平布局中
        hbox.getChildren().addAll(signupBtn, loginBtn);

        primaryStage.setScene(new Scene(gridOfLogin, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
