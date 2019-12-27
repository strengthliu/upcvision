package com.surpass.vision.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**---------------------------------------
 * @Title: User.java
 * @Package:com.surpass.vision.domain
 * ---------------------------------------
 * @Description:
 *     用户的缩略信息
 * ---------------------------------------
 * @author 刘强
 * 2019年7月27日 下午5:44:12
 * @version:V1.0  
 * ---------------------------------------
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseDomain implements Serializable,Cloneable  {
    private Double id;

    private String name;
    
    private String department;
    
    public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	
        this.name = name == null ? null : name.trim();
    }

    
    @Override
    public User clone() throws CloneNotSupportedException {
    	return (User) super.clone();
    }
}
