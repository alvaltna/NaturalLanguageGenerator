package com.example.jsonParser;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artifact extends Element {

    private String name;
    public Artifact() {
    }

    public Artifact(int elementId, String type, String note, String name) {
        super(elementId, type, note);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
