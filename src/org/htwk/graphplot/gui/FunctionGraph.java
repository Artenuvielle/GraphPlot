package org.htwk.graphplot.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.htwk.graphplot.expression.ExpressionInterpreter;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;

public class FunctionGraph extends JComponent
{
    private static final long serialVersionUID = -4713114097942932089L;
    private static final int scaleLineLength = 3;
    
    private double xmin = -10, xmax = 10, xscale = 1;
    private double ymin = -10, ymax = 10, yscale = 1;
    private Expression functionToDraw;
    
    public void setFunction(Expression function)
    {
        functionToDraw = function;
        repaint();
    }
    
    public void setFunction(String unformattedFunction)
    {
        try {
            ExpressionInterpreter expressionInterpreter = new ExpressionInterpreter(unformattedFunction);
            functionToDraw = expressionInterpreter.getExpression();
        } catch (Exception e) {
            String s = e.toString();
            for(StackTraceElement ste : e.getStackTrace()) {
                s += "\n\tat " + ste;
            }
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + s, "Error in the given expression", JOptionPane.CANCEL_OPTION);
        }
    }
    
    public void setXMin(double xmin) {
        this.xmin = xmin;
    }
    
    public void setXMax(double xax) {
        this.xmin = xmax;
    }
    
    public void setXScale(double xscale) {
        this.xscale = xscale;
    }
    
    public void setYMin(double ymin) {
        this.ymin = ymin;
    }
    
    public void setYMax(double ymax) {
        this.ymax = ymax;
    }
    
    public void setYScale(double yscale) {
        this.yscale = yscale;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawGraphAxes(g);
        g.setColor(Color.blue);
        try {
            if(functionToDraw != null) {
                drawFunction(g);
            }
        } catch (InvalidVariableNameException e) {
            e.printStackTrace();
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }
    
    private void drawFunction(Graphics g) throws InvalidVariableNameException
    {
        if (functionToDraw != null) {
            HashMap<String, Double> variables = new HashMap<String, Double>();
            Integer[] functionPixels = new Integer[this.getWidth()];
            for (int xPixel = 0; xPixel < this.getWidth(); xPixel++) {
                variables.put("x", new Double(getXValueForHorizontalPixel(xPixel)));
                double value = functionToDraw.calculateValue(variables);
                if (!Double.isNaN(value)) {
                    functionPixels[xPixel] = getVerticalPixelForYValue(value);
                } else {
                    functionPixels[xPixel] = null;
                }
            }
            for (int iterator = 1; iterator < functionPixels.length - 1; iterator++) {
                if (functionPixels[iterator] != null && functionPixels[iterator + 1] != null) {
                    g.drawLine(iterator, functionPixels[iterator], iterator + 1, functionPixels[iterator + 1]);
                }
            }
        }
    }
    
    private void drawGraphAxes(Graphics g)
    {
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
    
    private int getHorizontalPixelForXValue(double xValue)
    {
        if (xmin > xValue) {
            return 0;
        } else if (xmax < xValue) {
            return this.getWidth() - 1;
        } else {
            return (int) (1 + Math.abs(xValue - xmin) / Math.abs(xmax - xmin) * (this.getWidth() - 3));
        }
    }
    
    private int getVerticalPixelForYValue(double yValue)
    {
        if (ymin > yValue) {
            return this.getHeight() - 1;
        } else if (ymax < yValue) {
            return 0;
        } else {
            return (int) (1 + Math.abs(yValue - ymax) / Math.abs(ymax - ymin) * (this.getHeight() - 3));
        }
    }
    
    private double getXValueForHorizontalPixel(int pixelValue)
    {
        return (double) (pixelValue - 1) / (double) (this.getWidth() - 3) * Math.abs(xmax - xmin) + xmin;
    }
}
