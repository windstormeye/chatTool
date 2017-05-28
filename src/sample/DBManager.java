package sample;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by pjpjpj on 2017/5/15.
 */

public class DBManager {
    // 连接mysql数据库
    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/chatDB?characterEncoding=utf8&useSSL=true";
        // 改为相应的mysql用户名和密码
        String username = "root";
        String password = "woaiwoziji123";
        Connection conn = null;
        try {
            //classLoader,加载对应动
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 插入
    public static int insert(User user) {
        Connection conn = getConn();
        int i = 0;
        // 在此可更改sql语句
        String sql = "insert into User (Uno, Uname, Upw) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUno());
            pstmt.setString(2, user.getUname());
            pstmt.setString(3, user.getUpw());

            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 插入当前用户表
    public static int insertCurrentUser(String name) {
        Connection conn = getConn();
        int i = 0;
        // 在此可更改sql语句
        String sql = "insert into currentUser (U_name) values(?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, name);

            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 查询某个用户是不是存在数据库中
    public static HashMap search(User user) {
        Connection conn = getConn();
        // 在此可更改sql语句
        String sql = "select * from User where Uno='" + user.getUno() + "'and Upw='" + user.getUpw() + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            // 创建保存用户信息的字典
            HashMap<String , String> map = new HashMap<String , String>();
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    if (i == 1)
                        map.put("Uno", rs.getString(i));
                    if (i == 2)
                        map.put("Uname", rs.getString(i));
                }
                return map;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新
    public static int update(User user) {
        Connection conn = getConn();
        // 在此可更改sql语句
        int i = 0;
        String sql = "update User set Uname='" + user.getUname() + "' where Uno='" + user.getUno() + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 获得全部User表信息
    public static Integer getAll() {
        Connection conn = getConn();
        String sql = "select * from User";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 删除
    public static int delete(String name) {
        Connection conn = getConn();
        int i = 0;
        String sql = "delete from User where Uname='" + name + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 插入数据到消息表中
    public int insertToMessage(String Uname, String Mes, String timeID) {
        Connection conn = getConn();
        int i = 0;
        // 在此可更改sql语句
        String sql = "insert into Message (Uname, Mes, Mid) values(?,?,?)";
        PreparedStatement pstmt;

        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, Uname);
            pstmt.setString(2, Mes);
            pstmt.setString(3, timeID);

            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
