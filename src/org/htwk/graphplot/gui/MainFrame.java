package org.htwk.graphplot.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5809565058231784769L;
    private JPanel contentPane;
    
    /**
     * Create the frame.
     */
    public MainFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        FunctionGraph functionGraph = new FunctionGraph();
        //functionGraph.setFunction("1/x");
        contentPane.add(functionGraph, BorderLayout.CENTER);
    }
    
}
