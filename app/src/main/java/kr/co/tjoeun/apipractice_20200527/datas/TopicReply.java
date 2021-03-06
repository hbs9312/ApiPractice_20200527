package kr.co.tjoeun.apipractice_20200527.datas;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TopicReply {

    private int id;
    private String content;
    private String side;
    private User writer;
    private Calendar createdAt = Calendar.getInstance(); // 작성 일시 기록


    public void setWriter(User writer) {
        this.writer = writer;
    }

    public static TopicReply getTopicReplyFromJson(JSONObject jsonObject) {

        TopicReply tr = new TopicReply();

        try {
            tr.id = jsonObject.getInt("id");
            tr.content = jsonObject.getString("content");
            tr.side = jsonObject.getString("side");

//            댓글 json 안에 있는 user Json 호출
            JSONObject user = jsonObject.getJSONObject("user");
//            이 user Json으로 User클래스로  변환하는 기능 활용
//            활용해서 만든 User객체 => 댓글의 작성자로 연결.
            tr.writer = User.getUserFromJson(user);

//            String으로 들어오는 created_at을 => Calendar 타입인 createdAt으로 변환
            String createdAtStr = jsonObject.getString("created_at");

//            만들어져있는 createdAt 캘린더에 => 년/월/일/시 등 데이터 세팅. => setTime
//            내 핸드폰의 시간과 Vs. UTC 시간의격차가 얼마인지 구해서 더해줘야함.

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tr.createdAt.setTime(sdf.parse(createdAtStr));

//            시차 : 9시간 =>: 작성시간 + 9
//            시차 구하는 방법 검색. => Timezone
            TimeZone myPhoneTimeZone = tr.createdAt.getTimeZone();
//            해당 Timezone의 실제 시차 값. => 밀리세컨드를 시간으로 변경
            int gmtOffset = myPhoneTimeZone.getRawOffset() / 60 / 60 / 1000;
//            현재 구해낸 시간에 더해준다.
            Log.d("시차확인", String.valueOf(gmtOffset));
            tr.createdAt.add(Calendar.HOUR_OF_DAY, gmtOffset);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tr;
    }

    public TopicReply() {
    }

    public TopicReply(int id, String content, String side) {
        this.id = id;
        this.content = content;
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public User getWriter() {
        return writer;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

//    현재 시간 대비 작성시간이 얼마나 오래되었나 체크해서, 다른 양식으로 출력

    public String getFormattedTimeAgo() {
//        1. 작성한 시간으로부터 현재시간이 얼마나 흘렀나?

        long writeTime = this.createdAt.getTimeInMillis();
        long now = System.currentTimeMillis(); // 현재시간을 long으로 바로 리턴.

        long diff = now - writeTime;

        if(diff < 1 * 60 * 1000) {
            return "방금 전";
        }
        else if (diff < 1 * 60 * 60 * 1000) {
            long minute = diff /60 / 1000;

            return String.format("%d분 전",minute);

        }
        else if (diff < 1 * 24 * 60 * 60 * 1000) {
            long hour  = diff / 1000 / 60 / 60;

            return String.format("%d시간 전", hour);
        }
        else {
//            하루가 넘어가면 그 날짜만 출력. 2020년 5월 29일
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 d일");
            return sdf.format(this.createdAt.getTime());
        }
    }
}
