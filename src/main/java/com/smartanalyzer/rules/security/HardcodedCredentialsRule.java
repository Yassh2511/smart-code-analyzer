package com.smartanalyzer.rules.security;

import com.smartanalyzer.core.Violation;
import com.smartanalyzer.core.Severity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;


public class HardcodedCredentialsRule {

    public List<Violation> analyze(String filePath) {
        List<Violation> violations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                checkLine(line, lineNumber, filePath, violations);
            }
        } catch (IOException e) {
            System.out.println("Error reading file:" + filePath);
            ;
        }

        return violations;
    }
    private void checkLine(String line,int lineNumber,String filePath,List<Violation> violations)
    {
        String lowerLine=line.toLowerCase().trim();
        if(lowerLine.startsWith("//") || lowerLine.startsWith("*")||lowerLine.startsWith("import")||lowerLine.isEmpty())
        {
            return;
        }

        String[] sensitiveKeywords={"password","passwd","pwd","secret","key","token","api_key","apikey","auth","credential"};

        if(line.contains("=") && line.contains("\""))
        {
            for(String keyWord:sensitiveKeywords)
            {
                if(lowerLine.contains(keyWord)&&!lowerLine.contains("system.getproperty")&&!lowerLine.contains("getenv"))
                {
                    violations.add(new Violation(
                            filePath,lineNumber,"HardCoded Credentials","Potential hardcoded sensitive information"+keyWord, Severity.CRITICAL
                    ));
                    return;
                }
            }
        }
    }


}
