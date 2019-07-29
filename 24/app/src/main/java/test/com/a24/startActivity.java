package test.com.a24;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class startActivity extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        //        强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
    }
    @OnClick({
            R.id.btn_pk, R.id.btn_my, R.id.btn_ranked
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pk: //开始pk
                // 先进入pk界面 再等待获取题目
                Intent intent = new Intent(this, pkActivity.class);
                //传递用户名
                intent.putExtra("username",username);
                startActivity(intent);
                finish();//销毁此Activity
                break;
            case R.id.btn_ranked:
                startActivity(new Intent(this, RankActivity.class));
//                finish();//返回键 不销毁
                break;
            case R.id.btn_my:
                Intent intent1 = new Intent(this, userinfoActivity.class);
                //传递用户名
                intent1.putExtra("username",username);
                startActivity(intent1);
                break;
        }
    }
}