package Gui;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Label{
    
    JLabel label;

    Label(String text) {
        label = new JLabel(text);
        label.setForeground(Color.BLUE);
        label.setBackground(new Color(0xDDDDDD));
        label.setFont(new Font("Calibri", Font.PLAIN, 20));
        label.setOpaque(true);
    }

    Label(String text,int x,int y,int w,int h){
        label = new JLabel(text);
        label.setForeground(Color.BLUE);
        label.setBackground(new Color(0xDDDDDD));
        label.setFont(new Font("Calibri", Font.PLAIN, 20));
        label.setOpaque(true);
        label.setBounds(x, y, w, h);
    }
    
    public JLabel getLabel(){
        return label;
    }
}
