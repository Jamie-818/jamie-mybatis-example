package com.jamie.mybatis.achieve.sqlSession;

/**
 * 创建sql会话工程
 * @author jamie
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
