package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivityEditReplyBinding;
import kr.co.tjoeun.apipractice_20200527.datas.Topic;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class EditReplyActivity extends BaseActivity {

    ActivityEditReplyBinding binding;

    Topic mainTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_reply);
        setupEvent();
        setValues();
    }

    @Override
    public void setupEvent() {

        binding.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                토픽의 주제?
                int topicId = mainTopic.getId();

//                선택한 진영?

                int selectedId = binding.sideRadioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(mContext, "진영을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String side = ((RadioButton) findViewById(selectedId)).getText().toString();

                Log.d("진영", side);


//                입력한 내용?

//                진영 미선택 / 내용이 10글자 이내면 등록 거부 처리. (응용문제)
                if (binding.contentEdt.length() < 10) {
                    Toast.makeText(mContext, "10글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = binding.contentEdt.getText().toString();
//                검사를 다 통과하면 서버에 요청.

                ServerUtil.postRequestTopic(mContext, topicId, content, side, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("의견 남기기응답", json.toString());
                    }
                });

            }
        });


    }

    @Override
    public void setValues() {
        mainTopic = (Topic) getIntent().getSerializableExtra("topic");

        binding.topicTitleTxt.setText(mainTopic.getTitle());
    }
}
