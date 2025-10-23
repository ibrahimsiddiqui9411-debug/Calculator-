package com.example.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class CalculatorGUI extends JFrame {
    private final JTextField display = new JTextField();
    private StringBuilder current = new StringBuilder();

    public CalculatorGUI() {
        setTitle("Java Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5,5));

        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(5,4,5,5));
        String[] labels = {
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0",".","=","+",
                "C","","",""
        };
        for (String s : labels) {
            if (s.isEmpty()) { buttons.add(new JLabel()); continue; }
            JButton b = new JButton(s);
            b.setFont(new Font("SansSerif", Font.BOLD, 18));
            b.addActionListener(this::onButton);
            buttons.add(b);
        }

        add(buttons, BorderLayout.CENTER);
    }

    private void onButton(ActionEvent e) {
        String cmd = ((JButton)e.getSource()).getText();
        switch (cmd) {
            case "C": current.setLength(0); display.setText(""); break;
            case "=": evaluate(); break;
            default: current.append(cmd); display.setText(current.toString()); break;
        }
    }

    private void evaluate() {
        try {
            String s = current.toString();
            char op = 0; int idx = -1;
            for (int i=0;i<s.length();i++) {
                char c = s.charAt(i);
                if ((c=='+'||c=='-'||c=='*'||c=='/') && i!=0) { op=c; idx=i; break; }
            }
            if (idx==-1) return;
            BigDecimal a = new BigDecimal(s.substring(0, idx));
            BigDecimal b = new BigDecimal(s.substring(idx+1));
            BigDecimal res;
            switch (op) {
                case '+': res = Calculator.add(a,b); break;
                case '-': res = Calculator.subtract(a,b); break;
                case '*': res = Calculator.multiply(a,b); break;
                case '/': res = Calculator.divide(a,b); break;
                default: return;
            }
            String out = res.stripTrailingZeros().toPlainString();
            display.setText(out);
            current.setLength(0);
            current.append(out);
        } catch (Exception ex) {
            display.setText("Error");
            current.setLength(0);
        }
    }
}
