package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivityMainBinding;
import kr.co.tjoeun.apipractice_20200527.datas.User;
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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nickName.setText(me.getNickName());
                                binding.emailTxt.setText(me.getEmail());
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
