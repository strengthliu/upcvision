package com.surpass.vision.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.dao.ListDao;
import com.surpass.vision.utils.AcUp;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.DBUtil;
import com.surpass.vision.utils.TwoString;

public class ListDaoImpl implements ListDao {

	@Override
	public String getBid(String name) {
		String sql = "select id from bia where route=?";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String n = "";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name); 
			rs = ps.executeQuery();
			if (rs.next()) {
				n = rs.getString("id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		return n;
	}

	@Override
	public List<AcUp> getUser(String pwd, String bid, String n) {
		String sql = "select id,name from account where 1=1";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AcUp> list = new ArrayList<AcUp>(); 
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				if (!rs.getString("id").equals(n)) {
					AcUp a = new AcUp();
					a.setId(rs.getString("id"));
					a.setName(rs.getString("name"));
					a.setBid(bid);
					a.setSonid(pwd);
					list.add(a);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<String> getId(String pwd, String bid) {
		String sql = "select aid from user where bid=? and sonid=?";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>(); 
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bid);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			while(rs.next()) {
				String a = rs.getString("aid");
				System.out.println(a+"��");
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
	public boolean getUpdate(String string, String bid, String sonid) {
		String sql = "insert into user values(?,?,?)";
		Connection con = null ;
		PreparedStatement ps = null;
		boolean f = false;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, string);
			ps.setString(2, bid);
			ps.setString(3, sonid);
			int i = ps.executeUpdate();
			if (i==1) {
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
	public void delete(String bid, String sonid) {
		String sql = "delete from user where bid=? and sonid=?";
		
		Connection con = null ;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, bid);
			ps.setString(2, sonid);
			int i =ps.executeUpdate();
			System.out.println("delete+"+i);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps);
		}
		
	}

	@Override
	public List<String> getStrId(String id, String name) {
		String sql = "select route from "+name+" where id=?";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>(); 
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				String[] a = rs.getString("route").split(",");
			
				for (int i = 0; i < a.length; i++) {
					System.out.println(a[i]);
					list.add(a[i]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		return list;
	}

	@Override
	public boolean Xougai(String id, String name, String mz, String name2) {
		String sql = "update "+name+" set route=?,name=? where id=? ";
		Connection con = null ;
		PreparedStatement ps = null;
		boolean f = false;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, mz);
			ps.setString(3, name2);
			int a = ps.executeUpdate();
			f = a==1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public int getAdd(String id, String name, String mz) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int a = 0;
		String sql = "insert into "+name+" values(?,?,?)";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, null); 
			ps.setString(2,	mz);
			ps.setString(3, id);
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				a=rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBUtil.setClos(con, ps, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return a;
	}

	@Override
	public int getSelect(String name) {
		String sql = "select id from bia where route=?";
		Connection con = null ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int a = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				a = rs.getInt("id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.setClos(con, ps, rs);
		}
		
		return a;
	}

	
	@Override
	public boolean setAdd(String n, int num2, int num) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean f = false;
		String sql = "insert into user values(?,?,?)";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, n);
			ps.setInt(2,num2);
			ps.setInt(3,num);
			int a = ps.executeUpdate();
			if (a>=1&&!"1".equals(n)) {//Ĭ�����Ȩ�޵�ʱ����������Ա�˺������ͬȨ��
				ps = con.prepareStatement(sql);
				ps.setString(1, "1");
				ps.setInt(2,num2);
				ps.setInt(3,num);
			}
			f = a==1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	
	@Override
	public boolean delete1(String name, String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean f = false;
		try { 
			con = DBUtil.getConnection();
			ps = con.prepareStatement(" delete from "+name+" where id=? ");
			ps.setString(1, id);
			int i =ps.executeUpdate();
			f = i== 1;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBUtil.setClos(con, ps, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return f;
	}

	@Override
	public boolean delete2(int num, String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean f = false;
		try { 
			con = DBUtil.getConnection();
			ps = con.prepareStatement(" delete from user where bid=? and sonid=?");
			ps.setInt(1, num);
			ps.setString(2, id);
			int i =ps.executeUpdate();
			f = i== 1;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBUtil.setClos(con, ps, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return f;
	}

	@Override
	public List<TwoString> getList(String name, List<AutUtils> bid) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TwoString>list  = new ArrayList<TwoString>();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("select id,name,route from "+name);
			rs = ps.executeQuery();

			while(rs.next()) {
				for (int i = 0; i < bid.size(); i++) {
					if (rs.getString("id").equals(bid.get(i).getId())) {
						TwoString ts = new TwoString();
						ts.setId(rs.getString("id"));
						ts.setName(rs.getString("name"));
						list.add(ts);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBUtil.setClos(con, ps, rs);

		}
		return list;
		
	}
	
}
