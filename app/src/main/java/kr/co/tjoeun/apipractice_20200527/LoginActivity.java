package kr.co.tjoeun.apipractice_20200527;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivityLoginBinding;
import kr.co.tjoeun.apipractice_20200527.databinding.ActivityLoginBinding;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        setupEvent();
        setValues();
    }

    @Override
    public void setupEvent() {

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.emailEdt.getText().toString();
                String password = binding.pwEdt.getText().toString();

                ServerUtil.postRequestLogin(mContext, email, password, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        Log.d("JSON 확인", json.toString());

                        try {
                            int code = json.getInt("code");

                            if(code == 200) {
                                Log.d("분석결과", "로그인에 성공!");
                            }
                            else {
                                Log.d("분석결과", "로그인 실패..");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void setValues() {

    }
}
