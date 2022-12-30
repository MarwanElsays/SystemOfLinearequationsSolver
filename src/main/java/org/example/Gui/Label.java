package org.example.Gui;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Label{
    
    JLabel label;

    Label(String text) {
        label = new JLabel(text);
        label.setForeground(Color.black);
        label.setFont(new Font("Cascadia Mono", Font.PLAIN, 20));
    }

    Label(String text,int x,int y,int w,int h){
        label = new JLabel(text);
        label.setForeground(Color.black);
        label.setFont(new Font("Cascadia Mono", Font.PLAIN, 20));
        label.setBounds(x, y, w, h);
    }
    
    public JLabel getLabel(){
        return label;
    }
}
