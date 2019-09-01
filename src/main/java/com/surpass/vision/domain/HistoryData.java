package com.surpass.vision.domain;

import java.io.Serializable;

import com.surpass.vision.appCfg.GlobalConsts;

public class HistoryData extends PointGroup implements Serializable {
	public HistoryData() {
		super();
		this.setType(GlobalConsts.Type_historydata_);
	}
}
