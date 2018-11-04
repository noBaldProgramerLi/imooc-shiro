package com.imooc.dao;

import com.imooc.bean.User;

import java.util.List;

public interface UserDao {
    User getUserNyUserName(String username);

    List<String> queryRolesByUserName(String username);
}
