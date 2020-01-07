package com.surpass.vision.domain;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;

public class RealTimeData extends PointGroup implements Serializable {

//	public RealTimeData(PointGroupData pgd) {
//		// TODO Auto-generated constructor stub
//	}
//	@Value("${upc.realTimeDataDefaultImg}")
//	private String realTimeDataDefaultImg;

	public RealTimeData() {
		super();
		this.setType(GlobalConsts.Type_linealertdata_);
		this.setImg("/images/samples/realtime.jpg");
	}

}
