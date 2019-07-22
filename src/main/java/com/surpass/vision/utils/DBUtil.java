package com.surpass.vision.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.surpass.realkits.JGecService;

public class DBUtil {
	public static void main(String[] args) {
		try {
			Connection con = DBUtil.getConnection();
			JGecService gc = DBUtil.gec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws Exception{
	    Class.forName("org.sqlite.JDBC");
	    Connection con = DriverManager.getConnection("jdbc:sqlite:database.db3");
	    //Class.forName("com.mysql.jdbc.Driver");
		//Connection con =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test01?useUnicode=true&characterEncoding=utf8","root","toor");
		return con;
	}

	public static void setClos(Connection con, PreparedStatement ps,
			ResultSet rs) {
		try {
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static JGecService gec() throws Exception{
		
		return new JGecService("..\\geC.dll");
	}

	public static void setClos(Connection con, PreparedStatement ps) {
		// TODO Auto-generated method stub
		try {
			
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
