package academy.javapro;

/* Project: Recursive Descent Parser
 * Class: ExpressionParser.java
 * Author: MEHMET SOYDAN
 * Date: FEBRUARY 24 2024
 * This program implements a recursive descent parser to evaluate arithmetic expressions
 * with support for addition, multiplication, parentheses, and decimal numbers.
 */
class ExpressionParser {
    private final String input; // Input expression
    private int position;       // Current position in the input

    // ---------------------------------------------------------------
    // Constructor to initialize the parser with the input string
    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    // ---------------------------------------------------------------
    // Parse and evaluate addition operations
    public double parseExpression() {
        double result = parseTerm(); // Get the leftmost term
        while (position < input.length() && input.charAt(position) == '+') {
            position++; // Skip the '+' operator
            result += parseTerm(); // Add the next term
        }
        return result;
    }

    // ---------------------------------------------------------------
    // Parse and evaluate multiplication operations
    private double parseTerm() {
        double result = parseFactor(); // Get the leftmost factor
        while (position < input.length() && input.charAt(position) == '*') {
            position++; // Skip the '*' operator
            result *= parseFactor(); // Multiply by the next factor
        }
        return result;
    }

    // ---------------------------------------------------------------
    // Parse parentheses and numbers
    private double parseFactor() {
        if (position < input.length() && input.charAt(position) == '(') {
            position++; // Skip the opening parenthesis
            double result = parseExpression(); // Parse the expression inside
            if (position >= input.length() || input.charAt(position) != ')') {
                throw new RuntimeException("Mismatched parentheses at position " + position);
            }
            position++; // Skip the closing parenthesis
            return result;
        } else {
            return parseNumber(); // Parse a number if no parentheses
        }
    }

    // ---------------------------------------------------------------
    // Parse a numeric value
    private double parseNumber() {
        StringBuilder numberBuilder = new StringBuilder();
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            numberBuilder.append(input.charAt(position)); // Append digits or decimal point
            position++; // Move to the next character
        }
        if (numberBuilder.length() == 0) {
            throw new RuntimeException("Invalid number at position " + position);
        }
        return Double.parseDouble(numberBuilder.toString()); // Convert to double
    }

    // ---------------------------------------------------------------
    // Main method to test the parser
    public static void main(String[] args) {
        // Test cases
        String[] testCases = {
                "2 + 3 * (4 + 5)",    // Mismatched parentheses (error case)
                "2 + 3 * (4 + 5)",    // Complex expression with parentheses
                "2 + 3 * 4",          // Basic arithmetic with precedence
                "(2 + 3) * 4",        // Parentheses changing precedence
                "2 * (3 + 4) * (5 + 6)", // Multiple parentheses
                "1.5 + 2.5 * 3"       // Decimal numbers
        };

        // Process each test case
        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", "")); // Remove spaces
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
