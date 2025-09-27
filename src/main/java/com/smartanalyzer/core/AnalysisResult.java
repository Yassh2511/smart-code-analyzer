package com.smartanalyzer.core;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult
{
    private List<Violation> violations;
    private int totalFiles;
    private int totalLines;
    public AnalysisResult()
    {
        this.violations=new ArrayList<>();
        this.totalFiles=0;
        this.totalLines=0;
    }

    public void addViolation(Violation violation)
    {
        violations.add(violation);
    }

    //getter and setter to access total result
    public List<Violation> getViolations()
    {
        return violations;
    }
    public int getTotalFiles()
    {
        return totalFiles;
    }
    public void setTotalFiles(int totalFiles)
    {
        this.totalFiles=totalFiles;
    }
    public int getTotalLines()
    {
        return totalLines;
    }
    public void setTotalLines(int totalFiles)
    {
        this.totalFiles=totalLines;
    }

}