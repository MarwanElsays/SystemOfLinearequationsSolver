package Gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
public class Button {

    JButton button;

    Button(int x, int y, int w, int h) {
        button = new JButton("Calculate");
        button.setFont(new Font("MV Boli", Font.BOLD, 15));
        button.setBackground(Color.ORANGE);
        button.setFocusable(false);
        button.setBounds(x, y, w, h);
    }

    public JButton getButton() {
        return button;
    }

    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
        button.addMouseListener(listener);
    }
}
