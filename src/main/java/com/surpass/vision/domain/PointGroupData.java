package com.surpass.vision.domain;

import java.io.Serializable;

public class PointGroupData  extends BaseDomain implements Serializable, Cloneable {
    private Double id;

    private String type;

    private String name;

    private String owner;

    private String creater;

    private String shared;

    private String shareddepart;

    private String points;

    private String otherrule1;

    private String otherrule2;

    private String otherrule3;

    private String otherrule4;

    private String otherrule5;

    private String otherrule6;

    private String otherrule7;

    private String otherrule8;

    private String otherrule9;

    private String otherrule10;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared == null ? null : shared.trim();
    }

    public String getShareddepart() {
        return shareddepart;
    }

    public void setShareddepart(String shareddepart) {
        this.shareddepart = shareddepart == null ? null : shareddepart.trim();
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points == null ? null : points.trim();
    }

    public String getOtherrule1() {
        return otherrule1;
    }

    public void setOtherrule1(String otherrule1) {
        this.otherrule1 = otherrule1 == null ? null : otherrule1.trim();
    }

    public String getOtherrule2() {
        return otherrule2;
    }

    public void setOtherrule2(String otherrule2) {
        this.otherrule2 = otherrule2 == null ? null : otherrule2.trim();
    }

    public String getOtherrule3() {
        return otherrule3;
    }

    public void setOtherrule3(String otherrule3) {
        this.otherrule3 = otherrule3 == null ? null : otherrule3.trim();
    }

    public String getOtherrule4() {
        return otherrule4;
    }

    public void setOtherrule4(String otherrule4) {
        this.otherrule4 = otherrule4 == null ? null : otherrule4.trim();
    }

    public String getOtherrule5() {
        return otherrule5;
    }

    public void setOtherrule5(String otherrule5) {
        this.otherrule5 = otherrule5 == null ? null : otherrule5.trim();
    }

    public String getOtherrule6() {
        return otherrule6;
    }

    public void setOtherrule6(String otherrule6) {
        this.otherrule6 = otherrule6 == null ? null : otherrule6.trim();
    }

    public String getOtherrule7() {
        return otherrule7;
    }

    public void setOtherrule7(String otherrule7) {
        this.otherrule7 = otherrule7 == null ? null : otherrule7.trim();
    }

    public String getOtherrule8() {
        return otherrule8;
    }

    public void setOtherrule8(String otherrule8) {
        this.otherrule8 = otherrule8 == null ? null : otherrule8.trim();
    }

    public String getOtherrule9() {
        return otherrule9;
    }

    public void setOtherrule9(String otherrule9) {
        this.otherrule9 = otherrule9 == null ? null : otherrule9.trim();
    }

    public String getOtherrule10() {
        return otherrule10;
    }

    public void setOtherrule10(String otherrule10) {
        this.otherrule10 = otherrule10 == null ? null : otherrule10.trim();
    }
    
    @Override
    public PointGroupData clone() throws CloneNotSupportedException {
    	PointGroup ret = null;
			ret = (PointGroup)super.clone();
    	return ret;
    }

}