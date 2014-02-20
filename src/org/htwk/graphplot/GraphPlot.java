package org.htwk.graphplot;

import java.awt.EventQueue;
import java.util.logging.Logger;
import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Operation;
import org.htwk.graphplot.gui.FunctionFrame;

public final class GraphPlot {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Logger.getLogger("").getHandlers()[0].setFormatter(new GraphPlotFormatter());
		Function.loadAllFunctions();
		Operation.loadAllOperators();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FunctionFrame frame = new FunctionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
