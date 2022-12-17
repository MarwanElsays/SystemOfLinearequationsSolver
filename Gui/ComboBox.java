package Gui;

import javax.swing.JComboBox;
import java.awt.Dimension; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBox {
    
    JComboBox<String> comboBox;

    ComboBox(String[] methods) {
        comboBox = new JComboBox<String>(methods);
        comboBox.setPreferredSize(new Dimension(110, 30));
        //comboBox.addActionListener(this);
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public static ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    };

    // @Override
    // public void actionPerformed(ActionEvent e) {

    //     if (e.getSource() == methodComboBox) {
    //         method = methodComboBox.getSelectedItem().toString();

    //         if (method.equals("LU Decomposition"))
    //             LUPanel.setVisible(true);
    //         else
    //             LUPanel.setVisible(false);

    //         if (method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration"))
    //             IterativePanel.setVisible(true);
    //         else
    //             IterativePanel.setVisible(false);
    //     }

    //     if (e.getSource() == LUComboBox) {
    //         LUmethod = LUComboBox.getSelectedItem().toString();
    //     }
    // }
}
