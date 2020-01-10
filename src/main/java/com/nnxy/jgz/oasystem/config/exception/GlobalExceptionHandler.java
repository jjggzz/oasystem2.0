package com.nnxy.jgz.oasystem.config.exception;

import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * 全局异常处理
 * @author JGZ
 * CreateTime 2019/12/5 9:36
 * Email 1945282561@qq.com
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 权限不足返回信息
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseMessage unauthorizedExceptionHandler(){
        return new ResponseMessage(ErrorEnum.E_UNAUTHORIZED);
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseMessage unauthenticatedException() {
        return new ResponseMessage(ErrorEnum.E_UNAUTHENTICATED);
    }

    /**
     * 文件超过大小调用此全局处理
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseMessage fileSizeLimitExceeded(){
        return new ResponseMessage("-1","文件超过最大限制");
    }
}
