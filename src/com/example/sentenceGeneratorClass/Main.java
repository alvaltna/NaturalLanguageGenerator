package com.example.sentenceGeneratorClass;
import com.example.jsonparserclass.*;

import java.io.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {


    public static void main(String[] args) {

        Phrases phrases = new Phrases();
        ParseJsonClass parser = new ParseJsonClass();
        SentenceWriter sentenceWriter = new SentenceWriter(phrases, parser);
        ApplicationUI applicationUI = new ApplicationUI(sentenceWriter);
        applicationUI.test();

        TimerTask task = new FileWatcher(parser) {
            protected void onChange( File file ) {
                // here we code the action on a change
                System.out.println( "File "+ file.getName() +" have change !" );
                applicationUI.removeLabels();
                applicationUI.updateLabelsText();
            }
        };
        Timer timer = new Timer();
        // repeat the check every 6 seconds
        timer.schedule( task , new Date(), 6000 );
    }

}



