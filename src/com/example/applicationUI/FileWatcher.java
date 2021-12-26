package com.example.applicationUI;
import com.example.jsonParser.ParseJson;

import java.util.*;
import java.io.*;

public abstract class FileWatcher extends TimerTask {
    private long timeStamp;
    private ParseJson parser;


    public FileWatcher(ParseJson parser) {
        this.parser = parser;
        this.timeStamp = new File(parser.getInputJsonString()).lastModified();
    }

    public final void run() {
        long timeStamp = new File(parser.getInputJsonString()).lastModified();

        if (this.timeStamp != timeStamp) {
            this.timeStamp = timeStamp;
            onChange(new File(parser.getInputJsonString()));
        }
    }
    protected abstract void onChange( File file );
}
