package com.jamie.mybatis.achieve.sqlSession;

import com.jamie.mybatis.achieve.config.XMLConfiguraBuilder;
import com.jamie.mybatis.achieve.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    /**
     * 数据源对象（往里面存入数据源）
     */
    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException,
            ClassNotFoundException {
        // 第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfiguraBuilder xmlConfiguraBuilder = new XMLConfiguraBuilder(configuration);
        Configuration configuration = xmlConfiguraBuilder.parseConfiguration(inputStream);
        // 第二：创建SqlSessionFactory对象：工厂类：生产sqlSession:会话对象
        DefaultSqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(configuration);
        return sessionFactory;

    }

}
