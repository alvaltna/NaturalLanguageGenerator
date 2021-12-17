package com.example.jsonparserclass;

public class DiagramNote extends Element {


    private int y_coord;

    public DiagramNote() {
    }

    public DiagramNote(int elementId, String type, String note, int y_coord) {
        super(elementId, type, note);
        this.y_coord = y_coord;
    }


    public int getY_coord() {
        return y_coord;
    }

    public void setY_coord(int y_coord) {
        this.y_coord = y_coord;
    }
}
