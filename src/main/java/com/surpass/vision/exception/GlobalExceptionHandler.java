package com.surpass.vision.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(RedisConnectionFailureException.class)
    public void handlerRedisConnectionFailureException(RedisConnectionFailureException exception){
        logError(exception);
        noticeToDev();
    }
	public static final String DEFAULT_ERROR_VIEW = "401";
	 
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
    
    @ExceptionHandler(GirlFriendNotFoundException.class)
    public ModelAndView handlerGirlFriendNoFoundException(HttpServletRequest req, GirlFriendNotFoundException exception){
//		ModelAndView mav = new ModelAndView("error/401.html");
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
//		mav.addObject("url", "public/error/401.html");
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
    }

    private void logError(Exception e){
    	System.out.println(e.toString());
    }

    private void noticeToDev(){
        // 通知具体开发人员
    }
}
