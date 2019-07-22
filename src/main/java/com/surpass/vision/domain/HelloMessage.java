package com.surpass.vision.domain;

public class HelloMessage {

    private String name;

    private String content;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public HelloMessage(String name,String content){
        this.name=name;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
