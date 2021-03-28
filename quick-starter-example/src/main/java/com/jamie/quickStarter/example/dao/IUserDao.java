package com.jamie.quickStarter.example.dao;

import com.jamie.quickStarter.example.pojo.User;

import java.io.IOException;
import java.util.List;

public interface IUserDao {

    /**
     * 查询所有用户
     */
    List<User> findAll() throws IOException;

    /**
     * 多条件组合查询：演示if
     */
    List<User> findByCondition(User user);

    /**
     * 多值查询：演示foreach
     */
    List<User> findByIds(int[] ids);

}
