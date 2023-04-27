package com.company;

import java.io.File;

public class HolePunchStreamWriterThread extends Thread {
    File sourceFile;
    public HolePunchStreamWriterThread(File inputStreamTmpFile){
        sourceFile=inputStreamTmpFile;
    }
}
