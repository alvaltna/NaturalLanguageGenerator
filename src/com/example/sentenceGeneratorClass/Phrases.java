package com.example.sentenceGeneratorClass;

import java.lang.reflect.Field;

public class Phrases {

    private String diagramDescribesSentence;
    private String diagramDescribeEmptyDiagram;
    private String addToDiagramDescribeSentenceIfMultiple;
    private String describeClassAttributesMandatory;
    private String describeClassAttributesOptional;
    private String addInitialValue;
    private String describeConnectorGeneralizationSubToSuper;
    private String describeConnectorGeneralizationSuperToSub;
    private String describeConnectorUsage1;
    private String describeConnectorUsage2;
    private String describeConnectorDependency;
    private String describeManyRelationship;
    private String describeZeroToManyRelationship1;
    private String describeZeroToManyRelationship2;
    private String describeOneToManyRelationship1;
    private String describeOneToManyRelationship2;
    private String describeExactRelationship;
    private String describeZeroToOneRelationship1;
    private String describeZeroToOneRelationship2;
    private String describeZeroToNRelationship1;
    private String describeZeroToNRelationship2;
    private String describeOneToNRelationship1;
    private String describeOneToNRelationship2;
    private String describeNToMRelationship1;
    private String describeNToMRelationship2;
    private String describeNToManyRelationship1;
    private String describeNToManyRelationship2;
    private String describeClassMethods;
    private String describeEmptyMultiplicity;
    private String describeNotes;
    private String describeConstraints;
    private String describeAggregation1;
    private String describeAggregation2;
    private String describeAggregationHas;
    private String describeAggregationHasNo;
    private String describeAggregationCanHave;
    private String describeAggregationMustHave;
    private String describeAggregationBelongsTo;
    private String describeAggregationBelongsToNo;
    private String describeAggregationCanBelongTo;
    private String describeAggregationMustBelongTo;
    private String describeRole;
    private String describeGeneralizationSetMandatoryPlural;
    private String describeGeneralizationSetAndPlural;
    private String describeGeneralizationSetOrPlural;
    private String describeGeneralizationSetOptional;
    private String describeGeneralizationSetMandatorySingular;
    private String describeAggregationComposite;
    private String describeIsAssociatedWith;
    private String describeCanBeAssociatedWith;
    private String describeMustBeAssociatedWith;
    private String describeIsAssociatedWithNo;
    private String describeUseCaseRealisationRelationship;
    private String wordMany;
    private String wordTo;
    private String diagramLabel;
    private String introductoryCommentsLabel;
    private String artifactsLabel;
    private String elementDescriptionLabel;
    private String connectorsLabel;
    private String additionalCommentsLabel;
    private String elementClass;
    private String elementInterface;
    private String elementUseCase;
    private String elementElement;
    private String numberZero;
    private String numberOne;
    private String numberTwo;
    private String numberThree;
    private String numberFour;
    private String numberFive;
    private String numberSix;
    private String numberSeven;
    private String numberEight;
    private String numberNine;
    private String packageName;





    public Phrases() {
        this.numberZero = "zero";
        this.numberOne = "one";
        this.numberTwo = "two";
        this.numberThree = "three";
        this.numberFour = "four";
        this.numberFive = "five";
        this.numberSix = "six";
        this.numberSeven = "seven";
        this.numberEight = "eight";
        this.numberNine = "nine";
        this.diagramDescribesSentence = "Diagram %s describes %s %s";
        this.diagramDescribeEmptyDiagram = "Diagram %s is empty";
        this.addToDiagramDescribeSentenceIfMultiple = "and relationships between them";
        this.describeClassAttributesMandatory = "%s is characterized by %s %s that has type %s %s";
        this.describeClassAttributesOptional = "%s can be characterized by %s %s that has type %s %s";
        this.addInitialValue = "with initial value %s";
        this.describeConnectorGeneralizationSubToSuper = "%s is %s";
        this.describeConnectorGeneralizationSuperToSub = "%s can be %s";
        this.describeConnectorUsage1 = "%s uses %s";
        this.describeConnectorUsage2 = "%s is used by %s";
        this.describeConnectorDependency = "%s depends on %s";
        this.describeManyRelationship = "Each %s %s many %s";
        this.describeZeroToManyRelationship1 = "Each %s %s many %s";
        this.describeZeroToManyRelationship2 = "Some %s %s %s";
        this.describeOneToManyRelationship1 = "Each %s %s many %s";
        this.describeOneToManyRelationship2 = "Each %s %s at least " + this.numberOne + " %s";
        this.describeExactRelationship = "Each %s %s exactly %s %s";
        this.describeZeroToOneRelationship1 = "Each %s %s at most " + this.numberOne + " %s";
        this.describeZeroToOneRelationship2 = "Some %s %s %s";
        this.describeZeroToNRelationship1 = "Each %s %s at most %s %s";
        this.describeZeroToNRelationship2 = "Some %s %s %s";
        this.describeOneToNRelationship1 = "Each %s %s at most %s %s";
        this.describeOneToNRelationship2 = "Each %s %s at least " + this.numberOne + " %s";
        this.describeNToMRelationship1 = "Each %s %s at most %s %s";
        this.describeNToMRelationship2 = "Each %s %s at least %s %s";
        this.describeNToManyRelationship1 = "Each %s %s many %s";
        this.describeNToManyRelationship2 = "Each %s %s at least %s %s";
        this.describeEmptyMultiplicity = "%s is associated with %s";
        this.describeClassMethods = "%s can %s";
        this.describeNotes = "Note: %s";
        this.describeConstraints = "Constraint: %s";
        this.describeAggregationComposite = "%s is a part of %s and can't exist without it." +
                " If %s is deleted, then %s should also be deleted";
        this.describeAggregation1 = "%s has a %s";
        this.describeAggregation2 = "%s belongs to a %s";
        this.describeAggregationHas = "has";
        this.describeAggregationHasNo = "has no";
        this.describeAggregationMustHave = "must have";
        this.describeAggregationCanHave = "can have";
        this.describeAggregationBelongsTo = "belongs to";
        this.describeAggregationBelongsToNo = "belongs to no";
        this.describeAggregationCanBelongTo = "can belong to";
        this.describeAggregationMustBelongTo = "must belong to";
        this.describeRole = " as a %s";
        this.describeGeneralizationSetMandatoryPlural = "Each %s must have at least one %s of the following type: %s";
        this.describeGeneralizationSetAndPlural = "Each %s can at same time have more than one %s of the following type: %s";
        this.describeGeneralizationSetOptional = "Each %s doesn't have to have %s of the following type: %s";
        this.describeGeneralizationSetOrPlural = "Each %s can at same time have only one %s of the following type: %s";
        this.describeGeneralizationSetMandatorySingular = "Each %s must have %s of the following type %s";
        this.describeIsAssociatedWith = "is associated with";
        this.describeCanBeAssociatedWith = "can be associated with";
        this.describeMustBeAssociatedWith = "must be associated with";
        this.describeIsAssociatedWithNo = "is associated with no";
        this.describeUseCaseRealisationRelationship = "%s is needed to implement %s";
        this.wordMany = "many";
        this.wordTo = "to";
        this.diagramLabel = "DIAGRAM";
        this.introductoryCommentsLabel = "INTRODUCTORY COMMENTS";
        this.artifactsLabel = "ARTIFACTS";
        this.elementDescriptionLabel = "ELEMENT DESCRIPTIONS";
        this.connectorsLabel = "CONNECTORS";
        this.additionalCommentsLabel = "ADDITIONAL COMMENTS";
        this.elementClass = "Class";
        this.elementInterface = "Interface";
        this.elementUseCase = "UseCase";
        this.elementElement = "Element";
        this.packageName = "PACKAGE %s";





    }

    public String getDiagramDescribesSentence() {
        return diagramDescribesSentence;
    }

    public void setDiagramDescribesSentence(String diagramDescribesSentence) {
        this.diagramDescribesSentence = diagramDescribesSentence;
    }

    public String getDescribeClassAttributesMandatory() {
        return describeClassAttributesMandatory;
    }

    public void setDescribeClassAttributesMandatory(String describeClassAttributesMandatory) {
        this.describeClassAttributesMandatory = describeClassAttributesMandatory;
    }

    public String getDescribeClassAttributesOptional() {
        return describeClassAttributesOptional;
    }

    public void setDescribeClassAttributesOptional(String describeClassAttributesOptional) {
        this.describeClassAttributesOptional = describeClassAttributesOptional;
    }

    public String getAddInitialValue() {
        return addInitialValue;
    }

    public void setAddInitialValue(String addInitialValue) {
        this.addInitialValue = addInitialValue;
    }

    public String getDescribeConnectorGeneralizationSubToSuper() {
        return describeConnectorGeneralizationSubToSuper;
    }

    public void setDescribeConnectorGeneralizationSubToSuper(String describeConnectorGeneralizationSubToSuper) {
        this.describeConnectorGeneralizationSubToSuper = describeConnectorGeneralizationSubToSuper;
    }

    public String getDescribeConnectorGeneralizationSuperToSub() {
        return describeConnectorGeneralizationSuperToSub;
    }

    public void setDescribeConnectorGeneralizationSuperToSub(String describeConnectorGeneralizationSuperToSub) {
        this.describeConnectorGeneralizationSuperToSub = describeConnectorGeneralizationSuperToSub;
    }

    public String getDescribeConnectorUsage1() {
        return describeConnectorUsage1;
    }

    public void setDescribeConnectorUsage1(String describeConnectorUsage) {
        this.describeConnectorUsage1 = describeConnectorUsage;
    }

    public String getDescribeConnectorUsage2() {
        return describeConnectorUsage2;
    }

    public void setDescribeConnectorUsage2(String describeConnectorUsage2) {
        this.describeConnectorUsage2 = describeConnectorUsage2;
    }

    public String getDescribeManyRelationship() {
        return describeManyRelationship;
    }

    public void setDescribeManyRelationship(String describeManyRelationship) {
        this.describeManyRelationship = describeManyRelationship;
    }

    public String getDescribeZeroToManyRelationship1() {
        return describeZeroToManyRelationship1;
    }

    public void setDescribeZeroToManyRelationship1(String describeZeroToManyRelationship1) {
        this.describeZeroToManyRelationship1 = describeZeroToManyRelationship1;
    }

    public String getDescribeZeroToManyRelationship2() {
        return describeZeroToManyRelationship2;
    }

    public void setDescribeZeroToManyRelationship2(String describeZeroToManyRelationship2) {
        this.describeZeroToManyRelationship2 = describeZeroToManyRelationship2;
    }

    public String getDescribeOneToManyRelationship1() {
        return describeOneToManyRelationship1;
    }

    public void setDescribeOneToManyRelationship1(String describeOneToManyRelationship1) {
        this.describeOneToManyRelationship1 = describeOneToManyRelationship1;
    }

    public String getDescribeOneToManyRelationship2() {
        return describeOneToManyRelationship2;
    }

    public void setDescribeOneToManyRelationship2(String describeOneToManyRelationship2) {
        this.describeOneToManyRelationship2 = describeOneToManyRelationship2;
    }

    public String getDescribeExactRelationship() {
        return describeExactRelationship;
    }

    public void setDescribeExactRelationship(String describeExactRelationship) {
        this.describeExactRelationship = describeExactRelationship;
    }

    public String getDescribeZeroToOneRelationship1() {
        return describeZeroToOneRelationship1;
    }

    public void setDescribeZeroToOneRelationship1(String describeZeroToOneRelationship1) {
        this.describeZeroToOneRelationship1 = describeZeroToOneRelationship1;
    }

    public String getDescribeZeroToOneRelationship2() {
        return describeZeroToOneRelationship2;
    }

    public void setDescribeZeroToOneRelationship2(String describeZeroToOneRelationship2) {
        this.describeZeroToOneRelationship2 = describeZeroToOneRelationship2;
    }

    public String getDescribeZeroToNRelationship1() {
        return describeZeroToNRelationship1;
    }

    public void setDescribeZeroToNRelationship1(String describeZeroToNRelationship1) {
        this.describeZeroToNRelationship1 = describeZeroToNRelationship1;
    }

    public String getDescribeZeroToNRelationship2() {
        return describeZeroToNRelationship2;
    }

    public void setDescribeZeroToNRelationship2(String describeZeroToNRelationship2) {
        this.describeZeroToNRelationship2 = describeZeroToNRelationship2;
    }

    public String getDescribeOneToNRelationship1() {
        return describeOneToNRelationship1;
    }

    public void setDescribeOneToNRelationship1(String describeOneToNRelationship1) {
        this.describeOneToNRelationship1 = describeOneToNRelationship1;
    }

    public String getDescribeOneToNRelationship2() {
        return describeOneToNRelationship2;
    }

    public void setDescribeOneToNRelationship2(String describeOneToNRelationship2) {
        this.describeOneToNRelationship2 = describeOneToNRelationship2;
    }

    public String getDescribeNToMRelationship1() {
        return describeNToMRelationship1;
    }

    public void setDescribeNToMRelationship1(String describeNToMRelationship1) {
        this.describeNToMRelationship1 = describeNToMRelationship1;
    }

    public String getDescribeNToMRelationship2() {
        return describeNToMRelationship2;
    }

    public void setDescribeNToMRelationship2(String describeNToMRelationship2) {
        this.describeNToMRelationship2 = describeNToMRelationship2;
    }


    public String getDescribeNToManyRelationship1() {
        return describeNToManyRelationship1;
    }

    public void setDescribeNToManyRelationship1(String describeNToManyRelationship1) {
        this.describeNToManyRelationship1 = describeNToManyRelationship1;
    }

    public String getDescribeNToManyRelationship2() {
        return describeNToManyRelationship2;
    }

    public void setDescribeNToManyRelationship2(String describeNToManyRelationship2) {
        this.describeNToManyRelationship2 = describeNToManyRelationship2;
    }

    public String getDescribeClassMethods() {
        return describeClassMethods;
    }

    public void setDescribeClassMethods(String describeClassMethods) {
        this.describeClassMethods = describeClassMethods;
    }

    public String getAddToDiagramDescribeSentenceIfMultiple() {
        return addToDiagramDescribeSentenceIfMultiple;
    }

    public void setAddToDiagramDescribeSentenceIfMultiple(String addToDiagramDescribeSentenceIfMultiple) {
        this.addToDiagramDescribeSentenceIfMultiple = addToDiagramDescribeSentenceIfMultiple;
    }

    public String getDescribeEmptyMultiplicity() {
        return describeEmptyMultiplicity;
    }

    public void setDescribeEmptyMultiplicity(String describeEmptyMultiplicity) {
        this.describeEmptyMultiplicity = describeEmptyMultiplicity;
    }

    public String getDescribeNotes() {
        return describeNotes;
    }

    public void setDescribeNotes(String describeNotes) {
        this.describeNotes = describeNotes;
    }

    public String getDescribeConstraints() {
        return describeConstraints;
    }

    public void setDescribeConstraints(String describeConstraints) {
        this.describeConstraints = describeConstraints;
    }

    public String getDescribeAggregation1() {
        return describeAggregation1;
    }

    public void setDescribeAggregation1(String describeAggregation1) {
        this.describeAggregation1 = describeAggregation1;
    }

    public String getDescribeAggregation2() {
        return describeAggregation2;
    }

    public void setDescribeAggregation2(String describeAggregation2) {
        this.describeAggregation2 = describeAggregation2;
    }

    public String getDescribeAggregationHas() {
        return describeAggregationHas;
    }

    public void setDescribeAggregationHas(String describeAggregationHas) {
        this.describeAggregationHas = describeAggregationHas;
    }

    public String getDescribeAggregationCanHave() {
        return describeAggregationCanHave;
    }

    public void setDescribeAggregationCanHave(String describeAggregationCanHave) {
        this.describeAggregationCanHave = describeAggregationCanHave;
    }

    public String getDescribeAggregationMustHave() {
        return describeAggregationMustHave;
    }

    public void setDescribeAggregationMustHave(String describeAggregationMustHave) {
        this.describeAggregationMustHave = describeAggregationMustHave;
    }

    public String getDescribeAggregationBelongsTo() {
        return describeAggregationBelongsTo;
    }

    public void setDescribeAggregationBelongsTo(String describeAggregationBelongsTo) {
        this.describeAggregationBelongsTo = describeAggregationBelongsTo;
    }

    public String getDescribeAggregationCanBelongTo() {
        return describeAggregationCanBelongTo;
    }

    public void setDescribeAggregationCanBelongTo(String describeAggregationCanBelongTo) {
        this.describeAggregationCanBelongTo = describeAggregationCanBelongTo;
    }

    public String getDescribeAggregationMustBelongTo() {
        return describeAggregationMustBelongTo;
    }

    public void setDescribeAggregationMustBelongTo(String describeAggregationMustBelongto) {
        this.describeAggregationMustBelongTo = describeAggregationMustBelongto;
    }

    public String getDescribeRole() {
        return describeRole;
    }

    public void setDescribeRole(String describeRole) {
        this.describeRole = describeRole;
    }

    public String getDiagramDescribeEmptyDiagram() {
        return diagramDescribeEmptyDiagram;
    }

    public void setDiagramDescribeEmptyDiagram(String diagramDescribeEmptyDiagram) {
        this.diagramDescribeEmptyDiagram = diagramDescribeEmptyDiagram;
    }

    public String getDescribeConnectorDependency() {
        return describeConnectorDependency;
    }

    public void setDescribeConnectorDependency(String describeConnectorDependency) {
        this.describeConnectorDependency = describeConnectorDependency;
    }

    public String getDescribeGeneralizationSetMandatoryPlural() {
        return describeGeneralizationSetMandatoryPlural;
    }

    public void setDescribeGeneralizationSetMandatoryPlural(String describeGeneralizationSetMandatoryPlural) {
        this.describeGeneralizationSetMandatoryPlural = describeGeneralizationSetMandatoryPlural;
    }

    public String getDescribeGeneralizationSetAndPlural() {
        return describeGeneralizationSetAndPlural;
    }

    public void setDescribeGeneralizationSetAndPlural(String describeGeneralizationSetAndPlural) {
        this.describeGeneralizationSetAndPlural = describeGeneralizationSetAndPlural;
    }

    public String getDescribeGeneralizationSetOrPlural() {
        return describeGeneralizationSetOrPlural;
    }

    public void setDescribeGeneralizationSetOrPlural(String getDescribeGeneralizationSetOr) {
        this.describeGeneralizationSetOrPlural = getDescribeGeneralizationSetOr;
    }

    public String getDescribeGeneralizationSetOptional() {
        return describeGeneralizationSetOptional;
    }

    public void setDescribeGeneralizationSetOptional(String getDescribeGeneralizationSetOptional) {
        this.describeGeneralizationSetOptional = getDescribeGeneralizationSetOptional;
    }

    public String getDescribeGeneralizationSetMandatorySingular() {
        return describeGeneralizationSetMandatorySingular;
    }

    public void setDescribeGeneralizationSetMandatorySingular(String describeGeneralizationSetMandatorySingular) {
        this.describeGeneralizationSetMandatorySingular = describeGeneralizationSetMandatorySingular;
    }

    public String getDescribeAggregationComposite() {
        return describeAggregationComposite;
    }

    public void setDescribeAggregationComposite(String describeAggregationComposite) {
        this.describeAggregationComposite = describeAggregationComposite;
    }

    public String getDescribeIsAssociatedWith() {
        return describeIsAssociatedWith;
    }

    public void setDescribeIsAssociatedWith(String describeIsAssociatedWith) {
        this.describeIsAssociatedWith = describeIsAssociatedWith;
    }

    public String getDescribeCanBeAssociatedWith() {
        return describeCanBeAssociatedWith;
    }

    public void setDescribeCanBeAssociatedWith(String describeCanBeAssociatedWith) {
        this.describeCanBeAssociatedWith = describeCanBeAssociatedWith;
    }

    public String getDescribeMustBeAssociatedWith() {
        return describeMustBeAssociatedWith;
    }

    public void setDescribeMustBeAssociatedWith(String describeMustBeAssociatedWith) {
        this.describeMustBeAssociatedWith = describeMustBeAssociatedWith;
    }

    public String getDescribeIsAssociatedWithNo() {
        return describeIsAssociatedWithNo;
    }

    public void setDescribeIsAssociatedWithNo(String describeIsAssociatedWithNo) {
        this.describeIsAssociatedWithNo = describeIsAssociatedWithNo;
    }

    public String getDescribeAggregationHasNo() {
        return describeAggregationHasNo;
    }

    public void setDescribeAggregationHasNo(String describeAggregationHasNo) {
        this.describeAggregationHasNo = describeAggregationHasNo;
    }

    public String getDescribeAggregationBelongsToNo() {
        return describeAggregationBelongsToNo;
    }

    public void setDescribeAggregationBelongsToNo(String describeAggregationBelongsToNo) {
        this.describeAggregationBelongsToNo = describeAggregationBelongsToNo;
    }

    public String getDiagramLabel() {
        return diagramLabel;
    }

    public void setDiagramLabel(String diagramLabel) {
        this.diagramLabel = diagramLabel;
    }

    public String getIntroductoryCommentsLabel() {
        return introductoryCommentsLabel;
    }

    public void setIntroductoryCommentsLabel(String introductoryCommentsLabel) {
        this.introductoryCommentsLabel = introductoryCommentsLabel;
    }

    public String getArtifactsLabel() {
        return artifactsLabel;
    }

    public void setArtifactsLabel(String artifactsLabel) {
        this.artifactsLabel = artifactsLabel;
    }

    public String getElementDescriptionLabel() {
        return elementDescriptionLabel;
    }

    public void setElementDescriptionLabel(String elementDescriptionLabel) {
        this.elementDescriptionLabel = elementDescriptionLabel;
    }

    public String getConnectorsLabel() {
        return connectorsLabel;
    }

    public void setConnectorsLabel(String connectorsLabel) {
        this.connectorsLabel = connectorsLabel;
    }

    public String getAdditionalCommentsLabel() {
        return additionalCommentsLabel;
    }

    public void setAdditionalCommentsLabel(String additionalCommentsLabel) {
        this.additionalCommentsLabel = additionalCommentsLabel;
    }

    public String getWordMany() {
        return wordMany;
    }

    public void setWordMany(String wordMany) {
        this.wordMany = wordMany;
    }

    public String getWordTo() {
        return wordTo;
    }

    public void setWordTo(String wordTo) {
        this.wordTo = wordTo;
    }

    public String getElementClass() {
        return elementClass;
    }

    public void setElementClass(String elementClass) {
        this.elementClass = elementClass;
    }

    public String getElementInterface() {
        return elementInterface;
    }

    public void setElementInterface(String elementInterface) {
        this.elementInterface = elementInterface;
    }

    public String getNumberZero() {
        return numberZero;
    }

    public void setNumberZero(String numberZero) {
        this.numberZero = numberZero;
    }

    public String getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    public String getNumberThree() {
        return numberThree;
    }

    public void setNumberThree(String numberThree) {
        this.numberThree = numberThree;
    }

    public String getNumberFour() {
        return numberFour;
    }

    public void setNumberFour(String numberFour) {
        this.numberFour = numberFour;
    }

    public String getNumberFive() {
        return numberFive;
    }

    public void setNumberFive(String numberFive) {
        this.numberFive = numberFive;
    }

    public String getNumberSix() {
        return numberSix;
    }

    public void setNumberSix(String numberSix) {
        this.numberSix = numberSix;
    }

    public String getNumberSeven() {
        return numberSeven;
    }

    public void setNumberSeven(String numberSeven) {
        this.numberSeven = numberSeven;
    }

    public String getNumberEight() {
        return numberEight;
    }

    public void setNumberEight(String numberEight) {
        this.numberEight = numberEight;
    }

    public String getNumberNine() {
        return numberNine;
    }

    public void setNumberNine(String numberNine) {
        this.numberNine = numberNine;
    }

    public int getNumOfPhrases() {
        return getClass().getDeclaredFields().length;
    }

    public String getElementUseCase() {
        return elementUseCase;
    }

    public void setElementUseCase(String elementUseCase) {
        this.elementUseCase = elementUseCase;
    }

    public String getDescribeUseCaseRealisationRelationship() {
        return describeUseCaseRealisationRelationship;
    }

    public void setDescribeUseCaseRealisationRelationship(String describeUseCaseRealisationRelationship) {
        this.describeUseCaseRealisationRelationship = describeUseCaseRealisationRelationship;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getElementElement() {
        return elementElement;
    }

    public void setElementElement(String elementElement) {
        this.elementElement = elementElement;
    }

    public Field[] allPhrases() {

        Field[] allFields =  Phrases.class.getDeclaredFields();
        for(Field field : allFields) {

            field.setAccessible(true);

        }
        return allFields;
    }

}
