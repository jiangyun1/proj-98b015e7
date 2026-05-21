package com.exam.service;

import com.exam.entity.Users;

public interface IUserService {

    public int addUsers(Users users);

    public void update(Users users);

    public Users selectUserByName(Users users);
}