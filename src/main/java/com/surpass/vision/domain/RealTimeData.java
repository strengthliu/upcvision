package com.surpass.vision.domain;

import java.io.Serializable;

import com.surpass.vision.appCfg.GlobalConsts;

public class RealTimeData extends PointGroup implements Serializable {

//	public RealTimeData(PointGroupData pgd) {
//		// TODO Auto-generated constructor stub
//	}

	public RealTimeData() {
		super();
		this.setType(GlobalConsts.Type_linealertdata_);
	}

}
