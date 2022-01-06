package com.example.jsonParser;

public class Method {

    private String name;
    private String note;

    public Method() {

    }

    public Method(String name, String note) {
        this.name = name;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
