package com.example.jsonparserclass;

import java.util.Comparator;
import java.util.List;

public class ClassType extends Element implements Comparable<ClassType> {


        private String name;
        private List<Connector> connectors;
        private List<Attribute> attributes;
        private List<Method> methods;
        private List<Comment> comments;

        public ClassType() {

        }

        public ClassType(int elementId, String type, String name, String note,
                       List<Connector> connectors, List<Attribute> attributes, List<Method> methods, List<Comment> comments) {
            super(elementId,type,note);
            this.name = name;
            this.attributes = attributes;
            this.connectors = connectors;
            this.methods = methods;
            this.comments = comments;
        }

        @Override
        public int compareTo(ClassType c1) {
            return this.name.compareTo(c1.getName());
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

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
}


