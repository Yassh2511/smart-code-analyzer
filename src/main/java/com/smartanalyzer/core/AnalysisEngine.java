package com.smartanalyzer.core;


import com.smartanalyzer.parser.CodeStructure;
import com.smartanalyzer.parser.JavaFileParser;
import com.smartanalyzer.rules.performance.StringConcatenationRule;
import com.smartanalyzer.rules.security.HardcodedCredentialsRule;
import com.smartanalyzer.rules.maintainability.MethodComplexityRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        System.out.println("Scanning directory:"+sourceDirectory);

        // scanDirectory will locate the particular directory
        // by sending parameter as a File Object of that directory

        scanDirectory(new File(sourceDirectory));

        System.out.println("Found"+sourceFiles.size()+"java Files");
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

        result.setTotalFiles(sourceFiles.size());
        return result;
    }

    // In analyzeFile we will handle each file individually
    // Open the java file
    //Parse java file
    //apply analysis rules
    // like string concatenation in loops,hardcoded passwords,unused variables
    //Then find violations and add it into result
    private void analyzeFile(String filePath, AnalysisResult result) {
        try {

            StringConcatenationRule stringRule = new StringConcatenationRule();
            HardcodedCredentialsRule credentialsRule = new HardcodedCredentialsRule();

            JavaFileParser parser = new JavaFileParser();
            CodeStructure codeStructure = parser.parseFile(filePath);

            MethodComplexityRule complexityRule = new MethodComplexityRule();
            List<Violation> complexityViolations=complexityRule.analyze(codeStructure);
            for (Violation violation : complexityViolations) {
                result.addViolation(violation);
            }

            List<Violation> stringViolations = stringRule.analyze(codeStructure);
            for (Violation violation : stringViolations) {
                result.addViolation(violation);
            }

            List<Violation> credentialViolations = credentialsRule.analyze(filePath);
            for (Violation violation : credentialViolations) {
                result.addViolation(violation);
            }

        } catch (Exception e) {
            System.err.println("Error analyzing file: " + filePath + " - " + e.getMessage());
        }
    }
}









