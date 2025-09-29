package com.smartanalyzer.rules.performance;

import com.smartanalyzer.core.Issue;
import com.smartanalyzer.core.Severity;
import com.smartanalyzer.core.Violation;
import com.smartanalyzer.parser.CodeStructure;
import com.smartanalyzer.rules.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This class will detect concatenation in loops which can cause performance issue
public class StringConcatenationRule implements Rule
{
    private static final Pattern STRING_CONCAT_PATTERN=
            Pattern.compile("\\w+\\s*\\+=\\s*[\"'][^\"']*[\"']|\\w+\\s*\\+=\\s*\\w+");

    public List<Violation> analyze(CodeStructure codeStructure)
    {
        List<Violation> violation=new ArrayList<>();
        String[] lines=codeStructure.getLines();

        for(int i=0;i<lines.length;i++)
        {
            String line=lines[i].trim();
            Matcher matcher=STRING_CONCAT_PATTERN.matcher(line);

            if(matcher.find() && isInsideLoop(lines,i))
            {
                violation.add(new Violation(codeStructure.getFileName(),i+1,getRuleName(),"String concatenation in loops using += operator", Severity.WARNING));

            }
        }

        return violation;
    }

    private boolean isInsideLoop(String[] lines,int currentLine)
    {
        for(int i=(Math.max(0,currentLine-10));i<currentLine;i++)
        {
            String line=lines[i].trim();
            if(line.matches(".*\\b(for|while|do)\\s*\\(.*")) {
                return true;
            }
        }
        return false;
    }

    public String getRuleName()
    {
        return "StringConcatenationInLoop";
    }

    public String getDescription()
    {
        return "Detects string concatenation using += in loops";
    }

    public Issue.Severity getDefaultSeverity()
    {
        return Issue.Severity.MAJOR;
    }

    public Issue.Category getCategory()
    {
        return Issue.Category.PERFORMANCE;
    }
}

