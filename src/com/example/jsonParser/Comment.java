package com.example.jsonParser;

public class Comment extends Element{


    private String connectedTo;

    public Comment() {

    }

    public Comment(int elementId,String type, String note, String connectedTo) {
        super(elementId,type, note);
        this.connectedTo = connectedTo;
    }



    public String getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(String connectedTo) {
        this.connectedTo = connectedTo;
    }
}
