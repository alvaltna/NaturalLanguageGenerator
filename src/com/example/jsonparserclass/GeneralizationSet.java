package com.example.jsonparserclass;

import java.util.HashMap;

public class GeneralizationSet {

    private String id;
    private HashMap<Integer, Connector> connectors = new HashMap<>();
    private boolean isCovering;
    private boolean isDisjoint;
    private String name;
    private String PowerType;

    public GeneralizationSet(String id,
                             boolean isCovering, boolean isDisjoint, String name, String powerType) {
        this.id = id;
        this.isCovering = isCovering;
        this.isDisjoint = isDisjoint;
        this.name = name;
        PowerType = powerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<Integer, Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(HashMap<Integer, Connector> connectors) {
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

    public void addConnector(Integer id){
        connectors.put(id, null);
    }
}
