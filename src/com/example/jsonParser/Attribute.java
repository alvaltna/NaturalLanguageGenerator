package com.example.jsonParser;

public class Attribute {

    private String name;
    private String type;
    private String initialValue;
    private String lowerBound;
    private String upperBound;
    private String note;


    public Attribute() {

    }

    public Attribute(String name, String type, String initialValue, String lowerBound, String upperBound, String note) {
        this.name = name;
        this.type = type;
        this.initialValue = initialValue;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(String initialvalue) {
        this.initialValue = initialvalue;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
