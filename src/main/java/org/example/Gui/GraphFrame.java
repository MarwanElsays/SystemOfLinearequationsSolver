package org.example.Gui;

import java.awt.Color;
import javax.swing.JFrame;

public class GraphFrame extends JFrame {

    GraphFrame(String[] functions) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Graphs");
        this.setSize(600, 600);
        // this.getContentPane().setBackground(new Color(0xDDDDDD));
        this.setLocationRelativeTo(null);
        this.add(new GraphDrawer(functions, 600, 600));
        this.setVisible(true);
    }
}
