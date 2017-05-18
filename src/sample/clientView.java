package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by pjpjpj on 2017/5/18.
 */
public class ClientView extends Stage {
    public ClientView(String host, String port) {
        this.setTitle("客户端");
        this.setX((Screen.getPrimary().getBounds().getWidth() - 300) / 2);
        this.setY((Screen.getPrimary().getBounds().getHeight() - 300) / 2);
        this.setWidth(300);
        this.setHeight(300);

        GridPane gridOfText = new GridPane();
        // 居中显示
        gridOfText.setAlignment(Pos.CENTER);
        // 设置各个控件间的水平距离
        gridOfText.setHgap(10);
        // 设置各个控件间的垂直距离
        gridOfText.setVgap(10);
        // 设置各个控件间的内边距
        gridOfText.setPadding(new Insets(10, 10, 0, 10));

        TextArea msgArea = new TextArea();
        msgArea.setPromptText("在此接受到消息");
        msgArea.setFocusTraversable(false);
        gridOfText.add(msgArea, 0, 0);

        TextArea writeArea = new TextArea();
        writeArea.setPromptText("在此写下发送的消息");
        writeArea.setFocusTraversable(false);
        gridOfText.add(writeArea, 0 ,1);

        this.setScene(new Scene(gridOfText, 500, 500));
    }
}