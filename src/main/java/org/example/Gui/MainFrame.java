package org.example.Gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame implements ActionListener, MouseListener {

    JFrame frame = new JFrame();
    ImageIcon imageicon;
    JButton myButton = new JButton("Linear");
    JButton myButton1 = new JButton("Non-Linear");
    JButton myButton2 = new JButton("EXIT");

    MainFrame() {

        imageicon = new ImageIcon("war1.jpg");

        myButton.setBounds(300, 200, 210, 70);
        myButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        myButton.setForeground(Color.BLACK);
        myButton.setBackground(Color.orange);
        myButton.setFocusable(false);
        myButton.addActionListener(this);
        myButton.addMouseListener(this);

        myButton1.setBounds(300, 300, 210, 70);
        myButton1.setFont(new Font("Helvetica", Font.BOLD, 20));
        myButton1.setForeground(Color.BLACK);
        myButton1.setBackground(Color.orange);
        myButton1.setFocusable(false);
        myButton1.addActionListener(this);
        myButton1.addMouseListener(this);

        myButton2.setBounds(300, 400, 210, 70);
        myButton2.setFont(new Font("Helvetica", Font.BOLD, 20));
        myButton2.setForeground(Color.BLACK);
        myButton2.setBackground(Color.orange);
        myButton2.setFocusable(false);
        myButton2.addActionListener(this);
        myButton2.addMouseListener(this);

        JLabel label = new JLabel();
        label.setIcon(imageicon);
        label.setBounds(-300, 0, 1960, 1000);

        frame.add(myButton);
        frame.add(myButton1);
        frame.add(myButton2);
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300, 60, 800, 660);
        frame.setLayout(null);
        frame.setVisible(true);
        // frame.getContentPane().setBackground(Color.CYAN);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == myButton) {
            new LinearFrame();
        }
        if (e.getSource() == myButton1) {
            new nonLinearFrame();
        }
        if (e.getSource() == myButton2) {
            System.exit(1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == myButton) {
            myButton.setBackground(Color.blue);
            myButton.setForeground(Color.WHITE);
        }
        if (e.getSource() == myButton1) {
            myButton1.setBackground(Color.blue);
            myButton1.setForeground(Color.WHITE);
        }
        if (e.getSource() == myButton2) {
            myButton2.setBackground(Color.blue);
            myButton2.setForeground(Color.WHITE);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == myButton) {
            myButton.setBackground(Color.orange);
            myButton.setForeground(Color.BLACK);
        }
        if (e.getSource() == myButton1) {
            myButton1.setBackground(Color.orange);
            myButton1.setForeground(Color.BLACK);
        }
        if (e.getSource() == myButton2) {
            myButton2.setBackground(Color.orange);
            myButton2.setForeground(Color.BLACK);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
