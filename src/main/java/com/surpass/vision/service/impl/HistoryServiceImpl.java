package com.surpass.vision.service.impl;

import com.surpass.vision.dao.HistoryDao;
import com.surpass.vision.dao.impl.HistoryDaoImpl;
import com.surpass.vision.service.HistoryService;

public class HistoryServiceImpl implements HistoryService {
	HistoryDao dao =new HistoryDaoImpl();

	@Override
	public boolean setUpdate(String name, String data, String val, String lid) {
		boolean f = dao.getUpdate(name,data,val,lid);
		return f;
	}
	
}
