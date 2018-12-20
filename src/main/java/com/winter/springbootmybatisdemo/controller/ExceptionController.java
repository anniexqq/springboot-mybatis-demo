package com.winter.springbootmybatisdemo.controller;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    /**
     * 处理使用@RequiresPermissions注解后，访问方法权限不足时抛出异常的问题
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e)  {
        return "security/authority/authorization";
    }
}