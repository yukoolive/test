package com.yesx.ssm.po;

import org.apache.solr.client.solrj.beans.Field;

public class User {
    private int id;
    private String login_name;
    private String password;
    private int role_id;

    public User(Integer id, String login_name, String password, Integer role_id) {
        this.id = id;
        this.login_name = login_name;
        this.password = password;
        this.role_id = role_id;
    }

    public int getId() {
        return id;
    }

    @Field("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    @Field("login_name")
    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    @Field("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    @Field("role_id")
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login_name='" + login_name + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                '}';
    }
}
