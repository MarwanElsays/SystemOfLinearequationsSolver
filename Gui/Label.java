package Gui;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Label {
    
    JLabel label;

    Label(String text) {
        label = new JLabel(text);
        label.setForeground(Color.black);
        label.setFont(new Font("Cascadia Mono", Font.PLAIN, 18));
    }
    
    public JLabel getLabel() {
        return label;
    }
}
