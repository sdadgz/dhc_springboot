package cn.sdadgz.dhc_springboot.config;

import cn.sdadgz.dhc_springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result handleBussinessException(BusinessException e) {
        return Result.error(e.getCode(), "异常：" + e.getMessage());
    }

}
