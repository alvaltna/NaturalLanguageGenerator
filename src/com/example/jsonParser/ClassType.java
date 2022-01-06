package com.example.jsonParser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.List;

public class ClassType extends Element implements Comparable<ClassType> {


    private String name;
    private List<Connector> connectors;
    private List<Attribute> attributes;
    private List<Method> methods;



        public ClassType() {

        }

        public ClassType(int elementId, String type, String name, String note,
                       List<Connector> connectors, List<Attribute> attributes, List<Method> methods, List<Comment> comments) {
            super(elementId,type,note);
            this.name = name;
            this.attributes = attributes;
            this.connectors = connectors;
            this.methods = methods;

        }

        @Override
        public int compareTo(ClassType c1) {
            return Comparator.comparing(ClassType::getName)
                    .thenComparing(ClassType::toString)
                    .compare(this, c1);
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



        public List<Attribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<Attribute> attributes) {
            this.attributes = attributes;
        }

        public List<Connector> getConnectors() {
            return connectors;
        }

        public void setConnectors(List<Connector> connectors) {
            this.connectors = connectors;
        }

        public List<Method> getMethods() {
            return methods;
        }

        public void setMethods(List<Method> methods) {
            this.methods = methods;
        }


}


