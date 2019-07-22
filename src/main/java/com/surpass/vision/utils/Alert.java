package com.surpass.vision.utils;

public class Alert {
	private Integer id;// ���id
	private String position;// λ��
	private String explain;// ��λ˵��
	private double value;// ֵ
	private String status;// ����״̬

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatus(long status) {
		if(status==2){
			this.status = "�;���";
		}else if(status==3){
			this.status = "�߾���";
		}else{
			this.status = "�޾���";
		}
	}

}
