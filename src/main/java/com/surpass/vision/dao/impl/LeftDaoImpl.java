package com.surpass.vision.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.dao.LeftDao;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.DBUtil;
import com.surpass.vision.utils.TwoList;
import com.surpass.vision.utils.TwoString;

public class LeftDaoImpl implements LeftDao {

	@Override
	public List<TwoString> getTwoString(List<AutUtils> list) {
		List<TwoString> list1 = new ArrayList<TwoString>();
		String sql = "select id,name,route from bia where 1=1";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			for (;rs.next();) {
				TwoString t = new TwoString();
				int z = rs.getInt("id");
				for (int i = 0; i < list.size(); i++) {
					if (Integer.parseInt(list.get(i).getId())==z) {//ת�����������ͽ��бȶ�ӵ�и�Ȩ����д��list
						t.setName(rs.getString("name"));
						t.setId(rs.getString("route"));;
						list1.add(t);
						break;
					}
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			DBUtil.setClos(con, ps, rs);
		}
		
		return list1;
	}

	@Override
	public List<TwoString> getList(String string,List<AutUtils> list1) {
		List<TwoString> list = new ArrayList<TwoString>();
		String sql = "select name,id from "+string+" where 1=1";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				TwoString t = new TwoString();
				int z = rs.getInt("id");
				for (int i = 0; i < list1.size(); i++) {
					if(Integer.parseInt(list1.get(i).getId())==z){
						t.setName(rs.getString("name"));
						System.out.println(t.getName()+"::"+string);
						t.setId(z+"");
						list.add(t);
						break;
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			DBUtil.setClos(con, ps, rs);
		}
		
		return list;
	}
	

}
