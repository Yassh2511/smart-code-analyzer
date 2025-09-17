package com.smartanalyzer.rules;

public interface Rule
{
    /*Analyze the code structure and return list of issues found
    * working or analyze method*/
    List<Issue> analzye(CodeStructure codeStructure);
}
