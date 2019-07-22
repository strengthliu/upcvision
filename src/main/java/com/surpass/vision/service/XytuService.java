package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.utils.TwoString;

public interface XytuService {

	List<TwoString> getList(String id, String name, int i);

	List<TwoString> getList(String id, String name, String date2, String date3);
	
}
