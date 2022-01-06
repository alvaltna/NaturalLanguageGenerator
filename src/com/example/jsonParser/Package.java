package com.example.jsonParser;

import java.util.List;

public class Package {

    private String packageName;
    private String note;
    private List<Diagram> diagrams;

    public Package(){

    }

    public Package(String packageName, String note, List<Diagram> diagrams) {

        this.packageName = packageName;
        this.note = note;
        this.diagrams = diagrams;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Diagram> getDiagrams() {
        return diagrams;
    }

    public void setDiagrams(List<Diagram> diagrams) {
        this.diagrams = diagrams;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
