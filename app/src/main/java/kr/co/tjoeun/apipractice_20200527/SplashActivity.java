package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kr.co.tjoeun.apipractice_20200527.utils.ContextUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupEvent();
        setValues();
    }

    @Override
    public void setupEvent() {

    }

    @Override
    public void setValues() {

//        저장된 토큰이 있나 AND 자동로그인이 체크됐나 => MainActivity로 이동
//        둘 중 하나라도 틀리면 LoginActivity로 이동

//        이 모든 행동을 2초 후세 실행.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!ContextUtil.getLoginUserToken(mContext).equals("") && ContextUtil.isAutoLogin(mContext)) {
                    Intent myIntent = new Intent(mContext, MainActivity.class);
                    startActivity(myIntent);
                }
                else {
                    Intent myIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(myIntent);
                }

                finish();
            }
        }, 2000);

    }
}
