package com.smartanalyzer;

// Smart code Analyzer - This is the Main entry point for the project
// That is this is the class where we are writing main function
public class SmartCodeAnalyzer
{
    private static final String VERSION="1.0.0";

    public static void main(String[] args) {
        System.out.println("\uD83D\uDD0DSmart code Anazlyzer v"+VERSION);
        System.out.println("=".repeat(50));
        System.out.println();

        System.out.println("Starting code analysis...");
        // Here main execution will be done where operations that to perform are
        // 1.Parse Command line arguments
        // 2.Initialize analysis engine
        //3.Run Analysis
        //4.Generate Reports

        System.out.println("✅Analysis Complete");
        System.out.println("This will start anaylsis");
    }
}

package com.smartanalyzer.rules.performance;

import com.smartanalyzer.core.Issue;
import com.smartanalyzer.parser.CodeStructure;
import com.smartanalyzer.rules.Rule;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConcatenationRule implements Rule {

    private static final Pattern STRING_CONCAT_PATTERN =
            Pattern.compile("\\w+\\s*\\+=\\s*[\"'][^\"']*[\"']|\\w+\\s*\\+=\\s*\\w+");

    @Override
    public List<Issue> analyze(CodeStructure codeStructure) {
        List<Issue> issues = new ArrayList<>();
        String[] lines = codeStructure.getLines();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            Matcher matcher = STRING_CONCAT_PATTERN.matcher(line);

            if (matcher.find() && isInsideLoop(lines, i)) {  // ← This calls the method below
                issues.add(new Issue(
                        codeStructure.getFileName(),
                        i + 1,
                        getRuleName(),
                        getDefaultSeverity(),
                        getCategory(),
                        "String concatenation in loop using '+=' operator",
                        "Use StringBuilder for better performance: StringBuilder sb = new StringBuilder();"
                ));
            }
        }

        return issues;
    }

    /**
     * ✅ ADD THIS METHOD - Checks if current line is inside a loop
     */
    private boolean isInsideLoop(String[] lines, int currentLine) {
        int braceCount = 0;

        // Look backwards from current line to find loop constructs
        for (int i = currentLine; i >= 0; i--) {
            String line = lines[i].trim();

            // Count braces to track code blocks/scope
            for (char c : line.toCharArray()) {
                if (c == '{') braceCount--;  // Opening brace
                if (c == '}') braceCount++;  // Closing brace
            }

            // If we've gone outside the current scope, stop looking
            if (braceCount > 0) {
                break;
            }

            // Check for loop keywords (for, while, do-while)
            if (line.matches(".*\\b(for|while|do)\\s*\\(.*")) {
                return true;  // Found a loop!
            }
        }

        return false;  // No loop found
    }

    @Override
    public String getRuleName() {
        return "StringConcatenationInLoop";
    }

    @Override
    public String getDescription() {
        return "Detects string concatenation using += in loops which can cause performance issues";
    }

    @Override
    public Issue.Severity getDefaultSeverity() {
        return Issue.Severity.MAJOR;
    }

    @Override
    public Issue.Category getCategory() {
        return Issue.Category.PERFORMANCE;
    }
}
