package test.com.a24;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import test.com.a24.bean.UserBean;

public class registerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制竖屏
    }

    @BindView(R.id.register_username) EditText RegisterUsername;
    @BindView(R.id.register_psw) EditText RegisterPsw;
    @BindView(R.id.register_psw2) EditText RegisterPsw2;
    @BindView(R.id.btn_register) Button btnRegister;

    @OnClick({
            R.id.register_login, R.id.btn_register,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_login: //返回登录页面
                Intent intent1 = new Intent(this, loginActivity.class);
                startActivity(intent1);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);//动画效果 左入右出
                break;
            case R.id.btn_register:
                //获取用户输入的用户名、密码
                final String username = RegisterUsername.getText().toString().trim();
                String password = RegisterPsw.getText().toString().trim();
                String password2 = RegisterPsw2.getText().toString().trim();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                        if(password2.equals(password)){
                           // 显示圆形进度条对话框
                            btnRegister.setEnabled(false);
                            final ProgressDialog progressDialog = new ProgressDialog(registerActivity.this, R.style.AppTheme_Wating);
//                                progressDialog.setIndeterminate(true); //是否不显示明确进度
                            progressDialog.setMessage("正在创建...");
                            progressDialog.show();
                            OkGo.get(this.getResources().getString(R.string.URL_register))
                                    .params("username", username)
                                    .params("password", password)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Gson gson = new Gson();
                                            UserBean userBean= gson.fromJson(s, UserBean.class);
                                            if (userBean.getUsername().equals(username)) { //注册失败返回用户名+not ok
                                                Intent intent2 = new Intent(getApplicationContext(), loginActivity.class);
                                                startActivity(intent2);
                                                Toast.makeText(getApplicationContext(),  "注册成功！", Toast.LENGTH_SHORT).show();
                                                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);//动画效果 左入右出
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                                            }
                                            progressDialog.dismiss();//对话框消失
                                            btnRegister.setEnabled(true);
                                        }
                                    });
                        }else {
                            Toast.makeText(this, "密码输入不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    Toast.makeText(this, "请完善信息！", Toast.LENGTH_SHORT).show();
                }break;
        }
    }
}