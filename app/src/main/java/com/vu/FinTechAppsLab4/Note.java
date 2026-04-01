package com.vu.FinTechAppsLab4;

import java.io.Serializable;

public class Note implements Serializable {
    private String name;
    private String content;

    public Note() {
    }

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return name;
    }
}
