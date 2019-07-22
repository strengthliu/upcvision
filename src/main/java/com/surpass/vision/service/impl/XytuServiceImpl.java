package com.surpass.vision.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.surpass.realkits.JGecService;
import com.surpass.vision.dao.XytuDao;
import com.surpass.vision.dao.impl.XytuDaoImpl;
import com.surpass.vision.service.XytuService;
import com.surpass.vision.utils.DBUtil;
import com.surpass.vision.utils.TwoString;

public class XytuServiceImpl implements XytuService {
	private XytuDao dao = new XytuDaoImpl();
	@Override
	public List<TwoString> getList(String id, String name, int num) {
		JGecService gec = null;
		String str = dao.getString(id,name);
		String []ida = str.split(",");
		List<Integer> idb = new ArrayList<Integer>();

		for (int b = 0; b < ida.length; b++) {// �������ÿ�����鲢��ת������������
			idb.add(Integer.parseInt(ida[b]));
		}
		try {
			gec = DBUtil.gec();
			String ser = gec.DBECEnumServerName().get(0);// ��÷���������
			List<Double> dous = new ArrayList<Double>();// ��õ���ʷ����ֵ���洦
			List<Long> strs = new ArrayList<Long>();// ��õ���ʷ���ݴ��漯��ʱ�䴦
			
			List<Double> dous2 = new ArrayList<Double>();// ��õ���ʷ����ֵ���洦
			List<Long> strs2 = new ArrayList<Long>();// ��õ���ʷ���ݴ��漯��ʱ�䴦
			
			for (int a = 0; a < idb.size(); a++) {// ������ü�����id
				if (a>=1) {
					dous2 = dous;
					strs2 = strs;
					dous = new ArrayList<Double>();
					strs = new ArrayList<Long>();
				}
				
				String tag = gec.DBECGetTagName(ser, idb.get(a)).trim();// ��ö�Ӧid�ĺ�λ��
				System.out.println("λ��"+tag);
				gec.DBECGetTagRealHistory(ser,tag,idb.get(a),
						(System.currentTimeMillis() - 1000 * 60 * 60 * num) / 1000,
						System.currentTimeMillis() / 1000, dous, 5000, strs);// ��ö�Ӧ����ʷ����
				System.out.println(dous.size()+"dou");
					System.out.println(tag);



			}
			List<TwoString> st2 = new ArrayList<TwoString>();
			if (strs.size()>strs2.size()) {
				for (int j = 0; j < strs.size(); j++) {
					for (int j2 = 0; j2 < strs2.size(); j2++) {
						long l = strs.get(j);
						long l2 = strs2.get(j2);
						//System.out.println(strs.get(j)+"::"+strs2.get(j2)+"::"+(l==l2)+"�Ա�");
						if (l==l2) {
							TwoString a = new TwoString();
							DecimalFormat df = new DecimalFormat("#.00");
							
							a.setId(Double.parseDouble(df.format(dous.get(j)))+"");
							a.setName(Double.parseDouble(df.format(dous2.get(j2)))+"");
							st2.add(a);
							break;
						}else {
							if (!(j2 < strs2.size())) {
								TwoString a = new TwoString();
								a.setId(new Double(0)+"");
								a.setName(new Double(0)+"");
							}
						}
					}
				}
			}else {
				for (int j = 0; j < strs2.size(); j++) {
					for (int j2 = 0; j2 < strs.size(); j2++) {
						long l = strs2.get(j);
						long l2 = strs.get(j2);
						//System.out.println(strs.get(j2)+"::"+strs2.get(j)+"::"+(l==l2)+"�Ա�");
						if (l==l2) {
							TwoString a = new TwoString();
							DecimalFormat df = new DecimalFormat("#.00");
							
							a.setId(Double.parseDouble(df.format(dous.get(j2)))+"");
							a.setName(Double.parseDouble(df.format(dous2.get(j)))+"");
							st2.add(a);
							break;
						}else {
							if (!(j2 < strs.size())) {
								TwoString a = new TwoString();
								a.setId(new Double(0)+"");
								a.setName(new Double(0)+"");
							}
						}
					}
				}
			}
		
		return st2;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<TwoString> getList(String id, String name, String date2,
			String date3) {
		JGecService gec = null;
		DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dao.getString(id,name);
		String []ida = str.split(",");
		List<Integer> idb = new ArrayList<Integer>();

		for (int b = 0; b < ida.length; b++) {// �������ÿ�����鲢��ת������������
			idb.add(Integer.parseInt(ida[b]));
		}
		try {
			gec = DBUtil.gec();
			Date d1 = d.parse(date2);
			Date d2 = d.parse(date3);
			String ser = gec.DBECEnumServerName().get(0);// ��÷���������
			List<Double> dous = new ArrayList<Double>();// ��õ���ʷ����ֵ���洦
			List<Long> strs = new ArrayList<Long>();// ��õ���ʷ���ݴ��漯��ʱ�䴦
			
			List<Double> dous2 = new ArrayList<Double>();// ��õ���ʷ����ֵ���洦
			List<Long> strs2 = new ArrayList<Long>();// ��õ���ʷ���ݴ��漯��ʱ�䴦
			
			for (int a = 0; a < idb.size(); a++) {// ������ü�����id
				if (a>=1) {
					dous2 = dous;
					strs2 = strs;
					dous = new ArrayList<Double>();
					strs = new ArrayList<Long>();
				}
				
				String tag = gec.DBECGetTagName(ser, idb.get(a)).trim();// ��ö�Ӧid�ĺ�λ��
				System.out.println("λ��"+tag);
				gec.DBECGetTagRealHistory(ser,tag,idb.get(a),
						d1.getTime() / 1000,
						d2.getTime() / 1000, dous, 5000, strs);// ��ö�Ӧ����ʷ����
				System.out.println(dous.size()+"dou");
					System.out.println(tag);



			}
			List<TwoString> st2 = new ArrayList<TwoString>();
			if (strs.size()>strs2.size()) {
				for (int j = 0; j < strs.size(); j++) {
					for (int j2 = 0; j2 < strs2.size(); j2++) {
						long l = strs.get(j);
						long l2 = strs2.get(j2);
						//System.out.println(strs.get(j)+"::"+strs2.get(j2)+"::"+(l==l2)+"�Ա�");
						if (l==l2) {
							TwoString a = new TwoString();
							DecimalFormat df = new DecimalFormat("#.00");
							
							a.setId(Double.parseDouble(df.format(dous.get(j)))+"");
							a.setName(Double.parseDouble(df.format(dous2.get(j2)))+"");
							st2.add(a);
							break;
						}else {
							if (!(j2 < strs2.size())) {
								TwoString a = new TwoString();
								a.setId(new Double(0)+"");
								a.setName(new Double(0)+"");
							}
						}
					}
				}
			}else {
				for (int j = 0; j < strs2.size(); j++) {
					for (int j2 = 0; j2 < strs.size(); j2++) {
						long l = strs2.get(j);
						long l2 = strs.get(j2);
						//System.out.println(strs.get(j2)+"::"+strs2.get(j)+"::"+(l==l2)+"�Ա�");
						if (l==l2) {
							TwoString a = new TwoString();
							DecimalFormat df = new DecimalFormat("#.00");
							
							a.setId(Double.parseDouble(df.format(dous.get(j2)))+"");
							a.setName(Double.parseDouble(df.format(dous2.get(j)))+"");
							st2.add(a);
							break;
						}else {
							if (!(j2 < strs.size())) {
								TwoString a = new TwoString();
								a.setId(new Double(0)+"");
								a.setName(new Double(0)+"");
							}
						}
					}
				}
			}
		
		return st2;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
