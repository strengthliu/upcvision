package com.surpass.vision.utils;

import java.text.DecimalFormat;

import com.surpass.realkits.GecService;
import com.surpass.realkits.JGecService;
import com.surpass.realkits.exception.GecException;

public class Data {
	private String lpszServerName;//����������
	private String lpszDeviceName;//�豸����
	private Integer id;//����id
	private String explain;//����˵��
	private String number;//���Ժ�λ
	private String Company;//����ֵ�ĵ�λ
	private String value;//����ֵ
	static JGecService gec = null;
	static{
		try {
			gec = DBUtil.gec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	public Data() {
		
	}
	public String getLpszServerName() {
		return lpszServerName;
	}
	public void setLpszServerName(String lpszServerName) {
		this.lpszServerName = lpszServerName;
	}
	public String getLpszDeviceName() {
		return lpszDeviceName;
	}
	public void setLpszDeviceName(String lpszDeviceName) {
		this.lpszDeviceName = lpszDeviceName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
		try {
			this.number = gec.DBECGetTagName(lpszServerName, id);
			this.explain = gec.DBECGetTagStringField(lpszServerName, this.number, id, "FN_TAGNOTE");
			this.Company = gec.DBECGetTagStringField(lpszServerName, this.number, id, "FN_ENUNITS");
			DecimalFormat df = new DecimalFormat("#.00");
			this.value = (String)df.format(gec.DBECGetTagRealFieldByTagName(lpszServerName, this.number, "FN_RTVALUE"));
			
		} catch (GecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
	
	

	