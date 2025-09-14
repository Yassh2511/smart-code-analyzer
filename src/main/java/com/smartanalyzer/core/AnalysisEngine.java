package com.smartanalyzer.core;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

// This can be called as core analysis for the program that is enitre
// code analysis process will be done here

public class AnalysisEngine {

    // SourceFiles and config are used to store the list of paths of java files
    // which we want to analyze and cofig will contain some settings
    private List<String> sourceFiles=null;
    private AnalysisConfig config=null;

    public AnalysisEngine()
    {

        this.sourceFiles=new ArrayList<>(); //Empty list
        this.config=new AnalysisConfig();   // Default config

    }
    // Initialization
    // Find files to analyze
    // Recursively explore all subdirectories and collect path

    public void initialize(String sourceDirectory)
    {
        System.out.println("\uD83D\uDCC1 Scanning directory:"+sourceDirectory);

        // scanDirectory will locate the particular directory
        // by sending parameter as a File Object of that directory

        scanDirectory(new File(sourceDirectory));

        System.out.println("\uD83D\uDCCAFound"+sourceFiles.size()+"jave Files");
    }

    private void scanDirectory(File directory)

    {
        // If directory isnt exit it will simply return no any operations
        // or findings will be done
        if(!directory.exists() ||!directory.isDirectory())
        {
            return;
        }
        // if directory present it will list all files present in
        // directoory
        // if its again directory recursive call made for the scanDirectory function
        // but if not it will find the file having extension as .java and return the absoulte
        // path of that particular file
        File[] files=directory.listFiles();
        if(files!=null)
        {
            for(File file:files)
            {
                if(file.isDirectory())
                {
                    scanDirectory(file);
                }
                else if(file.getName().endsWith(".java"))
                {
                    sourceFiles.add(file.getAbsolutePath());
                }
            }
        }
    }

    // Run complete analysis now we have check the path of the files
    // that we want to analyze now complete Analysis of code will be run

    public AnalysisResult runAnalysis()
    {
        AnalysisResult result=new AnalysisResult();
        System.out.println("Analyzing Code quality...");
        for(String filePath:sourceFiles)
        {
            System.out.println("Analyzing:"+filePath);
            analyzeFile(filePath,result);
        }

        return Result;
    }

    private void analyzeFile(String filePath,AnalysisResult result)
    {
        // Implement file analysis logic here
    }

}

// Analysis Configuration

class AnalysisConfig
{
    private boolean enablePerformanceRules=true;
    private boolean eneableSecurityRules=true;
    private boolean enableMaintainabiltyRules=true;

    public boolean isEnablePerformance()
    {
        return enablePerformanceRules;
    }
}