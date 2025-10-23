package com.example.calculator;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0].toLowerCase() : "gui";
        if (mode.equals("cli")) {
            runCLI();
        } else {
            javax.swing.SwingUtilities.invokeLater(() -> new CalculatorGUI().setVisible(true));
        }
    }

    private static void runCLI() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Simple CLI Calculator. Type 'exit' to quit.");
        while (true) {
            try {
                System.out.print("Enter expression (e.g. 2 + 3): ");
                String line = sc.nextLine();
                if (line == null) break;
                line = line.trim();
                if (line.equalsIgnoreCase("exit")) break;
                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    System.out.println("Invalid input. Use format: number operator number");
                    continue;
                }
                BigDecimal a = new BigDecimal(parts[0]);
                String op = parts[1];
                BigDecimal b = new BigDecimal(parts[2]);
                BigDecimal res;
                switch (op) {
                    case "+": res = Calculator.add(a,b); break;
                    case "-": res = Calculator.subtract(a,b); break;
                    case "*": res = Calculator.multiply(a,b); break;
                    case "/": res = Calculator.divide(a,b); break;
                    default:
                        System.out.println("Unknown operator: " + op);
                        continue;
                }
                System.out.println("= " + res.stripTrailingZeros().toPlainString());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
        System.out.println("Goodbye");
    }
}
