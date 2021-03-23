package com.jamie.mybatis.achieve.sqlSession;

import com.jamie.mybatis.achieve.config.BoundSql;
import com.jamie.mybatis.achieve.pojo.Configuration;
import com.jamie.mybatis.achieve.pojo.MappedStatement;
import com.jamie.mybatis.achieve.utils.GenericTokenParser;
import com.jamie.mybatis.achieve.utils.ParameterMapping;
import com.jamie.mybatis.achieve.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单的执行器
 * @author jamie
 */
public class SimpleExecutor implements Executor {

    /**
     * 数据库查询接口
     * @param configuration   数据库配置信息
     * @param mappedStatement sql执行信息
     * @param params          入参
     */
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IntrospectionException {
        // 1. 注册驱动，获取连接
        // 获取配置里面的连接对象
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取sql语句 : select * from user where id = #{id} and username = #{username}
        //转换sql语句： select * from user where id = ? and username = ? ，转换的过程中，还需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4. 设置参数
        // 获取参数的全路径
        Class<?> parameterType = mappedStatement.getParameterType();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for(int i = 0; i < parameterMappingList.size(); i++){
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = parameterType.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            // 获取对象属性值
            Object o = declaredField.get(params[0]);
            // 设置参数
            preparedStatement.setObject(i + 1, o);
        }
        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        // 获取返回值对象
        Class<?> resultType = mappedStatement.getResultType();
        ArrayList<Object> objects = new ArrayList<>();
        // 6. 封装返回结果集
        while(resultSet.next()){
            Object o = resultType.getDeclaredConstructor().newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i = 1; i <= metaData.getColumnCount(); i++){
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段的值
                Object value = resultSet.getObject(columnName);
                //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);

            }
            objects.add(o);
        }
        return (List<E>)objects;
    }

    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     * @param sql mapper里面的sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作，其实就是把#{id} 转换成 ? ,并且把id占位符存在在 List<ParameterMapping> 里面
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        // 把解析后的sql，和参数名存入boundSql
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;

    }

}
