package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.dao.LeftDao;
import com.surpass.vision.dao.impl.LeftDaoImpl;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoList;
import com.surpass.vision.utils.TwoString;

public interface LeftService {
	

	List<TwoString> getTwoString(List<AutUtils> list);

	List<TwoString> getList(String string, List<AutUtils> list);
	
	
}
