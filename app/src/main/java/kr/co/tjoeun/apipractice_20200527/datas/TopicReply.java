package kr.co.tjoeun.apipractice_20200527.datas;

import org.json.JSONException;
import org.json.JSONObject;

public class TopicReply {

    private int id;
    private String content;
    private String side;
    private User writer;


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

        } catch (JSONException e) {
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
}
