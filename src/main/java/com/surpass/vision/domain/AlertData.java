package com.surpass.vision.domain;

import java.io.Serializable;

import com.surpass.vision.appCfg.GlobalConsts;

public class AlertData extends PointGroup implements Serializable {
	public AlertData() {
		super();
		this.setType(GlobalConsts.Type_alertdata_);
	}
	
}
