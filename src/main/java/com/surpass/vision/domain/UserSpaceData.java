package com.surpass.vision.domain;

public class UserSpaceData {
    private Integer uid;

    private String right;

    private String graphs;

    private String xygraph;

    private String realtimedata;

    private String alertdata;

    private String historydata;

    private String linealertdata;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right == null ? null : right.trim();
    }

    public String getGraphs() {
        return graphs;
    }

    public void setGraphs(String graphs) {
        this.graphs = graphs == null ? null : graphs.trim();
    }

    public String getXygraph() {
        return xygraph;
    }

    public void setXygraph(String xygraph) {
        this.xygraph = xygraph == null ? null : xygraph.trim();
    }

    public String getRealtimedata() {
        return realtimedata;
    }

    public void setRealtimedata(String realtimedata) {
        this.realtimedata = realtimedata == null ? null : realtimedata.trim();
    }

    public String getAlertdata() {
        return alertdata;
    }

    public void setAlertdata(String alertdata) {
        this.alertdata = alertdata == null ? null : alertdata.trim();
    }

    public String getHistorydata() {
        return historydata;
    }

    public void setHistorydata(String historydata) {
        this.historydata = historydata == null ? null : historydata.trim();
    }

    public String getLineAlertdata() {
        return linealertdata;
    }

    public void setLineAlertdata(String linealertdata) {
        this.linealertdata = linealertdata == null ? null : linealertdata.trim();
    }
}