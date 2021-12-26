package com.example;
import com.example.applicationUI.ApplicationUI;
import com.example.jsonParser.*;
import com.example.applicationUI.FileWatcher;
import com.example.sentenceGenerator.Phrases;
import com.example.sentenceGenerator.SentenceWriter;

import java.io.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {


    public static void main(String[] args) {
        System.setProperty("file.encoding","UTF-8");
        Phrases phrases = new Phrases();
        ParseJson parser = new ParseJson();
        SentenceWriter sentenceWriter = new SentenceWriter(phrases, parser);
        ApplicationUI applicationUI = new ApplicationUI(sentenceWriter);
        applicationUI.homeView();

        TimerTask task = new FileWatcher(parser) {
            protected void onChange( File file ) {

                System.out.println( "File "+ file.getName() +" have change !" );
                applicationUI.removeLabels();
                applicationUI.updateLabelsText();
            }
        };
        Timer timer = new Timer();
        // repeat the check every 2 seconds
        timer.schedule( task , new Date(), 2000 );

    }
}



