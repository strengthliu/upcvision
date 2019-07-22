package com.surpass.vision.dao;

import java.util.List;

import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface LoginDao {

	TwoString VerificationAccount(String name, String pwd);

	AutUtils getAut(int f);


	List<AutUtils> getAutson(int f, String id);

}
