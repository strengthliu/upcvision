package com.surpass.vision.domain;

import java.io.Serializable;

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
public class User implements Serializable  {
    private Integer id;

    private String name;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	
        this.name = name == null ? null : name.trim();
    }

}
