package Gui;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Label {
    
    JLabel label;

    Label(String text) {
        label = new JLabel(text);
        label.setForeground(Color.BLUE);
        label.setBackground(new Color(0xDDDDDD));
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setOpaque(true);
    }
    
    public JLabel getLabel() {
        return label;
    }
}
