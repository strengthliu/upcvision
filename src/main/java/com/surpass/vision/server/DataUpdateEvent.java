package com.surpass.vision.server;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class DataUpdateEvent extends ApplicationEvent {
    /**
	 * ---------------------------------------
	 * @author 刘强 2019年8月26日 下午4:13:51 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String message;

    public DataUpdateEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
