//package com.smartanalyzer.core;
//
//import java.io.File;
//import java.util.List;
//import java.util.ArrayList;
//
//// This can be called as core analysis for the program that is enitre
//// code analysis process will be done here
//
//public class AnalysisEngine {
//
//    // SourceFiles and config are used to store the list of paths of java files
//    // which we want to analyze and cofig will contain some settings
//    private List<String> sourceFiles=null;
//    private AnalysisConfig config=null;
//
//    public AnalysisEngine()
//    {
//
//        this.sourceFiles=new ArrayList<>(); //Empty list
//        this.config=new AnalysisConfig();   // Default config
//
//    }
//    // Initialization
//    // Find files to analyze
//    // Recursively explore all subdirectories and collect path
//
//    public void initialize(String sourceDirectory)
//    {
//        System.out.println("\uD83D\uDCC1 Scanning directory:"+sourceDirectory);
//
//        // scanDirectory will locate the particular directory
//        // by sending parameter as a File Object of that directory
//
//        scanDirectory(new File(sourceDirectory));
//
//        System.out.println("\uD83D\uDCCAFound"+sourceFiles.size()+"jave Files");
//    }
//
//    private void scanDirectory(File directory)
//
//    {
//        // If directory isnt exit it will simply return no any operations
//        // or findings will be done
//        if(!directory.exists() ||!directory.isDirectory())
//        {
//            return;
//        }
//        // if directory present it will list all files present in
//        // directoory
//        // if its again directory recursive call made for the scanDirectory function
//        // but if not it will find the file having extension as .java and return the absoulte
//        // path of that particular file
//        File[] files=directory.listFiles();
//        if(files!=null)
//        {
//            for(File file:files)
//            {
//                if(file.isDirectory())
//                {
//                    scanDirectory(file);
//                }
//                else if(file.getName().endsWith(".java"))
//                {
//                    sourceFiles.add(file.getAbsolutePath());
//                }
//            }
//        }
//    }
//
//    // Run complete analysis now we have check the path of the files
//    // that we want to analyze now complete Analysis of code will be run
//
//    public AnalysisResult runAnalysis()
//    {
//        AnalysisResult result=new AnalysisResult();
//        System.out.println("Analyzing Code quality...");
//        for(String filePath:sourceFiles)
//        {
//            System.out.println("Analyzing:"+filePath);
//            analyzeFile(filePath,result);
//        }
//
//        return result;
//    }
//
//    // In analyzeFile we will handle each file individually
//    // Open the java file
//    //Parse java file
//    //apply analysis rules
//    // like string concatenation in loops,hardcoded passwords,unused variables
//    //Then find violations and add it into result
//    private void analyzeFile(String filePath,AnalysisResult result)
//    {
//        // Implement file analysis logic here
//    }
//
//}
//
//// Analysis Configuration
//// This config will tell about which rules we should apply by keeping them
//// False or true
//public class AnalysisConfig
//{
//    private boolean enablePerformanceRules=true;  // Check for slow code
//    private boolean enableSecurityRules=true;   // Check for security issues
//    private boolean enableMaintainabiltyRules=true;   // Check for hard to maintain code
//
//    public boolean isEnablePerformanceRules()
//    {
//        return enablePerformanceRules;
//    }
//    public void setEnablePerformanceRules(boolean enable)
//    {
//        this.enablePerformanceRules=enable;
//    }
//    public boolean isEnableSecurityRules()
//    {
//        return enableSecurityRules;
//    }
//    public void setEnableSecurityRules(boolean enable)
//    {
//        this.enableSecurityRules=enable;
//    }
//    public boolean isEnableMaintainabilityRules()
//    {
//        return enableMaintainabiltyRules;
//    }
//    public void isEnableMaintainabilityRules(boolean enable)
//    {
//        this.enableMaintainabiltyRules=enable;
//    }
//}
//
//class AnalysisResult
//{
//    private List<Violation> violations;
//    private int totalFiles;
//    private int totalLines;
//    public AnalysisResult()
//    {
//        this.violations=new ArrayList<>();
//        this.totalFiles=0;
//        this.totalLines=0;
//    }
//
//    public void addViolation(Violation violation)
//    {
//        violations.add(violation);
//    }
//
//    //getter and setter to access total result
//    public List<Violation> getViolations()
//    {
//        return violations;
//    }
//    public int getTotalFiles()
//    {
//        return totalFiles;
//    }
//    public void setTotalFiles(int totalFiles)
//    {
//        this.totalFiles=totalFiles;
//    }
//    public int getTotalLines()
//    {
//        return totalLines;
//    }
//    public void setTotalLines(int totalFiles)
//    {
//        this.totalFiles=totalLines;
//    }
//
//}
//
//class Violation
//{
//    private String fileName;
//    private int lineNumber;
//    private String ruleName;
//    private String message;
//    private Severity severity;
//
//    public Violation(String fileName,int lineNumber,String ruleName,String message,Severity severity)
//    {
//        this.fileName=fileName;
//        this.lineNumber=lineNumber;
//        this.ruleName=ruleName;
//        this.message=message;
//        this.severity=severity;
//    }
//    public String getFileName()
//    {
//        return fileName;
//    }
//    public int getLineNumber()
//    {
//        return lineNumber;
//    }
//    public String getRuleName()
//    {
//        return ruleName;
//    }
//    public String getMessage()
//    {
//        return message;
//    }
//    public Severity getSeverity()
//    {
//        return severity;
//    }
//
//    // Override toString method to print violation
//
//    public String toString()
//    {
//        return severity+":"+fileName+":"+lineNumber+"-"+message+"["+ruleName+"]";
//    }
//
//}
//
//enum Severity
//{
//    INFO,
//    WARNING,
//    ERROR,
//    CRITICAL
//}
//




package com.smartanalyzer.core;

import com.smartanalyzer.parser.JavaFileParser;
import com.smartanalyzer.parser.CodeStructure;
import com.smartanalyzer.rules.performance.StringConcatenationRule;
import com.smartanalyzer.core.Issue;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class AnalysisEngine {

    private List<String> sourceFiles = null;
    private AnalysisConfig config = null;

    public AnalysisEngine() {
        this.sourceFiles = new ArrayList<>();
        this.config = new AnalysisConfig();
    }

    public void initialize(String sourceDirectory) {
        System.out.println("📁 Scanning directory:" + sourceDirectory);
        scanDirectory(new File(sourceDirectory));
        System.out.println("📊 Found " + sourceFiles.size() + " Java Files");
    }

    private void scanDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectory(file);
                } else if (file.getName().endsWith(".java")) {
                    sourceFiles.add(file.getAbsolutePath());
                }
            }
        }
    }

    public AnalysisResult runAnalysis() {
        AnalysisResult result = new AnalysisResult();
        result.setTotalFiles(sourceFiles.size());

        System.out.println("Analyzing Code quality...");
        for (String filePath : sourceFiles) {
            System.out.println("Analyzing:" + filePath);
            analyzeFile(filePath, result);
        }

        return result;
    }

    private void analyzeFile(String filePath, AnalysisResult result) {
        try {
            System.out.println("📝 Parsing: " + filePath);

            // Parse the Java file
            JavaFileParser parser = new JavaFileParser();
            CodeStructure codeStructure = parser.parseFile(filePath);

            System.out.println("🔍 Found: " + codeStructure.getClasses().size() + " classes, " +
                    codeStructure.getMethods().size() + " methods");

            // Apply rules based on configuration
            if (config.isEnablePerformanceRules()) {
                applyPerformanceRules(codeStructure, result);
            }

        } catch (Exception e) {
            System.err.println("❌ Error analyzing " + filePath + ": " + e.getMessage());
        }
    }

    private void applyPerformanceRules(CodeStructure codeStructure, AnalysisResult result) {
        // Create and apply performance rules
        StringConcatenationRule rule = new StringConcatenationRule();
        List<Issue> issues = rule.analyze(codeStructure);

        for (Issue issue : issues) {
            // Convert Issue to Violation
            Violation violation = new Violation(
                    issue.getFileName(),
                    issue.getLineNumber(),
                    issue.getRuleName(),
                    issue.getDescription(),
                    convertSeverity(issue.getSeverity())
            );
            result.addViolation(violation);
        }
    }

    private Severity convertSeverity(Issue.Severity issueSeverity) {
        switch (issueSeverity) {
            case CRITICAL: return Severity.CRITICAL;
            case MAJOR: return Severity.ERROR;
            case MINOR: return Severity.WARNING;
            case INFO: return Severity.INFO;
            default: return Severity.INFO;
        }
    }

    // NESTED CLASSES - Updated with proper methods
    public static class AnalysisResult {
        private List<Violation> violations;
        private int totalFiles;
        private int totalLines;

        public AnalysisResult() {
            this.violations = new ArrayList<>();
            this.totalFiles = 0;
            this.totalLines = 0;
        }

        public void addViolation(Violation violation) {
            violations.add(violation);
        }

        // Getters and setters
        public List<Violation> getViolations() {
            return violations;
        }

        public int getTotalFiles() {
            return totalFiles;
        }

        public void setTotalFiles(int totalFiles) {
            this.totalFiles = totalFiles;
        }

        public int getTotalLines() {
            return totalLines;
        }

        public void setTotalLines(int totalLines) {
            this.totalLines = totalLines;
        }
    }

    public static class Violation {
        private String fileName;
        private int lineNumber;
        private String ruleName;
        private String message;
        private Severity severity;

        public Violation(String fileName, int lineNumber, String ruleName, String message, Severity severity) {
            this.fileName = fileName;
            this.lineNumber = lineNumber;
            this.ruleName = ruleName;
            this.message = message;
            this.severity = severity;
        }

        // Getters
        public String getFileName() {
            return fileName;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getRuleName() {
            return ruleName;
        }

        public String getMessage() {
            return message;
        }

        public Severity getSeverity() {
            return severity;
        }

        @Override
        public String toString() {
            return severity + ":" + fileName + ":" + lineNumber + "-" + message + "[" + ruleName + "]";
        }
    }

    public enum Severity {
        INFO,
        WARNING,
        ERROR,
        CRITICAL
    }

    public static class AnalysisConfig {
        private boolean enablePerformanceRules = true;
        private boolean enableSecurityRules = true;
        private boolean enableMaintainabilityRules = true;

        public boolean isEnablePerformanceRules() {
            return enablePerformanceRules;
        }

        public void setEnablePerformanceRules(boolean enable) {
            this.enablePerformanceRules = enable;
        }

        public boolean isEnableSecurityRules() {
            return enableSecurityRules;
        }

        public void setEnableSecurityRules(boolean enable) {
            this.enableSecurityRules = enable;
        }

        public boolean isEnableMaintainabilityRules() {
            return enableMaintainabilityRules;
        }

        public void setEnableMaintainabilityRules(boolean enable) {
            this.enableMaintainabilityRules = enable;
        }
    }
}
