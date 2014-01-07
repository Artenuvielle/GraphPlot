package org.htwk.graphplot;

import java.awt.EventQueue;
import java.util.HashMap;

import org.htwk.graphplot.expression.ExpressionInterpreter;
import org.htwk.graphplot.expression.InvalidExpressionException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Operation;
import org.htwk.graphplot.gui.MainFrame;

public final class GraphPlot
{
    
    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        Function.loadAllFunctions();
        Operation.loadAllOperators();
        /*try {
            ExpressionInterpreter ie;
            if (args.length > 0) {
                ie = new ExpressionInterpreter(args[0]);
            } else {
                ie = new ExpressionInterpreter(" x+ 3 x /(-4 +sqrt(31-sqrt(36)) ^ 4 -3^(1+ x)   )*(-67)");
            }
            HashMap<String, Double> variables = new HashMap<String, Double>();
            variables.put("x", new Double(Math.PI));
            Expression e = ie.getExpression();
            System.out.println(e.calculateValue(variables) + "");
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
        } catch (InvalidVariableNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
