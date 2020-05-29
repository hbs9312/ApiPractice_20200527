package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivityMainBinding;
import kr.co.tjoeun.apipractice_20200527.datas.Topic;
import kr.co.tjoeun.apipractice_20200527.datas.User;
import kr.co.tjoeun.apipractice_20200527.utils.ContextUtil;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setupEvent();
        setValues();
    }

    @Override
    public void setupEvent() {


        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("로그아웃");
                alert.setMessage("정말 로그아웃 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContextUtil.setLoginUserToken(mContext, "");

                        Intent myIntent = new Intent(mContext, LoginActivity.class);
                        startActivity(myIntent);

                        finish();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "로그아웃을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();

            }
        });

    }

    @Override
    public void setValues() {
        ServerUtil.getRequestMainInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("메인화면 응답", json.toString());

                try {
                    int code = json.getInt("code");

                    if(code==200) {
                        JSONObject data = json.getJSONObject("data");

                        JSONObject user = data.getJSONObject("user");
                        final User me = User.getUserFromJson(user);

                        final JSONObject topic = data.getJSONObject("topic");
                        final Topic thisWeekTopic = Topic.getTopicFromJson(topic);

//                        댓글목록도 파싱하자.

                        JSONArray replies = data.getJSONArray("replies");

                        for (int i=0; i < replies.length(); i++) {

                            JSONObject reply = replies.getJSONObject(i);

                            Log.d("댓글내용", reply.getString("content"));
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nickName.setText(me.getNickName());
                                binding.emailTxt.setText(me.getEmail());

                                Glide.with(mContext).load(thisWeekTopic.getImageUrl()).into(binding.topicImg);
                                binding.topicTxt.setText(thisWeekTopic.getTitle());
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
