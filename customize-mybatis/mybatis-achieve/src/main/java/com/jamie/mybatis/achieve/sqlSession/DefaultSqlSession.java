package com.jamie.mybatis.achieve.sqlSession;

import com.jamie.mybatis.achieve.pojo.Configuration;
import com.jamie.mybatis.achieve.pojo.MappedStatement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 默认sql会话实现类
 * @author jamie
 */
public class DefaultSqlSession implements SqlSession {

    Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        SimpleExecutor executor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return executor.query(configuration, mappedStatement, params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<T> objects = selectList(statementId, params);
        if(objects.size() == 1){
            return objects.get(0);
        }else if(objects.size() == 0){
            return null;
        }
        throw new RuntimeException("查询结果返回结果过多");
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass}, (proxy, method, args) -> {
            // 底层都还是去执行JDBC代码 //根据不同情况，来调用selectList或者selectOne
            // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
            // 方法名：findAll
            String methodName = method.getName();
            // 获取接口权限命名
            String className = method.getDeclaringClass().getName();
            String statementId = className + "." + methodName;
            // 准备参数2：params:args
            // 获取被调用方法的返回值类型
            Type genericReturnType = method.getGenericReturnType();
            // 判断是否进行了 泛型类型参数化
            if(genericReturnType instanceof ParameterizedType){
                List<Object> objects = selectList(statementId, args);
                return objects;
            }

            return selectOne(statementId, args);

        });

        return (T)proxyInstance;
    }

}
