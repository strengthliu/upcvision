package com.surpass.vision.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.surpass.vision.dao.LoginDao;
import com.surpass.vision.dao.impl.LoginDaoImpl;
import com.surpass.vision.service.LoginService;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

@Service
public class LoginServiceImpl implements LoginService {
	private LoginDao dao = new LoginDaoImpl();
	
	@Override
	public TwoString VerificationAccount(String name, String pwd) {
		TwoString f = dao.VerificationAccount(name,pwd);
		
		
		
		return f;
	}

	@Override
	public AutUtils getAut(int f) {//查询拥有的权限
		AutUtils aut = dao.getAut(f);//查询父类id和表名
		List<AutUtils> list = aut.getBid();
		for (int i = 0; i < list.size(); i++) {//遍历子类id
			List<AutUtils> list2 = dao.getAutson(f,list.get(i).getId());
			aut.getBid().get(i).setBid(list2);
		}
		
		return aut;
	}
	
}
