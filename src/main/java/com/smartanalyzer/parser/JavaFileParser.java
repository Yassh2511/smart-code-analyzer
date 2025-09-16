package com.smartanalyzer.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private void extractClasses(String content,CodeStructure structure){}
    private void extractMethods(String content,CodeStructure structure){}

}
class CodeStructure
{
    CodeStructure(String fileName,String content){}
    void addImport(String importName){}
}
