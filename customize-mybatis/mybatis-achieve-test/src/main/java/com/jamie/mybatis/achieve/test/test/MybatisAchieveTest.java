package com.jamie.mybatis.achieve.test.test;

import com.jamie.mybatis.achieve.io.Resources;
import com.jamie.mybatis.achieve.test.dao.IUserDao;
import com.jamie.mybatis.achieve.test.pojo.User;
import com.jamie.mybatis.achieve.sqlSession.SqlSession;
import com.jamie.mybatis.achieve.sqlSession.SqlSessionFactory;
import com.jamie.mybatis.achieve.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 自定义mybatis框架自定义类
 * @author jamie
 */
public class MybatisAchieveTest {

    @Test
    public void selectOne() throws Exception {
        // 获取配置信息
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("jamie");
        User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user2);

    }

    @Test
    public void selectList() throws Exception {
        // 获取配置信息
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用

        List<User> users = sqlSession.selectList("user.selectList");
        for(User user1: users){
            System.out.println(user1);
        }

    }

    @Test
    public void findAll() throws Exception {
        // 获取配置信息
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //调用
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        List<User> all = userDao.findAll();
        for(User user1: all){
            System.out.println(user1);
        }

    }

    @Test
    public void findByCondition() throws Exception {
        // 获取配置信息
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //调用
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setId(1);
        user.setUsername("jamie");

        User user2 = userDao.findByCondition(user);
        System.out.println(user2);

    }

}
