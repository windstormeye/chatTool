package sample;

/**
 * Created by pjpjpj on 2017/5/15.
 */
public class User {
    private String Uno;
    private String Uname;
    private String Upw;

    public User(String Uno, String Uname, String Upw) {
        this.Uno = Uno;
        this.Uname = Uname;
        this.Upw = Upw;
    }

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
