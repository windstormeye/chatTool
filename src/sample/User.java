package sample;

/**
 * Created by pjpjpj on 2017/5/15.
 */
public class User {
    private String Uno;
    private String Uname;
    private String Upw;

    // 一旦自己定义了构造方法,则java自带的构造方法失效
    // 创建一个带全部参数的构造方法
    public User(String Uno, String Uname, String Upw) {
        this.Uno = Uno;
        this.Uname = Uname;
        this.Upw = Upw;
    }

    // 创建一个只带学号、密码的构造方法
    public User(String Uno, String Upw) {
        this.Uno = Uno;
        this.Upw = Upw;
    }

    public String getUno() {
        return Uno;
    }

    public void setUno(String uno) {
        Uno = uno;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUpw() {
        return Upw;
    }

    public void setUpw(String upw) {
        Upw = upw;
    }
}
