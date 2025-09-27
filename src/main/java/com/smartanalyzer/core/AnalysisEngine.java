package com.smartanalyzer.core;


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

        return result;
    }

    // In analyzeFile we will handle each file individually
    // Open the java file
    //Parse java file
    //apply analysis rules
    // like string concatenation in loops,hardcoded passwords,unused variables
    //Then find violations and add it into result
    private void analyzeFile(String filePath,AnalysisResult result)
    {
        try {
            // Read the file and apply basic string concatenation rule
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int lineNumber = 0;
            boolean inLoop = false;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();

                // Detect loop start
                if (trimmedLine.contains("for (") || trimmedLine.contains("while (")) {
                    inLoop = true;
                }

                // Detect loop end
                if (trimmedLine.equals("}")) {
                    inLoop = false;
                }

                // Check for string concatenation in loop
                if (inLoop && trimmedLine.contains("+=") && trimmedLine.contains("\"")) {
                    Violation violation = new Violation(
                            filePath,
                            lineNumber,
                            "String Concatenation",
                            "String concatenation in loop detected. Use StringBuilder for better performance",
                            Severity.WARNING
                    );
                    result.addViolation(violation);
                }
            }
            reader.close();

        } catch (Exception e) {
            System.err.println("Error analyzing file: " + filePath + " - " + e.getMessage());
        }
    }

}








