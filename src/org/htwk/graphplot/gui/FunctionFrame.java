/**
 * 
 */
package org.htwk.graphplot.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.lang.Double;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.LineBorder;

/**
 * @author Sophie Eckenstaler, René Martin
 * 
 */
public class FunctionFrame extends JFrame {

	private static final long serialVersionUID = -3500312048315935586L;

	private final Font titleFont = new Font("Verdana", Font.BOLD, 20);
	private final Font standardFont = new Font("Verdana", Font.BOLD, 15);
	private final Font inputFont = new Font("Verdana", Font.PLAIN, 13);
	private final Color fontColor = Color.DARK_GRAY;
	private final Color inputColor = Color.BLACK;
	private final String defaultFunction = "x";

	private JPanel contentPane;
	private JPanel panelFunctionplot;
	private JPanel panelInput;
	private JTextField txtFunction;
	private JPanel panelX;
	private JPanel panelY;
	private JPanel panelXMin;
	private JTextField textFieldXMin;
	private JPanel panelXMax;
	private JTextField textFieldXMax;
	private JPanel panelYMin;
	private JTextField textFieldYMin;
	private JPanel panelYMax;
	private JTextField textFieldYMax;
	private JPanel panelButton;
	private JButton btnRedraw;
	private JCheckBox chkYEnabled;
	private JPanel panelYSelection;
	private JLabel txtX;
	private JLabel txtY;
	private JLabel txtYMin;
	private JLabel txtYMax;
	private JLabel txtXMin;
	private JLabel txtXMax;
	private JLabel txtInput;
	private JLabel txtTitle;
	private final FunctionGraph functionGraph = new FunctionGraph();

	/**
	 * Create the function frame.
	 */
	public FunctionFrame() {
		setTitle("GraphPlot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 10, 0));
		functionGraph.setFunction(defaultFunction);
		contentPane.add(functionGraph);

		panelFunctionplot = new JPanel();
		contentPane.add(panelFunctionplot);
		panelFunctionplot.setLayout(new GridLayout(0, 1, 2, 10));

		insertTitle();
		insertMainInput();
		insertXInput();
		insertYInput();
		insertRedrawButton();
		addDataBinding();
	}

	/**
	 * Inserts a panel with the title.
	 */
	private void insertTitle() {
		txtTitle = new JLabel("Mathematischer Funktionsplotter");
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setFont(titleFont);
		panelFunctionplot.add(txtTitle);
	}

	/**
	 * Inserts a panel with the main input text field where the user can put in
	 * custom functions.
	 */
	private void insertMainInput() {
		panelInput = new JPanel();
		panelFunctionplot.add(panelInput);
		panelInput.setLayout(new GridLayout(0, 2, 2, 0));

		txtInput = createLabel("Eingabe", true);
		panelInput.add(txtInput);

		txtFunction = createTextField(defaultFunction, true);
		panelInput.add(txtFunction);
	}

	/**
	 * Inserts a panel for defining x axe limits.
	 */
	private void insertXInput() {
		panelX = new JPanel();
		panelX.setBorder(new LineBorder(fontColor, 1, true));
		panelFunctionplot.add(panelX);
		panelX.setLayout(new GridLayout(0, 3, 2, 0));

		txtX = createLabel("X", true);
		panelX.add(txtX);

		panelXMin = new JPanel();
		panelX.add(panelXMin);
		panelXMin.setLayout(new GridLayout(2, 1, 0, 0));

		txtXMin = createLabel("min", true);
		panelXMin.add(txtXMin);

		textFieldXMin = createTextField("-10", true);
		panelXMin.add(textFieldXMin);

		panelXMax = new JPanel();
		panelX.add(panelXMax);
		panelXMax.setLayout(new GridLayout(2, 1, 0, 0));

		txtXMax = createLabel("max", true);
		panelXMax.add(txtXMax);

		textFieldXMax = createTextField("10", true);
		panelXMax.add(textFieldXMax);
	}

	/**
	 * Inserts a panel for defining y axe limits.
	 */
	private void insertYInput() {
		panelY = new JPanel();
		panelY.setBorder(new LineBorder(fontColor, 1, true));
		panelFunctionplot.add(panelY);
		panelY.setLayout(new GridLayout(0, 3, 2, 0));

		panelYSelection = new JPanel();
		panelY.add(panelYSelection);
		panelYSelection.setLayout(new GridLayout(3, 1, 0, 0));

		chkYEnabled = new JCheckBox("Eigene Y-Grenzen");
		chkYEnabled.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtY.setEnabled(chkYEnabled.isSelected());
				txtYMin.setEnabled(chkYEnabled.isSelected());
				txtYMax.setEnabled(chkYEnabled.isSelected());
				textFieldYMin.setEnabled(chkYEnabled.isSelected());
				textFieldYMax.setEnabled(chkYEnabled.isSelected());
			}
		});
		chkYEnabled.setFont(inputFont);
		chkYEnabled.setForeground(inputColor);
		chkYEnabled.setHorizontalAlignment(SwingConstants.CENTER);
		panelYSelection.add(chkYEnabled);

		txtY = createLabel("Y", false);
		panelYSelection.add(txtY);

		panelYMin = new JPanel();
		panelY.add(panelYMin);
		panelYMin.setLayout(new GridLayout(2, 1, 0, 0));

		txtYMin = createLabel("min", false);
		panelYMin.add(txtYMin);

		textFieldYMin = createTextField("-10", false);
		panelYMin.add(textFieldYMin);

		panelYMax = new JPanel();
		panelY.add(panelYMax);
		panelYMax.setLayout(new GridLayout(2, 1, 0, 0));

		txtYMax = createLabel("max", false);
		panelYMax.add(txtYMax);

		textFieldYMax = createTextField("10", false);
		panelYMax.add(textFieldYMax);
	}

	/**
	 * Inserts a panel with the button to redraw the function plotter.
	 */
	private void insertRedrawButton() {
		panelButton = new JPanel();
		panelFunctionplot.add(panelButton);

		btnRedraw = new JButton("Jetzt zeichnen!");
		btnRedraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				functionGraph.setFunction(txtFunction.getText());
				functionGraph.setXMin(Double.parseDouble(textFieldXMin.getText().replace(",", ".")));
				functionGraph.setXMax(Double.parseDouble(textFieldXMax.getText().replace(",", ".")));
				if (chkYEnabled.isSelected()) {
					functionGraph.setAutomaticY(false);
					functionGraph.setYMin(Double.parseDouble(textFieldYMin.getText().replace(",", ".")));
					functionGraph.setYMax(Double.parseDouble(textFieldYMax.getText().replace(",", ".")));
				} else {
					functionGraph.setAutomaticY(true);
				}
			}
		});
		panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		btnRedraw.setForeground(fontColor);
		btnRedraw.setFont(standardFont);
		panelButton.add(btnRedraw);
	}

	/**
	 * Creates a text field with a given standard text.
	 * 
	 * @param content
	 *            The given standard text
	 * @param enabled
	 *            True if the element should be enabled by default
	 * @return The created text field
	 */
	private JTextField createTextField(String content, boolean enabled) {
		JTextField createdElement = new JTextField();
		createdElement.setEnabled(enabled);
		createdElement.setText(content);
		createdElement.setHorizontalAlignment(SwingConstants.CENTER);
		createdElement.setForeground(inputColor);
		createdElement.setFont(inputFont);
		return createdElement;
	}

	/**
	 * Creates a label with a given standard text.
	 * 
	 * @param content
	 *            The given standard text
	 * @param enabled
	 *            True if the element should be enabled by default
	 * @return The created label
	 */
	private JLabel createLabel(String content, boolean enabled) {
		JLabel createdElement = new JLabel();
		createdElement.setEnabled(enabled);
		createdElement.setText(content);
		createdElement.setHorizontalAlignment(SwingConstants.CENTER);
		createdElement.setForeground(fontColor);
		createdElement.setFont(standardFont);
		return createdElement;
	}

	/**
	 * Adds a binding to function graph as data model. Whenever the axe limits
	 * of the model are changed it will be displayed in the corresponding text
	 * fields. This creates an observer pattern which is implemented using
	 * Java-Beans.
	 */
	private void addDataBinding() {
		functionGraph.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				JTextField reciever = null;
				switch (evt.getPropertyName()) {
				case "xmin":
					reciever = textFieldXMin;
					break;
				case "xmax":
					reciever = textFieldXMax;
					break;
				case "ymin":
					reciever = textFieldYMin;
					break;
				case "ymax":
					reciever = textFieldYMax;
					break;
				}
				if (reciever != null) {
					reciever.setText(String.valueOf(Math.round((double) evt.getNewValue() * 100.0) / 100.0));
				}
			}
		});
	}

}
