package org.example.Gui;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;

public class Panel{

    private JPanel panel;
    Panel[][] subPanels;

    Panel(int x, int y, int w, int h, boolean visible) {
        panel = new JPanel();
        panel.setBounds(x, y, w, h);
        panel.setBackground(new Color(0xDDDDDD));
        panel.setLayout(new FlowLayout());
        panel.setVisible(visible);
    }

    Panel(int w, int h, boolean visible) {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(w, h));
        panel.setBackground(new Color(0xDDDDDD));
        panel.setLayout(new FlowLayout());
        panel.setVisible(visible);
        panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
    }

    public void setlayoutNull(){
        panel.setLayout(null);
    }

    public void AddComponents(Component[] components) {
        for (Component component : components) {
            panel.add(component);
        }
    }

    public void setVisible(boolean visible) {
        panel.setVisible(visible);
    }

    public JPanel getPanel() {
        return panel;
    }
}
