package com.surpass.vision.domain;

import java.io.Serializable;

public class PointGroupData extends BaseDomain implements Serializable {
    private Double id;

    private String type;

    private String name;

    private String owner;

    private String creater;

    private String shared;

    private String points;

    private String otherrule1;

    private String otherrule2;

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
}