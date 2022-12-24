package com.example.lawsection;

public class model {
    public String id,law_info,title;


    public model() {
    }

    public model(String id, String law_info, String title) {
        this.id = id;
        this.law_info = law_info;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getLaw_info() {
        return law_info;
    }

    public String getTitle() {
        return title;
    }
}
