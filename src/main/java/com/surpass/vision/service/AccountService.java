package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface AccountService {

	List<TwoString> getList(String name, String id);

	List<String> getAcc(String id);

	boolean getUpdate(String id, String name, String pwd, String upval);

	List<AutUtils> getAut(String id);

	boolean getupdate(String id, String data);



	int  getQuanxian(String id);

	boolean Add(String id, String name, String pwd);

	boolean Delete(String id, String name);

}
