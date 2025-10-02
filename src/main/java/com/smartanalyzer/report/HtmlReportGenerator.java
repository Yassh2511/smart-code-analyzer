package com.smartanalyzer.report;

import com.smartanalyzer.core.AnalysisResult;
import com.smartanalyzer.core.Violation;
import com.smartanalyzer.core.Severity;

import java.io.FileWriter;
import java.io.IOException;

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
        html.append("</head>\n");
        html.append("<body>\n");

        html.append("   <div class='header'>\n");
        html.append("       <h1>Smart Code Analyzer Report</h1>\n");
        html.append("        <p class='timestamp'>Generated:").append(new java.util.Date()).append("</p>\n");
        html.append("   </div>\n");

        //Summary
        html.append(generateSummarySection(result));

        //violations
        html.append(generateViiolationsByFile(result));

        // Recommedations
        html.append(generateRecommendations(result));

        html.append("</body>\n");
        html.append("</html>\n");

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(html.toString());
        }
        System.out.println("Html report generated:" + outputFile);
    }

    private char[] generateRecommendations(AnalysisResult result) {
    }

    private char[] generateViiolationsByFile(AnalysisResult result) {
    }

    private char[] generateSummarySection(AnalysisResult result)
    {
        StringBuilder html=new StringBuilder();

        int totalViolations=result.getViolations().size();
        int totalFiles=result.getTotalFiles();

        return html.toString();
    }

    private String getStyles() {

        return """
                    body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 20px;
                    background-color: #f5f5f5;
                }
                        .header {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    padding: 30px;
                    border-radius: 10px;
                    margin-bottom: 20px;
                    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                }
                        .header h1 {
                    margin: 0;
                    font-size: 2em;
                }
                        .timestamp {
                    margin: 10px 0 0 0;
                    opacity: 0.9;
                }
                        .summary {
                    background: white;
                    padding: 20px;
                    border-radius: 10px;
                    margin-bottom: 20px;
                    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                }
                        .stat-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                    gap: 15px;
                    margin-top: 15px;
                }
                        .stat-card {
                    padding: 15px;
                    border-radius: 8px;
                    text-align: center;
                }
                        .stat-card h3 {
                    margin: 0 0 10px 0;
                    font-size: 2em;
                }
                        .stat-card p {
                    margin: 0;
                    color: #666;
                }
                        .critical-card { background: #fee; border-left: 4px solid #f44336; }
                        .error-card { background: #fff3e0; border-left: 4px solid #ff9800; }
                        .warning-card { background: #fffde7; border-left: 4px solid #ffc107; }
                        .info-card { background: #e3f2fd; border-left: 4px solid #2196f3; }
                        .violations {
                    background: white;
                    padding: 20px;
                    border-radius: 10px;
                    margin-bottom: 20px;
                    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                }
                        .violation {
                    padding: 15px;
                    margin: 10px 0;
                    border-left: 4px solid #ccc;
                    background: #fafafa;
                    border-radius: 4px;
                }
                        .violation.critical { border-left-color: #f44336; }
                        .violation.error { border-left-color: #ff9800; }
                        .violation.warning { border-left-color: #ffc107; }
                        .violation.info { border-left-color: #2196f3; }
                        .violation-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 10px;
                }
                        .violation-title {
                    font-weight: bold;
                    font-size: 1.1em;
                }
                        .severity-badge {
                    padding: 4px 12px;
                    border-radius: 12px;
                    font-size: 0.85em;
                    font-weight: bold;
                    color: white;
                }
                        .severity-critical { background: #f44336; }
                        .severity-error { background: #ff9800; }
                        .severity-warning { background: #ffc107; color: #333; }
                        .severity-info { background: #2196f3; }
                        .violation-location {
                    color: #666;
                    font-size: 0.9em;
                    margin-bottom: 8px;
                }
                        .violation-message {
                    color: #333;
                    line-height: 1.5;
                }
                        .recommendations {
                    background: white;
                    padding: 20px;
                    border-radius: 10px;
                    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                }
                        .recommendation-item {
                    padding: 12px;
                    margin: 10px 0;
                    background: #f0f7ff;
                    border-left: 4px solid #2196f3;
                    border-radius: 4px;
                }
                        .no-issues {
                    text-align: center;
                    padding: 40px;
                    color: #4caf50;
                    font-size: 1.5em;
                }
                """ ;

    }

}
