package com.surpass.vision.dao;

import java.util.List;

import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface AccountDao {

	List<TwoString> getList(String name, String id);

	List<String> getAcc(String id);

	boolean getUpdate(String id, String name, String pwd, String upval);

	List<AutUtils> getAut(String id);

	List<AutUtils> getAutS();

	List<AutUtils> getBIao(String route,int id);

	void delete(String id);

	int getUpdateJ(String id, String string, String string2);

	List<AutUtils> getBiao(int id1, String id);


	int getQianxian(String id);

	boolean Add(String id, String name, String name2);


	void AccDelete(String id);

}
