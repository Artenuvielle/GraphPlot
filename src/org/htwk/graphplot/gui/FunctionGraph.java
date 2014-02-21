package org.htwk.graphplot.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.htwk.graphplot.expression.ExpressionInterpreter;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;

/**
 * This Class represents a Swing component for drawing a graph.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class FunctionGraph extends JComponent {

	private static final long serialVersionUID = -4713114097942932089L;
	private static final Logger logger = Logger.getLogger(FunctionGraph.class.getName());
	private static final int scaleLineLength = 3;

	private double xmin = -10, xmax = 10, xscale = 1;
	private double ymin = -10, ymax = 10, yscale = 1;
	private boolean automaticY = true;
	private Expression functionToDraw;

	/**
	 * Simple Constructor. Only the Axes will be drawn in the components area.
	 */
	public FunctionGraph() {
		super();
	}

	/**
	 * Sets the function to draw in the graph and displays it.
	 * 
	 * @param function
	 *            The function to draw
	 */
	public void setFunction(Expression function) {
		functionToDraw = function;
		repaint();
	}

	/**
	 * Sets the function to draw in the graph given as String and displays it.
	 * 
	 * @param unformattedFunction
	 *            The String which will be transformed and displayed as
	 *            mathematical function.
	 */
	public void setFunction(String unformattedFunction) {
		try {
			ExpressionInterpreter expressionInterpreter = new ExpressionInterpreter(unformattedFunction);
			functionToDraw = expressionInterpreter.getExpression();
		} catch (Exception e) {
			logger.severe(e.getMessage());
			String s = e.toString();
			for (StackTraceElement ste : e.getStackTrace()) {
				s += "\n\tat " + ste;
			}
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + s, "Error in the given expression", JOptionPane.CANCEL_OPTION);
		}
	}

	/**
	 * Sets the minimum x value.
	 * 
	 * @param xmin
	 *            The minimum value for x
	 */
	public void setXMin(double xmin) {
		firePropertyChange("xmin", this.xmin, this.xmin = xmin);
		this.repaint();
	}

	/**
	 * Sets the maximum x value.
	 * 
	 * @param xmax
	 *            The maximum value for x
	 */
	public void setXMax(double xmax) {
		firePropertyChange("xmax", this.xmax, this.xmax = xmax);
		this.repaint();
	}

	/**
	 * Sets the scale for the x axis.
	 * 
	 * @param xscale
	 *            The scale which shall be used.
	 */
	public void setXScale(double xscale) {
		firePropertyChange("xscale", this.xscale, this.xscale = xscale);
		this.repaint();
	}

	/**
	 * Sets the minimum y value.
	 * 
	 * @param ymin
	 *            The minimum value for y
	 */
	public void setYMin(double ymin) {
		firePropertyChange("ymin", this.ymin, this.ymin = ymin);
		this.repaint();
	}

	/**
	 * Sets the maximum y value.
	 * 
	 * @param ymax
	 *            The maximum value for y
	 */
	public void setYMax(double ymax) {
		firePropertyChange("ymax", this.ymax, this.ymax = ymax);
		this.repaint();
	}

	/**
	 * Sets the scale for the y axis.
	 * 
	 * @param yscale
	 *            The scale which shall be used.
	 */
	public void setYScale(double yscale) {
		firePropertyChange("yscale", this.yscale, this.yscale = yscale);
		this.repaint();
	}

	/**
	 * Sets if the limits for the y axis shall be automatically determined.
	 * 
	 * @param automatic
	 *            True, if the limits shall be automatically determined
	 */
	public void setAutomaticY(boolean automatic) {
		this.automaticY = automatic;
	}

	/**
	 * Paints the control. Also draws a function graph if a function was already
	 * set.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.blue);
		try {
			if (functionToDraw != null) {
				drawFunction(g);
			}
		} catch (InvalidVariableNameException e) {
			logger.severe("Invaid variable names where used.");
			String s = e.toString();
			for (StackTraceElement ste : e.getStackTrace()) {
				s += "\n\tat " + ste;
			}
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + s, "Invaid variable name", JOptionPane.CANCEL_OPTION);
			functionToDraw = null;
		}
		drawGraphAxes(g);
		g.setColor(Color.black);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
	}

	/**
	 * Draws a function on a graphical surface.
	 * 
	 * @param g
	 *            The graphical surface to draw on
	 * @throws InvalidVariableNameException
	 *             is thrown if invalid variable names are used.
	 */
	private void drawFunction(Graphics g) throws InvalidVariableNameException {
		if (functionToDraw != null) {
			HashMap<String, Double> variables = new HashMap<String, Double>();
			Double ymin = Double.MAX_VALUE, ymax = -Double.MAX_VALUE;
			Double[] functionPoints = new Double[this.getWidth()];
			Integer[] functionPixels = new Integer[this.getWidth()];
			for (int xPixel = 0; xPixel < this.getWidth(); xPixel++) {
				variables.put("x", new Double(getXValueForHorizontalPixel(xPixel)));
				double value = functionToDraw.calculateValue(variables);
				if (!Double.isNaN(value)) {
					if (value < ymin) {
						ymin = value;
					}
					if (value > ymax) {
						ymax = value;
					}
					functionPoints[xPixel] = value;
				} else {
					functionPoints[xPixel] = null;
				}
			}
			if (this.automaticY) {
				setYMin(ymin - (ymax - ymin) / 4);
				setYMax(ymax + (ymax - ymin) / 4);
			}
			if (functionPoints[0] != null) {
				functionPixels[0] = getVerticalPixelForYValue(functionPoints[0]);
			} else {
				functionPixels[0] = null;
			}
			for (int iterator = 1; iterator < functionPixels.length - 1; iterator++) {
				if (functionPoints[iterator] != null) {
					functionPixels[iterator] = getVerticalPixelForYValue(functionPoints[iterator]);
				} else {
					functionPixels[iterator] = null;
				}
				if (functionPixels[iterator - 1] != null && functionPixels[iterator] != null) {
					g.drawLine(iterator - 1, functionPixels[iterator - 1], iterator, functionPixels[iterator]);
				}
			}
		}
	}

	/**
	 * Draws the axes for the graph display on a graphical surface.
	 * 
	 * @param g
	 *            The graphical surface to draw on
	 */
	private void drawGraphAxes(Graphics g) {
		int xAxisPosition, yAxisPosition, xScaleLength, yScaleLength;
		int xScalePosition, yScalePosition;

		g.setColor(Color.black);
		// Draw y-Axis
		yAxisPosition = getHorizontalPixelForXValue(0);
		g.drawLine(yAxisPosition, 0, yAxisPosition, this.getHeight());

		// Draw x-Axis
		xAxisPosition = getVerticalPixelForYValue(0);
		g.drawLine(0, xAxisPosition, this.getWidth(), xAxisPosition);

		// Draw y-Axis scales
		yScaleLength = ((yAxisPosition < this.getWidth() / 2) ? 1 : -1) * scaleLineLength;
		double yFirstScaleOffset = ymin % yscale;
		for (double yScaleValue = ymin - yFirstScaleOffset; yScaleValue <= ymax; yScaleValue += yscale) {
			yScalePosition = getVerticalPixelForYValue(yScaleValue);
			g.drawLine(yAxisPosition, yScalePosition, yAxisPosition + yScaleLength, yScalePosition);
		}

		// Draw x-Axis scales
		xScaleLength = ((xAxisPosition < this.getHeight() / 2) ? 1 : -1) * scaleLineLength;
		double xFirstScaleOffset = xmin % xscale;
		for (double xScaleValue = xmin - xFirstScaleOffset; xScaleValue <= xmax; xScaleValue += xscale) {
			xScalePosition = getHorizontalPixelForXValue(xScaleValue);
			g.drawLine(xScalePosition, xAxisPosition, xScalePosition, xAxisPosition + xScaleLength);
		}
	}

	/**
	 * Looks up the pixel column for a given x value.
	 * 
	 * @param xValue
	 *            The x value
	 * @return the pixel column
	 */
	private int getHorizontalPixelForXValue(double xValue) {
		if (xmin > xValue) {
			return 0;
		} else if (xmax < xValue) {
			return this.getWidth() - 1;
		} else {
			return (int) (1 + Math.abs(xValue - xmin) / Math.abs(xmax - xmin) * (this.getWidth() - 3));
		}
	}

	/**
	 * Looks up the pixel row for a given y value.
	 * 
	 * @param yValue
	 *            The y value
	 * @return The pixel row
	 */
	private int getVerticalPixelForYValue(double yValue) {
		if (ymin > yValue) {
			return this.getHeight() - 1;
		} else if (ymax < yValue) {
			return 0;
		} else {
			return (int) (1 + Math.abs(yValue - ymax) / Math.abs(ymax - ymin) * (this.getHeight() - 3));
		}
	}

	/**
	 * Looks up the x value which is represented by a given pixel column.
	 * 
	 * @param pixelValue
	 *            The given pixel column
	 * @return The x value
	 */
	private double getXValueForHorizontalPixel(int pixelValue) {
		return (double) (pixelValue - 1) / (double) (this.getWidth() - 3) * Math.abs(xmax - xmin) + xmin;
	}

}
