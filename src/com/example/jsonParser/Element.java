package com.example.jsonParser;

import com.fasterxml.jackson.annotation.*;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassType.class, names={"Class","Interface","UseCase"}),
        @JsonSubTypes.Type(value = Artifact.class, name = "Artifact") ,
        @JsonSubTypes.Type(value = Comment.class, names = {"Note", "Constraint"}),
        @JsonSubTypes.Type(value = DiagramNote.class, name = "diagramNote")}
)
public abstract class Element {


    private int elementId;
    private String type;
    private String note;


    public Element() {

    }


    public Element(int elementId, String type, String note) {
        this.elementId = elementId;
        this.type = type;
        this.note = note;

    }



    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
