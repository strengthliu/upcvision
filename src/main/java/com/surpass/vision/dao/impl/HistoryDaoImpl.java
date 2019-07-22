package com.surpass.vision.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.surpass.vision.dao.HistoryDao;
import com.surpass.vision.utils.DBUtil;

public class HistoryDaoImpl implements HistoryDao {
	@Override
	public boolean getUpdate(String name, String data, String val, String lid) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean f = false;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("update "+name+" set route=? where id=?");
			ps.setString(1, val+":"+data);
			ps.setString(2,lid);
			int num = ps.executeUpdate();
			if(num==1){
				f=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}

}
