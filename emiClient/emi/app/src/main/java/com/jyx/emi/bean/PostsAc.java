package com.jyx.emi.bean;

/**
 * Created by H_T on 2016/10/5.
 */
public class PostsAc {
	private int id;
    private String user;
    private String body;
    private String floor;
    private String time;
    private String name;
    private String sid;

    public int getId() {
		return id;
	}
    public void setId(int id) {
		this.id = id;
	}
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setName(String name) {
		this.name = name;
	}
    public String getName() {
		return name;
	}
    public void setSid(String sid) {
		this.sid = sid;
	}
    public String getSid() {
		return sid;
	}
}
