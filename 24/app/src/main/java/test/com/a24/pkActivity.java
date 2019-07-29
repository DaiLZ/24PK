package test.com.a24;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.com.a24.bean.JGradeBean;
import test.com.a24.bean.JQBean;
import test.com.a24.bean.JResultBean;
import test.com.a24.bean.QuestionBean;

public class pkActivity extends AppCompatActivity implements Runnable{

    @BindView(R.id.viewPager) ViewPager viewpager;
    @BindView(R.id._chro_exam) Chronometer chronometer;
    @BindView(R.id.Grade) TextView Grade;
    @BindView(R.id.Match) TextView Match;

    InputStream is = null;
    OutputStream os = null;
    Socket socket;
    BufferedReader in;

    public static final int TIME = 60;//1分钟
    private int time = TIME; //便于修改
    private ArrayList<Fragment> fragmentlists;
    private List<QuestionBean> qbeans;
    private int nowpager = 0;
    private int grade =0;
    String username="c";
    String matchname;

    int flag=0;   // 0 数字键可用 1运算符
    int flag1=0,flag2=0,flag3=0,flag4=0;  //为1表示此数字键 已使用
    int flagK=0;  // >0表示 左括号已经使用，可以使用右括号
    String text,text1="";
    ProgressDialog progressDialog=null;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        fragmentlists = new ArrayList<>();
        Grade.setText("当前得分："+grade+"");
//        chronometer.setText("-");
        //进度条对话框
        progressDialog = new ProgressDialog(pkActivity.this, R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("等待对手 获取题目...");
        progressDialog.setCancelable(false); //设置点击ProgressDialog外的区域 ProgressDialog不消失
        progressDialog.show();
        new Thread(this).start(); //启动一个新的线程
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
    }
    private void connection() {
       try {
            socket = new Socket(this.getResources().getString(R.string.IP), 5000);
            os = socket.getOutputStream();
            os.write(username.getBytes());//传递用户名
            socket.shutdownOutput();
            Gson  gson = new Gson();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
            final String Qs = in.readLine();
           JQBean questionBean = gson.fromJson(Qs,JQBean.class);
            qbeans = questionBean.getQbeans();
            matchname=questionBean.getMatchname();
           for (int i = 0; i < qbeans.size()+1; i++) {
               if(i==0){
                   QuestionBean question = qbeans.get(i);
                   question.setId(i+1);
                   AnswerFragment af =new AnswerFragment();
                   af.AnswerFragment(question);
                   fragmentlists.add(af);
               }else {
                   QuestionBean question = qbeans.get(i - 1);
                   question.setId(i);
                   AnswerFragment af = new AnswerFragment();
                   af.AnswerFragment(question);
                   fragmentlists.add(af);
               }
           }
            progressDialog.dismiss();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 如果对手先完成 会收到服务器的消息
    public void run() {  //Runable 必须包含
        connection();// 连接到服务器
        handler.post(new Runnable() {
            @Override
            public void run() {
                Match.setText("对手："+matchname);
                //设置适配器
                viewpager.setAdapter(new MainAdapter(getSupportFragmentManager()));
                viewpager.setCurrentItem(nowpager);
            }
        });
        setChronometer();
    }
    //设置倒计时
    private void setChronometer() {
        chronometer.start();
        //捕捉计时
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                time-= 1;
                chronometer.setText(time+"s");
                if (time==0){
                    if(nowpager<9){
                        time = TIME;
                        reset();
                        viewpager.setCurrentItem(++nowpager);//直接进入下一题 无需加分
                    }else{
                        submit(); //提交当前分数
                    }
                }
            }
        });
    }
    @OnClick({
            R.id.button_num1, R.id.button_num2, R.id.button_num3, R.id.button_num4,
            R.id.button_add, R.id.button_sub, R.id.button_mul, R.id.button_divide,
            R.id.button_left, R.id.button_right, R.id.button_reset,R.id.btn_leave,
            R.id.btn_submit
    })
/**
 * 1.输入时，仅控制数字只能用一次且不能连用
 * 2.初始状态：num1-4 （可用
 * 3.num1-4） 后可使用+-/*  if 能配对（  ）可用
 * 4.+-/*（ 后可使用1-4 （
 * */
    public void onClick(View view) {
        Button btn=findViewById(view.getId());// 得到点击的btn
        Button btn1=findViewById(R.id.button_num1);
        Button btn2=findViewById(R.id.button_num2);
        Button btn3=findViewById(R.id.button_num3);
        Button btn4=findViewById(R.id.button_num4);
        Button add=findViewById(R.id.button_add);
        Button sub=findViewById(R.id.button_sub);
        Button mul=findViewById(R.id.button_mul);
        Button div=findViewById(R.id.button_divide);
        Button left=findViewById(R.id.button_left);
        Button right=findViewById(R.id.button_right);
        EditText editText1 =findViewById(R.id.editText1);
        double proof=0.00000001;  //避免精度丢失造成23.9999之类情况
        text=editText1.getText().toString();
        switch (view.getId()) {
            case R.id.btn_leave: //用户可以中途离开 暂时不支持和对手再来一局
                sentgrade();
                Intent intent = new Intent(getApplicationContext(), startActivity.class);
//                只传递用户名 后用户名获取用户信息 考虑到需要实时更新用户数据
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.button_num1:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 1;flag1=1;
                break;
            case R.id.button_num2:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 1;flag2=1;
                break;
            case R.id.button_num3:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 1;flag3=1;
                break;
            case R.id.button_num4:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 1;flag4=1;
                break;
            case R.id.button_add:
            case R.id.button_sub:
            case R.id.button_mul:
            case R.id.button_divide:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 0;
                break;
            case R.id.button_left:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 0;flagK++;//可以连用（
                break;
            case R.id.button_right:
                text1=btn.getText().toString();
                editText1.setText(text + text1);
                flag= 1;flagK--; // )后还是运算符可用
                break;
            case R.id.button_reset:
                reset();
                break;
            case R.id.btn_submit:
                if (flag1==1 && flag2==1 && flag3==1 && flag4==1) {    //数字全都用上才能提交
                    if (flagK == 0&& flag==1){//括号正确 且不以运算符结尾
                        if (calculate(text) - 24 < proof && calculate(text) + proof > 24) {
                            //计分 下一题
                            grade += 100 - (TIME - time) * 1; //每花费1秒-1分
                            Grade.setText("得分：" + grade);
                            if (nowpager < 9) {
                                Toast.makeText(this, "回答正确！ 进入下一题", Toast.LENGTH_SHORT).show();
                            }else{
                                submit();
                            }
                            viewpager.setCurrentItem(++nowpager);
                        } else {
                            TextView A = findViewById(R.id.Answer);
                            String answer = A.getText().toString();
                            final AlertDialog.Builder answerDialog = new AlertDialog.Builder(pkActivity.this);
                            answerDialog.setTitle("回答错误");
                            answerDialog.setMessage("正确答案" + answer);
                            answerDialog.setCancelable(false);
                            answerDialog.setPositiveButton("下一题",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            if (nowpager == 9) submit();
                                            viewpager.setCurrentItem(++nowpager);
                                        }
                                    });
                            answerDialog.show();
                        }
                        time = TIME;
                        reset();
                     }else {
                        Toast.makeText(this, "注意（）匹配及式子的正确性！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "必须使用全部数字！", Toast.LENGTH_SHORT).show();
                }break;
            default: break;
        }
        if (flag == 0){
            // 数字键可用时，仅对未使用的恢复
            if (flag1 == 0) btn1.setEnabled(true);
            if (flag2 == 0) btn2.setEnabled(true);
            if (flag3 == 0) btn3.setEnabled(true);
            if (flag4 == 0) btn4.setEnabled(true);
            left.setEnabled(true);

            add.setEnabled(false);
            sub.setEnabled(false);
            mul.setEnabled(false);
            div.setEnabled(false);
            right.setEnabled(false);
        } else {
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            left.setEnabled(false);

            add.setEnabled(true);
            sub.setEnabled(true);
            mul.setEnabled(true);
            div.setEnabled(true);
            if (flagK>0){
                right.setEnabled(true);
            }
        }
    }
    public void submit(){
        chronometer.stop();//停止计时
        progressDialog = new ProgressDialog(pkActivity.this, R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("等待对手完成题目...");
        progressDialog.setCancelable(false); //设置点击ProgressDialog外的区域 ProgressDialog不消失
        progressDialog.show();
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(pkActivity.this);
        //创建匿名类实例，直接启动线程
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(getApplicationContext().getString(R.string.IP), 5001);
                    JGradeBean gradeBean = new JGradeBean();
                    gradeBean.setGrade(grade);
                    gradeBean.setUsername(username);
                    gradeBean.setMatchname(matchname);
                    Gson gson = new Gson();
                    String jsonObj = gson.toJson(gradeBean);
                    os = socket.getOutputStream();
                    os.write(jsonObj.getBytes());
                    socket.shutdownOutput();

                    Gson gson1 = new Gson();
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String result = in.readLine();//返回输赢
                    JResultBean resultBean = gson1.fromJson(result, JResultBean.class);
                    int user = resultBean.getUsergrade();
                    int match = resultBean.getMatchgrade();
                    int score = resultBean.getScore();

                    normalDialog.setTitle("Game Over");
                    normalDialog.setCancelable(false);
                    if (match==-1){
                        normalDialog.setMessage("你的成绩：" + user + " \n对方超时无效！ \n积分：" + score);
                    }else {
                        normalDialog.setMessage("你的成绩：" + user + " \n对方成绩：" + match + " \n积分：" + score);
                    }
                    normalDialog.setPositiveButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent1 = new Intent(getApplicationContext(), startActivity.class);
                                    //  只传递用户名 后用户名获取用户信息 考虑到需要实时更新用户数据
                                    intent1.putExtra("username",username);
                                    startActivity(intent1);
                                }
                            });
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            normalDialog.show();
                        }
                    });
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void sentgrade(){
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(getApplicationContext().getString(R.string.IP), 5001);
                    JGradeBean gradeBean = new JGradeBean();
                    gradeBean.setGrade(-1);
                    gradeBean.setUsername(username);
                    gradeBean.setMatchname(matchname);
                    Gson gson = new Gson();
                    String jsonObj = gson.toJson(gradeBean);
                    os = socket.getOutputStream();
                    os.write(jsonObj.getBytes());
                    socket.shutdownOutput();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void reset(){
        ((EditText)findViewById(R.id.editText1)).setText("");
        text1="";
        flag=0;
        flag1=0;flag2=0;flag3=0;flag4=0;flagK=0;
    }

    public static double calculate(final String answer) {
        return new Object() {
            int position = -1;
            char ch;
            void next() {
                if(++position < answer.length()){
                    ch=answer.charAt(position);
                }else{
                    ch='?';  //匹配不到而结束
                }
            }
            boolean match(char MatchChar) {
                if (ch == MatchChar) {
                    next();
                    return true;
                }return false;
            }
            double addsub() { //后加减
                double x = muldiv();
                while(true){
                    if(match('+')) x += muldiv();
                    else if(match('-')) x -= muldiv();
                    else return x;
                }
            }
            double muldiv() {   //先乘除
                double x = getnum();
                while(true){
                    if(match('*')) x *= getnum();
                    else if(match('/')) x /= getnum();
                    else return x;
                }
            }
            double getnum() {
                double x=-99999; //控制输入 不会出现其他字符 初始化无意义
                int start = this.position;
                if (match('(')) {
                    x = addsub();
                    match(')'); //跳过）
                } else if ((ch >= '0' && ch <= '9')) {
                    //题目中有 10 的情况
                    while ((ch >= '0' && ch <= '9')) next();
                    x = Double.valueOf(answer.substring(start, this.position));
                }return x;
            }
            double result() {
                next();
                return addsub();
            }
        }.result();
    }
    // viewpager适配器
    class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm ) {
            super(fm);
        }
        @Override //获取条目
        public Fragment getItem(int position) {
            return fragmentlists.get(position);
        }
        @Override//数目
        public int getCount() {
            return fragmentlists.size();
        }
    }
    //viewpager监听事件
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        @Override
        public void onPageSelected(int position) {
            nowpager = position;
        }
        @Override
        public void onPageScrollStateChanged(int state) { }
    }
}