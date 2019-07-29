package test.com.a24.bean;

public class QuestionBean {
    //变量名跟JSON数据字段名必须一致
    //解析时不使用变量名 需保持所有字段一致？
    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private String answer;
    private  int id; //pk 时的题号 1-10

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getNum1(){
        return num1;
    }
    public void setNum1(int num1){
        this.num1=num1;
    }
    public int getNum2(){
        return num2;
    }
    public void setNum2(int num2){
        this.num2=num2;
    }
    public int getNum3(){
        return num3;
    }
    public void setNum3(int num3){
        this.num3=num3;
    }
    public int getNum4(){
        return num4;
    }
    public void setNum4(int num4){
        this.num4=num4;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
