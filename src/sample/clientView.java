package sample;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pjpjpj on 2017/5/18.
 */
public class ClientView extends Stage {
    TextArea msgArea;
    TextArea writeArea;
    TextArea userArea;
    String nameStr;

    int III = 0;
    int UUU = 0;

    public ClientView(String host, String port, String nameStr) {
        this.nameStr = nameStr;

        DBManager dbManager = new DBManager();
        dbManager.insertCurrentUser(nameStr);

        this.setTitle("客户端");
        this.setX((Screen.getPrimary().getBounds().getWidth() - 600) / 2);
        this.setY((Screen.getPrimary().getBounds().getHeight() - 400) / 2);
        this.setWidth(600);
        this.setHeight(400);

        VBox vBox = new VBox();
        HBox hhBox = new HBox();

        msgArea = new TextArea();
        msgArea.setPromptText("在此接受到消息");
        msgArea.setFocusTraversable(false);
        msgArea.setEditable(false);
        hhBox.getChildren().add(msgArea);

        writeArea = new TextArea();
        writeArea.setPromptText("在此写下发送的消息");
        writeArea.setFocusTraversable(false);
        writeArea.setFocusTraversable(true);


        userArea = new TextArea();
        userArea.setPromptText("等待用户接入");
        userArea.setEditable(false);
        hhBox.getChildren().add(userArea);


        vBox.getChildren().addAll(hhBox, writeArea);

        this.setScene(new Scene(vBox, 1000, 1000));

        // 给writeArea设置回车键相应事件
        writeArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    // 获取当前时间
                    Date date=new Date();
                    DateFormat format=new SimpleDateFormat("MMddHHmmss");
                    String time=format.format(date);

                    // 插入数据到消息表中
                    DBManager dbManager = new DBManager();
                    dbManager.insertToMessage(nameStr, writeArea.getText(), time);
                    writeArea.clear();
                }
            }
        });



        // 启动定时器,每隔1秒执行一次定时器中的方法
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getMsg();
                getUser();
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 500;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }

    // 获取Message表中信息
    public void getMsg() {
        DBManager db = new DBManager();
        Connection conn =  db.getConn();
        String sql = "select * from Message";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.last(); //移到最后一行
            // 获取Message表中的行数
            int rowCount = rs.getRow();
            // 如果行数没有发生变化,则直接退出
            if (III == rowCount) {
                return;
            }
            // 否则更新行数统计变量
            III = rowCount;
            rs.beforeFirst(); //如果还要用结果集，就把指针再移到初始化的位置
            int col = rs.getMetaData().getColumnCount();
            String msg = "";
            String name = "";
            msgArea.clear();
            while (rs.next()) {
                // col是表中的列
                for (int i = 1; i <= col; i++) {
                    switch (i) {
                        case 1: name = rs.getString("Uname") + ":"; break;
                        case 2: msg = rs.getString("Mes") + "\n"; break;
                    }
                }
                // 追加textArea中内容
                msgArea.appendText(name + msg);
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取当前用户信息
    public void getUser() {
        DBManager db = new DBManager();
        Connection conn =  db.getConn();
        String sql = "select * from currentUser";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.last(); //移到最后一行
            // 获取Message表中的行数
            int rowCount = rs.getRow();
            // 如果行数没有发生变化,则直接退出
            if (UUU == rowCount) {
                return;
            }
            // 否则更新行数统计变量
            UUU = rowCount;
            rs.beforeFirst(); //如果还要用结果集，就把指针再移到初始化的位置
            int col = rs.getMetaData().getColumnCount();
            String name = "";
            msgArea.clear();
            while (rs.next()) {
                // col是表中的列
                for (int i = 1; i <= col; i++) {
                    switch (i) {
                        case 1: name = rs.getString("U_name") + "进入了聊天......" + "\n"; break;
                    }
                }
                // 追加textArea中内容
                userArea.appendText(name);
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

