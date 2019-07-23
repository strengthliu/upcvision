package com.surpass.vision.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.stereotype.Service;

import com.surpass.vision.domain.FileList;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.service.GraphService;

@Service
public class GraphServiceImpl implements GraphService {

	@Override
	public int getCurrentVersion() {
		return GraphManager.getCurrentVersion();
	}

	@Override
	public FileList getCurrentFileList() {
		return GraphManager.getCurrentFileList();
	}

	@Override
	public ArrayList<FileList> getCurrentUpdate() {
		return GraphManager.getCurrentUpdate();
	}

}
