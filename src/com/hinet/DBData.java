package com.hinet;

public class DBData {
    DB db = new DB();
    private  int id;
    private String content;
    private String user_id;
    private String create_date;

    public DBData(int id, String content, String user_id, String create_date) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
        this.create_date = create_date;
    }
    public DBData(){
        db.getData();
    }
    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCreate_date() {
        return create_date;
    }
}
