package com.jamie.quickStarter.example.test;

import com.jamie.quickStarter.example.dao.IUserDao;
import com.jamie.quickStarter.example.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * mybatis测试类
 * @author jamie
 * @date 2021/3/28 16:57
 */
public class MybatisTest {

    InputStream resourceAsStream = null;

    SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void before() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析了配置文件，并创建了sqlSessionFactory工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    }

    @Test
    public void selectList() {
        //3.生产sqlSession
        // 默认开启一个事务，但是该事务不会自动提交
        // 在进行增删改操作时，要手动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.sqlSession调用方法：查询所有selectList  查询单个：selectOne 添加：insert  修改：update 删除：delete
        List<User> users = sqlSession.selectList("com.jamie.quickStarter.example.dao.IUserDao.findAll");
        for(User user: users){
            System.out.println(user);
        }
        sqlSession.close();

    }

    @Test
    public void insert() {
        //事务自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        User user = new User();
        user.setId(6);
        user.setUsername("tom");
        sqlSession.insert("com.jamie.quickStarter.example.dao.IUserDao.saveUser", user);
        sqlSession.close();
    }

    @Test
    public void update() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(6);
        user.setUsername("lucy");
        sqlSession.update("com.jamie.quickStarter.example.dao.IUserDao.updateUser", user);
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void delete() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("com.jamie.quickStarter.example.dao.IUserDao.deleteUser", 6);
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void findAll() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        for(User user: all){
            System.out.println(user);
        }

    }

    @Test
    public void findByCondition() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("jamie");

        List<User> all = mapper.findByCondition(user1);
        for(User user: all){
            System.out.println(user);
        }

    }

    @Test
    public void findByIds() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        int[] arr = {1, 2};

        List<User> all = mapper.findByIds(arr);
        for(User user: all){
            System.out.println(user);
        }
    }

}
