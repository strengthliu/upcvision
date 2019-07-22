package com.surpass.vision.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.dao.AccountDao;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.DBUtil;
import com.surpass.vision.utils.TwoString;

public class AccountDaoImpl implements AccountDao {

	@Override
	public List<TwoString> getList(String name, String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TwoString> list = new ArrayList<TwoString>();
		
		
		try {
			String sql = "select id,name from account where 1=1";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				TwoString t = new TwoString();
				t.setId(rs.getString("id"));
				t.setName(rs.getString("name"));
				list.add(t);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		return list;
	}

	@Override
	public List<String> getAcc(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		
		
		try {
			String sql = "select id,name from account where id=?";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1,id);
			rs = ps.executeQuery();
			if (rs.next()) {
				list.add(rs.getString("id"));
				list.add(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		return list;
	}

	@Override
	public boolean getUpdate(String id, String name, String pwd, String upval) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean f = false;
		try {
			String sql = "update account set name=?";
			if (pwd!=null&&!"".equals(pwd)) {
				sql+=",route=? ";
			}
			if (upval!=null&&!"".equals(upval)) {
				sql+=",quanxian=? ";
			}
			sql+=" where id=?";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			if(pwd!=null&&!"".equals(pwd)){
				ps.setString(2, pwd);
				if (upval!=null&&!"".equals(upval)) {
					ps.setString(3, upval);
					ps.setString(4, id);
				}else {
					ps.setString(3, id);
				}
			}else {
				if (upval!=null&&!"".equals(upval)) {
					ps.setString(2,upval);
					ps.setString(3, id);
				}else {
					ps.setString(2, id);
				}
				
			}
			int num = ps.executeUpdate();
			if(num==1){
				f=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps);
		}
		
		return f;
	}

	@Override
	public List<AutUtils> getAut(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AutUtils> list = new ArrayList<AutUtils>();
		try {
			String sql = "select bid from user where aid=? group by bid";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1,id);
			rs = ps.executeQuery();
			while (rs.next()) {
				AutUtils a = new AutUtils();
				a.setId(rs.getInt("bid"));
				list.add(a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		
		
		return list;
	}

	@Override
	public List<AutUtils> getAutS() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AutUtils> list = new ArrayList<AutUtils>();
		try {
			String sql = "select id,name,route from bia where 1=1";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AutUtils a = new AutUtils();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setRoute(rs.getString("route"));
				list.add(a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		
		
		return list;
	}

	@Override
	public List<AutUtils> getBIao(String route,int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AutUtils> list = new ArrayList<AutUtils>();
		try {
			String sql = "select id,name,route from "+route+" where 1=1";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AutUtils a = new AutUtils();
				a.setId(id+","+rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setRoute(rs.getString("route"));
				list.add(a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		return list;  
	}

	@Override
	public List<AutUtils> getBiao(int id,String id2) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AutUtils> list = new ArrayList<AutUtils>();
		try {
			String sql = "select sonid from user where aid=? and bid=? group by sonid";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1,id2);
			ps.setInt(2,id);
			rs = ps.executeQuery();
			while (rs.next()) {
				AutUtils a = new AutUtils();
				a.setId(id+","+rs.getInt("sonid"));
				list.add(a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		return list;
	}

	@Override
	public void delete(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from user where aid=?";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps);
		}
		
	}

	@Override
	public int getUpdateJ(String id, String string, String string2) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into user values(?,?,?)";
		int num = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, string);
			ps.setString(3, string2);
			num = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return num;
	}

	

	@Override
	public int getQianxian(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num = 0;
		try {
			String sql = "select quanxian from account where id=?";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				num = Integer.parseInt(rs.getString("quanxian"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		return num;
	}

	@Override
	public boolean Add(String id, String name, String name2) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into account values(?,?,?,?)";
		int num = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, null);
			ps.setString(2, name);
			ps.setString(3, name2);
			ps.setString(4, id);
			num = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return num==1;
	}

	@Override
	public void AccDelete(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from account where id=?";
		int num = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			num = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
