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
    String nameStr;

    int III = 0;

    public ClientView(String host, String port, String nameStr) {
        this.nameStr = nameStr;

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

        msgArea = new TextArea();
        msgArea.setPromptText("在此接受到消息");
        msgArea.setFocusTraversable(false);
        gridOfText.add(msgArea, 0, 0);

        writeArea = new TextArea();
        writeArea.setPromptText("在此写下发送的消息");
        writeArea.setFocusTraversable(false);
        gridOfText.add(writeArea, 0 ,1);

        this.setScene(new Scene(gridOfText, 500, 500));

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
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 1000;
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
}

