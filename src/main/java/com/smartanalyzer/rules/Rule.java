package com.smartanalyzer.rules;

import com.smartanalyzer.core.Issue;
import com.smartanalyzer.parser.CodeStructure;
import java.util.List;

// Interface for all anaylysis rules Every rule must implement this interface
public interface Rule
{
    /*Analyze the code structure and return list of issues found
    * working or analyze method*/
    List<Issue> analyze(CodeStructure codeStructure);

    // Get the unique name of this rule
    String getRuleName();

    // Get human readable description of what this rule really checking

    String getDescription();

    // Get defualtSeverity issues found by thie role

    Issue.Severity getDefaultSeverity();

    // get category belong to the issue

    Issue.Category getCategory();

    // check if rule is enabled

    default boolean isEnabled()
    {
        return true;
    }
}
