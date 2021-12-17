package com.example.jsonparserclass;

import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Diagram {

    private String diagram;
    private List<Element> elements = new ArrayList<>();
    private int smallestClassYCoord;
    private String generalizationSetsString;

    @JsonIgnore
    private List<ClassType> classes = new ArrayList<>();
    private HashMap<Integer, List<Comment>> comments = new HashMap<>();
    private List<DiagramNote> diagramNotes = new ArrayList<>();
    private List<Artifact> artifacts = new ArrayList<>();
    private HashMap<String, GeneralizationSet> generalizationSets = new HashMap<>();
    private HashMap<Integer, ClassType> classTypes = new HashMap<>();


    public Diagram() {

    }

    public Diagram(String name, List<ClassType> classes, HashMap<Integer,
            List<Comment>> comments, List<DiagramNote> diagramNotes,
                   int smallestClassYCoord) {
        this.classes = classes;
        this.comments = comments;
        this.diagramNotes = diagramNotes;
        this.smallestClassYCoord = smallestClassYCoord;
    }

    public List<ClassType> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassType> classes) {
        this.classes = classes;
    }



    public List<DiagramNote> getDiagramNotes() {
        return diagramNotes;
    }

    public void setDiagramNotes(List<DiagramNote> diagramNotes) {
        this.diagramNotes = diagramNotes;
    }

    public int getSmallestClassYCoord() {
        return smallestClassYCoord;
    }

    public void setSmallestClassYCoord(int smallestClassYCoord) {
        this.smallestClassYCoord = smallestClassYCoord;
    }


    public void addClassType(ClassType classType) {
        classes.add(classType);
    }

    public void addComment(Comment comment) {

        for (String connected : comment.getConnectedTo().split(";")) {

            if (connected.split("=").length > 1) {
                Integer key = Integer.valueOf(connected.split("=")[1]);
                if (comments.containsKey(key)) {
                    comments.get(key).add(comment);
                } else {
                    List<Comment> commentList = new ArrayList<>();
                    commentList.add(comment);
                    comments.put(key, commentList);
                }
            }
        }
    }

    public void addElementType(ClassType classType) {

        classTypes.put(classType.getElementId(), classType);
    }

    public void adddiagramNote(DiagramNote diagramNote) {
        diagramNotes.add(diagramNote);
    }

    public HashMap<Integer, List<Comment>> getComments() {
        return comments;
    }

    public void setComments(HashMap<Integer, List<Comment>> comments) {
        this.comments = comments;
    }

    public HashMap<String, GeneralizationSet> getGeneralizationSets() {
        return generalizationSets;
    }

    public void setGeneralizationSets(HashMap<String, GeneralizationSet> generalizationSets) {
        this.generalizationSets = generalizationSets;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public HashMap<Integer, ClassType> getClassTypes() {
        return classTypes;
    }

    public void setClassTypes(HashMap<Integer, ClassType> classTypes) {
        this.classTypes = classTypes;
    }

    public void addArtifact(Artifact artifact) {
        this.artifacts.add(artifact);
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }


    public String getGeneralizationSetsString() {
        return generalizationSetsString;
    }

    public void setGeneralizationSetsString(String generalizationSetsString) {
        this.generalizationSetsString = generalizationSetsString;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public void divideElementsByType() {

        for (Element element : elements) {
            if(element != null) {
                if (element.getClass() == ClassType.class) {
                    this.addClassType((ClassType) element);
                    this.addElementType((ClassType) element);
                } else if (element.getClass() == Comment.class) {
                    this.addComment((Comment) element);
                } else if (element.getClass() == DiagramNote.class) {
                    this.adddiagramNote((DiagramNote) element);
                } else if (element.getClass() == Artifact.class) {
                    this.addArtifact((Artifact) element);
                }
            }
        }
    }

    public void populateGeneralizationSetsMap() {
        if(!(generalizationSetsString == null)) {
            for(String row : generalizationSetsString.split("@")) {
                for(String generalizationSetItem : row.split(",")) {
                    String[] rowItems = generalizationSetItem.split(";");
                    if (rowItems.length == 6) {
                        if(checkIfConnectorOnDiagram(Integer.valueOf(rowItems[5]))) {
                            String id = rowItems[0].split("=")[1];
                            if (generalizationSets.containsKey(id)) {
                                generalizationSets.get(id).addConnector(Integer.valueOf(rowItems[5]));

                            }
                            else {

                                String name = rowItems[1].split("=")[1];
                                boolean isCovering = false;
                                boolean isDisjoint = false;
                                if (rowItems[2].split("=")[1].equals("1")) {
                                    isCovering = true;
                                }
                                if (rowItems[3].split("=")[1].equals("1")) {
                                    isDisjoint = true;
                                }
                                String PowerType = "";
                                if(rowItems[4].split("=").length > 1) {
                                    PowerType = rowItems[4].split("=")[1];
                                }
                                Integer connectorId = Integer.valueOf(rowItems[5]);
                                GeneralizationSet generalizationSet = new GeneralizationSet(id, isCovering, isDisjoint, name, PowerType);
                                generalizationSet.addConnector(connectorId);
                                generalizationSets.put(id, generalizationSet);

                            }
                        }

                    }
                }

            }
        }
    }

    public boolean checkIfConnectorOnDiagram(Integer connectorId) {
        boolean onDiagram = false;
        for(ClassType classType : classes) {
            for(Connector connector : classType.getConnectors()) {
                if (connector.getConnectorId() == connectorId) {
                    onDiagram = true;
                }
            }
        }
        return onDiagram;
    }
}
