package priv.for24.db.databean;
//JavaBean ͨ���ṩ����һ�������ģʽ�Ĺ����������ڲ���¶��Ա���ԣ�set��get������ȡ

public class User{
    private String name;            //�û���
    private String password;       //����
    private int integral;         //����
    private int total;            //�ܾ���
    private int win;              //ʤ
    private int lose;             //��
    private int tie;              //ƽ
    private int high_grade;      //������ߵ÷�
    //���ڴ����û���Ϣ ����Ҫ����
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