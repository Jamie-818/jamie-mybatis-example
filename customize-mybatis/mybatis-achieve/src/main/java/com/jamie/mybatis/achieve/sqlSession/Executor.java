package com.jamie.mybatis.achieve.sqlSession;

import com.jamie.mybatis.achieve.pojo.Configuration;
import com.jamie.mybatis.achieve.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * sql执行接口
 * @author jamie
 */
public interface Executor {

    /**
     * 数据库查询接口
     * @param configuration   数据库配置信息
     * @param mappedStatement sql执行信息
     * @param params          入参
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IntrospectionException;

}
