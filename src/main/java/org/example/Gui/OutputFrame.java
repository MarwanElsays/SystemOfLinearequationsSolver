package org.example.Gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;

public class OutputFrame extends JFrame {

    JTextArea OutputArea;
    JScrollPane outputPane;

    OutputFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Eliminations");
        this.setSize(700, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        OutputArea = new JTextArea();
        OutputArea.setLineWrap(true);
        OutputArea.setWrapStyleWord(true);
        OutputArea.setFont(new Font("Arial", Font.PLAIN, 16));
        OutputArea.setBackground(new Color(0x123456));
        OutputArea.setForeground(Color.green);

        outputPane = new JScrollPane(OutputArea);
        outputPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputPane.setBounds(50, 50, 600, 600);

        this.add(outputPane);
        OutputArea.setText("");
        this.setVisible(true);
    }

    public void setOutput(double[] result) {
        String output = "Result:\n";
        for (int i = 0; i < result.length; i++) {
            output += "x" + (i + 1) + " = " + result[i] + "\n";
        }
        OutputArea.append(output);
        OutputArea.setEditable(false);
    }

    public void printSteps(String s) {
        OutputArea.append(s);
        OutputArea.setEditable(false);
    }

    public void setText(String x){
        OutputArea.setText(x);
        OutputArea.setEditable(false);
    }
}

