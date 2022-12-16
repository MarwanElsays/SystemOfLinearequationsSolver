package Gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;

public class OutputFrame extends JFrame{
    
    private double[] result;
    JTextArea OutputArea;
    JScrollPane outputPane;
    
    OutputFrame(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Eliminations");
        this.setSize(500, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        OutputArea = new JTextArea();
        OutputArea.setLineWrap(true);
        OutputArea.setWrapStyleWord(true);
        OutputArea.setFont(new Font("Arial",Font.PLAIN,16));
        OutputArea.setBackground(new Color(0x123456));
        OutputArea.setForeground(Color.green);
        
        outputPane = new JScrollPane(OutputArea);
        outputPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputPane.setBounds(50, 50, 400, 400);

        this.add(outputPane);
        this.setVisible(true);
    }

    public void setResult(double[] result){
        this.result = result;
    }

    public void setOutputAreaText(){
        String outputtoScreen = "Result:\n";
        for(int i=0;i<this.result.length;i++){
            outputtoScreen+="x"+(i+1)+"= "+this.result[i]+"\n";
        }
        OutputArea.setText(outputtoScreen);
        OutputArea.setEditable(false);
    }
    
}
