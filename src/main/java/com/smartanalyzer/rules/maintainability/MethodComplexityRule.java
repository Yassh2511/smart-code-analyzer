package com.smartanalyzer.rules.maintainability;

public class MethodComplexityRule {

    import com.smartanalyzer.core.Violation;
import com.smartanalyzer.core.Severity;
import com.smartanalyzer.parser.CodeStructure;
import com.smartanalyzer.rules.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
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


                }
