package com.surpass.vision.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.surpass.realkits.JGecService;
import com.surpass.realkits.exception.GecException;
import com.surpass.vision.utils.Alert;
import com.surpass.vision.utils.AutUtils;
import com.surpass.vision.utils.DBUtil;
import com.surpass.vision.utils.Data;
import com.surpass.vision.utils.History;
import com.surpass.vision.utils.NameAndID;
import com.surpass.vision.utils.PageNumber;
import com.surpass.vision.utils.TwoString;


public class WholeService {



	public static boolean getBoolean(String name, String pwd) {

		Connection con = null;

		PreparedStatement ps = null;

		ResultSet rs = null;

		boolean f = false;

		try {

			con = DBUtil.getConnection();

			ps = con.prepareStatement("select count(*) from si where name=? and pwd=?");

			ps.setString(1, name);

			ps.setString(2, pwd);

			rs = ps.executeQuery();



			if (rs.next()) {//jdbc登录时验证用户是否登录

				f = rs.getInt(1) == 1;

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

		return f;

	}



	public static List getDeviceName() {

		List<String> list = null;

		List<String> list2 = new ArrayList<String>();

		try {

			JGecService gec = DBUtil.gec();

			String name = gec.DBECEnumServerName().get(0);

			list = gec.DBECEnumDeviceName(name);//遍历设备名称存储并返回

			for (int i = 0; i < list.size(); i++) {

				String a = list.get(i);

				list2.add(a);

			}



		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}



		return list2;

	}



	public static PageNumber getPage(String equipment, String p) {

		try {

			PageNumber page = new PageNumber();

			Integer i = 1;// 默认页码

			if (p != null && !"".equals(p)) {// 如果有页码则转页码为整数类型

				i = Integer.parseInt(p);

			}

			JGecService gec = DBUtil.gec();// 连接数据库

			String lpszServerName = gec.DBECEnumServerName().get(0);// 获得服务器名称

			System.out.println("错误"+lpszServerName);

			List<Long> list = gec.DBECEnumTagIDOfDeviceByDeviceName(

					lpszServerName, equipment);// 通过设备名获取设备下属性id

			long cc = list.get(0);// 将设备第一条的id转int类型

			int aa = (int) cc;// 转int类型

			int size = list.size();// 获得id数量

			page.setLpszDeviceName(equipment);// 放入获得设备名

			page.setTotal(size);// 放入id数量

			page.setStrip(aa);// 放入第一条的id

			page.setPagetotal(size % 10 == 0 ? size / 10 : size / 10 + 1);// 计算总页码

			page.setPage(i);// 获得当前页码

			return page;

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return null;



	}



	public static List<Data> getData(PageNumber page, String equipment)

			throws Exception {

		List<Data> list = new ArrayList<Data>();

		JGecService gec = DBUtil.gec();

		String ServerName = gec.DBECEnumServerName().get(0);// 获得服务器名字



		for (int i = page.getI() - 10; i < page.getI(); i++) {// 通过页码计算好的去循环我所要的id值

			Data data = new Data();

			data.setLpszServerName(ServerName);// 获得服务器名称

			data.setLpszDeviceName(equipment);// 获得设备名称

			data.setId(i);// 获得设备下属性id 获得id以后内部会根据id计算位号名和说明以及值和单位

			list.add(data);// 放入集合

			System.out.println(data.getNumber() + ":::" + data.getId());

		}



		return list;

	}



	public static List<Alert> getAlert(List<NameAndID> list ) {

		List<Alert> alerts = new ArrayList<Alert>();

		JGecService gec = null;



		try {

			gec = DBUtil.gec();

			String serverName = gec.DBECEnumServerName().get(0);//获得服务器名称

			int num = Integer.parseInt(list.get(list.size()-1).getId());//获得id个数

			int num2 = Integer.parseInt(list.get(0).getId());

			List<Long> pnTagIDArray = new ArrayList<Long>();// 存放位号

			for (long i = num2; i <= num; i++) {

				System.out.println(i);

				pnTagIDArray.add(i);

			}

			List<Long> pAlarmTypeArray = new ArrayList<Long>();// 获得警报

			List<Double> pValueArray = new ArrayList<Double>();// 警报值

			List<Long> pOccuredTimeArray = new ArrayList<Long>();// 警报时间



			

			gec.DBACGetCurrentAlarm(serverName, pnTagIDArray,

					pAlarmTypeArray, pnTagIDArray.size(), pValueArray, pOccuredTimeArray);

			System.out.println(pAlarmTypeArray);

			int j = 1;

			for (int i = num2; i <=num; i++) {

				

				String name = gec.DBECGetTagName(serverName, i);

				System.out.println(name);

				//获得警报数据

				if (pAlarmTypeArray.get(j-1) >= 2) {//是否为警报

					Alert a = new Alert();//为警报则吧数据存入

					a.setId(i);//存入id

					a.setPosition(name);//名称

					a.setExplain(gec.DBECGetTagStringField(serverName, name, i,

							"FN_TAGNOTE"));//值

					a.setValue(pValueArray.get(j-1));//警报状态

					a.setStatus(pAlarmTypeArray.get(j-1));//警报时间

					alerts.add(a);

				}

				j++;

			}



			return alerts;

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}



		return alerts;

	}

	





	

	public static List<History> getHistory( String time, String id) {

		List<History> list = new ArrayList<History>();

		JGecService gec = null;

		int num = 1;// 默认时间是1个小时

		if (time != null && !"".equals(time)) {// 有没有传入时间

			num = Integer.parseInt(time);// 修改为传入时间

			

		}

		System.out.println(id);

		String[] ida = id.split(",");// 将id拆分为数组

		List<Integer> idb = new ArrayList<Integer>();



		for (int i = 0; i < ida.length; i++) {// 遍历获得每个数组并且转换成整数类型

			idb.add(Integer.parseInt(ida[i]));

		}

		try {

			gec = DBUtil.gec();

			String ser = gec.DBECEnumServerName().get(0);// 获得服务器名称

			for (int i = 0; i < idb.size(); i++) {// 遍历获得集合内id

				String tag = gec.DBECGetTagName(ser, idb.get(i)).trim();// 获得对应id的号位名

				List<Double> dous = new ArrayList<Double>();// 集合值

				List<Long> strs = new ArrayList<Long>();// 集合时间

				gec.DBECGetTagRealHistory(ser,tag,idb.get(i),

						(System.currentTimeMillis() - 1000 * 60 * 60 * num) / 1000,

						System.currentTimeMillis() / 1000, dous, 5000, strs);// 获得对应的历史数据

				System.out.println(dous.size()+"dou");

				if (strs.size() >= 1 && dous.size() >= 1) {// 确认历史数据的集合值有值

					System.out.println(tag);

					History h = new History();

					h.setId(tag.trim());// 放入号位名

					h.setVal(dous);// 放入值集合

					h.setArr(strs);// 放时间

					list.add(h);// 将类放进去

				}



			}



			return list;// 返回

		} catch (Exception e) {

			e.printStackTrace();

		}



		return list;

	}



	public static List<String> getDataHis(List<History> list) {

		List<String> data = new ArrayList<String>();

		SimpleDateFormat sdFormatter = new SimpleDateFormat(

				"yyyy-MM-dd HH:mm:dd");// 时间格式

		int[] e = { 0, 0, 0 };// 通过遍历选出时间组最长的一组做为时间表

		for (int i = 0; i < list.size(); i++) {

			e[0] = list.get(i).getArr().size();

			if (e[1] < e[0]) {

				e[1] = e[0];

				e[2] = i;

			}

		}

		List<Long> arr = list.get(e[2]).getArr();// 将遍历出的组id放入集合

		int num = arr.size();// 得出时间表长度

		System.out.println(num + ":::" + list.size());

		for (int i = 0; i < num; i++) {// 遍历时间表按照时间格式存入

			Date nowTime = new Date(arr.get(i) * 1000);

			String da = sdFormatter.format(nowTime);

			data.add("'" + da + "'");// 存入

		}

		System.out.println(data);

		return data;

	}



	public static List<NameAndID> getHis(String but) {

		JGecService gec;

		try {

			gec = DBUtil.gec();

			List<NameAndID> hi = new ArrayList<NameAndID>();// 获取设备下的id

			List<Long> dev = gec.DBECEnumTagIDOfDeviceByDeviceName(gec

					.DBECEnumServerName().get(0), but);

			for (int j = 0; j < dev.size(); j++) {// 将设备下的id和位号遍历出来

				NameAndID and = new NameAndID();

				String n = gec.DBECGetTagName(gec.DBECEnumServerName().get(0),

						dev.get(j));

				and.setId(dev.get(j));

				and.setName(n);

				hi.add(and);// 存入id集合

			}

			return hi;

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return null;

	}



	public static List<Alert> getAlertS(String val, String idNum) {

		try {

			JGecService gec =null;

			String serverName = null;

			gec = DBUtil.gec();

			serverName = gec.DBECEnumServerName().get(0);

			String []id = idNum.split(",");

			List<Long> num = new ArrayList<Long>();

			List<Alert> list = new ArrayList<Alert>();

			

			for (int i = 0; i < id.length; i++) {

				num.add(Long.parseLong(id[i]));

			}

			List<Long> pAlarmTypeArray = new ArrayList<Long>();// 获得警报

			List<Double> pValueArray = new ArrayList<Double>();// 警报值

			List<Long> pOccuredTimeArray = new ArrayList<Long>();// 警报时间

			gec.DBACGetCurrentAlarm(serverName, num,

			pAlarmTypeArray, num.size(), pValueArray, pOccuredTimeArray);

			for (int i = 0; i < num.size(); i++) {

				Alert a = new Alert();

				long j = num.get(i);

				a.setId((int)j);

				a.setPosition(gec.DBECGetTagName(serverName, j));

				a.setStatus(pAlarmTypeArray.get(i));

				a.setValue(pValueArray.get(i));

				list.add(a);

			}

			System.out.println(pAlarmTypeArray);

			

			return list;

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		

		

		return null;

	}



	public static List<TwoString> getId(String name) {

		JGecService gec =null;

		String serverName = null;

		List<TwoString> list = new ArrayList<TwoString>();

		

		try {

			gec = DBUtil.gec();

			serverName = gec.DBECEnumServerName().get(0);

			List<Long> l =gec.DBECEnumTagIDOfDeviceByDeviceName(serverName, name); 

			for (int i = 0; i < l.size(); i++) {

				TwoString tw = new TwoString();

				String namea = gec.DBECGetTagName(serverName, l.get(i));

				namea = namea.trim();

				tw.setName(namea);

				tw.setId(l.get(i)+"");

				list.add(tw);

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		

		

		return list;

	}



	



	public static String getTo(String name, String id) {

		Connection con = null;

		PreparedStatement ps = null;

		ResultSet rs = null;

		String f = "";

		try {

			con = DBUtil.getConnection();

			ps = con.prepareStatement("select route from "+name+" where id=?");

			ps.setString(1, id);

			rs = ps.executeQuery();



			if (rs.next()) {

				f= rs.getString("route");

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

				DBUtil.setClos(con, ps, rs);



		}

		return f;

	}



	public static List<AutUtils> getAuts(List<AutUtils> list,

			List<AutUtils> list2) {

		for (int i = 0; i < list2.size(); i++) {

			for (int j = 0; j < list.size(); j++) {

				for (int k = 0; k < list2.get(i).getBid().size(); k++) {

					String c = list2.get(i).getBid().get(k).getId();

					for (int l = 0; l < list.get(j).getBid().size(); l++) {

						String d = list.get(j).getBid().get(l).getId();

						if (c.equals(d)) {

							System.out.println(c+"::"+d);

							list.get(j).getBid().get(l).setF(true);

						}

					}

				}

			}

		}

		return list;

	}



	public static List<TwoString> getList(String name) {

		Connection con = null;

		PreparedStatement ps = null;

		ResultSet rs = null;

		List<TwoString>list  = new ArrayList<TwoString>();

		try {

			con = DBUtil.getConnection();

			ps = con.prepareStatement("select id,name,route from "+name);

			rs = ps.executeQuery();



			while(rs.next()) {

				TwoString ts = new TwoString();

				ts.setId(rs.getString("id"));

				ts.setName(rs.getString("name"));

				list.add(ts);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

				DBUtil.setClos(con, ps, rs);



		}

		return list;

	}



	public static List<AutUtils> getUpdate() throws Exception {

		List<AutUtils> list = new ArrayList<AutUtils>();

		JGecService gec = DBUtil.gec();

		String sn = gec.DBECEnumServerName().get(0);

		List<String> l = gec.DBECEnumDeviceName(sn);

		String eid = "";

		for (int i = 0; i < l.size(); i++) {

			AutUtils aut = new AutUtils();

			aut.setName(l.get(i));

			List<AutUtils> l2 = new ArrayList<AutUtils>();

			List<Long> id = new ArrayList<Long>();

			try {

				 id = gec.DBECEnumTagIDOfDeviceByDeviceName(sn, l.get(i)); 

			} catch (GecException e) {

				aut.setBid(l2);

				list.add(aut);

				continue;

				

			}

			

			System.out.println(id);

			for (int j = 0; j < id.size(); j++) {

				AutUtils aut2 = new AutUtils();

				long log = 1847;

				if (log==id.get(j)) {

					eid = l.get(i);

				}

				String tagid = gec.DBECGetTagName(sn, id.get(j));

				aut2.setId(id.get(j)+"");

				aut2.setName(tagid);

				l2.add(aut2);

				

			}

			aut.setBid(l2);

			list.add(aut);

		}

		

		System.out.println(eid);

		return list;

	}



	public static boolean getAdd(String id, String name,String mz) {//id是保存的位号  name是表名   mz是设置的名字

	

		Connection con = null;

		PreparedStatement ps = null;

		ResultSet rs = null;

		boolean f = false;

		try {

			con = DBUtil.getConnection();

			ps = con.prepareStatement(" insert into "+name+" values(?,?,?) ");

			ps.setString(1, "0");

			ps.setString(2, new String(mz.getBytes(),"utf-8"));

			ps.setString(3, id);

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



	public static boolean Delete(String name, String id) {

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



	public static List<Data> getData1(String[] stas) throws Exception {

		List<Data> list = new ArrayList<Data>();

		JGecService gec = DBUtil.gec();

		String ServerName = gec.DBECEnumServerName().get(0);// 获得服务器名字

		for (int i = 0; i < stas.length; i++) {

			Data data = new Data();

			data.setLpszServerName(ServerName);// 获得服务器名称

			data.setLpszDeviceName("所有设备");// 获得设备名称

			data.setId(Integer.parseInt(stas[i]));// 获得设备下属性id 获得id以后内部会根据id计算位号名和说明以及值和单位

			list.add(data);// 放入集合

		}

		

		return list;

	}



	public static List<Alert> getAlert2(String[] stas) throws Exception {

		

		List<Alert> alerts = new ArrayList<Alert>();

		JGecService gec = DBUtil.gec();

		String serverName = gec.DBECEnumServerName().get(0);//获得服务器名称

		List<Long> pnTagIDArray = new ArrayList<Long>();// 存放位号

		for (int i = 0; i < stas.length; i++) {

			pnTagIDArray.add(Long.parseLong(stas[i]));

		}

		List<Long> pAlarmTypeArray = new ArrayList<Long>();// 获得警报

		List<Double> pValueArray = new ArrayList<Double>();// 警报值

		List<Long> pOccuredTimeArray = new ArrayList<Long>();// 警报时间

		

		

		gec.DBACGetCurrentAlarm(serverName, pnTagIDArray,

				pAlarmTypeArray, pnTagIDArray.size(), pValueArray, pOccuredTimeArray);

		System.out.println(pAlarmTypeArray);

		for (int i = 0; i <stas.length; i++) {

			

			String name = gec.DBECGetTagName(serverName, pnTagIDArray.get(i));

			System.out.println(name);

			//获得警报数据

			if (pAlarmTypeArray.get(i) >= 2) {//是否为警报

				Alert a = new Alert();//为警报则吧数据存入

				a.setId(Integer.parseInt(pnTagIDArray.get(i)+""));//存入id

				a.setPosition(name);//名称

				a.setExplain(gec.DBECGetTagStringField(serverName, name, pnTagIDArray.get(i),

						"FN_TAGNOTE"));//值

				a.setValue(pValueArray.get(i));//警报状态

				a.setStatus(pAlarmTypeArray.get(i));//警报时间

				alerts.add(a);

			}

			

		}



		return alerts;

	}

	public static List<AutUtils> getUpdate2(List<String> list2) throws Exception {

		List<AutUtils> list = new ArrayList<AutUtils>();

		JGecService gec = DBUtil.gec();

		String sn = gec.DBECEnumServerName().get(0);

		List<String> l = gec.DBECEnumDeviceName(sn);

		for (int i = 0; i < l.size(); i++) {

			AutUtils aut = new AutUtils();

			aut.setName(l.get(i));

			List<AutUtils> l2 = new ArrayList<AutUtils>();

			List<Long> id = gec.DBECEnumTagIDOfDeviceByDeviceName(sn, l.get(i)); 

			System.out.println(id);

			for (int j = 0; j < id.size(); j++) {

				AutUtils aut2 = new AutUtils();

				String tagid = gec.DBECGetTagName(sn, id.get(j));

				aut2.setId(id.get(j)+"");

				aut2.setName(tagid);

				for (int k = 0; k < list2.size(); k++) {

					if (id.get(j)==Long.parseLong(list2.get(k))) {

						System.out.println(tagid);

						aut2.setF(true);

					}

				}

				

				l2.add(aut2);

				

			}

			aut.setBid(l2);

			list.add(aut);

		}

		

		

		return list;

	}



	public static void getsqladd() {

		

		

	}



}
