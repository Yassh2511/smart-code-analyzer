package com.smartanalyzer.report;

import com.smartanalyzer.core.AnalysisResult;
import com.smartanalyzer.core.Violation;
import com.smartanalyzer.core.Severity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlReportGenerator {
    public void generateReport(AnalysisResult result, String outputFile) throws IOException {

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html lang='en'>\n");
        html.append("<head>\n");
        html.append("  <meta charset='UTF-8'>\n");
        html.append("  <meta name='viewport' content='width=device-width,initial-scale=1.0'>\n");
        html.append("  <title>Smart Code Analyzer Report</title>\n");
        html.append("  <style>\n");
        html.append(getStyles());
        html.append("  </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");

        html.append("   <div class='header'>\n");
        html.append("       <h1>Smart Code Analyzer Report</h1>\n");
        html.append("        <p class='timestamp'>Generated:").append(new java.util.Date()).append("</p>\n");
        html.append("   </div>\n");

        //Summary
        html.append(generateSummarySection(result));

        //violations
        html.append(generateViolationsByFile(result));

        // Recommedations
        html.append(generateRecommendations(result));

        html.append("</body>\n");
        html.append("</html>\n");

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(html.toString());
        }
        System.out.println("Html report generated:" + outputFile);
    }

    private String generateRecommendations(AnalysisResult result)
    {
        StringBuilder html=new StringBuilder();

        if(result.getViolations().isEmpty())
        {
            return "";
        }

        html.append("   <div class='recommendations'>\n");
        html.append("      <h2>Recommendations</h2>\n");

        long stringIssue=result.getViolations().stream().filter(v->v.getRuleName().toLowerCase().contains("string"))
                .count();

        long credentialIssues=result.getViolations().stream().filter(v -> v.getRuleName().toLowerCase().contains("hardcoded"))
                .count();

        if(stringIssue>0)
        {
            html.append("        <div class='recommendation-item'>\n");
            html.append("            <strong>String Concatenation Issues (").append(stringIssue).append(")</strong><br>\n");
            html.append("            Use StringBuilder for string operations in loops to improve performance.\n");
            html.append("        </div>\n");
        }

        if (credentialIssues > 0) {
            html.append("        <div class='recommendation-item'>\n");
            html.append("            <strong>Hardcoded Credentials (").append(credentialIssues).append(")</strong><br>\n");
            html.append("            Move sensitive data to environment variables or secure configuration files.\n");
            html.append("        </div>\n");
        }

        html.append("    </div>\n");

        return html.toString();
    }

    private String generateViolationsByFile(AnalysisResult result)
    {
        StringBuilder html = new StringBuilder();

        html.append("    <div class='violations'>\n");
        html.append("        <h2>Detected Issues</h2>\n");

        if (result.getViolations().isEmpty()) {
            html.append("        <div class='no-issues'>No issues found! Your code looks great!</div>\n");
        } else {
            for (Violation v : result.getViolations()) {
                String severityClass = v.getSeverity().toString().toLowerCase();

                html.append("        <div class='violation ").append(severityClass).append("'>\n");
                html.append("            <div class='violation-header'>\n");
                html.append("                <span class='violation-title'>").append(escapeHtml(v.getRuleName())).append("</span>\n");
                html.append("                <span class='severity-badge severity-").append(severityClass).append("'>");
                html.append(v.getSeverity()).append("</span>\n");
                html.append("            </div>\n");
                html.append("            <div class='violation-location'>");
                html.append(escapeHtml(getFileName(v.getFileName()))).append(" - Line ").append(v.getLineNumber());
                html.append("</div>\n");
                html.append("            <div class='violation-message'>").append(escapeHtml(v.getMessage())).append("</div>\n");
                html.append("        </div>\n");
            }
        }

        html.append("    </div>\n");

        return html.toString();
    }

    private String getFileName(String fullPath) {
        int lastSlash = Math.max(fullPath.lastIndexOf('/'), fullPath.lastIndexOf('\\'));
        return lastSlash >= 0 ? fullPath.substring(lastSlash + 1) : fullPath;
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }


    private String generateSummarySection(AnalysisResult result)
    {
        StringBuilder html=new StringBuilder();

        int totalViolations=result.getViolations().size();
        int totalFiles=result.getTotalFiles();

        Map<Severity, Integer> counts = new HashMap<>();
        counts.put(Severity.CRITICAL, 0);
        counts.put(Severity.ERROR, 0);
        counts.put(Severity.WARNING, 0);
        counts.put(Severity.INFO, 0);

        for (Violation v : result.getViolations()) {
            counts.put(v.getSeverity(), counts.get(v.getSeverity()) + 1);
        }

        html.append("    <div class='summary'>\n");
        html.append("        <h2>Summary</h2>\n");
        html.append("        <p>Files Analyzed: <strong>").append(totalFiles).append("</strong></p>\n");
        html.append("        <p>Total Issues: <strong>").append(totalViolations).append("</strong></p>\n");
        html.append("        <div class='stat-grid'>\n");
        html.append("            <div class='stat-card critical-card'>\n");
        html.append("                <h3>").append(counts.get(Severity.CRITICAL)).append("</h3>\n");
        html.append("                <p>Critical</p>\n");
        html.append("            </div>\n");
        html.append("            <div class='stat-card error-card'>\n");
        html.append("                <h3>").append(counts.get(Severity.ERROR)).append("</h3>\n");
        html.append("                <p>Errors</p>\n");
        html.append("            </div>\n");
        html.append("            <div class='stat-card warning-card'>\n");
        html.append("                <h3>").append(counts.get(Severity.WARNING)).append("</h3>\n");
        html.append("                <p>Warnings</p>\n");
        html.append("            </div>\n");
        html.append("            <div class='stat-card info-card'>\n");
        html.append("                <h3>").append(counts.get(Severity.INFO)).append("</h3>\n");
        html.append("                <p>Info</p>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");

        return html.toString();
    }

    private String getStyles() {

        return "body {\n" +
                "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "    margin: 0;\n" +
                "    padding: 20px;\n" +
                "    background-color: #f5f5f5;\n" +
                "}\n" +
                ".header {\n" +
                "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "    color: white;\n" +
                "    padding: 30px;\n" +
                "    border-radius: 10px;\n" +
                "    margin-bottom: 20px;\n" +
                "    box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n" +
                "}\n" +
                ".header h1 {\n" +
                "    margin: 0;\n" +
                "    font-size: 2em;\n" +
                "}\n" +
                ".timestamp {\n" +
                "    margin: 10px 0 0 0;\n" +
                "    opacity: 0.9;\n" +
                "}\n" +
                ".summary {\n" +
                "    background: white;\n" +
                "    padding: 20px;\n" +
                "    border-radius: 10px;\n" +
                "    margin-bottom: 20px;\n" +
                "    box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n" +
                "}\n" +
                ".stat-grid {\n" +
                "    display: grid;\n" +
                "    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));\n" +
                "    gap: 15px;\n" +
                "    margin-top: 15px;\n" +
                "}\n" +
                ".stat-card {\n" +
                "    padding: 15px;\n" +
                "    border-radius: 8px;\n" +
                "    text-align: center;\n" +
                "}\n" +
                ".stat-card h3 {\n" +
                "    margin: 0 0 10px 0;\n" +
                "    font-size: 2em;\n" +
                "}\n" +
                ".stat-card p {\n" +
                "    margin: 0;\n" +
                "    color: #666;\n" +
                "}\n" +
                ".critical-card { background: #fee; border-left: 4px solid #f44336; }\n" +
                ".error-card { background: #fff3e0; border-left: 4px solid #ff9800; }\n" +
                ".warning-card { background: #fffde7; border-left: 4px solid #ffc107; }\n" +
                ".info-card { background: #e3f2fd; border-left: 4px solid #2196f3; }\n" +
                ".violations {\n" +
                "    background: white;\n" +
                "    padding: 20px;\n" +
                "    border-radius: 10px;\n" +
                "    margin-bottom: 20px;\n" +
                "    box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n" +
                "}\n" +
                ".violation {\n" +
                "    padding: 15px;\n" +
                "    margin: 10px 0;\n" +
                "    border-left: 4px solid #ccc;\n" +
                "    background: #fafafa;\n" +
                "    border-radius: 4px;\n" +
                "}\n" +
                ".violation.critical { border-left-color: #f44336; }\n" +
                ".violation.error { border-left-color: #ff9800; }\n" +
                ".violation.warning { border-left-color: #ffc107; }\n" +
                ".violation.info { border-left-color: #2196f3; }\n" +
                ".violation-header {\n" +
                "    display: flex;\n" +
                "    justify-content: space-between;\n" +
                "    align-items: center;\n" +
                "    margin-bottom: 10px;\n" +
                "}\n" +
                ".violation-title {\n" +
                "    font-weight: bold;\n" +
                "    font-size: 1.1em;\n" +
                "}\n" +
                ".severity-badge {\n" +
                "    padding: 4px 12px;\n" +
                "    border-radius: 12px;\n" +
                "    font-size: 0.85em;\n" +
                "    font-weight: bold;\n" +
                "    color: white;\n" +
                "}\n" +
                ".severity-critical { background: #f44336; }\n" +
                ".severity-error { background: #ff9800; }\n" +
                ".severity-warning { background: #ffc107; color: #333; }\n" +
                ".severity-info { background: #2196f3; }\n" +
                ".violation-location {\n" +
                "    color: #666;\n" +
                "    font-size: 0.9em;\n" +
                "    margin-bottom: 8px;\n" +
                "}\n" +
                ".violation-message {\n" +
                "    color: #333;\n" +
                "    line-height: 1.5;\n" +
                "}\n" +
                ".recommendations {\n" +
                "    background: white;\n" +
                "    padding: 20px;\n" +
                "    border-radius: 10px;\n" +
                "    box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n" +
                "}\n" +
                ".recommendation-item {\n" +
                "    padding: 12px;\n" +
                "    margin: 10px 0;\n" +
                "    background: #f0f7ff;\n" +
                "    border-left: 4px solid #2196f3;\n" +
                "    border-radius: 4px;\n" +
                "}\n" +
                ".no-issues {\n" +
                "    text-align: center;\n" +
                "    padding: 40px;\n" +
                "    color: #4caf50;\n" +
                "    font-size: 1.5em;\n" +
                "}\n";

    }

}
