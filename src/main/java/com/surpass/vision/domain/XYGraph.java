package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.List;

import com.surpass.vision.server.Point;

public class XYGraph extends PointGroup implements Serializable {

	public XYGraph(PointGroupData pgd) {
		super();
		this.setCreater(pgd.getCreater());
		this.setId(pgd.getId());
		this.setName(pgd.getName());
		this.setOtherrule1(pgd.getOtherrule1());
		this.setOtherrule2(pgd.getOtherrule2());
		this.setOwner(pgd.getOwner());
		// TODO:
		this.setPoints(pgd.getPoints());
		this.setShared(pgd.getShared());
		this.setType(pgd.getType());
	}

//	List<Point> points;
//	User owner;
//	User creater;
//	List<User> shared;
	
	public XYGraph() {
		// TODO Auto-generated constructor stub
	}

}
