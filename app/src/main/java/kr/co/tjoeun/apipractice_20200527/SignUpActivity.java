package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import org.w3c.dom.Text;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivitySignUpBinding;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;
//    응용문제.
//    비번을 타이핑할 때마다 길이 검사
//     => 0글자 : 비밀번호를 입력해주세요.
//     => 8글자 미만 : 비밀번호가 너무 짧습니다.
//     => 그 이상 : 사용해도 좋은 비밀번호 입니다.

//    비밀 확인도 타이핑할 때마다 검사.
//     => 0글자 : 비밀번호 확인을 입력해주세요.
//     => 비번과 같다 : 비밀번호 재입력이  확인 되었습니다.
//     => 다르다 : 비밀번호가 서로 다릅니다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        setupEvent();
        setValues();
    }

    @Override
    public void setupEvent() {

        binding.checkPasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkSignUpEnalbe();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.inputPasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkSignUpEnalbe();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void setValues() {

    }

    boolean checkPasswords() {

        boolean isPwOk = false;

        String pw = binding.inputPasswordEdt.getText().toString();

        if(pw.length() == 0) {
            binding.passwordResultTxt.setText("비밀번호를 입력해주세요.");
        }
        else if(pw.length() < 8) {
            binding.passwordResultTxt.setText("비밀번호가 너무 짧습니다.");
        }
        else {
            binding.passwordResultTxt.setText("사용해도 좋은 비밀번호입니다.");
            isPwOk = true;
        }

        boolean isPwRepeatOk = false;
        String pwRepeat = binding.checkPasswordEdt.getText().toString();

        if(pwRepeat.length() == 0) {
            binding.checkPasswordResultTxt.setText("비밀번호 확인을 입력해주세요.");
        }
        else if (pwRepeat.equals(pw)){
            binding.checkPasswordResultTxt.setText("비밀번호 재입력이  확인 되었습니다.");
            isPwRepeatOk = true;
        }
        else {
            binding.checkPasswordResultTxt.setText("비밀번호가 서로 다릅니다.");
        }

        return isPwOk && isPwRepeatOk;

    }
//     아이디 중복 / 비번확인 / 닉네임 중복이 모두 통과여야, 회원가입버튼 활성화
    void checkSignUpEnalbe() {

        boolean isAllPasswordOk = checkPasswords();

    }
}