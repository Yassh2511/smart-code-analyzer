public class TestBadCode {

    // SECURITY VIOLATIONS - These should be detected by HardcodedCredentialsRule
    private static final String password = "admin123";
    private static final String API_KEY = "sk-1234567890abcdef";
    private String dbPassword = "mySecretPassword";
    private String authToken = "bearer_token_12345";

    // PERFORMANCE VIOLATION - String concatenation in loop
    public void badStringConcatenation() {
        String result = "";

        // This should trigger StringConcatenationRule
        for (int i = 0; i < 100; i++) {
            result += "Item " + i + "\n";
        }

        System.out.println(result);
    }

    // MULTIPLE VIOLATIONS - Both security and performance
    public void multipleIssues() {
        String apiKey = "secret-api-key-xyz";  // Security violation

        String output = "";
        for (int j = 0; j < 50; j++) {
            output += "Data: " + j;  // Performance violation
        }
    }

    // DATABASE CONNECTION - Security violation
    public void connectToDatabase() {
        String username = "admin";
        String pwd = "database_password_123";  // Should be detected
        String connectionString = "jdbc:mysql://localhost/db?password=mysqlpass";  // Should be detected
    }

    // NESTED LOOPS - Multiple performance violations
    public void nestedLoopsConcatenation() {
        String data = "";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                data += i + "," + j + ";";  // Violation in nested loop
            }
        }
    }

    // WHILE LOOP - Performance violation
    public void whileLoopConcatenation() {
        String content = "";
        int counter = 0;
        ounter + "\n";  // Should be detected
        counter++;
    }
        while (counter < 20) {
            content += "Line " + c
    }

    // GOOD EXAMPLES - These should NOT trigger violations
    public void goodPractices() {
        // Good: Using environment variable
        String apiKey = System.getenv("API_KEY");

        // Good: Using StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("Item ").append(i).append("\n");
        }
        String result = sb.toString();

        // Good: Reading from config
        String password = System.getProperty("db.password");
    }

    public void veryLongMethod() {
        System.out.println("Line 1");
        System.out.println("Line 2");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");

        System.out.println("Line 3");

        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");

        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");

        System.out.println("Line 3");
        System.out.println("Line 3");
        System.out.println("Line 3");
        // ... add 50+ lines to trigger the rule
        for (int i = 0; i < 20; i++) {
            System.out.println("Line " + i);
            System.out.println("More code");
            System.out.println("Even more code");
        }
    }
}