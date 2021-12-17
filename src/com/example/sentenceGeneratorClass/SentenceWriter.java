package com.example.sentenceGeneratorClass;

import com.example.jsonparserclass.*;
import com.example.jsonparserclass.Package;

import java.io.BufferedWriter;
import java.util.List;

public class SentenceWriter {

    private Phrases phrases;
    private ParseJsonClass parser;
    private Diagram diagram;
    private List<Package> packages;
    private SentenceBuilder sentenceBuilder;
    private String generatedNaturalLanguage="";

    public SentenceWriter(Phrases phrases, ParseJsonClass parser) {

        this.phrases = phrases;
        this.parser = parser;
    }

    public void writePackageSentences() {

        for(Package eaPackage: packages) {
            generatedNaturalLanguage += String.format(phrases.getPackageName(), eaPackage.getPackageName())
                    + "----------------------------------------------------------------------------------------------------------"
                    + "<br/><br/>";
            for(Diagram eaDiagram : eaPackage.getDiagrams()) {
                this.diagram = eaDiagram;
                writeDiagramSentences();
            }
        }

    }

    public void writeDiagramSentences() {

         getDiagramInfo();
         buildSentences();
         sentenceBuilder.addGeneralizationSetsInfo();
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

        /**diagram.getClasses().clear();
        diagram.getComments().clear();
        diagram.getDiagramNotes().clear();
        diagram.getArtifacts().clear();
        diagram.getGeneralizationSets().clear();
        diagram.setSmallestClassYCoord(0);
        diagram.setDiagram(null);**/
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
        sentenceBuilder.DiagramDescribesSentence();
        sentenceBuilder.DiagramNotes();
        sentenceBuilder.describeArtifactsInfo();
        sentenceBuilder.describeClass();
    }

    public void diagramDescribeSentence() {

        generatedNaturalLanguage += phrases.getDiagramLabel() + " " + diagram.getDiagram() + "<br/><br/>";
        generatedNaturalLanguage += sentenceBuilder.getDiagramDescribeSentence() + ".";
        generatedNaturalLanguage += "<br/><br/>";


    }

    public void diagramIntroductoryNotes() {
        generatedNaturalLanguage += phrases.getIntroductoryCommentsLabel() + "<br/><br/>";
        for (String note : sentenceBuilder.getDiagramIntroductoryNotes()) {
            generatedNaturalLanguage += note + ".";
            generatedNaturalLanguage += "<br/>";
        }
        generatedNaturalLanguage += "<br/>";
    }

    public void diagramArtifacts() {

        generatedNaturalLanguage += phrases.getArtifactsLabel() + "<br/><br/>";
        for(var entry : sentenceBuilder.getDescribeArtifacts().entrySet()) {
            generatedNaturalLanguage += entry.getValue() + ".";
            generatedNaturalLanguage += "<br/>";
        }
        generatedNaturalLanguage += "<br/>";
    }

    public void diagramDescribeClasses() {
        generatedNaturalLanguage += phrases.getElementDescriptionLabel() + "<br/><br/>";

        for (var entry : sentenceBuilder.sortElementsByName().entrySet()) {
            generatedNaturalLanguage += classTypeTranslator(entry.getKey().getType()) + " " + entry.getKey().getName();
            generatedNaturalLanguage += "<br/>";
            for (String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence.trim() + ".";
                generatedNaturalLanguage += "<br/>";

            }
            generatedNaturalLanguage += sentenceBuilder.getClassMethods().get(entry.getKey().getElementId());
            generatedNaturalLanguage += "<br/>";

            if (sentenceBuilder.getDescribeClassNotes().containsKey(entry.getKey())) {

                for (String note : sentenceBuilder.getDescribeClassNotes().get(entry.getKey())) {

                    generatedNaturalLanguage += note + ".";
                    generatedNaturalLanguage += "<br/>";
                }
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

    public void diagramDescribeConnectors() {
        generatedNaturalLanguage += phrases.getConnectorsLabel() + "<br/><br/>";

        for (var entry : sentenceBuilder.getDescribeConnectors().entrySet()) {
            for (String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence + ".";
                generatedNaturalLanguage += "<br/>";
            }
            if (diagram.getComments().containsKey(entry.getKey())) {
                for (Comment comment : diagram.getComments().get(entry.getKey())) {
                    generatedNaturalLanguage += comment.getNote() + ".";
                    generatedNaturalLanguage += "<br/>";
                }
            }
            generatedNaturalLanguage += "<br/>";;
        }

        for(var entry : sentenceBuilder.getDescribeGeneralizationSets().entrySet()) {
            for(String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence + ".";
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";
        }
        for (var entry: sentenceBuilder.getDescribeConnectorDescriptiveSentences().entrySet()) {
            for(String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence + ".";
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";
        }

        for (var entry : sentenceBuilder.getDescribeConnectorsBothMultiplicities().entrySet()) {
            for (String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence + ".";
                generatedNaturalLanguage += "<br/>";
            }
            if (diagram.getComments().containsKey(entry.getKey().getConnectorId())) {
                for (Comment comment : diagram.getComments().get(entry.getKey().getConnectorId())) {
                    generatedNaturalLanguage += comment.getNote() + ".";
                    generatedNaturalLanguage += "<br/>";
                }
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";
        }

    }
    public void diagramDescribeConnectorsGroupedByElement() {

        generatedNaturalLanguage += phrases.getConnectorsLabel() + "<br/><br/>";
        for(var entry : sentenceBuilder.getDescribeGeneralizationSets().entrySet()) {
            for(String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence + ".";
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";
        }

        for(var  entry : sentenceBuilder.sortGroupedConnectorsByElementsName().entrySet()) {
            generatedNaturalLanguage += classTypeTranslator(entry.getKey().getType()) + " " + entry.getKey().getName();
            generatedNaturalLanguage += "<br/>";

            for(String sentence : entry.getValue()) {
                generatedNaturalLanguage += sentence + ".";
                generatedNaturalLanguage += "<br/>";
            }
            generatedNaturalLanguage += "<br/>";

        }
    }



    public void diagramDescribeAdditionalNotes() {

        generatedNaturalLanguage += phrases.getAdditionalCommentsLabel() + "<br/><br/>";
        for (String note : sentenceBuilder.getDiagramAdditionalNotes()) {


            generatedNaturalLanguage += note + ".";
            generatedNaturalLanguage += "<br/>";


        }
        generatedNaturalLanguage += "<br/>";
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

    public ParseJsonClass getParser() {
        return parser;
    }

    public void setParser(ParseJsonClass parser) {
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


