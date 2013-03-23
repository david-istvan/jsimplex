package simplex;

import simplex.output.Output;
import simplex.utils.ArrayTools;

/**
 * The class implementing the Simplex algorithm.
 * 
 * @author idavid
 * 
 */
public class Simplex {
	private double[][] tableau;
	private int n;
	private int m;
	private int[] ids;
	private Output output;

	public Simplex(double[][] tableau, int n, int m) {
		this.tableau = tableau;
		this.n = n;
		this.m = m;
		this.ids = new int[2];
		this.output = new Output(this.n, this.m);
	}

	/**
	 * After the class has been instantiated and the tableau has been
	 * initialized the execution is started by this method.
	 */
	public void run() {
		int turn = 0;
		output.printTableau(this.tableau, turn);
		output.saveTargetFunction(this.tableau[m]);
		// while the pivot is possible go with it
		while (isPivotPossible()) {
			ids = locatePivotElement();
			// if there is no suitable pivot element in the entry column,
			// the solution is unbounded
			if (ids[0] == -1) {
				System.out.println("The solution is unbounded.");
				return;
			}
			pivot(ids[0], ids[1]);
			output.printTableau(this.tableau, ++turn);
		}
		// after reaching the final tableau check the feasibility of the
		// solution
		// if feasible, print the solution
		if (solutionIsFeasibile()) {
			output.printSolution(this.n, this.m, this.tableau);
		} else {
			System.out.println("The solution is infeasible.");
		}

	}

	/**
	 * Locates the pivot element, i.e. the most negative element in the
	 * objective row.
	 * 
	 * @return The row ID and the column ID of the pivot element in an integer
	 *         array of size of two.
	 */
	private int[] locatePivotElement() {
		double colVal = Double.MAX_VALUE;
		int colId = -1;
		for (int i = 0; i < tableau[m].length - 1; i++) {
			if (tableau[m][i] < colVal) {
				colVal = tableau[m][i];
				colId = i;
			}
		}

		int pivotRowId = -1;
		double ratio = Double.MAX_VALUE;
		for (int i = 0; i < m; i++) {
			double tmpRatio;
			if (tableau[i][colId] > 0) {
				tmpRatio = tableau[i][n + m + 1] / tableau[i][colId];
				if (tmpRatio < ratio) {
					ratio = tmpRatio;
					pivotRowId = i;
				}
			}
		}

		int[] ids = { pivotRowId, colId };
		return ids;
	}

	/**
	 * Executes the pivot.
	 * 
	 * @param rowId
	 *            The row ID of the pivot element.
	 * @param colId
	 *            THe column ID of the pivot element.
	 */
	private void pivot(int rowId, int colId) {
		normalizeRow(rowId, tableau[rowId][colId]);

		for (int i = 0; i < m + 1; i++) {
			if (i != rowId) {
				double multiplier = tableau[i][colId] / tableau[rowId][colId];
				for (int j = 0; j < n + m + 2; j++) {
					tableau[i][j] = tableau[i][j] - multiplier
							* tableau[rowId][j];
				}
			}
		}
	}

	/**
	 * Normalizes a row, i.e. denominates the row by the pivot element. This
	 * step is required to reach a uniform unit column, i.e. where only ones and
	 * zeros are present.
	 * 
	 * @param rowId
	 *            The ID of the row.
	 * @param denominator
	 *            The value of the pivot element.
	 */
	private void normalizeRow(int rowId, double denominator) {
		for (int i = 0; i < n + m + 2; i++) {
			tableau[rowId][i] = tableau[rowId][i] / denominator;
		}
	}

	/**
	 * Checks whether the reached solution is feasible or not.
	 * 
	 * @return Is the solution feasible?
	 */
	private boolean solutionIsFeasibile() {
		int unitSlacks = 0;
		for (int i = n; i < n + m; i++) {
			double[] tmpCol = new double[m + 1];
			for (int j = 0; j < m + 1; j++) {
				tmpCol[j] = this.tableau[j][i];
			}
			if (ArrayTools.unitColumn(tmpCol) > -1) {
				unitSlacks++;
			}
		}
		// if all the slack variables are not zero,
		// the solution is infeasible
		if (unitSlacks == m)
			return false;
		return true;
	}

	/**
	 * Checks whether a pivot is possible or not.
	 * 
	 * @return Is a pivot possible?
	 */
	private boolean isPivotPossible() {
		// if there is at least one negative element in the objective row,
		// the pivot is possible
		for (int i = 0; i < tableau[m].length - 1; i++) {
			if (tableau[m][i] < 0)
				return true;
		}
		return false;
	}
}