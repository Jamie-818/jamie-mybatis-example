package com.jamie.mybatis.achieve.sqlSession;

import java.util.List;

/**
 * sql会话接口
 * @author jamie
 */
public interface SqlSession {

    /**
     * 查询所有
     * @param statementId sqlId
     * @param params      入参
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    /**
     * 根据条件查询单个
     * @param statementId sqlId
     * @param params      入参
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;

    /**
     * 为Dao接口生成代理实现类
     * @param mapperClass 对象class
     */
    <T> T getMapper(Class<?> mapperClass);

}
