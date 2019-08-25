/**
 * 
 */
package com.surpass.vision.service;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.domain.FileList;


/**
 * @author Administrator
 *
 */
public interface GraphService {

	int getCurrentVersion();

	FileList getCurrentFileList();

	ArrayList<FileList> getCurrentUpdate();
	

}
