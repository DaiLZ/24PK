package test.com.a24;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
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

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
    }
    @BindView(R.id.login_register) TextView LoginRegister;
    @BindView(R.id.login_username) EditText LoginUsername;
    @BindView(R.id.login_psw) EditText LoginPsw;
    @BindView(R.id.btn_login) Button btnLogin;
    @OnClick({R.id.login_register, R.id.btn_login})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register: //注册
                startActivity(new Intent(this, registerActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);//动画效果 左出右入
                break;
            case R.id.btn_login:
                //获取输入数据，并去左右两边空格
                final String name = LoginUsername.getText().toString().trim();
                String password = LoginPsw.getText().toString().trim();
                //进行匹配验证,先判断用户名密码是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    //输入合法，将登录按钮置为不可用，显示圆形进度对话框
                    btnLogin.setEnabled(false);
                    final ProgressDialog progressDialog = new ProgressDialog(loginActivity.this, R.style.AppTheme_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false); //设置点击ProgressDialog外的区域 ProgressDialog不消失
                    progressDialog.setMessage("正在登录...");
                    progressDialog.show();
                    OkGo.get(this.getResources().getString(R.string.URL_login))
                            .params("username", name)
                            .params("password", password)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    UserBean userBean= gson.fromJson(s, UserBean.class);
                                    //如果得到用户名一致 登录失败返回的名字+failed
                                    if (name.equals(userBean.getUsername())) {
                                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), startActivity.class);
                                        //只传递用户名 后用户名获取用户信息 考虑到需要实时更新用户数据
                                        intent.putExtra("username",userBean.getUsername());
                                        startActivity(intent);
                                        finish();//销毁此Activity
                                    } else {
                                        Toast.makeText(getApplicationContext(), "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                    btnLogin.setEnabled(true);
                                }
                            });
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }break;
        }
    }
}