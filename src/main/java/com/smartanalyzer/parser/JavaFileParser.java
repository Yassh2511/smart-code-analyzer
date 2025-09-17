package com.smartanalyzer.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// It Parses the java source files and extracts code structure
public class JavaFileParser
{
    // Parse a java file and return its code Structure

    public CodeStructure parseFile(String filePath)throws IOException
    {
        String content = Files.readString(Paths.get(filePath));
        String fileName = Paths.get(filePath).getFileName().toString();

        CodeStructure structure = new CodeStructure(fileName, content);

        //Extracting different elements from the code likes imports classes and methods

        extractImports(content, structure);
        extractClasses(content, structure);
        extractMethods(content, structure);

        return structure;
    }

    // Extract import statements

    private void extractImports(String content,CodeStructure structure)
    {
        Pattern importPattern=Pattern.compile("^import\\s+([^;]+)",Pattern.MULTILINE);
        //First for finding we will us redis
        // in object importPattern we define the pattern type that is here it is
        // like ^import\\s+  that means find pattern having start of line as import
        // and s+ means after at least one white space we will search for the group which is written in ()
        // that group is like from the start of line till we get ; it should be atleast one
        // pattern,multiline say that line by line we are checking
        Matcher matcher=importPattern.matcher(content);

        // now this pattern have method call as matcher so we provide our content
        // and it will give matcher object which store all the imports in groups

        while(matcher.find())
        {
            structure.addImport(matcher.group(1).trim());
            // gorup(1) beacuae group(0) will give entire pattern but we only want group next to import
        }
    }
    private void extractMethods(String content,CodeStructure structure)
    {
        Pattern methodPattern=Pattern.compile(
                "(public|private|protected)?\\s*(static)?\\s*([\\w<>\\[\\]]+)\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{"
        );
        Matcher matcher=methodPattern.matcher(content);
        while(matcher.find())
        {
            String methodSignature=matcher.group(4);
            structure.addMethod(methodSignature);
        }
    }
    private void extractClasses(String content,CodeStructure structure)
    {
        Pattern classPattern=Pattern.compile(
                "(public|private|protected)?\\\\s*(abstract|final)?\\\\s*class\\\\s+(\\\\w+)"
        );
        Matcher matcher=classPattern.matcher(content);

        while(matcher.find())
        {
            structure.addClass(matcher.group(3));
            // group 3 because its the class name that we want
        }
    }

    public static int getLineNumber(String content,int position)
    {
        int lineNumber=1;
        for(int i=0;i<position &&i<content.length();i++)
        {
            if(content.charAt(i)=='\n')lineNumber++;
        }
        return lineNumber;
    }

}



