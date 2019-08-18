package com.surpass.vision.schedule;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsynchroniousService {
	  @Async
	  public Future springAsynchronousMethod(){
	    return null;
	  }
}
