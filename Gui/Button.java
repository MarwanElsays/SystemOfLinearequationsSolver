package Gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button {

    JButton button;
    
    ActionListener buttonAction = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }

    };

    Button(int x, int y, int w, int h) {
        button = new JButton("Calculate");
        button.setFont(new Font("MV Boli", Font.BOLD, 15));
        button.setBackground(Color.white);
        button.setFocusable(false);
        button.setBounds(x, y, w, h);
    }

    public JButton getButton() {
        return button;
    }

}
