package com.example.jsonparserclass;


import java.util.List;

public class Main {

    public static void main(String[] args) {

        ParseJsonClass json = new ParseJsonClass();
       json.getElementsFromJson();
        List<Element> elements = json.getElements();
        for(Element element : elements) {

        }


    }
}
