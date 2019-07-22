package com.surpass.vision.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.RowSet;

import com.surpass.vision.dao.XytuDao;
import com.surpass.vision.utils.DBUtil;

public class XytuDaoImpl implements XytuDao {

	@Override
	public String getString(String id, String name) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		String str = new String();
		String sql = "select route from "+name+" where id=? ";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(id));
			rs =ps.executeQuery();
			if (rs.next()) {
				str = rs.getString(1);
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
	
		return null;
	}

}
