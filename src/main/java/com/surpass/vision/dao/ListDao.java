package com.surpass.vision.dao;

import java.util.List;

import com.surpass.vision.utils.AcUp;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface ListDao {

	String getBid(String name);

	List<AcUp> getUser(String pwd, String bid, String n);

	List<String> getId(String pwd, String bid);

	boolean getUpdate(String string, String bid, String sonid);

	List<String> getStrId(String id, String name);

	boolean Xougai(String id, String name, String mz, String name2);

	int getAdd(String id, String name, String mz);

	int getSelect(String name);

	boolean setAdd(String n, int num2, int num);

	void delete(String bid, String sonid);

	boolean delete1(String name, String id);

	boolean delete2(int num, String id);

	List<TwoString> getList(String name, List<AutUtils> bid);

}
