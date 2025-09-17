package com.smartanalyzer.core;
 // Represents code quality issue during analysis

public class Issue
{
    public enum Severity
    {
        CRITICAL("🔴", "Critical", 1),
        MAJOR("\uD83D\uDFE1","Major",2),
        MINOR("🟢", "Minor", 3),
        INFO("ℹ️", "Info", 4)
        ;

        private final String icon;
        private final String displayName;
        private final int priority;
        Severity(String icon,String displayName,int priority)
        {
            this.icon=icon;
            this.displayName=displayName;
            this.priority=priority;
        }

        public String getIcon(){return icon;}
        public String getDisplayName(){return displayName;}
        public int getPriority(){return priority;}

    }

    // Categories of issues

    public enum Category
    {
        PERFORMANCE("⚡", "Performance"),
        SECURITY("🔒", "Security"),
        MAINTAINABILITY("🔧", "Maintainability"),
        BUG_RISK("🐛", "Bug Risk"),
        CODE_SMELL("💨", "Code Smell");

        private final String icon;
        private final String displayName;

        Category(String icon,String displayName)
        {
            this.icon=icon;
            this.displayName=displayName;
        }

        public String getIcon(){return icon;}
        public String getDisplayName(){return displayName;}
    }

    private final String fileName;
    private final int lineNumber;
    private final String ruleName;
    private final Category category;
    private final Severity severity;
    private final String description;
    private final String suggestion;

    public Issue(String fileName, int lineNumber, String ruleName,
                 Severity severity, Category category,
                 String description, String suggestion) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.ruleName = ruleName;
        this.severity = severity;
        this.category = category;
        this.description = description;
        this.suggestion = suggestion;
    }

    // Getters
    public String getFileName() { return fileName; }
    public int getLineNumber() { return lineNumber; }
    public String getRuleName() { return ruleName; }
    public Severity getSeverity() { return severity; }
    public Category getCategory() { return category; }
    public String getDescription() { return description; }
    public String getSuggestion() { return suggestion; }

    @Override
    public String toString() {
        return String.format("%s %s:%d [%s] %s",
                severity.getIcon(), fileName, lineNumber,
                severity.getDisplayName(), description);
    }
}
