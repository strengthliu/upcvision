package com.surpass.vision.graph;

import com.surpass.vision.domain.FileList;

public class GraphManager {

	FileList repo;
	private GraphManager() {}
	private static GraphManager instance;
	{
		if(instance == null) instance = new GraphManager();
		if(repo == null) repo = new FileList();
	}
	
	public static synchronized GraphManager getInstance() {
		return instance;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
