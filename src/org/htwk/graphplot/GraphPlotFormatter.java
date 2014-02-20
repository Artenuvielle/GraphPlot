package org.htwk.graphplot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A simple logging formatter for a console handler for java.util.logging
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class GraphPlotFormatter extends Formatter {

	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1000);
		buf.append(rec.getLevel().getName());
		buf.append(": ");
		buf.append(rec.getMessage());
		buf.append(" [");
		buf.append(calcDate(rec.getMillis()));
		buf.append("]\n");
		return buf.toString();
	}

	private String calcDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

}
