package com.surpass.vision.utils;

import sun.rmi.runtime.Log;

public class PageNumber {
	private Integer page;//��ǰҳ��
	private Integer pagetotal;//��ҳ��
	private Integer strip;//��һ��
	private Integer total;//һ������������
	private Integer i;//ѭ������
	private String lpszDeviceName;//��¼�豸
	
	public PageNumber() {
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
		if(this.pagetotal!=null&&!"".equals(this.pagetotal)&&page!=this.pagetotal){
			this.i = this.strip+(page*10);
		}else{
			System.out.println(this.strip+"::"+this.total);
			this.i = this.strip+this.total;
		}
	}
	public Integer getPagetotal() {
		return pagetotal;
	}
	public void setPagetotal(Integer pagetotal) {
		this.pagetotal = pagetotal;
	}
	public Integer getStrip() {
		return strip;
	}
	public void setStrip(Integer strip) {
		this.strip = strip;
		
	}
		
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getI() {
		return i;
	}
	public void setI(Integer i) {
		this.i = i;
	}
	public String getLpszDeviceName() {
		return lpszDeviceName;
	}
	public void setLpszDeviceName(String lpszDeviceName) {
		this.lpszDeviceName = lpszDeviceName;
	}
	
	
}
