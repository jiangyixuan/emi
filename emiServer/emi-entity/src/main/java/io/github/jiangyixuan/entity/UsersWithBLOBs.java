package io.github.jiangyixuan.entity;

public class UsersWithBLOBs extends Users {
    private String name;

    private String pwd;

    private String headportraiturl;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getHeadportraiturl() {
        return headportraiturl;
    }

    public void setHeadportraiturl(String headportraiturl) {
        this.headportraiturl = headportraiturl == null ? null : headportraiturl.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}