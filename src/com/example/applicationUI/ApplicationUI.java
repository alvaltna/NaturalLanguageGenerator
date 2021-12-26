package com.example.applicationUI;

import com.example.sentenceGenerator.Phrases;
import com.example.sentenceGenerator.SentenceWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ApplicationUI {
    private JFrame frame = new JFrame();
    private JLabel describeDiagramLabel;
    private JLabel describeDiagramIntroductoryNotes;
    private JLabel describeDiagramClasses;
    private JLabel describeDiagramConnectors;
    private JLabel describeDiagramAdditionalNotes;
    private JPanel panel;
    private JTextPane f;
    private SentenceWriter sentenceWriter;
    private String generatedNaturalLanguage;
    private int numOfPhrases;
    private Phrases phrases;
    private List<String> usersLanguageFiles = new ArrayList<>();
    private Button currentLanguage;
    private String currentLanguageName = "English";
    private Button modifyLanguageButton;
    private Button chooseInputFileButton;
    private Button deleteLanguageButton;
    private JPanel buttonPanel;
    private String inputJsonFilePath;
    private JPanel infoPanel;
    private JLabel infoLabel;



    public ApplicationUI(SentenceWriter sentenceWriter) {

        this.sentenceWriter = sentenceWriter;
        this.numOfPhrases = sentenceWriter.getPhrases().getNumOfPhrases();
        this.phrases = sentenceWriter.getPhrases();
        updateGeneratedLanguage();
        getUserLanguages();
        addButtonPanel();

    }


    public void updateGeneratedLanguage() {

        sentenceWriter.getInputInfo();
        generatedNaturalLanguage = "";
        generatedNaturalLanguage = sentenceWriter.getGeneratedNaturalLanguage();
    }

    public void homeView() {

        usersLanguageFiles.clear();
        frame.getContentPane().removeAll();
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(23, 11, 50, 50);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1, 1 , 5,5));
        describeDiagramLabel = new JLabel(generatedNaturalLanguage);
        f = new JTextPane();
        f.setBorder(new EmptyBorder(10, 10, 10, 10));
        f.setContentType("text/html");
        f.setEditable(false);
        f.setBackground(null);
        f.setBorder(null);
        f.setText(generatedNaturalLanguage);
        frame.setTitle("Generated Natural Language");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel(new GridLayout(2,1));
        infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoLabel = new JLabel("Generated sentences in " + currentLanguageName + ":");
        infoPanel.add(infoLabel);
        contentPanel.add(buttonPanel);
        contentPanel.add(infoPanel);
        frame.getContentPane().add(contentPanel, BorderLayout.NORTH);
        panel.add(f);
        scrollPane.setViewportView(panel);
        frame.add(scrollPane);
        frame.repaint();
        frame.setVisible(true);

    }

    private void createModifyLanguageButton() {

        modifyLanguageButton = new Button("Add new Language");
        modifyLanguageButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                changeToLanguageModificationView();
            }
        });
    }

    private void createChooseInputFileButton() {
        chooseInputFileButton = new Button("Choose input file");
        chooseInputFileButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {   FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("Input Files", "json", "txt");
                JFileChooser j = new JFileChooser();
                j.setFileFilter(filter);
                j.showSaveDialog(null);
                inputJsonFilePath = j.getSelectedFile().getAbsolutePath();
                try{

                    BufferedWriter out = new BufferedWriter(new FileWriter("InputJsonFilePath.json"));
                    out.write(inputJsonFilePath);
                    out.close();

                }catch (IOException exc) {
                    exc.printStackTrace();
                }

                sentenceWriter.getParser().setInputJsonString(inputJsonFilePath);
                updateGeneratedLanguage();
                homeView();

            }
        });
    }

    private void createDeleteLanguageButton() {
        deleteLanguageButton = new Button("Delete language");
        deleteLanguageButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                changeToLanguageDeletionView();
            }
        });

    }

    private void changeToLanguageDeletionView() {
        getUserLanguages();
        frame.getContentPane().removeAll();
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(23, 11, 50, 50);
        panel = new JPanel();
        panel.setLayout(new GridLayout(usersLanguageFiles.size(), 2 , 5,5));
        for(int i=0; i<usersLanguageFiles.size(); i++) {
            if (!usersLanguageFiles.get(i).equals("English.txt")) {
                JLabel languageLabel = new JLabel(usersLanguageFiles.get(i));
                Button deleteButton = new Button("Remove");
                deleteButton.setBackground(Color.RED);
                String fileToDelete = usersLanguageFiles.get(i);
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteSelectedLanguage(fileToDelete);
                        panel.remove(deleteButton);
                        panel.remove(languageLabel);
                        panel.repaint();

                    }
                });
                panel.add(languageLabel);
                panel.add(deleteButton);
            }
        }
        scrollPane.setViewportView(panel);
        frame.add(scrollPane);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Button backToSentencesButton = createGetBackButton();
        buttonPanel.add(backToSentencesButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.repaint();
        frame.setVisible(true);
    }

    private void deleteSelectedLanguage(String language){

        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        for(File file: listOfFiles) {

            if(file.getName().equals(language)) {
                file.delete();
            }
        }
    }

    private Button createGetBackButton() {


        Button backToSentencesButton = new Button("Back");
        backToSentencesButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getUserLanguages();
                if(!usersLanguageFiles.contains(currentLanguageName + ".txt")) {
                    currentLanguageName = "English";
                    addButtonPanel();
                    setLanguageToEnglish();

                }else {
                    getUserLanguages();
                    addButtonPanel();
                    homeView();
                }
            }
        });
        return backToSentencesButton;
    }

    private void addButtonPanel() {
        createDeleteLanguageButton();
        createChooseInputFileButton();
        createModifyLanguageButton();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(chooseInputFileButton);
        buttonPanel.add(modifyLanguageButton);
        buttonPanel.add(deleteLanguageButton);
        for(String language : usersLanguageFiles) {
            Button languageButton = new Button(language.replace(".txt", ""));
            languageButton.setBackground(Color.WHITE);
            if((languageButton.getLabel()).equals(currentLanguageName)) {
                currentLanguage = languageButton;
                currentLanguage.setBackground(Color.GRAY);
            }
            languageButton.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                        currentLanguage.setBackground(Color.WHITE);
                        currentLanguage = languageButton;
                        currentLanguageName = languageButton.getLabel();
                        currentLanguage.setBackground(Color.GRAY);
                        getNewPhrasesOfLanguage(language);


                }
            });

            buttonPanel.add(languageButton);
        }
    }

    public void changeToLanguageModificationView() {

        frame.getContentPane().removeAll();
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(23, 11, 50, 50);
        panel = new JPanel();
        panel.setLayout(new GridLayout(numOfPhrases * 2, 1 , 5,5));
        for(Field phrase : phrases.allPhrases()) {
            try {

                panel.add(new JLabel(String.valueOf(phrase.get(phrases))));
                panel.add(new JTextField(String.valueOf(phrase.get(phrases))));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        scrollPane.setViewportView(panel);

        JPanel contentPanel = new JPanel();
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel guideLabel = new JLabel("<html>" + "To add a new language, translate the words " +
                "in English to a language of your choosing." + "<br/>" +
                "Try to translate every word and phrase as accurately as you can." + "<br/>" +
                "Please do not remove any %s symbols." +
                "They are needed for data input." + "<br/>" +
                "Translations can be saved by clicking the 'Save a new language' button." + "<br/>" +
                "Translations will be saved in the text file with the given language name." + "<br/>" +
                "From there translations can be continued or modified." + "<html/>");
        infoPanel.add(guideLabel);
        contentPanel.add(infoPanel);
        contentPanel.add(scrollPane);
        LayoutManager layout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
        Box boxes[] = new Box[2];
        boxes[0] = Box.createHorizontalBox();
        boxes[1] = Box.createHorizontalBox();

        boxes[0].createGlue();
        boxes[1].createGlue();


        infoPanel.setPreferredSize(new Dimension(780, infoPanel.getMinimumSize().height));
       scrollPane.setPreferredSize(new Dimension(780, 450));

        boxes[0].add(infoPanel);
        boxes[1].add(scrollPane);
        contentPanel.add(boxes[0]);
        contentPanel.add(boxes[1]);

        frame.add(contentPanel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Button saveLanguageButton = new Button("Save new language");
        Button backToSentencesButton = new Button("Back");
        backToSentencesButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                getUserLanguages();
                addButtonPanel();
                homeView();

            }
        });

        saveLanguageButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                List<String> newPhrases = new ArrayList<>();
                for (Component c : panel.getComponents()) {
                    if (c instanceof JTextField) {
                        newPhrases.add(((JTextField)c).getText());
                    }
                }
                String newLanguage = JOptionPane.showInputDialog(frame,
                        "<html>"+"If language already exists it will be overwritten!" + "<br/>" +
                                "New language name: " + "<html/>", null);
                if (newLanguage != null) {
                    saveNewLanguageToFile(newPhrases, newLanguage);
                }

            }
        });
        buttonPanel.add(saveLanguageButton);
        buttonPanel.add(backToSentencesButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.repaint();
        frame.setVisible(true);


    }

    public void saveNewLanguageToFile(List<String> newPhrases, String newLanguage) {

        Path file = Paths.get(newLanguage + ".txt");
        try{
            Files.write(file, newPhrases, StandardCharsets.UTF_8);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void getUserLanguages() {
        usersLanguageFiles.clear();
        usersLanguageFiles.add("English.txt");
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().endsWith("txt") &&
                    !listOfFiles[i].getName().equals("InputJsonFilePath.txt")) {
                usersLanguageFiles.add(listOfFiles[i].getName());

            }
        }
    }

    public void getNewPhrasesOfLanguage(String fileName) {

        File folder = new File(System.getProperty("user.dir"));
        List<String> newPhrases = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        if (fileName.equals("English.txt")) {
            setLanguageToEnglish();
        }
        else {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].getName().equals(fileName)) {
                    try{
                        Scanner s = new Scanner(listOfFiles[i]);
                        while (s.hasNextLine()){
                            newPhrases.add(s.nextLine());
                        }
                        s.close();
                    }
                    catch(FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            buildPhrasesObjectWithNewPhrases(newPhrases);

        }
    }


    public void setLanguageToEnglish() {
        Phrases newPhrases = new Phrases();
        sentenceWriter.setPhrases(newPhrases);
        sentenceWriter.getSentenceBuilder().setPhrases(newPhrases);
        sentenceWriter.getInputInfo();
        updateGeneratedLanguage();
        homeView();

    }

    public void buildPhrasesObjectWithNewPhrases(List<String> newLanguagePhrases){

        Phrases newPhrases = new Phrases();
        newPhrases.setDiagramDescribesSentence(newLanguagePhrases.get(0));
        newPhrases.setDiagramDescribeEmptyDiagram(newLanguagePhrases.get(1));
        newPhrases.setAddToDiagramDescribeSentenceIfMultiple(newLanguagePhrases.get(2));
        newPhrases.setDescribeClassAttributesMandatory(newLanguagePhrases.get(3));
        newPhrases.setDescribeClassAttributesOptional(newLanguagePhrases.get(4));
        newPhrases.setAddInitialValue(newLanguagePhrases.get(5));
        newPhrases.setDescribeConnectorGeneralizationSubToSuper(newLanguagePhrases.get(6));
        newPhrases.setDescribeConnectorGeneralizationSuperToSub(newLanguagePhrases.get(7));
        newPhrases.setDescribeConnectorUsage1(newLanguagePhrases.get(8));
        newPhrases.setDescribeConnectorUsage2(newLanguagePhrases.get(9));
        newPhrases.setDescribeConnectorDependency(newLanguagePhrases.get(10));
        newPhrases.setDescribeManyRelationship(newLanguagePhrases.get(11));
        newPhrases.setDescribeZeroToManyRelationship1(newLanguagePhrases.get(12));
        newPhrases.setDescribeZeroToManyRelationship2(newLanguagePhrases.get(13));
        newPhrases.setDescribeOneToManyRelationship1(newLanguagePhrases.get(14));
        newPhrases.setDescribeOneToManyRelationship2(newLanguagePhrases.get(15));
        newPhrases.setDescribeExactRelationship(newLanguagePhrases.get(16));
        newPhrases.setDescribeZeroToOneRelationship1(newLanguagePhrases.get(17));
        newPhrases.setDescribeZeroToOneRelationship2(newLanguagePhrases.get(18));
        newPhrases.setDescribeZeroToNRelationship1(newLanguagePhrases.get(19));
        newPhrases.setDescribeZeroToNRelationship2(newLanguagePhrases.get(20));
        newPhrases.setDescribeOneToNRelationship1(newLanguagePhrases.get(21));
        newPhrases.setDescribeOneToNRelationship2(newLanguagePhrases.get(22));
        newPhrases.setDescribeNToMRelationship1(newLanguagePhrases.get(23));
        newPhrases.setDescribeNToMRelationship2(newLanguagePhrases.get(24));
        newPhrases.setDescribeNToManyRelationship1(newLanguagePhrases.get(25));
        newPhrases.setDescribeNToManyRelationship2(newLanguagePhrases.get(26));
        newPhrases.setDescribeClassMethods(newLanguagePhrases.get(27));
        newPhrases.setDescribeEmptyMultiplicity(newLanguagePhrases.get(28));
        newPhrases.setDescribeNotes(newLanguagePhrases.get(29));
        newPhrases.setDescribeConstraints(newLanguagePhrases.get(30));
        newPhrases.setDescribeAggregation1(newLanguagePhrases.get(31));
        newPhrases.setDescribeAggregation2(newLanguagePhrases.get(32));
        newPhrases.setDescribeAggregationHas(newLanguagePhrases.get(33));
        newPhrases.setDescribeAggregationHasNo(newLanguagePhrases.get(34));
        newPhrases.setDescribeAggregationCanHave(newLanguagePhrases.get(35));
        newPhrases.setDescribeAggregationMustHave(newLanguagePhrases.get(36));
        newPhrases.setDescribeAggregationBelongsTo(newLanguagePhrases.get(37));
        newPhrases.setDescribeAggregationBelongsToNo(newLanguagePhrases.get(38));
        newPhrases.setDescribeAggregationCanBelongTo(newLanguagePhrases.get(39));
        newPhrases.setDescribeAggregationMustBelongTo(newLanguagePhrases.get(40));
        newPhrases.setDescribeRole(newLanguagePhrases.get(41));
        newPhrases.setDescribeGeneralizationSetMandatoryPlural(newLanguagePhrases.get(42));
        newPhrases.setDescribeGeneralizationSetAndPlural(newLanguagePhrases.get(43));
        newPhrases.setDescribeGeneralizationSetOrPlural(newLanguagePhrases.get(44));
        newPhrases.setDescribeGeneralizationSetOptional(newLanguagePhrases.get(45));
        newPhrases.setDescribeGeneralizationSetMandatorySingular(newLanguagePhrases.get(46));
        newPhrases.setDescribeAggregationComposite(newLanguagePhrases.get(47));
        newPhrases.setDescribeIsAssociatedWith(newLanguagePhrases.get(48));
        newPhrases.setDescribeCanBeAssociatedWith(newLanguagePhrases.get(49));
        newPhrases.setDescribeMustBeAssociatedWith(newLanguagePhrases.get(50));
        newPhrases.setDescribeIsAssociatedWithNo(newLanguagePhrases.get(51));
        newPhrases.setDescribeUseCaseRealisationRelationship(newLanguagePhrases.get(52));
        newPhrases.setWordMany(newLanguagePhrases.get(53));
        newPhrases.setWordTo(newLanguagePhrases.get(54));
        newPhrases.setDiagramLabel(newLanguagePhrases.get(55));
        newPhrases.setIntroductoryCommentsLabel(newLanguagePhrases.get(56));
        newPhrases.setArtifactsLabel(newLanguagePhrases.get(57));
        newPhrases.setElementDescriptionLabel(newLanguagePhrases.get(58));
        newPhrases.setConnectorsLabel(newLanguagePhrases.get(59));
        newPhrases.setAdditionalCommentsLabel(newLanguagePhrases.get(60));
        newPhrases.setElementClass(newLanguagePhrases.get(61));
        newPhrases.setElementInterface(newLanguagePhrases.get(62));
        newPhrases.setElementUseCase(newLanguagePhrases.get(63));
        newPhrases.setElementElement(newLanguagePhrases.get(64));
        newPhrases.setNumberZero(newLanguagePhrases.get(65));
        newPhrases.setNumberOne(newLanguagePhrases.get(66));
        newPhrases.setNumberTwo(newLanguagePhrases.get(67));
        newPhrases.setNumberThree(newLanguagePhrases.get(68));
        newPhrases.setNumberFour(newLanguagePhrases.get(69));
        newPhrases.setNumberFive(newLanguagePhrases.get(70));
        newPhrases.setNumberSix(newLanguagePhrases.get(71));
        newPhrases.setNumberSeven(newLanguagePhrases.get(72));
        newPhrases.setNumberEight(newLanguagePhrases.get(73));
        newPhrases.setNumberNine(newLanguagePhrases.get(74));
        newPhrases.setPackageName(newLanguagePhrases.get(75));
        sentenceWriter.setPhrases(newPhrases);
        sentenceWriter.getSentenceBuilder().setPhrases(newPhrases);
        //sentenceWriter.writeDiagramSentences();
        updateGeneratedLanguage();
        homeView();
    }

    public void removeLabels() {

        panel.remove(f);

    }
    public void updateLabelsText() {

        updateGeneratedLanguage();
        f.setText(generatedNaturalLanguage);
        panel.add(f);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getDescribeDiagramLabel() {
        return describeDiagramLabel;
    }

    public void setDescribeDiagramLabel(JLabel describeDiagramLabel) {
        this.describeDiagramLabel = describeDiagramLabel;
    }

    public JLabel getDescribeDiagramIntroductoryNotes() {
        return describeDiagramIntroductoryNotes;
    }

    public void setDescribeDiagramIntroductoryNotes(JLabel describeDiagramIntroductoryNotes) {
        this.describeDiagramIntroductoryNotes = describeDiagramIntroductoryNotes;
    }

    public JLabel getDescribeDiagramClasses() {
        return describeDiagramClasses;
    }

    public void setDescribeDiagramClasses(JLabel describeDiagramClasses) {
        this.describeDiagramClasses = describeDiagramClasses;
    }

    public JLabel getDescribeDiagramConnectors() {
        return describeDiagramConnectors;
    }

    public void setDescribeDiagramConnectors(JLabel describeDiagramConnectors) {
        this.describeDiagramConnectors = describeDiagramConnectors;
    }

    public JLabel getDescribeDiagramAdditionalNotes() {
        return describeDiagramAdditionalNotes;
    }

    public void setDescribeDiagramAdditionalNotes(JLabel describeDiagramAdditionalNotes) {
        this.describeDiagramAdditionalNotes = describeDiagramAdditionalNotes;
    }
}
