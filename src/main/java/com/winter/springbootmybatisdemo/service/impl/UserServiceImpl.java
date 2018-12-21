package com.winter.springbootmybatisdemo.service.impl;
import com.github.pagehelper.PageHelper;
import com.winter.springbootmybatisdemo.mapper.UserMapper;
import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.UserService;
import com.winter.springbootmybatisdemo.shiro.password.PasswordHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User,Integer,UserMapper> implements UserService {

    @Override
    public int addUser(User user) throws Exception{
        String salt = PasswordHelper.generatorRandomNumber();//生成一个随机盐
        user.setPassword(PasswordHelper.encodePassword(user.getPassword(),salt));
        user.setSalt(salt);
        return mapper.insertSelective(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectAllUser();
    }
    @Override
    public User findById(Integer id){
        return mapper.selectByPrimaryKey(id);
    }

}