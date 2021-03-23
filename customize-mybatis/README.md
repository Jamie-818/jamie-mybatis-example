# 简单的Mybatis执行流程

## 目录结构说明

```
├─config
│      BoundSql.java
│      XMLConfiguraBuilder.java
│      XMLMapperBuilder.java
├─io
│      Resources.java
├─pojo
│      Configuration.java
│      MappedStatement.java
├─sqlSession
│      DefaultSqlSession.java
│      DefaulutSqlSessionFactory.java
│      Executor.java
│      SimpleExecutor.java
│      SqlSession.java
│      SqlSessionFactory.java
│      SqlSessionFactoryBuilder.java
└─utils
        GenericTokenParser.java
        ParameterMapping.java
        ParameterMappingTokenHandler.java
        TokenHandler.java
```

- config 配置解析包
    - BoundSql.java
        - 存储jdbc执行所需要的数据
    - XMLConfiguraBuilder.java
        - XML配置解析类，主要解析数据源配置和mapper路径配置
    - XMLMapperBuilder.java Mybatis
        - Mapper.xml解析类，获取里面的sql数据

- io 资源处理包
    - Resources.java
        - 主要用于读取配置文件获取字节输入流
- pojo 对象包
    - Configuration.java Mybatis
        - 数据库配置与xml预处理的存储对象，上面XMLConfiguraBuilder解析后的数据就是存入该对象
    - MappedStatement.java
        - Mybatis执行sql语句的单体对象，每行sql都是一个MappedStatement对象，它会组成集合存储在Configuration对象中。
- sqlSession sql会话处理包
    - DefaultSqlSession.java
        - SqlSession的默认实现，主要是Dao对象的创建和调用Executor实现增删改查
    - DefaulutSqlSessionFactory.java
        - SqlSession工程默认实现，主要是创建SqlSession对象
    - Executor.java
        - 接口，用于增删改查的方法定义。
    - SimpleExecutor.java
        - 简单的Executor实现类，和数据库的执行层，实现获取连接、设置参数、执行sql、封装返回结果集。
    - SqlSession.java
        - 接口，用于定义sql会话的方法，里面都是增删改查。
    - SqlSessionFactory.java
        - 接口，用于定义创建sqlsession的方法。
    - SqlSessionFactoryBuilder.java
        - 用于构建SqlSessionFactory，通过传入Resources读取配置获取到字节输入流，得到SqlSessionFactory对象。
- utils 工具包
    - GenericTokenParser.java
        - 通用令牌解析器，主要用于解析mybaits里面的 `#{}` 和 `${}`
    - ParameterMapping.java
        - 参数映射对象，令牌解析后 `#{}` 里面的内容就存在这里对象里面，用于匹配入参
    - ParameterMappingTokenHandler.java
        - TokenHandler的实现，主要作用是根据参数的key（即expression）进行参数处理，返回?作为占位符
    - TokenHandler.java
        - 接口，定义占位符的方法。

## 调用流程

### 调用方

> 1、通过Resources#getResourceAsSteam获取字节输入流。
>
> 2、通过SqlSessionFactoryBuilder#build获取SqlSessionFactory会话工厂。
>
> 3、通过SqlSessionFactory#openSession获取SqlSession会话对象。
>
> 4、通过SqlSession调用selectOne等方法执行sql，获取返回结果

### 内部

1、通过Resources#getResourceAsSteam获取字节输入流。

> 通过类加载器的 `getResourceAsStream()` 方法获取字节输入流 `inputStream `并返回

2、通过SqlSessionFactoryBuilder#build获取SqlSessionFactory会话工厂。

> 1. 调用SqlSessionFactoryBuilder#build传入上面拿到的 `inputStream ` 获取到 `SqlSessionFactory` 对象。解析配置文件和创建SqlSessionFactory工厂对象并返回。
     >    1. 调用 `XMLConfiguraBuilder` 的构造器传入 `configuration` 构造 `XMLConfiguraBuilder` 对象，并调用 `XMLConfiguraBuilder` 的 `parseConfiguration()`传入上面的 `inputStream` 解析xml的内容并加载到内存中，这里面有`数据源`，内部还调用了 `XMLMapperBuilder` 去解析 `Mapper.xml` 得到 `sql数据信息(唯一ID，入参出参，sql语句)` 并加载到内存中
>    2. 调用了 `DefaultSqlSessionFactory` 的构造器传入上面获取到的 `configuration`(数据源、全部sql数据信息) 得到`sessionFactory` 对象。

3、通过SqlSessionFactory#openSession获取SqlSession会话对象。

> `openSession` 调用 `DefaultSqlSession` 的构造器传入 `configuration` 对象获取 `SqlSession` 对象。
>
> `SqlSession` 对象可以调用增删改查方法进行数据库交互。

4、通过SqlSession调用selectOne等方法执行sql，获取返回结果

> 调用`selectOne()`需要传入 `statementId` (唯一Id)，也就是`mapper命名空间`+`mapper的select标签Id`(其他增删改标签同理)，和                                                                       `param`(入参对象，这里可以传对象或者字段)
>
> 1. 构造一个`SimpleExecutor`对象。
>
> 2. 从`configuration`里面通过`statementId` 获取到当前执行sql的MappedStatement对象(sql数据信息)。
> 3. 调用SimpleExecutor的query方法把configuration、mappedStatement和params传入。
     >    1. 注册驱动，`Connection connection = configuration.getDataSource().getConnection();` 可获得连接。
>    2. 从mappedStatement获取sql语句，并调用 `getBoundSql()` 把原sql语句处理，把 `#{}` 和 `${}` 处理成 `?` 并返回`BoundSql`对象(里面存储jdbc执行所需要的数据)
>    3. 调用`connection.prepareStatement(boundSql.getSqlText())`获取预处理对象`preparedStatement`。
>    4. 设置参数，通过反射获取到具体参数的值，并插入`preparedStatement`。
>    5. 调用`preparedStatement.executeQuery()`执行sql
>    6. 封装返回结果集，也是利用反射，把值插入到返回对象里面，并返回。



>  源码地址：https://github.com/Jamie-818/jamie-mybatis-exnaple.git