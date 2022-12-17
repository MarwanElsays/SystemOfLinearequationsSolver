package Gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TextField {
    JTextField field;
    
    TextField(boolean editable) {
        field = new JTextField();
        field.setPreferredSize(new Dimension(100, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 20));
        field.setForeground(new Color(0x000000));
        field.setBackground(new Color(0xFFFFFF));
        field.setCaretColor(Color.black);
        field.setEditable(editable);
        field.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public JTextField getField() {
        return field;
    }
}
