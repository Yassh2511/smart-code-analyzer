package com.smartanalyzer.rules.maintainability;

import com.smartanalyzer.core.Severity;
import com.smartanalyzer.core.Violation;
import com.smartanalyzer.rules.Rule;

import java.util.regex.Pattern;


public class MethodComplexityRule implements Rule {

        private static final int MAX_METHOD_LINES = 50;
        private static final int MAX_PARAMETERS = 5;
        private static final Pattern METHOD_PATTERN =
                Pattern.compile("(public|private|protected)\\s+(?:static\\s+)?\\w+\\s+(\\w+)\\s*\\(([^)]*)\\)");

        @Override
        public List<Violation> analyze(CodeStructure codeStructure) {
            List<Violation> violations = new ArrayList<>();
            String[] lines = codeStructure.getLines();

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim();
                Matcher matcher = METHOD_PATTERN.matcher(line);

                if (matcher.find()) {
                    String methodName = matcher.group(2);
                    String parameters = matcher.group(3);
                    int methodStartLine = i + 1;

                    // Check parameter count
                    int paramCount = countParameters(parameters);
                    if (paramCount > MAX_PARAMETERS) {
                        violations.add(new Violation(
                                codeStructure.getFileName(),
                                methodStartLine,
                                getRuleName(),
                                "Method '" + methodName + "' has " + paramCount + " parameters (max: " + MAX_PARAMETERS + ")",
                                Severity.WARNING
                        ));
                    }
                    int methodLength = calculateMethodLength(lines, i);
                    if (methodLength > MAX_METHOD_LINES) {
                        violations.add(new Violation(
                                codeStructure.getFileName(),
                                methodStartLine,
                                getRuleName(),
                                "Method '" + methodName + "' is " + methodLength + " lines long (max: " + MAX_METHOD_LINES + ")",
                                Severity.WARNING
                        ));
                    }
                }
            }

            return violations;
        }

        private int countParameters(String parameters) {
            if (parameters.trim().isEmpty()) {
                return 0;
            }

            // Remove whitespace and count commas
            String cleaned = parameters.trim();
            if (cleaned.isEmpty()) {
                return 0;
            }

            // Count commas + 1 for total parameters
            int count = 1;
            for (char c : cleaned.toCharArray()) {
                if (c == ',') {
                    count++;
                }
            }
            return count;
        }

        private int calculateMethodLength(String[] lines, int startIndex) {
            int braceCount = 0;
            int length = 0;
            boolean foundOpenBrace = false;

            for (int i = startIndex; i < lines.length; i++) {
                String line = lines[i].trim();
                length++;

                for (char c : line.toCharArray()) {
                    if (c == '{') {
                        braceCount++;
                        foundOpenBrace = true;
                    } else if (c == '}') {
                        braceCount--;
                    }
                }

                // Method ends when braces are balanced
                if (foundOpenBrace && braceCount == 0) {
                    return length;
                }

                // Safety limit
                if (length > 200) {
                    return length;
                }
            }

            return length;
        }

        @Override
        public String getRuleName() {
            return "Method Complexity";
        }

        @Override
        public String getDescription() {
            return "Detects methods that are too long or have too many parameters";
        }

        @Override
        public Severity getDefaultSeverity() {
            return Severity.WARNING;
        }

        @Override
        public com.smartanalyzer.core.Issue.Category getCategory() {
            return com.smartanalyzer.core.Issue.Category.MAINTAINABILITY;
        }
    }


