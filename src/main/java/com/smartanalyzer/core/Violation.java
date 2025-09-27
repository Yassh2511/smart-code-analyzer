package com.smartanalyzer.core;

public class Violation
{
    private String fileName;
    private int lineNumber;
    private String ruleName;
    private String message;
    private Severity severity;

    public Violation(String fileName,int lineNumber,String ruleName,String message,Severity severity)
    {
        this.fileName=fileName;
        this.lineNumber=lineNumber;
        this.ruleName=ruleName;
        this.message=message;
        this.severity=severity;
    }
    public String getFileName()
    {
        return fileName;
    }
    public int getLineNumber()
    {
        return lineNumber;
    }
    public String getRuleName()
    {
        return ruleName;
    }
    public String getMessage()
    {
        return message;
    }
    public Severity getSeverity()
    {
        return severity;
    }

    // Override toString method to print violation

    public String toString()
    {
        return severity+":"+fileName+":"+lineNumber+"-"+message+"["+ruleName+"]";
    }

}