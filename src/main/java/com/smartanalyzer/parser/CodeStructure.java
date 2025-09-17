package com.smartanalyzer.parser;
import java.util.ArrayList;
import java.util.List;

public class CodeStructure
{
    private final String fileName;
    private final String content;
    private final String[] lines;
    private final List<String> imports;
    private final List<String> classes;
    private final List<String> methods;

    CodeStructure(String fileName,String content)
    {
        this.fileName = fileName;
        this.content = content;
        this.lines=content.split("\n");
        this.imports=new ArrayList<>();
        this.classes=new ArrayList<>();
        this.methods=new ArrayList<>();
    }
    public String getFileName() { return fileName; }
    public String getContent() { return content; }
    public String[] getLines() { return lines; }
    public List<String> getImports() { return imports; }
    public List<String> getClasses() { return classes; }
    public List<String> getMethods() { return methods; }

    void addImport(String importName)
    {
        imports.add(importName);
    }
    void addMethod(String methodName)
    {
        methods.add(methodName);
    }

    void addClass(String className)
    {
        classes.add(className);
    }

    public String getLineContent(int lineNumber)
    {
        if(lineNumber>0 && lineNumber<=lines.length)
        {
            return lines[lineNumber-1];
        }
        return "";
    }

    public int getLineCount() {
        return lines.length;
    }

    public String toString()
    {
        return String.format("Codestructure{fileName='%s',classes='%s',methods='%s',imports=%d}",fileName,classes,methods,imports.size());
    }

}