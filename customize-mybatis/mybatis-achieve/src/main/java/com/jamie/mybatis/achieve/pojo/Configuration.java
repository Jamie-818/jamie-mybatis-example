package com.jamie.mybatis.achieve.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Mybatis 数据库配置与xml预处理的存储对象
 * @author jamie
 */
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * map集合，存放mybatis所预处理的sql执行对象
     * key:statementId  每条sql语句唯一的id
     * value:MappedStatement 每条sql语句的属性
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }

}
