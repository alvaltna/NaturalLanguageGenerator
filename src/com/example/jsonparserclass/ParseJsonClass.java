package com.example.jsonparserclass;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ParseJsonClass {

    private List<Element> elements;
    private String diagramName;
    private int smallestClassYCoord;
    private String generalizationSetsString;
    private File fileInput;
    private File workFile;
    private Path inputJsonPath;
    private Path workJsonPath;
    private String inputJsonStringData;
    private String inputJsonString;
    private String workJsonString = "utf8encoded.json";

    private Diagram diagram;
    private List<Package> packages;


    public ParseJsonClass() {

        this.workJsonPath=Path.of(workJsonString);
        this.workFile = new File(workJsonPath.toString());
        getInputJsonStringPathFromFile();

    }

    private void getInputJsonStringPathFromFile() {
        try {
            this.inputJsonString = Files.readString(Path.of("InputJsonFilePath.txt"), StandardCharsets.UTF_8);
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
            BufferedWriter out = new BufferedWriter(new FileWriter("utf8encoded.json"));
            out.write(inputJsonStringData);
            out.close();

            /**utf8JsonPath = Paths.get("utf8encoded.json");
            ByteBuffer bb = ByteBuffer.wrap(Files.readAllBytes(inputJsonPath));
            CharBuffer cb = Charset.forName("windows-1252").decode(bb);
            bb = Charset.forName("UTF-8").encode(cb);
            Files.write(utf8JsonPath, bb.array());
            workFile=new File(utf8JsonPath.toString());**/
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getElementsFromJson() {

        this.diagram = null;
        this.packages = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
        try {

            System.out.println("-- deserializing --");
            writeAnsiJsonToUtf8Json();
            final ObjectNode node = new ObjectMapper().readValue(workFile, ObjectNode.class);

            /**JsonNode jsonnode = node.get("Elements");
            this.elements = objectMapper.readValue(String.valueOf(jsonnode), new TypeReference<List<Element>>() {
            });
            jsonnode= node.get("Diagram");
            this.diagramName = objectMapper.readValue(String.valueOf(jsonnode), String.class);
            jsonnode = node.get("smallestClassYCoord");
            this.smallestClassYCoord = objectMapper.readValue(String.valueOf(jsonnode), Integer.class);
            jsonnode = node.get("generalizationSetsString");
            this.generalizationSetsString = objectMapper.readValue(String.valueOf(jsonnode), String.class);**/

            if(!node.has("RootPackage")) {

                this.diagram = objectMapper.readValue(Files.readString(workJsonPath), Diagram.class);
                //System.out.println(this.diagram == null);
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

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getDiagramName() {
        return diagramName;
    }

    public void setDiagramName(String diagramName) {
        this.diagramName = diagramName;
    }

    public int getSmallestClassYCoord() {
        return smallestClassYCoord;
    }

    public void setSmallestClassYCoord(int smallestClassYCoord) {
        this.smallestClassYCoord = smallestClassYCoord;
    }

    public File getFile() {
        return fileInput;
    }

    public void setFile(File file) {
        this.fileInput = file;
    }

    public String getGeneralizationSetsString() {
        return generalizationSetsString;
    }

    public void setGeneralizationSetsString(String generalizationSetsString) {
        this.generalizationSetsString = generalizationSetsString;
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
