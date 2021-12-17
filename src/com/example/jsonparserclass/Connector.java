package com.example.jsonparserclass;

public class Connector {

    private int sourceId;
    private int endId;
    private String sourceName;
    private String endName;
    private String type;
    private String stereotype;
    private String startMultiplicity;
    private String endMultiplicity;
    private int connectorId;
    private String connectorName;
    private String note;
    private String constraint;
    private String targetRole;
    private String sourceRole;
    private String sourceType;
    private String endType;
    private int aggregationType;

    public Connector() {
    }

    public Connector(int sourceId, int endId, String sourceName, String endName, String type, String stereotype,
                     String startMultiplicity, String endMultiplicity, int connectorId, String connectorName,
                     String note, String constraint, String sourceRole, String targetRole, int aggregationType,
    String sourceType, String endType) {
        this.sourceId = sourceId;
        this.endId = endId;
        this.sourceName = sourceName;
        this.endName = endName;
        this.type = type;
        this.stereotype = stereotype;
        this.startMultiplicity = startMultiplicity;
        this.endMultiplicity = endMultiplicity;
        this.connectorId = connectorId;
        this.connectorName = connectorName;
        this.note = note;
        this.constraint = constraint;
        this.sourceRole = sourceRole;
        this.targetRole = targetRole;
        this.aggregationType = aggregationType;
        this.sourceType = sourceType;
        this.endType = endType;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getEndId() {
        return endId;
    }

    public void setEndId(int endId) {
        this.endId = endId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStereotype() {
        return stereotype;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public String getStartMultiplicity() {
        return startMultiplicity;
    }

    public void setStartMultiplicity(String startMultiplicity) {
        this.startMultiplicity = startMultiplicity;
    }

    public String getEndMultiplicity() {
        return endMultiplicity;
    }

    public void setEndMultiplicity(String endMultiplicity) {
        this.endMultiplicity = endMultiplicity;
    }

    public int getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public String getSourceRole() {
        return sourceRole;
    }

    public void setSourceRole(String sourceRole) {
        this.sourceRole = sourceRole;
    }

    public int getAggregationType() {
        return aggregationType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }

    public void setAggregationType(int aggregationType) {
        this.aggregationType = aggregationType;
    }
}
