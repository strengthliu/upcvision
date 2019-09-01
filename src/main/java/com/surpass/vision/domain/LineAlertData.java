package com.surpass.vision.domain;

import java.io.Serializable;

import com.surpass.vision.appCfg.GlobalConsts;

public class LineAlertData extends PointGroup implements Serializable {
	public LineAlertData() {
		super();
		this.setType(GlobalConsts.Type_linealertdata_);
	}
}
