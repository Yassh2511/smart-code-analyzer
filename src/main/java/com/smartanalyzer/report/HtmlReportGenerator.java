package com.smartanalyzer.report;

import com.smartanalyzer.core.AnalysisResult;
import com.smartanalyzer.core.Violation;
import com.smartanalyzer.core.Severity;

import java.io.IOException;

public class HtmlReportGenerator
{
    public void generateReport(AnalysisResult result,String outputFile)throws IOException{

        StringBuilder html=new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html lang='en'>\n");
        html.append("<head>\n");
        html.append("  <meta charset='UTF-8'>\n");
        html.append("  <meta name='viewport' content='width=device-width,initial-scale=1.0'>\n");
        html.append("  <title>Smart Code Analyzer Report</title>\n");
        html.append("  <style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
    }
}
