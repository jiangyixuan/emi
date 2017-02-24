package com.jyx.emi.bean;

/**
 * 商品收藏类，保存至sqlite
 * Created by Administrator on 2016/6/3.
 */
public class CommCollect {

    private int id;
    private int commId;
    private String time;//收藏时间


    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCommId(int commId) {
        this.commId = commId;
    }

    public int getId() {

        return id;
    }

    public String getTime() {
        return time;
    }

    public int getCommId() {
        return commId;
    }

    @Override
    public String toString() {
        return "CommCollect{" +
                "id=" + id +
                ", commId=" + commId +
                ", time='" + time + '\'' +
                '}';
    }
}
