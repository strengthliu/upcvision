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
public class User implements Serializable  {
    private Long id;

    private String name;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	
        this.name = name == null ? null : name.trim();
    }

}
