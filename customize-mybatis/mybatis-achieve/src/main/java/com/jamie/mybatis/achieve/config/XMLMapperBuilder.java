package com.jamie.mybatis.achieve.config;

import com.jamie.mybatis.achieve.pojo.Configuration;
import com.jamie.mybatis.achieve.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * Mybatis Mapper.xml解析类，获取里面的sql数据
 * @author jamie
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析Mapper.xml
     * @param inputStream 流
     */
    public void parse(InputStream inputStream) throws DocumentException, ClassNotFoundException {
        // 根据流获取xml文档对象
        Document document = new SAXReader().read(inputStream);
        // 获取 根节点 元素对象
        Element rootElement = document.getRootElement();
        // 获取定义的命名空间
        String namespace = rootElement.attributeValue("namespace");
        // 获取SELECT类型的 xml
        List<Element> list = rootElement.selectNodes("//select");
        for(Element element: list){
            // 获取sql的ip
            String id = element.attributeValue("id");
            // 获取sql的请求类型
            String parameterType = element.attributeValue("parameterType");
            // 获取sql的返回类型
            String resultType = element.attributeValue("resultType");
            Class<?> parameterTypeClass = "".equals(parameterType) || parameterType == null
                                          ? null
                                          : getClassType(parameterType);
            //返回结果class
            Class<?> resultTypeClass = "".equals(resultType) || resultType == null
                                       ? null
                                       : getClassType(resultType);
            // 获取sql
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setParameterType(parameterTypeClass);
            mappedStatement.setSql(sqlText);
            String key = namespace + "." + id;
            // 插入缓存对象里面
            configuration.getMappedStatementMap().put(key, mappedStatement);

        }

    }

    /**
     * 根据路径获取calss实例
     * @param parameterType 类的全路径
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(parameterType);
        return aClass;
    }

}
