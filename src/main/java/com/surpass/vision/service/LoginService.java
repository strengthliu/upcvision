package com.surpass.vision.service;

import com.surpass.vision.domain.UserInfo;

public interface LoginService {

	UserInfo VerificationAccount(String name, String pwd);


}