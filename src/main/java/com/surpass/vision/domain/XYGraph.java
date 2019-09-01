package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.List;

import com.surpass.vision.appCfg.GlobalConsts;
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
		this.setPoints(pgd.getPoints());
		this.setShared(pgd.getShared());
		this.setType(pgd.getType());
	}

	public XYGraph() {
		super();
		this.setType(GlobalConsts.Type_xygraph_);
	}

}
