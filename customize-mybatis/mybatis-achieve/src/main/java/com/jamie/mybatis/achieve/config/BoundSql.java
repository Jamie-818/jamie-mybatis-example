package com.jamie.mybatis.achieve.config;

import com.jamie.mybatis.achieve.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储jdbc执行所需要的数据
 * @author jamie
 */
public class BoundSql {

    /**
     * 解析过后的sql
     */
    private String sqlText;

    /**
     * 存储参数
     */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }

    @Override
    public String toString() {
        return "BoundSql{" + "sqlText='" + sqlText + '\'' + ", parameterMappingList=" + parameterMappingList + '}';
    }

}
