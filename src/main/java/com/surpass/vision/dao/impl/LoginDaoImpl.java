package com.surpass.vision.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.surpass.vision.dao.LoginDao;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.DBUtil;
import com.surpass.vision.utils.TwoString;

@Service
public class LoginDaoImpl implements LoginDao {

	@Override
	public TwoString VerificationAccount(String name, String pwd) {
		String sql = "select id,quanxian from account where name=? and route=?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int f = 0;
		TwoString t = new TwoString();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next()){	
				t.setId( rs.getString("id")==null?"0":rs.getInt("id")+"");
				t.setQuanxian(rs.getString("quanxian"));
				
			}else {
				t.setId("0");
				t.setName("0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
				DBUtil.setClos(con, ps, rs);
			
		}
		
		return t;
	}

	@Override
	public AutUtils getAut(int f) {
		String sql = "select bid from user where aid=? group by bid";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AutUtils aut = new AutUtils();
		aut.setId(f+"");
		List<AutUtils> list = new ArrayList<AutUtils>();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, f);
			rs = ps.executeQuery();
			while (rs.next()) {
				AutUtils a = new AutUtils();
				a.setId(rs.getString("bid"));
				list.add(a);
			}
			aut.setBid(list);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		return aut;
		
	}

	@Override
	public List<AutUtils> getAutson(int f, String id) {
		String sql = "select sonid from user where aid=? and bid=?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AutUtils> list = new ArrayList<AutUtils>();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, f);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				AutUtils a = new AutUtils();
				a.setId(rs.getString("sonid"));
				list.add(a);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		return list;
	}
}