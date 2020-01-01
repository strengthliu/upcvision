package com.surpass.vision.domain;

public class DepartmentInfo extends BaseDomain implements Cloneable {
    private Integer id;

    private String departname;

    private String departdesc;

    private String graphs;

    private String xygraph;

    private String realtimedata;

    private String alertdata;

    private String historydata;

    private String linealertdata;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname == null ? null : departname.trim();
    }

    public String getDepartdesc() {
        return departdesc;
    }

    public void setDepartdesc(String departdesc) {
        this.departdesc = departdesc == null ? null : departdesc.trim();
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

    public String getLinealertdata() {
        return linealertdata;
    }

    public void setLinealertdata(String linealertdata) {
        this.linealertdata = linealertdata == null ? null : linealertdata.trim();
    }

    @Override
    public DepartmentInfo clone() throws CloneNotSupportedException {
    	return (DepartmentInfo) super.clone();
    }

}