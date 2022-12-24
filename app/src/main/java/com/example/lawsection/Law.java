package com.example.lawsection;

public class Law {
    String Title;
    String Law_info;

    public Law() {
    }

    public Law(String title, String law_info) {
        Title = title;
        Law_info = law_info;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLaw_info() {
        return Law_info;
    }

    public void setLaw_info(String law_info) {
        Law_info = law_info;
    }
}
