package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by pjpjpj on 2017/5/18.
 */
public class SettingView extends Stage {
    public SettingView(HashMap map) {
        this.setTitle("设置");
        this.setX((Screen.getPrimary().getBounds().getWidth() - 300) / 2);
        this.setY((Screen.getPrimary().getBounds().getHeight() - 300) / 2);
        this.setWidth(300);
        this.setHeight(300);

        // 添加表格布局
        GridPane gridOfSetting = new GridPane();
        // 居中显示
        gridOfSetting.setAlignment(Pos.CENTER);
        // 设置各个控件间的水平距离
        gridOfSetting.setHgap(10);
        // 设置各个控件间的垂直距离
        gridOfSetting.setVgap(10);
        // 设置各个控件间的内边距
        gridOfSetting.setPadding(new Insets(10, 10, 0, 10));

        Label nameLabel = new Label("当前用户:" + ((String) map.get("Uname")));
        gridOfSetting.add(nameLabel,0,0);

        TextField hostTXT = new TextField();
        hostTXT.setPromptText("输入服务器地址");
        hostTXT.setFocusTraversable(false);

        gridOfSetting.add(hostTXT, 0, 1);

        TextField portTXT = new TextField();
        portTXT.setPromptText("输入服务器端口号");
        portTXT.setFocusTraversable(false);
        gridOfSetting.add(portTXT, 0, 2);

        Button sureBtn = new Button("确定");
        gridOfSetting.add(sureBtn, 0, 3);
        sureBtn.setOnAction(event -> {
            this.close();
            String hostStr = hostTXT.getText();
            String portStr = portTXT.getText();

            Stage choiceView = new Stage();
            choiceView.setTitle("选择");
            choiceView.setX((Screen.getPrimary().getBounds().getWidth() - 200) / 2);
            choiceView.setY((Screen.getPrimary().getBounds().getHeight() - 200) / 2);

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(15);
            Button serverBtn = new Button("成为服务器");
            serverBtn.setOnAction(event1 -> {
                ServerView serverView = new ServerView(hostStr, portStr);
                choiceView.close();
                serverView.show();
            });

            Button clientBtn = new Button("成为客户机");
            clientBtn.setOnAction(event2 -> {
                ClientView clientView = new ClientView(hostStr, portStr);
                choiceView.close();
                clientView.show();
            });
            hbox.getChildren().addAll(serverBtn, clientBtn);
            choiceView.setScene(new Scene(hbox, 200, 200));
            choiceView.show();
        });



        this.setScene(new Scene(gridOfSetting));
    }
}
