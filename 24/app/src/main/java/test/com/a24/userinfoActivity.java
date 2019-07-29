package test.com.a24;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import test.com.a24.bean.UserBean;

public class userinfoActivity extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        ((TextView) findViewById(R.id.userinfo_name)).setText("用户名："+ username);
        //联网
        OkGo.get(this.getResources().getString(R.string.URL_Getuser))
                .params("username", username)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //gson解析
                        Gson gson = new Gson();
                        UserBean userBean = gson.fromJson(s, UserBean.class);
                        ((TextView) findViewById(R.id.userinfo_integral)).setText("积分："+ userBean.getIntegral());
                        ((TextView) findViewById(R.id.userinfo_win)).setText("胜："+ userBean.getWin());
                        ((TextView) findViewById(R.id.userinfo_lose)).setText("败："+ userBean.getLose());
                        ((TextView) findViewById(R.id.userinfo_tie)).setText("平："+ userBean.getTie());
                        ((TextView) findViewById(R.id.userinfo_high)).setText("最高分："+ userBean.getHigh_grade());
                    }
                });
    }

    @OnClick({
            R.id.btn_logout,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout: //退出登录
                startActivity(new Intent(this, loginActivity.class));
                finish();//销毁此Activity
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }return super.onOptionsItemSelected(item);
    }
}