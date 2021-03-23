package com.jamie.mybatis.achieve.io;

import java.io.InputStream;

/**
 * io资源操作类
 * @author jamie
 */
public class Resources {

    /**
     * 根据配置文件的路径，将配置文件加载成字节输入流，存在在内存中
     * @param path 配置文件路径
     */
    public static InputStream getResourceAsSteam(String path) {
        // 获取当前类的类加载器，调用类加载器的getResourceAsStream方法加载成字节输入流
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

}
