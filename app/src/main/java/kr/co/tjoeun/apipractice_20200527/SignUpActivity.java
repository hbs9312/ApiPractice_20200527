package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivitySignUpBinding;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;

    boolean idCheckOk = false;
    boolean nickNameCheck = false;
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

//        닉네임 중복확인 버튼 => 서버에 중복확인 요청 (문서 참조)
//         => 성공일 경우 "사용해도 좋습니다." 토스트
//         => 실패일 경우 "중복된 닉네입입니다." 토스트

        binding.nickNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nickNameCheck = false;
                binding.nickNameResultTxt.setText("중복 검사를 해주세요.");
                checkSignUpEnalbe();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.nickNameIsDuplBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputNickName = binding.nickNameEdt.getText().toString();

                ServerUtil.getRequestDuplicatedCheck(mContext, inputNickName, "NICKNAME", new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        Log.d("로그확인", json.toString());

                        try {
                            int code = json.getInt("code");

                            if (code == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.nickNameResultTxt.setText("사용해도 좋은 닉네임입니다.");
                                        nickNameCheck = true;


                                    }
                                });
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.nickNameResultTxt.setText("사용할 수 없는 닉네입입니다.");

                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkSignUpEnalbe();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });



            }
        });

        binding.emailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                이메일을 변경하면 무조건 중복검사를 실패로 변경 => 재검사 요구.
                idCheckOk = false;

//                버튼 비활성화 체크
                checkSignUpEnalbe();

                binding.idCheckResultTxt.setText("아이디 중복검사를 해주세요.");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.idCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = binding.emailEdt.getText().toString();

                ServerUtil.getRequestDuplicatedCheck(mContext, inputEmail, "EMAIL", new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        Log.d("중복응답확인", json.toString());

                        try {
                            int code = json.getInt("code");

                            if(code == 200) {
//                                중복검사 통과
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "사용해도 좋은 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        binding.idCheckResultTxt.setText("사용해도 좋은 아이디입니다.");
                                        idCheckOk = true;
                                    }
                                });

                           }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "중복검사에 통과하지 못했습니다.", Toast.LENGTH_SHORT).show();
                                        binding.idCheckResultTxt.setText("중복검사에 통과하지 못했습니다.");
                                    }
                                });


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        checkSignUpEnalbe();
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

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


        binding.signUpBtn.setEnabled(isAllPasswordOk && idCheckOk && nickNameCheck);



    }
}
