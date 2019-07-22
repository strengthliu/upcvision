package com.surpass.vision.utils;

public class NameAndID {
	private String name ;
	private String id ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.substring(0, 10);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setId(Long id) {
		this.id = id+"";
	}
	
	
}
