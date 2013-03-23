package simplex.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Formatter class for the output.
 * @author idavid
 *
 */
public class Formatter {
	/**
	 * A template method for formating the double values.
	 */
	public static DecimalFormat formatDouble(String template) {
		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setDecimalSeparator('.');
		return new DecimalFormat(template, sym);
	}
}