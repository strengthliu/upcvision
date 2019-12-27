package com.surpass.vision.domain;

import java.io.Serializable;

public class DepartmentInfo extends BaseDomain implements Serializable,Cloneable  {
    private Integer id;

    private String departname;

    private String departdesc;

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
    
    @Override
    public DepartmentInfo clone() throws CloneNotSupportedException {
    	return (DepartmentInfo) super.clone();
    }

}