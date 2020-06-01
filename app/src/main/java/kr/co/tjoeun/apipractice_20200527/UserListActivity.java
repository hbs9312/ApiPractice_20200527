package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Binder;
import android.os.Bundle;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivityUserListBinding;

public class UserListActivity extends BaseActivity {

//    /user - GET으로 접근해서, 사용자목록을 리스트뷰로 출력
//    닉네임 (이메일주소) => 이 양식으로 표현.

    ActivityUserListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list);
        setupEvent();
        setValues();
    }

    @Override
    public void setupEvent() {

    }

    @Override
    public void setValues() {

    }
}
