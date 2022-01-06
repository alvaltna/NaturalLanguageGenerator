package com.example.jsonParser;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.charset.Charset;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ParseJson {

    private Path inputJsonPath;
    private String inputJsonStringData;
    private String inputJsonString;


    private Diagram diagram;
    private List<Package> packages;


    public ParseJson() {

        getInputJsonStringPathFromFile();

    }

    private void getInputJsonStringPathFromFile() {
        try {
            if (Files.readString(Path.of("InputJsonFilePath.json"), StandardCharsets.UTF_8).length() == 0) {
                this.inputJsonString = System.getProperty("java.io.tmpdir") + "\\file.json";
            }
            else {
                this.inputJsonString = Files.readString(Path.of("InputJsonFilePath.json"), StandardCharsets.UTF_8);
            }


        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAnsiJsonToUtf8Json() {
        try {

            getInputJsonStringPathFromFile();
            inputJsonPath = Path.of(inputJsonString);

            inputJsonStringData = Files.readString(inputJsonPath, Charset.forName("Windows-1252"));
            if (inputJsonStringData.contains("RootPackage")) {
                inputJsonStringData = inputJsonStringData.substring(0, inputJsonStringData.length()-9) + "]}" ;
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getElementsFromJson() {
        writeAnsiJsonToUtf8Json();
        this.diagram = null;
        this.packages = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);

        try {

            System.out.println("-- deserializing --");
            final ObjectNode node = new ObjectMapper().readValue(inputJsonStringData, ObjectNode.class);


            if(!node.has("RootPackage")) {

                this.diagram = objectMapper.readValue(inputJsonStringData, Diagram.class);

            }
            else {
                JsonNode jsonnode = node.get("Packages");
                this.packages = objectMapper.readValue(String.valueOf(jsonnode), new TypeReference<List<Package>>() {
                });

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }


    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public String getInputJsonString() {
        return inputJsonString;
    }

    public void setInputJsonString(String inputJsonString) {
        this.inputJsonString = inputJsonString;
    }
}
