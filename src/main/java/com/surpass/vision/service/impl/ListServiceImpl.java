package com.surpass.vision.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.dao.ListDao;
import com.surpass.vision.dao.impl.ListDaoImpl;
import com.surpass.vision.service.ListService;
import com.surpass.vision.service.WholeService;
import com.surpass.vision.utils.AcUp;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public class ListServiceImpl implements ListService {
	private ListDao dao = new ListDaoImpl();

	@Override
	public List<AcUp> update(String pwd, String name, String n) {
		List<AcUp> list = new ArrayList<AcUp>();
		String bid = dao.getBid(name);
		list = dao.getUser(pwd,bid,n);
		List<String> l = dao.getId(pwd,bid);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getName());
			for (int j = 0; j < l.size(); j++) {
				System.out.println(l.get(j).equals(list.get(i).getId()));
				if (l.get(j).equals(list.get(i).getId())) {
					
					list.get(i).setF(true);
				}
			}
		}
		
		return list;
	}

	@Override
	public int update2(String id, String name) {
		String []id2 = id.split(":");
		String []name2 = name.split(",");
		
		String bid = name2[0].trim();
		String sonid = name2[1].trim();
		int a = 0;
		dao.delete(bid,sonid);
		for (int i = 0; i < id2.length; i++) {
			
				
				boolean f =  dao.getUpdate(id2[i].trim(),bid,sonid);
				a = f?a++:a;
		
			System.out.println(a);
		}
		
		return a;
	}

	@Override
	public List<AutUtils> getAut(String id, String name) {
		List<String> list = dao.getStrId(id,name);
		List<AutUtils> l = null;
		try {
			l =  WholeService.getUpdate2(list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return l;
	}

	@Override
	public boolean Xougai(String id, String name, String mz, String name2) {
		boolean f = dao.Xougai(id,name,mz,name2);
		
		
		return f;
	}

	@Override
	public boolean getAdd(String id, String name, String mz, String n) {//锟铰斤拷锟斤拷锟�
		int num = dao.getAdd(id,name,mz);//通锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟� 锟斤拷锟斤拷锟斤拷锟絠d
		int num2 = dao.getSelect(name);//锟斤拷酶锟斤拷锟絠d
		System.out.println("::锟斤拷锟斤拷锟�");
		boolean f = dao.setAdd(n,num2,num);//锟斤拷锟饺拷锟�
		return true;
	}

	@Override
	public boolean Delete(String name, String id) {
		boolean f = dao.delete1(name,id);
		int num = dao.getSelect(name);
		boolean f1 = dao.delete2(num,id);
		return f;
	}

	@Override
	public List<TwoString> getList(String name, List<AutUtils> list) {
		int num = dao.getSelect(name);
		List<TwoString> list2 = null;
		for (int i = 0; i < list.size(); i++) {
			if (num==list.get(i).getId1()) {
				list2 = dao.getList(name,list.get(i).getBid());
			}
		}
		
		return list2;
	}
	

	
	
}
