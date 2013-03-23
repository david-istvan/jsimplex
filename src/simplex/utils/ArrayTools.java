package simplex.utils;

/**
 * Helper class for the array functions.
 * @author idavid
 *
 */
public class ArrayTools {
	/**
	 * Decides wheter the column is a unit column. If it is, returns the index
	 * the one can be found at.
	 * 
	 * @param column Column of the matrix.
	 * @return The index of the single one element.
	 */
	public static int unitColumn(double[] column) {
		int ones = 0;
		int onePosition = -1;
		int zeros = 0;
		for (int i = 0; i < column.length; i++) {
			if (column[i] == 1){
				ones++;
				onePosition = i;
			}
			else if (column[i] == 0)
				zeros++;
			else
				return -1;
		}

		if (ones == 1 && zeros == column.length - 1)
			return onePosition;
		else
			return -1;
	}
}