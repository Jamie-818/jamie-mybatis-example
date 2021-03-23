package com.jamie.mybatis.achieve.pojo;

/**
 * Mybatis执行sql语句的单体对象
 * @author jamie
 */
public class MappedStatement {

    /**
     * 语句Id
     */
    private String id;

    /**
     * sql语句
     */
    private String sql;

    /**
     * 输入参数
     */
    private Class<?> parameterType;

    /**
     * 输出参数
     */
    private Class<?> resultType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MappedStatement{"
                + "id="
                + id
                + ", sql='"
                + sql
                + '\''
                + ", parameterType="
                + parameterType
                + ", resultType="
                + resultType
                + '}';
    }

}
