package com.smartanalyzer;

import com.smartanalyzer.core.AnalysisResult;
import com.smartanalyzer.core.AnalysisEngine;
import com.smartanalyzer.core.Violation;
import com.smartanalyzer.core.Severity;

public class SmartCodeAnalyzer {

    private static final String VERSION = "1.0.0";

    public static void main(String[] args) {
        try {
            // Print welcome banner
            printBanner();

            // Step 1: Parse command line arguments
            String sourceDirectory = "D:/JAVA-PROJECTS/smart-code-analyzer/src/test";

            // Step 2: Initialize analysis engine
            System.out.println("🔧 Initializing analysis engine...");
            AnalysisEngine engine = new AnalysisEngine();

            // Step 3: Scan for Java files
            engine.initialize(sourceDirectory);

            // Step 4: Run complete analysis
            System.out.println("🚀 Starting code analysis...");
            AnalysisResult result = engine.runAnalysis(); // Note: AnalysisEngine.AnalysisResult

            // Step 5: Generate and display reports
            generateReports(result);

            // Step 6: Exit with appropriate code
            int exitCode = determineExitCode(result);
            System.out.println("\n✅ Analysis complete!");

            if (exitCode != 0) {
                System.out.println("⚠️  Issues found - Review the report above.");
            } else {
                System.out.println("🎉 No critical issues found!");
            }

        } catch (Exception e) {
            System.err.println("❌ Analysis failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printBanner() {
        System.out.println("🔍 Smart Code Analyzer v" + VERSION);
        System.out.println("═".repeat(60));
        System.out.println("📋 A Java-based static code analysis tool");
        System.out.println("🎯 Detects performance issues, security flaws & code smells");
        System.out.println("═".repeat(60));
        System.out.println();
    }

    private static String parseArguments(String[] args) {
        if (args.length == 0) {
            System.out.println("📁 No source directory specified, using default: src/test/java");
            return "src/test/java";
        }

        String sourceDir = args[0];
        System.out.println("📁 Source directory: " + sourceDir);
        return sourceDir;
    }

    private static void generateReports(AnalysisResult result) {
        System.out.println("\n📊 ANALYSIS RESULTS");
        System.out.println("═".repeat(50));

        printSummary(result);
        printViolations(result);
        printRecommendations(result);
    }

    private static void printSummary(AnalysisResult result) {
        int totalViolations = result.getViolations().size();
        int totalFiles = result.getTotalFiles();

        System.out.println("📈 SUMMARY:");
        System.out.println("   📄 Files analyzed: " + totalFiles);
        System.out.println("   ⚠️  Total issues found: " + totalViolations);

        // Count by severity
        int critical = 0, errors = 0, warnings = 0, info = 0;
        for (Violation violation : result.getViolations()) {
            switch (violation.getSeverity()) {
                case CRITICAL: critical++; break;
                case ERROR: errors++; break;
                case WARNING: warnings++; break;
                case INFO: info++; break;
            }
        }

        System.out.println("   🔴 Critical: " + critical);
        System.out.println("   🟠 Errors: " + errors);
        System.out.println("   🟡 Warnings: " + warnings);
        System.out.println("   ℹ️  Info: " + info);
        System.out.println();
    }

    private static void printViolations(AnalysisResult result) {
        if (result.getViolations().isEmpty()) {
            System.out.println("🎉 No issues found! Your code looks great!");
            return;
        }

        System.out.println("🔍 DETAILED ISSUES:");
        System.out.println("─".repeat(80));

        String currentFile = "";

        for (Violation violation : result.getViolations()) {
            if (!violation.getFileName().equals(currentFile)) {
                currentFile = violation.getFileName();
                System.out.println("\n📄 " + currentFile + ":");
                System.out.println("   " + "─".repeat(40));
            }

            String severityIcon = getSeverityIcon(violation.getSeverity());
            System.out.printf("   %s Line %d: %s%n",
                    severityIcon,
                    violation.getLineNumber(),
                    violation.getMessage());
            System.out.printf("      🏷️  Rule: %s%n", violation.getRuleName());
            System.out.println();
        }
    }

    private static void printRecommendations(AnalysisResult result) {
        if (result.getViolations().isEmpty()) {
            return;
        }

        System.out.println("💡 RECOMMENDATIONS:");
        System.out.println("─".repeat(50));

        long stringConcatIssues = result.getViolations().stream()
                .filter(v -> v.getRuleName().contains("StringConcatenation"))
                .count();

        if (stringConcatIssues > 0) {
            System.out.println("1. 🔧 Fix " + stringConcatIssues + " string concatenation issues:");
            System.out.println("   → Use StringBuilder in loops for better performance");
        }

        System.out.println("\n📚 Learn more about Java best practices:");
        System.out.println("   → Use StringBuilder for string operations in loops");
        System.out.println();
    }

    private static String getSeverityIcon(Severity severity) {
        switch (severity) {
            case CRITICAL: return "🔴";
            case ERROR: return "🟠";
            case WARNING: return "🟡";
            case INFO: return "ℹ️";
            default: return "⚪";
        }
    }

    private static int determineExitCode(AnalysisResult result) {
        for (Violation violation : result.getViolations()) {
            if (violation.getSeverity() == Severity.CRITICAL ||
                    violation.getSeverity() == Severity.ERROR) {
                return 1;
            }
        }
        return 0;
    }
}