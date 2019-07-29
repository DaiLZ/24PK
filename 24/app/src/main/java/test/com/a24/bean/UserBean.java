package test.com.a24.bean;

public class UserBean {
    //变量名跟JSON数据字段名必须一致
    private String username;            //用户名
    private String password;       //密码
    private int integral;         //积分
    private int total;            //总局数
    private int win;              //胜
    private int lose;             //败
    private int tie;              //平
    private int high_grade;      //单局最高得分

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getIntegral() {
        return integral;
    }
    public int getTotal() {
        return total;
    }
    public int getWin() {
        return win;
    }
    public int getLose() {
        return lose;
    }
    public int getTie() {
        return tie;
    }
    public int getHigh_grade() {
        return high_grade;
    }
    public void setHigh_grade(int high_grade) {
        this.high_grade = high_grade;
    }
}