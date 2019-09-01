/*******************************************************************************
 * Copyright (c) 2019 Qiang.liu.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Copyright Law 
 * of the People's Republic of China (2010 Amendment)
 * which accompanies this distribution, and is available at
 * http://www.law-lib.com/law/law_view.asp?id=6938
 *
 * Contributors:
 *     Qiang.liu 
 *******************************************************************************/

package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.server.Point;

/**---------------------------------------
 * @Title: Graph.java
 * @Package:com.surpass.vision.domain
 * ---------------------------------------
 * @Description:
 *     
 * ---------------------------------------
 * @author 刘强
 * 2019年7月27日 下午6:02:32
 * @version: v1.0  
 * ---------------------------------------
 */
public class GraphData extends FileList implements Serializable {
	public GraphData() {
		super();
		this.setType(GlobalConsts.Type_graph_);
	}

	// 更新数据方法
	
}
