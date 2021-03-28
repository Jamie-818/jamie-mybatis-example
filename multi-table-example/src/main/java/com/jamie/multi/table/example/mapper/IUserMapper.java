package com.jamie.multi.table.example.mapper;

import com.jamie.multi.table.example.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;

@CacheNamespace(implementation = RedisCache.class)//开启二级缓存
public interface IUserMapper {

    //查询所有用户、同时查询每个用户关联的订单信息
    @Select("select * from user")
    @Results({@Result(property = "id", column = "id"), @Result(property = "username", column = "username"), @Result(
            property = "orderList", column = "id", javaType = List.class,
            many = @Many(select = "com.jamie.multi.table.example.mapper.IOrderMapper.findOrderByUid"))})
    List<User> findAll();

    //查询所有用户、同时查询每个用户关联的角色信息
    @Select("select * from user")
    @Results({@Result(property = "id", column = "id"), @Result(property = "username", column = "username"), @Result(
            property = "roleList", column = "id", javaType = List.class,
            many = @Many(select = "com.jamie.multi.table.example.mapper.IRoleMapper.findRoleByUid"))})
    List<User> findAllUserAndRole();

    //添加用户
    @Insert("insert into user values(#{id},#{username})")
    void addUser(User user);

    //更新用户
    @Update("update user set username = #{username} where id = #{id}")
    void updateUser(User user);

    //查询用户
    @Select("select * from user")
    List<User> selectUser();

    //删除用户
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    //根据id查询用户
    @Options(useCache = true)
    @Select({"select * from user where id = #{id}"})
    User findUserById(Integer id);

}
