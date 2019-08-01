package com.surpass.vision.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.surpass.vision.appCfg.Config;
import com.surpass.vision.common.ToWeb;

public interface DataExceptionSolver {
    @ExceptionHandler
    @ResponseBody
    default Object exceptionHandler(Exception e){
        try {
            throw e;
        } catch (SystemException systemException) {
            systemException.printStackTrace();
            return ToWeb.buildResult().status(Config.FAIL).msg("system error!");
        } catch (Exception e1){
            e1.printStackTrace();
            return ToWeb.buildResult().status(Config.FAIL).msg("system error!");
        }
    }
}