package test.com.a24;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import test.com.a24.bean.JRankBean;
import test.com.a24.bean.User_rank;

public class RankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        OkGo.get(this.getResources().getString(R.string.URL_rank))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //gson解析
                        Gson gson = new Gson();
                        JRankBean jRankBean=gson.fromJson(s,JRankBean.class);

                        List<User_rank> ranks=jRankBean.getRank();
                        User_rank user_rank=ranks.get(0);
                        ((TextView) findViewById(R.id.Rank_name1)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade1)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(1);
                        ((TextView) findViewById(R.id.Rank_name2)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade2)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(2);
                        ((TextView) findViewById(R.id.Rank_name3)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade3)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(3);
                        ((TextView) findViewById(R.id.Rank_name4)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade4)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(4);
                        ((TextView) findViewById(R.id.Rank_name5)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade5)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(5);
                        ((TextView) findViewById(R.id.Rank_name6)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade6)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(6);
                        ((TextView) findViewById(R.id.Rank_name7)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade7)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(7);
                        ((TextView) findViewById(R.id.Rank_name8)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade8)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(8);
                        ((TextView) findViewById(R.id.Rank_name9)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade9)).setText(user_rank.getHigh_grade()+"");
                        user_rank=ranks.get(9);
                        ((TextView) findViewById(R.id.Rank_name10)).setText(user_rank.getName());
                        ((TextView) findViewById(R.id.Rank_grade10)).setText(user_rank.getHigh_grade()+"");
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }return super.onOptionsItemSelected(item);
    }
}
