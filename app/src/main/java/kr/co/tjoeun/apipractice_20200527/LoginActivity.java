package kr.co.tjoeun.apipractice_20200527;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

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

                ServerUtil.postRequestLogin(mContext,email, password, null);

            }
        });

    }

    @Override
    public void setValues() {

    }
}
