package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.utils.AcUp;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface ListService {

	List<AcUp> update(String pwd, String name, String n);

	int update2(String id, String name);

	List<AutUtils> getAut(String id, String name);

	boolean Xougai(String id, String name, String mz, String name2);

	boolean getAdd(String id, String name, String mz, String n);

	boolean Delete(String name, String id);

	List<TwoString> getList(String name, List<AutUtils> list);

	
}
