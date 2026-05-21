package com.exam.entity;
import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class Users {


    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long userId;
    @NotNull
    @Column(name="user_name")
    private String userName;

    @Column(name="user_password")
    private String userPassWord;

    @Column(name="user_photo",length = 2000)
    private String userPhoto;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
