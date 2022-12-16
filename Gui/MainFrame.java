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
import javax.swing.JTextField;

import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener {

    JComboBox<String> methodComboBox,LUComboBox;
    JTextArea EquationsArea;
    JButton calcButton;
    JTextField presicionTxtField,TimeField,initialguessField,noOfIterationsField,absRelativeErrorField;
    JPanel LUPanel,IterativePanel;
    String method = "Gauss";
    String LUmethod = "Downlittle Form";
    
    MainFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Eliminations");
        this.setSize(700, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(0x1177CC)); 

        JPanel containerPanel = new JPanel();
        containerPanel.setBounds(10, 10, 665, 100);
        containerPanel.setBackground(new Color(0xF5F5DC));
        containerPanel.setLayout(new FlowLayout());

        String Methods[] = {"Gauss","jordan","LU Decomposition","Gauss-Seidel","Jacobi-Iteration"};
        methodComboBox = new JComboBox<String>(Methods);
        methodComboBox.setPreferredSize(new Dimension(110,30));;
        methodComboBox.addActionListener(this);

        /*Choose Method */
        JLabel choosemethodLabel = new JLabel();
        choosemethodLabel.setText("Choose Method");
        choosemethodLabel.setForeground(Color.BLUE);
        choosemethodLabel.setBackground(Color.white);
        choosemethodLabel.setOpaque(true);
        JPanel methodPanel = new JPanel();
        methodPanel.setPreferredSize(new Dimension(210,80));
        methodPanel.setBackground(new Color(0x080D95));
        methodPanel.add(choosemethodLabel);
        methodPanel.add(methodComboBox);
        methodPanel.setLayout(new FlowLayout());

       /*****************************************************/
        
       /*Precision Part */

        JLabel precisionLabel = new JLabel();
        precisionLabel.setText("Precision");
        precisionLabel.setForeground(Color.BLUE);
        precisionLabel.setBackground(Color.white);
        precisionLabel.setOpaque(true);
        presicionTxtField = new JTextField();
        presicionTxtField.setPreferredSize(new Dimension(100,40));
		presicionTxtField.setFont(new Font("MV Boli",Font.PLAIN,20));
		presicionTxtField.setForeground(new Color(0x00FF00));
		presicionTxtField.setBackground(Color.white);
		presicionTxtField.setCaretColor(Color.black);
		presicionTxtField.setText("");
        JPanel precisionPanel = new JPanel();
        precisionPanel.setPreferredSize(new Dimension(200,80));
        precisionPanel.setBackground(new Color(0x080D95));
        precisionPanel.setLayout(new FlowLayout());
        precisionPanel.add(precisionLabel);
        precisionPanel.add(presicionTxtField);

        /****************************************** */

        /*time Part */

        JLabel timeLabel = new JLabel();
        timeLabel.setText("Time");
        timeLabel.setForeground(Color.BLUE);
        timeLabel.setBackground(Color.white);
        timeLabel.setOpaque(true);
        TimeField = new JTextField();
        TimeField.setPreferredSize(new Dimension(100,40));
		TimeField.setFont(new Font("MV Boli",Font.PLAIN,20));
		TimeField.setForeground(new Color(0x00FF00));
		TimeField.setBackground(Color.white);
		TimeField.setCaretColor(Color.black);
		TimeField.setText("");
        TimeField.setEditable(false);
        JPanel timePanel = new JPanel();
        timePanel.setPreferredSize(new Dimension(200,80));
        timePanel.setBackground(new Color(0x080D95));
        timePanel.setLayout(new FlowLayout());
        timePanel.add(timeLabel);
        timePanel.add(TimeField);
        /*************************************************** */

        containerPanel.add(methodPanel);
        containerPanel.add(precisionPanel);
        containerPanel.add(timePanel);


        /*****Lu Panel*************** */
        LUPanel = new JPanel();
        LUPanel.setBounds(10, 120, 665, 100);
        LUPanel.setBackground(new Color(0xF5F5DC));
        LUPanel.setLayout(new FlowLayout());

        String LUMethods[] = {"Downlittle Form","Crout Form", "Cholesky Form"};
        LUComboBox = new JComboBox<String>(LUMethods);
        LUComboBox.setPreferredSize(new Dimension(110,30));;
        LUComboBox.addActionListener(this);
        LUPanel.add(LUComboBox);
        LUPanel.setVisible(false);



        /**************Iterative panel*********************/
        IterativePanel = new JPanel();
        IterativePanel.setBounds(10, 120, 660, 100);
        IterativePanel.setBackground(new Color(0xF5F5DC));
        IterativePanel.setLayout(new FlowLayout());

        JPanel initialguessPanel = new JPanel();
        initialguessPanel.setPreferredSize(new Dimension(210,80));
        initialguessPanel.setBackground(new Color(0x080D95));
        initialguessPanel.setLayout(new FlowLayout());

        JLabel initialguessLabel = new JLabel();
        initialguessLabel.setText("initial Guess");
        initialguessLabel.setForeground(Color.BLUE);
        initialguessLabel.setBackground(Color.white);
        initialguessLabel.setOpaque(true);
        initialguessLabel.setPreferredSize(new Dimension(100,40));
        initialguessField = new JTextField();
        initialguessField.setPreferredSize(new Dimension(100,40));
		initialguessField.setFont(new Font("MV Boli",Font.PLAIN,16));
		initialguessField.setForeground(new Color(0x00FF00));
		initialguessField.setBackground(Color.white);
		initialguessField.setCaretColor(Color.black);
		initialguessField.setText("");

        initialguessPanel.add(initialguessLabel);
        initialguessPanel.add(initialguessField);

        JPanel noOfIterationsPanel = new JPanel();
        noOfIterationsPanel.setPreferredSize(new Dimension(210,80));
        noOfIterationsPanel.setBackground(new Color(0x080D95));
        noOfIterationsPanel.setLayout(new FlowLayout());

        JLabel noOfIterationsLabel = new JLabel();
        noOfIterationsLabel.setText("No. Of Iterations");
        noOfIterationsLabel.setForeground(Color.BLUE);
        noOfIterationsLabel.setBackground(Color.white);
        noOfIterationsLabel.setOpaque(true);
        noOfIterationsLabel.setPreferredSize(new Dimension(100,40));
        noOfIterationsField = new JTextField();
        noOfIterationsField.setPreferredSize(new Dimension(100,40));
		noOfIterationsField.setFont(new Font("MV Boli",Font.PLAIN,16));
		noOfIterationsField.setForeground(new Color(0x00FF00));
		noOfIterationsField.setBackground(Color.white);
		noOfIterationsField.setCaretColor(Color.black);
		noOfIterationsField.setText("");

        noOfIterationsPanel.add(noOfIterationsLabel);
        noOfIterationsPanel.add(noOfIterationsField);

        
        JPanel absRelativeErrorPanel = new JPanel();
        absRelativeErrorPanel.setPreferredSize(new Dimension(210,80));
        absRelativeErrorPanel.setBackground(new Color(0x080D95));
        absRelativeErrorPanel.setLayout(new FlowLayout());

        JLabel absRelativeErrorLabel = new JLabel();
        absRelativeErrorLabel.setText("Relative Error");
        absRelativeErrorLabel.setForeground(Color.BLUE);
        absRelativeErrorLabel.setBackground(Color.white);
        absRelativeErrorLabel.setOpaque(true);
        absRelativeErrorLabel.setPreferredSize(new Dimension(100,40));
        absRelativeErrorField = new JTextField();
        absRelativeErrorField.setPreferredSize(new Dimension(100,40));
		absRelativeErrorField.setFont(new Font("MV Boli",Font.PLAIN,16));
		absRelativeErrorField.setForeground(new Color(0x00FF00));
		absRelativeErrorField.setBackground(Color.white);
		absRelativeErrorField.setCaretColor(Color.black);
		absRelativeErrorField.setText("");

        absRelativeErrorPanel.add(absRelativeErrorLabel);
        absRelativeErrorPanel.add(absRelativeErrorField);

        IterativePanel.add(initialguessPanel);
        IterativePanel.add(noOfIterationsPanel);
        IterativePanel.add(absRelativeErrorPanel);
        IterativePanel.setVisible(false);

        /************************************************************/


        EquationsArea = new JTextArea();
        EquationsArea.setLineWrap(true);
        EquationsArea.setWrapStyleWord(true);
        EquationsArea.setFont(new Font("Arial",Font.PLAIN,16));
        EquationsArea.setBackground(new Color(0x123456));
        EquationsArea.setForeground(Color.white);
        
        JScrollPane EqautionsPane = new JScrollPane(EquationsArea);
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

        this.add(IterativePanel);
        this.add(LUPanel);
        this.add(containerPanel);
        this.add(directinglabel);
        this.add(InputFormatPanel);
        this.add(EqautionsPane);
        this.add(calcButton);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
                
        if(e.getSource() == calcButton){
            
            String[] Equations = EquationsArea.getText().split("\\n");
            double A[][] = new double[Equations.length][Equations.length];
            double B[] = new double[Equations.length];
            int i=0;
            for (String equation : Equations){  
                A[i] = cofficientsExtractor(equation,B,i);
                i++;
            }

            double[] intialguess = new double[Equations.length] ;
            int noOfIterations;
            double AbsRealtiveError;
            if(method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration")){
                String[] intialGuessesArray = initialguessField.getText().split(",");
                for(int k=0;k<intialGuessesArray.length;k++){
                    intialguess[k] = Double.parseDouble(intialGuessesArray[k]);
                }
                noOfIterations = Integer.parseInt(noOfIterationsField.getText());
                AbsRealtiveError = Double.parseDouble(absRelativeErrorField.getText());
            }

            switch(method){
                case "Gauss": 
                    GaussElimination g = new GaussElimination(A, B,Integer.parseInt(presicionTxtField.getText()));
                    System.out.println(Arrays.toString(g.getResult())); 
                    OutputFrame outputframe = new OutputFrame();
                    outputframe.setResult(g.getResult());
                    outputframe.setOutputAreaText();
                    TimeField.setText(String.valueOf(g.getTime()));
                    break;
                case "Jordan":
                    GaussJordan gj = new GaussJordan(A, B, Integer.parseInt(presicionTxtField.getText()));
                    System.out.println(Arrays.toString(gj.getResult())); 
                    OutputFrame outputframe1 = new OutputFrame();
                    outputframe1.setResult(gj.getResult());
                    outputframe1.setOutputAreaText();
                    TimeField.setText(String.valueOf(gj.getTime()));
                    break;
                case "LU Decomposition":
                    switch(LUmethod){
                        case "Downlittle Form":
                            OutputFrame outputframe2 = new OutputFrame();
                            break;

                        case "Crout Form":
                            OutputFrame outputframe3 = new OutputFrame();
                            break;

                        case "Cholesky Form":
                            OutputFrame outputframe4 = new OutputFrame();
                            break;
                    }
                    break;
                case "Gauss-Seidel":
                    OutputFrame outputframe5 = new OutputFrame();
                    break;
                case "Jacobi-Iteration":
                    OutputFrame outputframe6 = new OutputFrame();
                    break;   
            }
        }

        if(e.getSource() == methodComboBox){
            method = methodComboBox.getSelectedItem().toString(); 
        
            if(method.equals("LU Decomposition"))LUPanel.setVisible(true);
            else LUPanel.setVisible(false);

            if(method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration"))IterativePanel.setVisible(true);
            else IterativePanel.setVisible(false);
        }

        if(e.getSource() == LUComboBox){
            LUmethod = LUComboBox.getSelectedItem().toString();
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
