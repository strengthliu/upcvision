package com.surpass.vision.server;

public class ServerPoint {
	String serverName;
	Long[] ids;
	String[] tagNames;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String[] getTagNames() {
		return tagNames;
	}

	public void setTagNames(String[] tagNames) {
		this.tagNames = tagNames;
	}

	public Point[] getPoints() {
		if (ids == null || ids.length == 0)
			return null;

		Point[] ps = new Point[ids.length];
		for (int i = 0; i < ids.length; i++) {
			Point p = new Point();
			p.setId(ids[i]);
			p.setTagName(tagNames[i]);
			p.setServerName(serverName);
			ps[i] = p;
		}
		return ps;
	}
}
