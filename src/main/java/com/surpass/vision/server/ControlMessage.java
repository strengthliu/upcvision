package com.surpass.vision.server;

import java.io.Serializable;

public class ControlMessage implements Serializable {

	private boolean reloadUserSpace;
	
	public boolean isReloadUserSpace() {
		return reloadUserSpace;
	}

	public void setReloadUserSpace(boolean reloadUserSpace) {
		this.reloadUserSpace = reloadUserSpace;
	}

	public boolean reloadUserSpace() {
		// TODO Auto-generated method stub
		return false;
	}

}
