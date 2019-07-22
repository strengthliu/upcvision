package com.surpass.vision.utils;

import java.util.List;

public class AutUtils {
	private String id ;//id��
	private String name;//����
	private String route;//���ݿ�����
	private List<AutUtils> bid ;//����
	private String quanxian;
	private boolean f = false;//�ж��Ƿ�ӵ��
	public String getId() {
		return id;
	}
	public int getId1() {
		return Integer.parseInt(id);
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setId(int id) {
		this.id = id+"";
	}
	public List<AutUtils> getBid() {
		return bid;
	}
	public void setBid(List<AutUtils> bid) {
		this.bid = bid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public boolean getF() {
		return f;
	}
	public void setF(boolean f) {
		this.f = f;
	}
	public String getQuanxian() {
		return quanxian;
	}
	public void setQuanxian(String quanxian) {
		this.quanxian = quanxian;
	}
	
	
}
