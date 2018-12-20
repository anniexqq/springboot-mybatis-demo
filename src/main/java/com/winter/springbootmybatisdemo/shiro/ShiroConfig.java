package com.winter.springbootmybatisdemo.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 下面两个方法对 注解权限起作用有很大的关系，请把这两个方法，放在配置的最上面
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        return sessionManager;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(MyRealm adminRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(adminRealm);
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //登录页面
        shiroFilter.setLoginUrl("/login/loginPage");
        // 登录成功后要跳转的连接
        //shiroFilter.setSuccessUrl("/user");

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon"); //静态资源不需要认证
        filterChainDefinitionMap.put("/login/checkLogin", "anon");//验证登录（用户名和密码）的url不需要认证

        //防止postman调用被拦截，ActiveMQ的三个例子，此处就不需要登录认证了
        filterChainDefinitionMap.put("/msg/sendMsg", "anon");
        filterChainDefinitionMap.put("/msg/pubSubMsg", "anon");
        filterChainDefinitionMap.put("/msg/addUser", "anon");

        /* 解说：
        filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);------>>> 访问/add这个链接必须要有“权限添加”这个权限才可以访问，
        filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”);------>>> 访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问
        */
        //filterChainDefinitionMap.put("/index","roles[USER_ROLE]");

        //为了省事，这边没有使用权限表，所以直接建立URL与权限字符的关联关系,此处是所有的url与权限字符串的关系。相当于Controller里的@RequiresPermissions注解。
        //filterChainDefinitionMap.put("/index","perms[index:view]");

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        shiroFilter.setUnauthorizedUrl("/security/unauthorized");//设置没有权限时，挑转到的URL
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");//authc是指登录验证，跟权限无关
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }

    /**
     * HTML页面的shiro标签<shiro:hasPermission>支持
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
