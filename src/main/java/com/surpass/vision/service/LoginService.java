package com.surpass.vision.service;

import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface LoginService {

	TwoString VerificationAccount(String name, String pwd);

	AutUtils getAut(int f);

}
