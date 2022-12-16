package Gui;

import Fucntions.GaussElimination;
import Fucntions.GaussJordan;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener {

    JComboBox<String> methodComboBox;
    JTextArea EquationsArea;
    JScrollPane EqautionsPane;
    JButton calcButton;
    JPanel containerPanel,methodPanel,precisionPanel,timePanel;
    
    MainFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Eliminations");
        this.setSize(700, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        containerPanel = new JPanel();
        containerPanel.setBounds(10, 10, 680, 100);
        containerPanel.setBackground(new Color(0xF5F5DC));
        containerPanel.setLayout(new FlowLayout());

        String Methods[] = {"Gauss","jordan"};
        methodComboBox = new JComboBox<String>(Methods);
        methodComboBox.setPreferredSize(new Dimension(100,30));;
        methodComboBox.addActionListener(this);

        JLabel choosemethodLabel = new JLabel();
        choosemethodLabel.setText("Choose Method");
        choosemethodLabel.setForeground(Color.BLUE);
        choosemethodLabel.setBackground(Color.white);
        choosemethodLabel.setOpaque(true);
        methodPanel = new JPanel();
        methodPanel.setPreferredSize(new Dimension(200,80));
        methodPanel.setBackground(new Color(0x00FF00));
        methodPanel.add(choosemethodLabel);
        methodPanel.add(methodComboBox);
        methodPanel.setLayout(new FlowLayout());
        

        precisionPanel = new JPanel();
        precisionPanel.setPreferredSize(new Dimension(200,80));
        precisionPanel.setBackground(new Color(0xFF0000));
        precisionPanel.setLayout(new FlowLayout());


        timePanel = new JPanel();
        timePanel.setPreferredSize(new Dimension(200,80));
        timePanel.setBackground(new Color(0x0000FF));
        timePanel.setLayout(new FlowLayout());

        containerPanel.add(methodPanel);
        containerPanel.add(precisionPanel);
        containerPanel.add(timePanel);




        EquationsArea = new JTextArea();
        EquationsArea.setLineWrap(true);
        EquationsArea.setWrapStyleWord(true);
        EquationsArea.setFont(new Font("Arial",Font.PLAIN,16));
        EquationsArea.setBackground(new Color(0x123456));
        EquationsArea.setForeground(Color.white);
        
        EqautionsPane = new JScrollPane(EquationsArea);
        EqautionsPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        EqautionsPane.setBounds(300, 250, 360, 350);

        JPanel InputFormatPanel = new JPanel();
        InputFormatPanel.setBounds(30, 350, 250, 250);
        InputFormatPanel.setBackground(new Color(0xF5F5DC));

        JLabel inFormatLabel = new JLabel();
        inFormatLabel.setText("Equations must be in this form");
        inFormatLabel.setFont(new Font("MV Boli",Font.BOLD,15));
        inFormatLabel.setForeground(Color.BLUE);
        inFormatLabel.setBackground(Color.white);
        inFormatLabel.setOpaque(true);
        InputFormatPanel.add(inFormatLabel);

        JLabel directinglabel = new JLabel();
        directinglabel.setText("Input the Equations:");
        directinglabel.setHorizontalAlignment(JLabel.CENTER);
        directinglabel.setFont(new Font("MV Boli",Font.BOLD,20));
        directinglabel.setForeground(Color.red);
        directinglabel.setBackground(Color.white);
        directinglabel.setOpaque(true);
        directinglabel.setBounds(30, 250, 250, 80);

        calcButton = new JButton();
        calcButton.setText("Calculate");
        calcButton.setFont(new Font("MV Boli",Font.BOLD,15));
        calcButton.setBackground(Color.ORANGE);
        calcButton.setFocusable(false);
        calcButton.setBounds(300, 600, 150, 50);
        calcButton.addActionListener(this);

        this.add(containerPanel);
        this.add(directinglabel);
        this.add(InputFormatPanel);
        this.add(EqautionsPane);
        this.add(calcButton);
        this.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        
        String method = "Gauss";
        if(e.getSource() == calcButton){
            
            String[] Equations = EquationsArea.getText().split("\\n");
            double A[][] = new double[Equations.length][Equations.length];
            double B[] = new double[Equations.length];
            int i=0;
            for (String equation : Equations){  
                A[i] = cofficientsExtractor(equation,B,i);
                i++;
            }

            switch(method){
                case "Gauss": 
                    GaussElimination g = new GaussElimination(A, B, 5);
                    System.out.println(Arrays.toString(g.getResult())); 
                    new OutputFrame();
                    break;
                case "Jordan":
                    GaussJordan gj = new GaussJordan(A, B, 5);
                    System.out.println(Arrays.toString(gj.getResult())); 
                    break;
            }
        }

        if(e.getSource() == methodComboBox){
            method = methodComboBox.getSelectedItem().toString(); 
        }
        
    }    


    public double[] cofficientsExtractor (String equation,double B[],int index){
        Vector<Double> Cofficents = new Vector<Double>();
        Vector<String> subEquations = new Vector<String>();
        String subEquation = "";

        int i=0;
        while(equation.charAt(i) != '=')
        {
            if((equation.charAt(i) == '-' || equation.charAt(i) == '+') && i!=0 ){
                subEquations.add(subEquation);
                subEquation = "";
            }

            if(equation.charAt(i) != '+')subEquation+=equation.charAt(i);
            i++;
        }

        subEquations.add(subEquation);
        double b = Double.parseDouble(equation.substring(i+1, equation.length()));
        B[index] = b;

        for(String eq : subEquations){
            double coff =  0;
            String subeq = eq.substring(0,eq.indexOf('x'));

            if(subeq.equals(""))coff = 1;
            else if(subeq.equals("-"))coff = -1;
            else coff = Double.parseDouble(eq.substring(0,eq.indexOf('x')));
            
            Cofficents.add(coff);             
        }

        double[] x= new double[Cofficents.size()]; 
        for(int j=0;j<Cofficents.size();j++){
            x[j] = Cofficents.elementAt(j);
        }

        return x;
    }
}
