package com.surpass.vision.service;

import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoString;

public interface LoginService {

	UserInfo VerificationAccount(String name, String pwd);

	AutUtils getAut(int f);

}
