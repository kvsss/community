package com.deng.community.entity;


import java.util.Date;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */
public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;  //一个随机字符串
    private int status;// 状态  '0-有效,登录状态; 1-无效;退出状态',    status状态为1或者expired已经过期,则需要重新登录
    private Date expired;//过期时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
