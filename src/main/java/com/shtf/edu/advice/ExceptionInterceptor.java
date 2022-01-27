package com.shtf.edu.advice;


import com.shtf.edu.bean.exception.ForcedOfflineAuthException;
import com.shtf.edu.utils.responseMessage.ResponseMessageHandle;
import com.shtf.edu.utils.exception.ExceptionLogHelper;
import com.shtf.edu.utils.responseMessage.ResponseMessage;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ExceptionController class
 *
 * @author chenlingyu
 * @date 2020/5/2 15:16
 */

@Slf4j
@ControllerAdvice
public class ExceptionInterceptor extends DefaultErrorAttributes {

    @Autowired
    private ResponseMessageHandle responseMessageHandle;

    private ResponseMessage exceptionHandleGather(Exception e, String msg) {
        e.printStackTrace();
        ExceptionLogHelper.taskExceptionLog(e.getClass(), e.getStackTrace(), e.getMessage());
        return responseMessageHandle
                .code(500)
                .customMsg(StringUtils.isEmpty(msg) ? "程序执行异常" : String.format("程序异常:[--%s--]",msg));
    }

    private ResponseMessage authExceptionHandleGather(Exception e, String msg,int code) {
        e.printStackTrace();
        ExceptionLogHelper.exceptionLog(e.getClass(),"权限检测异常", e.getStackTrace(), e.getMessage());
        return responseMessageHandle
                .code(code)
                .customMsg(StringUtils.isEmpty(msg) ? "权限检测异常" : String.format("权限异常:[--%s--]",msg));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseMessage exceptionHandler(Exception e) {
        return this.exceptionHandleGather(e, "");
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseMessage validExceptionHandler(BindException e, WebRequest request, HttpServletResponse response) {
        String errorMsg = "参数验证失败:";
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            errorMsg += " " + error.getField() + ":" + error.getDefaultMessage();
        }
        return this.exceptionHandleGather(e, errorMsg);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseMessage runtimeException(RuntimeException e) {
        return this.exceptionHandleGather(e, e.getClass().toString());
    }

    @ExceptionHandler({AuthException.class, ShiroException.class, JwtException.class})
    @ResponseBody
    public ResponseMessage authException(Exception e) {
        return this.authExceptionHandleGather(e, e.getMessage(),413);
    }

    @ExceptionHandler({ForcedOfflineAuthException.class})
    @ResponseBody
    public ResponseMessage forcedOfflineAuthException(ForcedOfflineAuthException e) {
        return this.authExceptionHandleGather(e, e.getMessage(),413);
    }
}
