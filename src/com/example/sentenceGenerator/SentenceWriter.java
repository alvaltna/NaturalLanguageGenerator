package com.example.sentenceGenerator;

import com.example.jsonParser.*;
import com.example.jsonParser.Package;

import java.util.List;
import java.util.Map;

public class SentenceWriter {

    private Phrases phrases;
    private ParseJson parser;
    private Diagram diagram;
    private List<Package> packages;
    private SentenceBuilder sentenceBuilder;
    private String generatedNaturalLanguage="";

    public SentenceWriter(Phrases phrases, ParseJson parser) {

        this.phrases = phrases;
        this.parser = parser;
    }

    public void writePackageSentences() {

        for(Package eaPackage: packages) {

            generatedNaturalLanguage += "----------------------------------------------------------------------------------------------------------" +
                    String.format(phrases.getPackageName(), eaPackage.getPackageName());
            if (!eaPackage.getNote().equals("")) {
                generatedNaturalLanguage += "<br/>" + String.format(phrases.getDescribeNotes(), eaPackage.getNote()) + ".";
            }
            generatedNaturalLanguage += "<br/><br/>";
            for(Diagram eaDiagram : eaPackage.getDiagrams()) {
                this.diagram = eaDiagram;
                writeDiagramSentences();
            }
        }

    }

    public void writeDiagramSentences() {

         getDiagramInfo();
         buildSentences();
         diagramDescribeSentence();
         diagramIntroductoryNotes();
         diagramArtifacts();
         diagramDescribeClasses();
         diagramDescribeConnectorsGroupedByElement();
         diagramDescribeAdditionalNotes();
    }

    public void getInputInfo(){
        generatedNaturalLanguage = "";
        this.diagram = null;
        this.packages = null;
        generatedNaturalLanguage += "<html>";
        parser.getElementsFromJson();
        if(parser.getDiagram() != null) {
            this.diagram = parser.getDiagram();
            writeDiagramSentences();
        } else if(parser.getPackages() != null) {
            this.packages = parser.getPackages();
            writePackageSentences();
        }
        generatedNaturalLanguage += "<html>";

    }

    public void getDiagramInfo() {

        diagram.divideElementsByType();
        diagram.populateGeneralizationSetsMap();
    }



    public String classTypeTranslator(String type) {

        if (type.equals("Class")) {
            return phrases.getElementClass();
        }
        else if (type.equals("Interface")) {
            return phrases.getElementInterface();
        }
        else if(type.equals("UseCase")) {
            return phrases.getElementUseCase();
        }
        return phrases.getElementElement();
    }



    public void buildSentences() {

        sentenceBuilder = new SentenceBuilder(phrases, diagram);
        sentenceBuilder.diagramDescribesSentence();
        sentenceBuilder.diagramNotes();
        sentenceBuilder.describeArtifactsInfo();
        sentenceBuilder.describeElement();
        sentenceBuilder.addGeneralizationSetsInfo();
    }

    public void diagramDescribeSentence() {

        generatedNaturalLanguage += phrases.getDiagramLabel() + " " + diagram.getDiagram();
        generatedNaturalLanguage += "<br/><br/>";
        if (!diagram.getNote().equals("")) {
            generatedNaturalLanguage += String.format(phrases.getDescribeNotes(), diagram.getNote()) + ".<br/>" ;
        }


        generatedNaturalLanguage += sentenceBuilder.getDiagramDescribeSentence().trim() + ".";
        generatedNaturalLanguage += "<br/><br/>";

    }

    public void diagramIntroductoryNotes() {
        if (sentenceBuilder.getDiagramIntroductoryNotes().size() > 0) {

            generatedNaturalLanguage += phrases.getIntroductoryCommentsLabel() + "<br/><br/>";
            for (String note : sentenceBuilder.getDiagramIntroductoryNotes()) {
                generatedNaturalLanguage += note + ".";
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";
        }

    }

    public void diagramArtifacts() {
        if (sentenceBuilder.getDescribeArtifacts().size() > 0) {

            generatedNaturalLanguage += phrases.getArtifactsLabel() + "<br/><br/>";
            for(Map.Entry<Integer, List<String>> entry : sentenceBuilder.getDescribeArtifacts().entrySet()) {
                for(String sentence : entry.getValue()) {
                    generatedNaturalLanguage += sentence + ".";
                    generatedNaturalLanguage += "<br/>";
                }
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";

        }

    }

    public void diagramDescribeClasses() {
        if (sentenceBuilder.sortElementsByName().size() > 0) {

            generatedNaturalLanguage += phrases.getElementDescriptionLabel() + "<br/><br/>";
            for (Map.Entry<ClassType, List<String>> entry : sentenceBuilder.sortElementsByName().entrySet()) {
                generatedNaturalLanguage += classTypeTranslator(entry.getKey().getType()) + " " + entry.getKey().getName();
                generatedNaturalLanguage += "<br/>";
                for (String sentence : entry.getValue()) {
                    generatedNaturalLanguage += sentence.trim() + ".";
                    generatedNaturalLanguage += "<br/>";

                }

                if(sentenceBuilder.getClassMethods().containsKey(entry.getKey().getElementId())) {
                    for (String method : sentenceBuilder.getClassMethods().get(entry.getKey().getElementId())) {
                        generatedNaturalLanguage += method + ".";
                        generatedNaturalLanguage += "<br/>";
                    }
                    generatedNaturalLanguage += "<br/>";
                }

                if (sentenceBuilder.getDescribeClassNotes().containsKey(entry.getKey())) {

                    for (String note : sentenceBuilder.getDescribeClassNotes().get(entry.getKey())) {

                        generatedNaturalLanguage += note + ".";
                        generatedNaturalLanguage += "<br/>";
                    }

                }
                if(!entry.getKey().getNote().equals("")) {
                    generatedNaturalLanguage+= String.format(phrases.getDescribeElementNotes(),
                            entry.getKey().getNote()) + ".<br/>";
                }

                if (sentenceBuilder.getDescribeClassConstraints().containsKey(entry.getKey())) {
                    for (String constraint : sentenceBuilder.getDescribeClassConstraints().get(entry.getKey())) {

                        generatedNaturalLanguage += constraint + ".";
                        generatedNaturalLanguage += "<br/>";
                    }
                }
                generatedNaturalLanguage += "<br/>";
            }

        }

    }

    public void diagramDescribeConnectorsGroupedByElement() {

        if(sentenceBuilder.sortGroupedConnectorsByElementsName().size() > 0) {

            generatedNaturalLanguage += phrases.getConnectorsLabel() + "<br/><br/>";
            for(Map.Entry<String, List<String>> entry : sentenceBuilder.getDescribeGeneralizationSets().entrySet()) {
                for(String sentence : entry.getValue()) {
                    generatedNaturalLanguage += sentence + ".";
                    generatedNaturalLanguage += "<br/>";
                }
                generatedNaturalLanguage += "<br/>";
            }

            for(Map.Entry<ClassType, List<String>>  entry : sentenceBuilder.sortGroupedConnectorsByElementsName().entrySet()) {
                generatedNaturalLanguage += classTypeTranslator(entry.getKey().getType()) + " " + entry.getKey().getName();
                generatedNaturalLanguage += "<br/>";

                for(String sentence : entry.getValue()) {
                    generatedNaturalLanguage += sentence + ".";
                    generatedNaturalLanguage += "<br/>";
                }
                generatedNaturalLanguage += "<br/>";

            }
        }

    }

    public void diagramDescribeAdditionalNotes() {

        if (sentenceBuilder.getDiagramAdditionalNotes().size() > 0) {
            generatedNaturalLanguage += phrases.getAdditionalCommentsLabel() + "<br/><br/>";
            for (String note : sentenceBuilder.getDiagramAdditionalNotes()) {

                generatedNaturalLanguage += note + ".";
                generatedNaturalLanguage += "<br/>";

            }
            generatedNaturalLanguage += "<br/>";
        }

    }


    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public Phrases getPhrases() {
        return phrases;
    }

    public void setPhrases(Phrases phrases) {
        this.phrases = phrases;
    }

    public ParseJson getParser() {
        return parser;
    }

    public void setParser(ParseJson parser) {
        this.parser = parser;
    }


    public SentenceBuilder getSentenceBuilder() {
        return sentenceBuilder;
    }

    public void setSentenceBuilder(SentenceBuilder sentenceBuilder) {
        this.sentenceBuilder = sentenceBuilder;
    }

    public String getGeneratedNaturalLanguage() {
        return generatedNaturalLanguage;
    }

    public void setGeneratedNaturalLanguage(String generatedNaturalLanguage) {
        this.generatedNaturalLanguage = generatedNaturalLanguage;
    }
}


