package com.example.jsonParser;

import java.util.HashMap;

public class GeneralizationSet {

    private String id;
    private HashMap<Integer, String> connectors = new HashMap<>();
    private boolean isCovering;
    private boolean isDisjoint;
    private String name;
    private String PowerType;
    private String superClassName;

    public GeneralizationSet(String id,
                             boolean isCovering, boolean isDisjoint, String name, String powerType, String superClassName) {
        this.id = id;
        this.isCovering = isCovering;
        this.isDisjoint = isDisjoint;
        this.name = name;
        this.PowerType = powerType;
        this.superClassName = superClassName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<Integer, String> getConnectors() {
        return connectors;
    }

    public void setConnectors(HashMap<Integer, String> connectors) {
        this.connectors = connectors;
    }

    public boolean isCovering() {
        return isCovering;
    }

    public void setCovering(boolean covering) {
        isCovering = covering;
    }

    public boolean isDisjoint() {
        return isDisjoint;
    }

    public void setDisjoint(boolean disjoint) {
        isDisjoint = disjoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPowerType() {
        return PowerType;
    }

    public void setPowerType(String powerType) {
        PowerType = powerType;
    }

    public void addSubtype(Integer id, String subtype){
        connectors.put(id, subtype);
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }
}
