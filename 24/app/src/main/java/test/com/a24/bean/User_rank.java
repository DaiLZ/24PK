package test.com.a24.bean;

public class User_rank {
    private String name;
    private int high_grade;

    public User_rank(String name,int high_grade) {
        this.high_grade=high_grade;
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHigh_grade(){
        return high_grade;
    }
    public void setHigh_grade(){
        this.high_grade=high_grade;
    }

}
