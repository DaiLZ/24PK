package priv.for24.db.databean;
//JavaBean 通过提供符合一致性设计模式的公共方法将内部域暴露成员属性，set和get方法获取

public class User{
    private String name;            //用户名
    private String password;       //密码
    private int integral;         //积分
    private int total;            //总局数
    private int win;              //胜
    private int lose;             //败
    private int tie;              //平
    private int high_grade;      //单局最高得分
    //用于传递用户信息 不需要密码
    public User(String name,int integral,int total,int win,int lose,int tie,int high_grade) {
        this.name = name;
        this.integral=integral;
        this.total=total;
        this.win=win;
        this.lose=lose;
        this.tie=tie;
        this.high_grade=high_grade;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public void setIntegral(int integral) {
    	this.integral=integral;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
    	this.total=total;
    }
    public int getWin() {
        return win;
    }
    public void setWin(int win) {
    	this.win=win;
    }
    public int getLose() {
        return lose;
    }
    public void setLose(int lose) {
    	this.lose=lose;
    }
    public int getTie() {
        return tie;
    }
    public void setTie(int tie) {
    	this.tie=tie;
    }
    public int getHigh_grade() {
        return high_grade;
    }
    public void setHigh_grade(int high_grade) {
        this.high_grade = high_grade;
    }
    public void addWin() {
        this.win += 1;
        this.total +=1;
        this.integral +=2;
    }
    public void addLose() {
        this.lose += 1;
        this.total +=1;
        this.integral -=2;
    }
    public void addTie() {
        this.tie += 1;
        this.total +=1;
        this.integral +=1;
    }
}