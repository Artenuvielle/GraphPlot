/**
 * 
 */
package org.htwk.graphplot.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.DropMode;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.lang.Double;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * @author Sophie Eckenstaler, René Martin
 *
 */
public class FunctionFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtFunktionsplot;
	private JTextField txtEingabe;
	private JPanel Panel_Eingabe;
	private JTextField txtX_1;
	private JPanel panel_x;
	private JTextField txtX;
	private JTextField txtMin_x;
	private JPanel panel_y;
	private JTextField txtY;
	private JPanel panel_xmin;
	private JTextField textField_xmin;
	private JPanel panel_xmax;
	private JTextField txtMax_x;
	private JTextField textField_xmax;
	private JPanel panel_ymin;
	private JTextField txtMin_y;
	private JTextField textField_ymin;
	private JPanel panel_ymax;
	private JTextField txtMax_y;
	private JTextField textField_ymax;
	private JPanel panel_Button;
	private JButton btnNewButton;

	/**
	 * Create the frame.
	 */
	public FunctionFrame() {
		setTitle("GraphPlot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		final FunctionGraph functionGraph = new FunctionGraph();
		functionGraph.setFunction("x");
		contentPane.add(functionGraph);
		
		JPanel Panel_Fktplot = new JPanel();
		contentPane.add(Panel_Fktplot);
		Panel_Fktplot.setLayout(new GridLayout(0, 1, 2, 0));
		
		txtFunktionsplot = new JTextField();
		txtFunktionsplot.setEditable(false);
		txtFunktionsplot.setHorizontalAlignment(SwingConstants.CENTER);
		txtFunktionsplot.setFont(new Font("Verdana", Font.BOLD, 20));
		txtFunktionsplot.setForeground(Color.DARK_GRAY);
		txtFunktionsplot.setText("Mathematischer Funktionsplotter");
		Panel_Fktplot.add(txtFunktionsplot);
		txtFunktionsplot.setColumns(10);
		
		Panel_Eingabe = new JPanel();
		Panel_Fktplot.add(Panel_Eingabe);
		Panel_Eingabe.setLayout(new GridLayout(0, 2, 2, 0));
		
		txtEingabe = new JTextField();
		txtEingabe.setEditable(false);
		Panel_Eingabe.add(txtEingabe);
		txtEingabe.setForeground(Color.DARK_GRAY);
		txtEingabe.setFont(new Font("Verdana", Font.BOLD, 15));
		txtEingabe.setHorizontalAlignment(SwingConstants.CENTER);
		txtEingabe.setText("Eingabe");
		txtEingabe.setColumns(2);
		
		txtX_1 = new JTextField();
		txtX_1.setText("x");
		txtX_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtX_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		txtX_1.setForeground(Color.BLACK);
		Panel_Eingabe.add(txtX_1);
		txtX_1.setColumns(10);
		
		panel_x = new JPanel();
		Panel_Fktplot.add(panel_x);
		panel_x.setLayout(new GridLayout(0, 3, 2, 0));
		
		txtX = new JTextField();
		txtX.setText("x");
		txtX.setHorizontalAlignment(SwingConstants.CENTER);
		txtX.setForeground(Color.DARK_GRAY);
		txtX.setFont(new Font("Verdana", Font.BOLD, 15));
		txtX.setEditable(false);
		txtX.setColumns(2);
		panel_x.add(txtX);
		
		panel_xmin = new JPanel();
		panel_x.add(panel_xmin);
		panel_xmin.setLayout(new GridLayout(2, 1, 0, 0));
		
		txtMin_x = new JTextField();
		txtMin_x.setText("min");
		txtMin_x.setEditable(false);
		panel_xmin.add(txtMin_x);
		txtMin_x.setHorizontalAlignment(SwingConstants.CENTER);
		txtMin_x.setForeground(Color.DARK_GRAY);
		txtMin_x.setFont(new Font("Verdana", Font.BOLD, 15));
		txtMin_x.setColumns(10);
		
		textField_xmin = new JTextField();
		textField_xmin.setText("-10");
		textField_xmin.setHorizontalAlignment(SwingConstants.CENTER);
		textField_xmin.setForeground(Color.BLACK);
		textField_xmin.setFont(new Font("Verdana", Font.PLAIN, 15));
		textField_xmin.setColumns(10);
		panel_xmin.add(textField_xmin);
		
		panel_xmax = new JPanel();
		panel_x.add(panel_xmax);
		panel_xmax.setLayout(new GridLayout(2, 1, 0, 0));
		
		txtMax_x = new JTextField();
		txtMax_x.setText("max");
		txtMax_x.setHorizontalAlignment(SwingConstants.CENTER);
		txtMax_x.setForeground(Color.DARK_GRAY);
		txtMax_x.setFont(new Font("Verdana", Font.BOLD, 15));
		txtMax_x.setEditable(false);
		txtMax_x.setColumns(10);
		panel_xmax.add(txtMax_x);
		
		textField_xmax = new JTextField();
		textField_xmax.setText("10");
		textField_xmax.setHorizontalAlignment(SwingConstants.CENTER);
		textField_xmax.setForeground(Color.BLACK);
		textField_xmax.setFont(new Font("Verdana", Font.PLAIN, 15));
		textField_xmax.setColumns(10);
		panel_xmax.add(textField_xmax);
		
		panel_y = new JPanel();
		Panel_Fktplot.add(panel_y);
		panel_y.setLayout(new GridLayout(0, 3, 2, 0));
		
		txtY = new JTextField();
		txtY.setText("y");
		txtY.setHorizontalAlignment(SwingConstants.CENTER);
		txtY.setForeground(Color.DARK_GRAY);
		txtY.setFont(new Font("Verdana", Font.BOLD, 15));
		txtY.setEditable(false);
		txtY.setColumns(2);
		panel_y.add(txtY);
		
		panel_ymin = new JPanel();
		panel_y.add(panel_ymin);
		panel_ymin.setLayout(new GridLayout(2, 1, 0, 0));
		
		txtMin_y = new JTextField();
		txtMin_y.setText("min");
		txtMin_y.setHorizontalAlignment(SwingConstants.CENTER);
		txtMin_y.setForeground(Color.DARK_GRAY);
		txtMin_y.setFont(new Font("Verdana", Font.BOLD, 15));
		txtMin_y.setEditable(false);
		txtMin_y.setColumns(10);
		panel_ymin.add(txtMin_y);
		
		textField_ymin = new JTextField();
		textField_ymin.setText("-10");
		textField_ymin.setHorizontalAlignment(SwingConstants.CENTER);
		textField_ymin.setForeground(Color.BLACK);
		textField_ymin.setFont(new Font("Verdana", Font.PLAIN, 15));
		textField_ymin.setColumns(10);
		panel_ymin.add(textField_ymin);
		
		panel_ymax = new JPanel();
		panel_y.add(panel_ymax);
		panel_ymax.setLayout(new GridLayout(2, 1, 0, 0));
		
		txtMax_y = new JTextField();
		txtMax_y.setText("max");
		txtMax_y.setHorizontalAlignment(SwingConstants.CENTER);
		txtMax_y.setForeground(Color.DARK_GRAY);
		txtMax_y.setFont(new Font("Verdana", Font.BOLD, 15));
		txtMax_y.setEditable(false);
		txtMax_y.setColumns(10);
		panel_ymax.add(txtMax_y);
		
		textField_ymax = new JTextField();
		textField_ymax.setText("10");
		textField_ymax.setHorizontalAlignment(SwingConstants.CENTER);
		textField_ymax.setForeground(Color.BLACK);
		textField_ymax.setFont(new Font("Verdana", Font.PLAIN, 15));
		textField_ymax.setColumns(10);
		panel_ymax.add(textField_ymax);
		
		panel_Button = new JPanel();
		Panel_Fktplot.add(panel_Button);
		
		btnNewButton = new JButton("Jetzt zeichnen!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				functionGraph.setFunction(txtX_1.getText());
				functionGraph.setXMin(Double.parseDouble(textField_xmin.getText()));
				functionGraph.setXMax(Double.parseDouble(textField_xmax.getText()));
				functionGraph.setYMin(Double.parseDouble(textField_ymin.getText()));
				functionGraph.setYMax(Double.parseDouble(textField_ymax.getText()));
				
			}
		});
		panel_Button.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 15));
		panel_Button.add(btnNewButton);
	}


}
