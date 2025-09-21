public class TestBadCode {

    public void badStringConcatenation() {
        String result = "";

        // This should be detected by StringConcatenationRule
        for (int i = 0; i < 100; i++) {
            result += "Item " + i + "\n";  // BAD: String concatenation in loop
        }

        System.out.println(result);
    }

    public void anotherBadLoop() {
        String output = "";
        for (String item : new String[]{"a", "b", "c"}) {
            output += item + ",";  // Another violation
        }
    }

    public void goodExample() {
        // This should NOT trigger the rule
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("Item ").append(i).append("\n");
        }
        String result = sb.toString();
    }
}