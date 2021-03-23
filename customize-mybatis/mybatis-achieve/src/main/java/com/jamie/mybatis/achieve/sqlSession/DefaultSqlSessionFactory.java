package com.jamie.mybatis.achieve.sqlSession;

import com.jamie.mybatis.achieve.pojo.Configuration;

/**
 * 默认sql会话工程
 * @author jamie
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    public Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

}
