package com.winter.springbootmybatisdemo.shiro;

import com.alibaba.fastjson.JSONObject;
import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.redis.RedisService;
import com.winter.springbootmybatisdemo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger(MyRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Resource(name = "adminCredentialsMatcher")
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }

    /** 认证回调函数
     * 验证当前登录的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取登录账号
        String loginName = (String) token.getPrincipal();
        User sysUser = new User();
        sysUser.setUserName(loginName);
        try {
            sysUser = userService.selectOne(sysUser);
            // 用户不存在
            if (sysUser == null) {
                logger.error("登录失败,用户不存在!!");
                throw new AuthenticationException();
            }
            //将用户信息存入redis，在LoginController中取出来看看，主要是测redis
            redisService.set("sys_user", JSONObject.toJSONString(sysUser));

            String salt = sysUser.getSalt();//数据库中存的盐
            return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(),
                    ByteSource.Util.bytes(salt), getName());
        } catch (Exception e) {
            logger.error("登录失败!!");
            throw new AuthenticationException();
        }
    }

    /**授权回调函数
     * 为当限前登录的用户授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权-->MyRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.addRole("USER_ROLE");//给当前用户设USER_ROLE角色，在shiroFilter中可对路径进行角色的控制
        User user = (User) principals.getPrimaryPrincipal();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        Set<String> stringPermissions = new HashSet<>();
        //为了省事，这边没有使用角色、权限表，直接将权限赋给当前用户
        stringPermissions.add("index:view");//首页展示权限
        stringPermissions.add("bclick:view1");//index.html页面上的，点击按钮的显示权限
        stringPermissions.add("bclick:view2");

        authorizationInfo.setStringPermissions(stringPermissions);
        session.setAttribute("_sysUser", user);
        return authorizationInfo;
    }

}
