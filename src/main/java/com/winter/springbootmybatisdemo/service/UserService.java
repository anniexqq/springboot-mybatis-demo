package com.winter.springbootmybatisdemo.service;
import com.winter.springbootmybatisdemo.mapper.UserMapper;
import com.winter.springbootmybatisdemo.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserService extends BaseService<User,Integer,UserMapper> {
    int addUser(User user) throws Exception;

    List<User> findAllUser(int pageNum, int pageSize);

}
