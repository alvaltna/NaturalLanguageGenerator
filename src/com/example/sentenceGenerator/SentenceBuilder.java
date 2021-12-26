package com.example.sentenceGenerator;

import com.example.jsonParser.*;

import java.util.*;

public class SentenceBuilder {

    private Phrases phrases;
    private Diagram diagram;
    private String diagramDescribeSentence;
    private List<String> diagramIntroductoryNotes = new ArrayList<>();
    private List<String> diagramAdditionalNotes = new ArrayList<>();
    private HashMap<ClassType, List<String>> describeClass = new HashMap<>();
    private HashMap<Integer, String> classMethods = new HashMap<>();
    private HashMap<Integer, List<String>> describeConnectors = new HashMap<>();
    private HashMap<ClassType, List<String>> describeClassNotes = new HashMap<>();
    private HashMap<ClassType, List<String>> describeClassConstraints = new HashMap<>();
    private HashMap<Integer, List<String>> describeConnectorComment = new HashMap<>();
    private HashMap<Connector, List<String>> describeConnectorsBothMultiplicities = new HashMap<>();
    private HashMap<Integer, List<String>> describeConnectorDescriptiveSentences = new HashMap<>();
    private HashMap<String, List<String>> describeGeneralizationSets = new HashMap<>();
    private HashMap<Integer, String> describeArtifacts = new HashMap<>();
    private HashMap<ClassType, List<String>> elementConnectorSentences = new HashMap<>();


    public SentenceBuilder(Phrases phrases, Diagram diagram) {
        this.phrases = phrases;
        this.diagram = diagram;


    }

    public void diagramDescribesSentence() {

        diagramDescribeSentence = "";
        String classNames = "";
        String diagramName = diagram.getDiagram();
        if (diagram.getClasses().size() != 0) {
            int numOfClasses = diagram.getClasses().size();
            for (int i = 0; i < numOfClasses - 1; i++) {
                classNames += diagram.getClasses().get(i).getName() + ", ";
            }

            classNames += diagram.getClasses().get(numOfClasses - 1).getName();
            if (diagram.getClasses().size() > 1) {
                diagramDescribeSentence = String.format(phrases.getDiagramDescribesSentence(),
                        diagramName, classNames, phrases.getAddToDiagramDescribeSentenceIfMultiple());
            } else {
                diagramDescribeSentence = String.format(phrases.getDiagramDescribesSentence(),
                        diagramName, classNames, "");
            }
        }
        else {
            diagramDescribeSentence = String.format(phrases.getDiagramDescribeEmptyDiagram(), diagramName);
         }



    }

    public void diagramNotes() {
        diagramIntroductoryNotes.clear();
        diagramAdditionalNotes.clear();

        for(DiagramNote diagramNote : diagram.getDiagramNotes()) {

            if(diagramNote.getY_coord() < diagram.getSmallestClassYCoord()) {

                diagramAdditionalNotes.add(diagramNote.getNote());
            }
            else {

                diagramIntroductoryNotes.add(diagramNote.getNote());
            }
        }

    }

    public void describeArtifactsInfo() {
        for(Artifact artifact : diagram.getArtifacts()) {
            describeArtifacts.put(artifact.getElementId(), artifact.getName());
        }
    }

    public void describeElement() {

        describeClass.clear();
        classMethods.clear();
        describeConnectors.clear();
        describeClassNotes.clear();
        describeClassConstraints.clear();
        describeConnectorComment.clear();
        describeConnectorDescriptiveSentences.clear();
        describeConnectorsBothMultiplicities.clear();
        describeGeneralizationSets.clear();

        for(ClassType classType : diagram.getClasses()) {
            describeClass.put(classType, new ArrayList<>());
            describeElementAttributes(classType);
            addConnectorInfo(classType);
            classMethods(classType);
        }
    }

    public void describeElementAttributes(ClassType classType) {

            for (Attribute attribute : classType.getAttributes()) {

                if ( attribute.getLowerBound().equals("*") && attribute.getUpperBound().equals("*")) {

                    describeClass.get(classType).add(String.format(phrases.getDescribeClassAttributesOptional(),
                            classType.getName(), phrases.getWordMany() ,attribute.getName(),
                            attribute.getType(), addInitialValue(attribute)));

                }
                else if (attribute.getLowerBound().equals("0") ) {
                    String upperBound = attribute.getUpperBound();
                    if (attribute.getUpperBound().equals("*")) {
                        upperBound = phrases.getWordMany();
                    }
                    describeClass.get(classType).add(String.format(phrases.getDescribeClassAttributesOptional(),
                            classType.getName(),  " " + digitTranslator("0") + " " + phrases.getWordTo() + " " + digitTranslator(upperBound),
                            attribute.getName(), attribute.getType(), addInitialValue(attribute)));


                }
                else if (attribute.getLowerBound().equals(attribute.getUpperBound())) {

                    describeClass.get(classType).add(String.format(phrases.getDescribeClassAttributesMandatory(),
                            classType.getName(),
                            digitTranslator(attribute.getLowerBound()), attribute.getName(),
                            attribute.getType(), addInitialValue(attribute)));

                }
                else {
                    String lowerBound = attribute.getLowerBound();
                    String upperBound = attribute.getUpperBound();
                    if (lowerBound.equals("*")) {
                        lowerBound = phrases.getWordMany();
                    }
                    if(upperBound.equals("*")) {
                        upperBound = phrases.getWordMany();
                    }
                    describeClass.get(classType).add(String.format(phrases.getDescribeClassAttributesMandatory(),
                            classType.getName(),
                            digitTranslator(lowerBound) + " " + phrases.getWordTo() + " " + digitTranslator(upperBound),
                            attribute.getName(), attribute.getType(), addInitialValue(attribute)));

                }

            }

    }
    public String addInitialValue(Attribute attribute) {

        if (!attribute.getInitialValue().equals("")) {

           return String.format(phrases.getAddInitialValue(), attribute.getInitialValue());
        }

        return "";
    }

    public void addConnectorInfo(ClassType classType) {
        elementConnectorSentences.put(classType, new ArrayList<>());
        for(Connector connector : classType.getConnectors()) {
            if((connector.getType().equals("Realisation")) && (connector.getSourceType().equals("UseCase") ||
                    connector.getEndType().equals("UseCase") )) {

                addUseCaseRealisationInfo(classType, connector);
            }
            else if (connector.getType().equals("Generalization") || connector.getType().equals("Realisation")) {

                if(connector.getType().equals("Generalization")) {
                    for(Map.Entry<String, GeneralizationSet> entry  : diagram.getGeneralizationSets().entrySet()) {
                        if (entry.getValue().getConnectors().containsKey(connector.getConnectorId())) {
                            if(!diagram.getGeneralizationSetsOnThisDiagram().containsKey(entry.getKey())) { ;
                                diagram.getGeneralizationSetsOnThisDiagram().put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
                addConnectorGeneralizationInfo(classType, connector);
            }
            else if(connector.getType().equals("Usage")) {

                addConnectorUsageInfo(classType, connector);
            }
            else if(connector.getType().equals("Association") || connector.getType().equals("Aggregation")) {

                addConnectorAssociationInfo(classType, connector);

            }
            else if(connector.getType().equals("NoteLink")) {
                addConnectorNoteLinkInfo(classType, connector);
            }

            else if(connector.getType().equals("Dependency")) {

                addConnectorDependencyInfo(classType, connector);
            }

        }

    }

    public void addUseCaseRealisationInfo(ClassType classType, Connector connector) {
            String useCaseRealisation;
            if (connector.getEndType().equals("UseCase")) {
                useCaseRealisation = String.format(
                        phrases.getDescribeUseCaseRealisationRelationship(), connector.getSourceName(),
                        connector.getEndName());
            }else {
                useCaseRealisation = String.format(
                        phrases.getDescribeUseCaseRealisationRelationship(), connector.getEndName(),
                        connector.getSourceName());
            }

            elementConnectorSentences.get(classType).add(useCaseRealisation);

            addConnectorComments(classType, connector);

    }
    private void addConnectorComments(ClassType classType, Connector connector) {
        if (diagram.getComments().containsKey(connector.getConnectorId())) {
            for(Comment comment : diagram.getComments().get(connector.getConnectorId())) {
                if (comment.getType().equals("Constraint")) {
                    elementConnectorSentences.get(classType)
                            .add(String.format(phrases.getDescribeConstraints(), comment.getNote()));
                }
                else if (comment.getType().equals("Note")) {
                    elementConnectorSentences.get(classType)
                            .add(String.format(phrases.getDescribeNotes(), comment.getNote()));
                }
            }

        }
    }

        public void addGeneralizationSetsInfo() {

        for(Map.Entry<String, GeneralizationSet> entry : diagram.getGeneralizationSetsOnThisDiagram().entrySet()) {
            List<String> sentences = new ArrayList<>();
            GeneralizationSet generalizationSet = entry.getValue();
            String generalizationClass = "";
            String generalizationName = generalizationSet.getName();
            String subtypes = "";
            String id = generalizationSet.getId();
            for (Map.Entry<Integer, String> connector : generalizationSet.getConnectors().entrySet()) {

                if (generalizationClass.equals("")) {
                    generalizationClass = generalizationSet.getSuperClassName();
                }
                subtypes += connector.getValue() + ", ";


            }
            subtypes = subtypes.substring(0, subtypes.length() - 2);
            describeGeneralizationSets.put(id, sentences);

            if (entry.getValue().isCovering()) {
                String sentence;
                if (generalizationSet.getConnectors().entrySet().size() > 1) {
                    sentence = phrases.getDescribeGeneralizationSetMandatoryPlural();
                }
                else {
                    sentence = phrases.getDescribeGeneralizationSetMandatorySingular();
                }
                describeGeneralizationSets.get(id)
                        .add(String.format(sentence,
                                generalizationClass, generalizationName, subtypes));
            }
            if (!entry.getValue().isCovering()) {
                describeGeneralizationSets.get(id)
                        .add(String.format(phrases.getDescribeGeneralizationSetOptional(),
                                generalizationClass, generalizationName, subtypes));
            }
            if (entry.getValue().isDisjoint()) {
                if (generalizationSet.getConnectors().entrySet().size() > 1) {

                    describeGeneralizationSets.get(id)
                            .add(String.format(phrases.getDescribeGeneralizationSetOrPlural(),
                                    generalizationClass, generalizationName, subtypes));
                }
            }
            if (!entry.getValue().isDisjoint()) {
                if (generalizationSet.getConnectors().entrySet().size() > 1) {

                    describeGeneralizationSets.get(id)
                            .add(String.format(phrases.getDescribeGeneralizationSetAndPlural(),
                                    generalizationClass, generalizationName, subtypes));
                }
            }

        }
    }



    public String translateToAggregationSentence(String sentence, boolean isPart) {
        if (!isPart) {
            if (sentence.equals(phrases.getDescribeIsAssociatedWith())) {
                return phrases.getDescribeAggregationHas();
            }
            else if (sentence.equals(phrases.getDescribeCanBeAssociatedWith())) {
                return  phrases.getDescribeAggregationCanHave();

            }
            else if (sentence.equals(phrases.getDescribeMustBeAssociatedWith())) {
                return  phrases.getDescribeAggregationMustHave();
            }

            else if (sentence.equals(phrases.getDescribeIsAssociatedWithNo())) {
                return phrases.getDescribeAggregationHasNo();
            }
            return sentence;

        }
        else {

            if (sentence.equals(phrases.getDescribeIsAssociatedWith())) {
                return phrases.getDescribeAggregationBelongsTo();
            }
            else if (sentence.equals(phrases.getDescribeCanBeAssociatedWith())) {
                return phrases.getDescribeAggregationCanBelongTo();

            }
            else if (sentence.equals(phrases.getDescribeMustBeAssociatedWith())) {
                return phrases.getDescribeAggregationMustBelongTo();
            }
            else if (sentence.equals(phrases.getDescribeIsAssociatedWithNo())) {
                return phrases.getDescribeAggregationBelongsToNo();
            }
            return sentence;
        }

    }


    public void addConnectorNoteLinkInfo(ClassType classType, Connector connector) {
        if(connector.getNote() != null) {
            if(describeClassNotes.containsKey(classType)) {
                describeClassNotes.get(classType).
                        add(String.format(phrases.getDescribeNotes(),connector.getNote()));
            }
            else {
                List<String> notes = new ArrayList<>();
                notes.add(String.format(phrases.getDescribeNotes() ,connector.getNote()));
                describeClassNotes.put(classType, notes);
            }

        }
        else {

            if(describeClassConstraints.containsKey(classType)) {
                describeClassConstraints.get(classType).
                        add(String.format(phrases.getDescribeConstraints(),connector.getConstraint()));
            }
            else {
                List<String> constraints = new ArrayList<>();
                constraints.add(String.format(phrases.getDescribeConstraints() ,connector.getConstraint()));
                describeClassConstraints.put(classType, constraints);
            }
        }

    }


    public void addConnectorGeneralizationInfo(ClassType classType, Connector connector) {
            describeConnectors.put(connector.getConnectorId(), new ArrayList<>());
            describeConnectors.get(connector.getConnectorId())
                    .add(String.format(phrases.getDescribeConnectorGeneralizationSubToSuper(),
                    connector.getSourceName(), connector.getEndName()));

            describeConnectors.get(connector.getConnectorId())
                    .add(String.format(phrases.getDescribeConnectorGeneralizationSuperToSub(),
                    connector.getEndName(), connector.getSourceName()));
            if(connector.getSourceId() == classType.getElementId()) {

                elementConnectorSentences.get(classType)
                        .add(String.format(phrases.getDescribeConnectorGeneralizationSubToSuper(),
                                connector.getSourceName(), connector.getEndName()));
                addConnectorComments(classType, connector);
            }
            else {
                elementConnectorSentences.get(classType)
                        .add(String.format(phrases.getDescribeConnectorGeneralizationSuperToSub(),
                                connector.getEndName(), connector.getSourceName()));
                addConnectorComments(classType, connector);
            }




    }

    public void addConnectorUsageInfo(ClassType classType, Connector connector) {

        describeConnectors.put(connector.getConnectorId(), new ArrayList<>());
        describeConnectors.get(connector.getConnectorId()).add(String.format(phrases.getDescribeConnectorUsage1(),
                connector.getSourceName(), connector.getEndName()));
        if(connector.getSourceId() == classType.getElementId()) {
            elementConnectorSentences.get(classType).add(String.format(phrases.getDescribeConnectorUsage1(),
                    connector.getSourceName(), connector.getEndName()));
            addConnectorComments(classType, connector);
        } else {
            elementConnectorSentences.get(classType).add(String.format(phrases.getDescribeConnectorUsage2(),
                    connector.getEndName(), connector.getSourceName()));
            addConnectorComments(classType, connector);
        }
    }

    public void addConnectorDependencyInfo(ClassType classType, Connector connector) {

        describeConnectors.put(connector.getConnectorId(), new ArrayList<>());
        describeConnectors.get(connector.getConnectorId()).add(String.format(phrases.getDescribeConnectorDependency(),
                connector.getSourceName(), connector.getEndName()));
        if(connector.getSourceId() == classType.getElementId()) {
            elementConnectorSentences.get(classType).add(String.format(phrases.getDescribeConnectorDependency(),
                    connector.getSourceName(), connector.getEndName()));
            addConnectorComments(classType, connector);
        }
    }

     public String addClassAndRole(String className, String classRole) {
        if (!classRole.equals("")) {
            return String.format("%s %s", className, String.format(phrases.getDescribeRole(), classRole));
        }
        return className;

     }

    public void addConnectorAssociationInfo(ClassType classType, Connector connector) {
        describeConnectorsBothMultiplicities.put(connector, new ArrayList<>());
        describeConnectorDescriptiveSentences.put(connector.getConnectorId(), new ArrayList<>());

        if(classType.getElementId() == connector.getSourceId()) {
            String multiplicity = connector.getEndMultiplicity();
            if (!checkMultiplicity(multiplicity)) {
                addConnectorSourceIsClassSource(classType, connector, multiplicity);
            }
            else {
                if(multiplicity.split("\\s*[..]+\\s*").length == 1) {
                    addConnectorSourceIsClassSourceNeededTranslating(classType, connector, multiplicity,
                            multiplicity, "");
                }
                else {
                    List<String> multiplicityDigits = getMultiplicityDigits(multiplicity);
                    addConnectorSourceIsClassSourceNeededTranslating(classType, connector,
                            multiplicity, multiplicityDigits.get(0), multiplicityDigits.get(1));
                }
            }
        }
        else {
            String multiplicity = connector.getStartMultiplicity();
            if (!checkMultiplicity(multiplicity)) {
                addConnectorSourceIsNotClassSource(classType, connector, multiplicity);
            }

            else {
                if(multiplicity.split("\\s*[..]+\\s*").length == 1) {
                    addConnectorSourceIsNotClassSourceNeededTranslating(classType, connector, multiplicity,
                            multiplicity, "");
                }
                else {
                    List<String> multiplicityDigits = getMultiplicityDigits(multiplicity);
                    addConnectorSourceIsNotClassSourceNeededTranslating(classType, connector,
                            multiplicity, multiplicityDigits.get(0), multiplicityDigits.get(1));
                }
            }
        }
    }

    public List<String> getMultiplicityDigits(String multiplicity) {
        List<String> multiplicityDigits = new ArrayList<>();
        multiplicityDigits.add(multiplicity.split("\\s*[..]+\\s*")[0]);
        multiplicityDigits.add(multiplicity.split("\\s*[..]+\\s*")[1]);
        return multiplicityDigits;
    }

    public List<String> aggregationSentences(Connector connector, List<String> multiplicitySentences, boolean isPart) {
        List<String> sentences = new ArrayList<>();
        String sentence1 = multiplicitySentences.get(0);
        String sentence2 = multiplicitySentences.get(1);
        if(multiplicitySentences.size() > 2) {
            String sentence3 = multiplicitySentences.get(2);
            String sentence4 = multiplicitySentences.get(3);
            if (connector.getType().equals("Aggregation")) {
                sentence2 = translateToAggregationSentence(sentence2, isPart);
                sentence4 = translateToAggregationSentence(sentence4, isPart);
                sentences.add(sentence1);
                sentences.add(sentence2);
                sentences.add(sentence3);
                sentences.add(sentence4);
                return sentences;
            }
            sentences.add(sentence1);
            sentences.add(sentence2);
            sentences.add(sentence3);
            sentences.add(sentence4);
            return sentences;
        }

        sentences.add(sentence1);
        sentences.add(sentence2);
        return sentences;
    }
    public void describeNoMultiplicitiesAssociation(ClassType classType, Connector connector, boolean isReversed) {
        String description;
        if(isReversed) {
            description = String.format(phrases.getDescribeEmptyMultiplicity(),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()));
        }
        else {
            description = String.format(phrases.getDescribeEmptyMultiplicity(),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()));
        }
        describeConnectorsBothMultiplicities.remove(connector);
        describeConnectorDescriptiveSentences.get(connector.getConnectorId()).add(description);
        elementConnectorSentences.get(classType).add(description);


    }

    public void describeAggregation(ClassType classType, Connector connector, String multiplicity, boolean isReversed) {
        String relationshipDescription = "";
        if(connector.getAggregationType() == 1) {
            if(isReversed) {
                relationshipDescription = String.format(phrases.getDescribeAggregation1(),
                        addClassAndRole(connector.getEndName(), connector.getTargetRole()),
                        addClassAndRole(connector.getSourceName(), connector.getSourceRole()));
            } else {
                relationshipDescription = String.format(phrases.getDescribeAggregation2(),
                        addClassAndRole(connector.getSourceName(), connector.getSourceRole()),
                        addClassAndRole(connector.getEndName(), connector.getTargetRole()));
            }
        }
        if (connector.getAggregationType() == 2) {
            relationshipDescription = String.format(phrases.getDescribeAggregationComposite(),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()));
        }
        if (multiplicity.equals("")) {
            describeConnectorsBothMultiplicities.remove(connector);
            describeConnectorDescriptiveSentences.get(connector.getConnectorId()).add(relationshipDescription);
            elementConnectorSentences.get(classType).add(relationshipDescription);


        }
        else {
            if (connector.getAggregationType() == 2) {
                describeConnectorsBothMultiplicities.get(connector).add(relationshipDescription);
                elementConnectorSentences.get(classType).add(relationshipDescription);

            }
        }

    }

    public String createConnectorDescriptiveSentence(Connector connector, String digit,
                                                     String sentence1, String sentence2,
                                                     boolean isReverse) {
        if (digit.equals("") && isReverse) {

            return String.format(sentence1,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentence2,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()));
        }
        else if (isReverse) {
            return String.format(sentence1,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentence2,
                    digit,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()));
        }
        else if (digit.equals("")) {
            return String.format(sentence1,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentence2,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()));
        }
        else {
            return String.format(sentence1,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentence2,
                    digit,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()));
        }

    }

    public void addConnectorSourceIsNotClassSourceNeededTranslating(ClassType classType, Connector connector, String multiplicity,
                                                                 String multiplicityFirstDigit, String multiplicitySecondDigit) {

        if (connector.getType().equals("Association") && multiplicity.equals("")) {
            describeNoMultiplicitiesAssociation(classType, connector, true);

        }
        if (connector.getType().equals("Aggregation")) {
            describeAggregation(classType, connector, multiplicity, true);
        }

        List<String> multiplicitySentences = multiplicityTranslator(multiplicity);
        if(multiplicitySecondDigit.equals("*")) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, false);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(1),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(3),
                    digitTranslator(multiplicityFirstDigit),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                    sentences.get(0), sentences.get(1), true));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicityFirstDigit),
                    sentences.get(2), sentences.get(3), true));

        }

        else if((multiplicityFirstDigit.equals("1") ||  multiplicityFirstDigit.equals("0"))
                && multiplicity.split("\\s*[..]+\\s*").length > 1) {
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, false);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(1),
                    multiplicitySecondDigit,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(3),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, digitTranslator(multiplicitySecondDigit),
                    sentences.get(0), sentences.get(1), true));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                    sentences.get(2), sentences.get(3), true));
        }

        else if (multiplicity.split("\\s*[..]+\\s*").length > 1) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, false);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(1),
                    multiplicitySecondDigit,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(3),
                    multiplicityFirstDigit,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicitySecondDigit),
                    sentences.get(0), sentences.get(1), true));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicityFirstDigit),
                    sentences.get(2), sentences.get(3), true));
        }


        else {

            List<String> sentences = aggregationSentences(connector, multiplicitySentences, false);
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(1),
                    multiplicityFirstDigit,
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicityFirstDigit),
                    sentences.get(0), sentences.get(1), true));

        }
        addConnectorComments(classType, connector);

    }


    public void addConnectorSourceIsClassSourceNeededTranslating(ClassType classType, Connector connector, String multiplicity,
                                                                 String multiplicityFirstDigit,
                                                                 String multiplicitySecondDigit) {


        if (connector.getType().equals("Association") && multiplicity.equals("")) {
            describeNoMultiplicitiesAssociation(classType, connector, false);

        }
        if (connector.getType().equals("Aggregation")) {
            describeAggregation(classType, connector, multiplicity, false);
        }

        List<String> multiplicitySentences = multiplicityTranslator(multiplicity);
        if(multiplicitySecondDigit.equals("*")) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, true);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(1),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(3),
                    multiplicityFirstDigit,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                    sentences.get(0), sentences.get(1), false));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicityFirstDigit),
                    sentences.get(2), sentences.get(3), false));
        }

        else if((multiplicityFirstDigit.equals("1") ||  multiplicityFirstDigit.equals("0"))
                && multiplicity.split("\\s*[..]+\\s*").length > 1) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, true);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(1),
                    multiplicitySecondDigit,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(3),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicitySecondDigit),
                    sentences.get(0), sentences.get(1), false));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                    sentences.get(2), sentences.get(3), false));
        }

        else if (multiplicity.split("\\s*[..]+\\s*").length > 1 ) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, true);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(1),
                    multiplicitySecondDigit,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(3),
                    multiplicityFirstDigit,
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicitySecondDigit),
                    sentences.get(0), sentences.get(1), false));
            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicityFirstDigit),
                    sentences.get(2), sentences.get(3), false));
        }
        else {

                List<String> sentences = aggregationSentences(connector, multiplicitySentences, true);


            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(1),
                    multiplicityFirstDigit, addClassAndRole(connector.getEndName(),
                            connector.getTargetRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector,
                    digitTranslator(multiplicityFirstDigit),
                    sentences.get(0), sentences.get(1), false));

        }
        addConnectorComments(classType, connector);
    }

    public void addConnectorSourceIsNotClassSource(ClassType classType, Connector connector, String multiplicity) {

        if (connector.getType().equals("Association") && multiplicity.equals("")) {
            describeNoMultiplicitiesAssociation(classType, connector, true);
        }
        if (connector.getType().equals("Aggregation")) {
            describeAggregation(classType, connector, multiplicity, true);
        }


        if (multiplicity.length() > 0) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> multiplicitySentences = getConnectorMultiplicitySentence(multiplicity);
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, false);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(1),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                    sentences.get(0), sentences.get(1), true));


            if (multiplicity.length() > 1) {
                describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                        addClassAndRole(connector.getEndName(), connector.getTargetRole()), sentences.get(3),
                        addClassAndRole(connector.getSourceName(), connector.getSourceRole())));

                elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                        sentences.get(2), sentences.get(3), true));
            }
        }

        addConnectorComments(classType, connector);
    }

    public void addConnectorSourceIsClassSource(ClassType classType, Connector connector, String multiplicity) {

        if (connector.getType().equals("Association") && multiplicity.equals("")) {
            describeNoMultiplicitiesAssociation(classType, connector, false);

        }
        if (connector.getType().equals("Aggregation")) {
            describeAggregation(classType, connector, multiplicity, false);
        }



        if (multiplicity.length() > 0) {
            describeConnectorDescriptiveSentences.remove(connector.getConnectorId());
            List<String> multiplicitySentences = getConnectorMultiplicitySentence(multiplicity);
            List<String> sentences = aggregationSentences(connector, multiplicitySentences, true);
            describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(0),
                    addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(1),
                    addClassAndRole(connector.getEndName(), connector.getTargetRole())));

            elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                    sentences.get(0), sentences.get(1), false));


            if (multiplicity.length() > 1) {
                describeConnectorsBothMultiplicities.get(connector).add(String.format(sentences.get(2),
                        addClassAndRole(connector.getSourceName(), connector.getSourceRole()), sentences.get(3),
                        addClassAndRole(connector.getEndName(), connector.getTargetRole())));

                elementConnectorSentences.get(classType).add(createConnectorDescriptiveSentence(connector, "",
                        sentences.get(2), sentences.get(3), false));
            }

        }
        addConnectorComments(classType, connector);
    }

    public boolean checkMultiplicity(String multiplicity) {
        boolean needsTranslating = false;

        if(multiplicity.length()==0) {
            return needsTranslating;
        }
        else if(multiplicity.equals("*")) {
            return needsTranslating;
        }
        else if(multiplicity.equals("0..*")) {
            return needsTranslating;
        }
        else if(multiplicity.equals("1..*")) {
            return needsTranslating;
        }
        else if(multiplicity.equals("0..1")) {
            return needsTranslating;
        }
        else {

            return true;
        }

    }

    public List<String> multiplicityTranslator(String multiplicity) {
        String[] multiplicityDigits  = multiplicity.split("\\s*[..]+\\s*");
        if(multiplicityDigits.length == 1) {
            return getConnectorMultiplicitySentence("n");
        }
        else if (multiplicityDigits[0].equals("0")) {
            return getConnectorMultiplicitySentence("0..n");
        }
        else if (multiplicityDigits[0].equals("1")) {
            return getConnectorMultiplicitySentence("1..n");
        }
        else if (multiplicityDigits[1].equals("*")) {
            return getConnectorMultiplicitySentence("n..*");
        }
        else {
            return getConnectorMultiplicitySentence("n..m");
        }

    }

    public List<String> getConnectorMultiplicitySentence(String multiplicity) {
        List<String> multiplicitySentences = new ArrayList<>();
        if (multiplicity.equals("*")) {
            multiplicitySentences.add(phrases.getDescribeManyRelationship());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("0..*")) {
            multiplicitySentences.add(phrases.getDescribeZeroToManyRelationship1());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeZeroToManyRelationship2());
            multiplicitySentences.add(phrases.getDescribeIsAssociatedWithNo());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("1..*")) {
            multiplicitySentences.add(phrases.getDescribeOneToManyRelationship1());
            multiplicitySentences.add(phrases.getDescribeMustBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeOneToManyRelationship2());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("n")) {
            multiplicitySentences.add(phrases.getDescribeExactRelationship());
            multiplicitySentences.add(phrases.getDescribeMustBeAssociatedWith());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("0..1")) {
            multiplicitySentences.add(phrases.getDescribeZeroToOneRelationship1());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeZeroToOneRelationship2());
            multiplicitySentences.add(phrases.getDescribeIsAssociatedWithNo());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("0..n")) {
            multiplicitySentences.add(phrases.getDescribeZeroToNRelationship1());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeZeroToNRelationship2());
            multiplicitySentences.add(phrases.getDescribeIsAssociatedWithNo());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("1..n")) {
            multiplicitySentences.add(phrases.getDescribeOneToNRelationship1());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeOneToNRelationship2());
            multiplicitySentences.add(phrases.getDescribeMustBeAssociatedWith());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("n..m")) {
            multiplicitySentences.add(phrases.getDescribeNToMRelationship1());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeNToMRelationship2());
            multiplicitySentences.add(phrases.getDescribeMustBeAssociatedWith());
            return multiplicitySentences;
        }
        else if(multiplicity.equals("n..*")) {
            multiplicitySentences.add(phrases.getDescribeNToManyRelationship1());
            multiplicitySentences.add(phrases.getDescribeCanBeAssociatedWith());
            multiplicitySentences.add(phrases.getDescribeNToManyRelationship2());
            multiplicitySentences.add(phrases.getDescribeMustBeAssociatedWith());
            return multiplicitySentences;
        }

        return multiplicitySentences;
    }

    public void classMethods(ClassType classType) {
        String methods = "";
        if (classType.getMethods().size() >= 1) {
            for(Method method : classType.getMethods()) {
                methods += method.getName() + ", ";
            }
            this.classMethods.put(classType.getElementId(),
                    String.format(phrases.getDescribeClassMethods(), classType.getName(), methods
                    .substring(0, methods.length() -2)));

        }

        else {

            this.classMethods.put(classType.getElementId(), methods);
        }

    }

    public String digitTranslator(String digit) {

        if(digit.equals("0")) {
            return phrases.getNumberZero();
        }
        if(digit.equals("1")) {
            return phrases.getNumberOne();
        }
        if(digit.equals("2")) {
            return phrases.getNumberTwo();
        }
        if(digit.equals("3")) {
            return phrases.getNumberThree();
        }
        if(digit.equals("4")) {
            return phrases.getNumberFour();
        }
        if(digit.equals("5")) {
            return phrases.getNumberFive();
        }
        if(digit.equals("6")) {
            return phrases.getNumberSix();
        }
        if(digit.equals("7")) {
            return phrases.getNumberSeven();
        }
        if(digit.equals("8")) {
            return phrases.getNumberEight();
        }
        if(digit.equals("9")) {
            return phrases.getNumberNine();
        }
        return digit;

    }


    public TreeMap<ClassType, List<String>> sortElementsByName() {
        return new TreeMap<>(describeClass);
    }

    public TreeMap<ClassType, List<String>> sortGroupedConnectorsByElementsName() {
        return new TreeMap<>(elementConnectorSentences);
    }

    public HashMap<ClassType, List<String>> getElementConnectorSentences() {
        return elementConnectorSentences;
    }

    public void setElementConnectorSentences(HashMap<ClassType, List<String>> elementConnectorSentences) {
        this.elementConnectorSentences = elementConnectorSentences;
    }

    public Phrases getPhrases() {
        return phrases;
    }

    public void setPhrases(Phrases phrases) {
        this.phrases = phrases;
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public List<String> getDiagramIntroductoryNotes() {
        return diagramIntroductoryNotes;
    }

    public void setDiagramIntroductoryNotes(List<String> diagramIntroductoryNotes) {
        this.diagramIntroductoryNotes = diagramIntroductoryNotes;
    }

    public List<String> getDiagramAdditionalNotes() {
        return diagramAdditionalNotes;
    }

    public void setDiagramAdditionalNotes(List<String> diagramAdditionalNotes) {
        this.diagramAdditionalNotes = diagramAdditionalNotes;
    }

    public String getDiagramDescribeSentence() {
        return diagramDescribeSentence;
    }

    public void setDiagramDescribeSentence(String diagramDescribeSentence) {
        this.diagramDescribeSentence = diagramDescribeSentence;
    }

    public HashMap<ClassType, List<String>> getDescribeClass() {
        return describeClass;
    }

    public void setDescribeClass(HashMap<ClassType, List<String>> describeClass) {
        this.describeClass = describeClass;
    }

    public HashMap<Integer, List<String>> getDescribeConnectors() {
        return describeConnectors;
    }

    public void setDescribeConnectors(HashMap<Integer, List<String>> describeConnectors) {
        this.describeConnectors = describeConnectors;
    }

    public HashMap<Connector, List<String>> getDescribeConnectorsBothMultiplicities() {
        return describeConnectorsBothMultiplicities;
    }

    public void setDescribeConnectorsBothMultiplicities(HashMap<Connector,
            List<String>> describeConnectorsBothMultiplicities) {
        this.describeConnectorsBothMultiplicities = describeConnectorsBothMultiplicities;
    }

    public HashMap<Integer, String> getClassMethods() {
        return classMethods;
    }

    public void setClassMethods(HashMap<Integer, String> classMethods) {
        this.classMethods = classMethods;
    }


    public HashMap<Integer, List<String>> getDescribeConnectorComment() {
        return describeConnectorComment;
    }

    public void setDescribeConnectorComment(HashMap<Integer, List<String>> describeConnectorComment) {
        this.describeConnectorComment = describeConnectorComment;
    }

    public HashMap<ClassType, List<String>> getDescribeClassNotes() {
        return describeClassNotes;
    }

    public void setDescribeClassNotes(HashMap<ClassType, List<String>> describeClassNotes) {
        this.describeClassNotes = describeClassNotes;
    }

    public HashMap<ClassType, List<String>> getDescribeClassConstraints() {
        return describeClassConstraints;
    }

    public void setDescribeClassConstraints(HashMap<ClassType, List<String>> describeClassConstraints) {
        this.describeClassConstraints = describeClassConstraints;
    }

    public HashMap<String, List<String>> getDescribeGeneralizationSets() {
        return describeGeneralizationSets;
    }

    public void setDescribeGeneralizationSets(HashMap<String, List<String>> describeGeneralizationSets) {
        this.describeGeneralizationSets = describeGeneralizationSets;
    }

    public HashMap<Integer, List<String>> getDescribeConnectorDescriptiveSentences() {
        return describeConnectorDescriptiveSentences;
    }

    public void setDescribeConnectorDescriptiveSentences(HashMap<Integer, List<String>> describeConnectorDescriptiveSentences) {
        this.describeConnectorDescriptiveSentences = describeConnectorDescriptiveSentences;
    }

    public HashMap<Integer, String> getDescribeArtifacts() {
        return describeArtifacts;
    }

    public void setDescribeArtifacts(HashMap<Integer, String> describeArtifacts) {
        this.describeArtifacts = describeArtifacts;
    }
}
