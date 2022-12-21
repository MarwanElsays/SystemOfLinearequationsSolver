package Gui;

import javax.swing.JComboBox;
import java.awt.Dimension; 
import java.awt.event.ActionListener;

public class ComboBox {
    
    JComboBox<String> comboBox;

    ComboBox(String[] methods) {
        comboBox = new JComboBox<String>(methods);
        comboBox.setPreferredSize(new Dimension(140, 40));
        comboBox.setSelectedItem(null);
    }

    ComboBox(String[] methods,int x,int y,int w,int h) {
        comboBox = new JComboBox<String>(methods);
        comboBox.setPreferredSize(new Dimension(140, 40));
        comboBox.setSelectedItem(null);
        comboBox.setBounds(x, y, w, h);
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public String getItem() {
        return comboBox.getSelectedItem().toString();
    }

    public void addActionListener(ActionListener listener) {
        comboBox.addActionListener(listener);
    }
}
