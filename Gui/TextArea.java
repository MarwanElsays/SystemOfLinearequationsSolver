package Gui;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;

public class TextArea {

    JTextArea area;

    TextArea() {
        area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 20));
        area.setBackground(new Color(0xFFFFFF));
        area.setForeground(Color.black);
        area.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public JTextArea getArea() {
        return area;
    }
}
