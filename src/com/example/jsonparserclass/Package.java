package com.example.jsonparserclass;

import java.util.List;

public class Package {

    private String packageName;
    private List<Diagram> diagrams;

    public Package(){

    }

    public Package(List<Diagram> diagrams) {
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
}
