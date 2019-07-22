package com.surpass.vision.dao;

import java.util.List;

import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.TwoList;
import com.surpass.vision.utils.TwoString;

public interface LeftDao {

	List<TwoString> getTwoString(List<AutUtils> list);

	List<TwoString> getList(String string, List<AutUtils> list);

}
