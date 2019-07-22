package com.surpass.vision.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.dao.AccountDao;
import com.surpass.vision.dao.LoginDao;
import com.surpass.vision.dao.impl.AccountDaoImpl;
import com.surpass.vision.dao.impl.LoginDaoImpl;
import com.surpass.vision.service.AccountService;
import com.surpass.vision.service.WholeService;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public class AccountServiceImpl implements AccountService {
	AccountDao dao = new AccountDaoImpl();

	@Override
	public List<TwoString> getList(String name, String id) {
		List<TwoString> list = dao.getList(name, id);

		return list;
	}

	@Override
	public List<String> getAcc(String id) {
		List<String> list = dao.getAcc(id);
		return list;
	}

	@Override
	public boolean getUpdate(String id, String name, String pwd ,String upval) {
		boolean f = dao.getUpdate(id, name, pwd,upval);
		
		return f;
	}

	@Override
	public List<AutUtils> getAut(String id) {
		List<AutUtils> list = dao.getAutS();
		List<AutUtils> list2 = dao.getAut(id);

		for (int i = 0; i < list.size(); i++) {
			List<AutUtils> list3 = dao.getBIao(list.get(i).getRoute(),list.get(i).getId1());
			list.get(i).setBid(list3);
		}
		for (int i = 0; i < list2.size(); i++) {
			List<AutUtils> list3 = dao.getBiao(list2.get(i).getId1(),id);
			list2.get(i).setBid(list3);
		}
		list = WholeService.getAuts(list,list2);
		return list;
	}

	@Override
	public boolean getupdate(String id, String data) {
		dao.delete(id);
		String str[] = data.split(":");
		boolean f =false;
		int num = 0;
		for (int i = 0; i < str.length; i++) {
			String s[] = str[i].split(",");
			num += dao.getUpdateJ(id,s[0],s[1]);
		}
		if(num==str.length){
			f=true;
		}
		
		return f;
	}

	
	@Override
	public int getQuanxian(String id) {
		int f = dao.getQianxian(id);
		return f;
	}

	@Override
	public boolean Add(String id, String name, String pwd) {
		boolean f = dao.Add(id,name,pwd);
		
		
		return f;
	}

	@Override
	public boolean Delete(String id, String name) {
		String []ids = id.split(",");
		int num = 0;
		for (int i = 0; i < ids.length; i++) {
			dao.AccDelete(id);
			num++;
		}
		
		return num==ids.length;
	}

}
