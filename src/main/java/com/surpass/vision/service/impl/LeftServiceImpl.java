package com.surpass.vision.service.impl;

import java.util.List;

import com.surpass.vision.dao.LeftDao;
import com.surpass.vision.dao.impl.LeftDaoImpl;
import com.surpass.vision.service.LeftService;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoList;
import com.surpass.vision.utils.TwoString;

public class LeftServiceImpl implements LeftService {
	private LeftDao dao = new LeftDaoImpl();
	@Override
	public List<TwoString> getTwoString(List<AutUtils> list) {
		List<TwoString> list1 = dao.getTwoString(list);
		
		return list1;
	}
	@Override
	public List<TwoString> getList(String string, List<AutUtils> list) {
		List<TwoString> list1 = dao.getList(string,list);
		return list1;
	}

}
