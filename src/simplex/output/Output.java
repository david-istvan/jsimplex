package simplex.output;

import simplex.utils.ArrayTools;
import simplex.utils.Formatter;

/**
 * The class is used for the logging to the consol.
 * @author idavid
 *
 */
public class Output {
	private int n;
	private int m;
	private String targetFunction;
	
	public Output(int n, int m){
		this.n = n;
		this.m = m;
	}
	
	/**
	 * Prints the simplex tableau.
	 * @param tableau The current state of the tableau.
	 * @param turn The turn number of the algorithm.
	 */
	public void printTableau(double[][] tableau, int turn) {
		if (turn == 0) {
			System.out.println("===== INITIAL TABLEAU =====");
		} else {
			System.out.println("\n\n===== TABLEAU AFTER TURN #" + turn + " =====");
		}
		System.out.print("| \t");
		for (int i = 0; i < n; i++) {
			System.out.print("x" + (i + 1) + "\t | \t");
		}
		for (int i = 0; i < m; i++) {
			System.out.print("s" + (i + 1) + "\t | \t");
		}
		System.out.print("P" + "\t | \t");
		System.out.println(" " + "\t | \t");
		for (int i = 0; i < tableau.length; i++) {
			System.out.print("| \t");
			for (int j = 0; j < tableau[i].length; j++) {
				System.out.print(Formatter.formatDouble("########.###").format(
						tableau[i][j])
						+ "\t | \t");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Saves the target function associated with the maximization problem.
	 * @param coefficients The coefficients of the target function.
	 */
	public void saveTargetFunction(double[] coefficients){
		targetFunction = "";
		
		for (int i = 0; i < n; i++) {
			targetFunction += (-1)*coefficients[i] + "*x" + (i+1);
			if(i<n-1) targetFunction += " + ";
		}
	}
	
	/**
	 * Outputs the solution.
	 * @param n Columns of the A matrix
	 * @param m Rows of the A matrix
	 * @param finalTableau The state of the tableau as of the reached optimum.
	 */
	public void printSolution(int n, int m, double[][] finalTableau){
		System.out.println("\n\nOptimal solution found: " + finalTableau[m][n+m+1]);
		System.out.println("\t(Objective function: P = " + targetFunction + ")");
		
		for (int i = 0; i < m+n; i++) {
			double[] tmpCol = new double[m+1];
			for (int j = 0; j < m+1; j++) {
				tmpCol[j] = finalTableau[j][i];
			}
			if(ArrayTools.unitColumn(tmpCol)>-1){
				if(i<n){
					System.out.println("x" + (i+1) + " = " + Formatter.formatDouble("########.###").format(finalTableau[ArrayTools.unitColumn(tmpCol)][n+m+1]));
				}
				else if(i<n+m){
					System.out.println("s" + (i-n+1) + " = " + Formatter.formatDouble("########.###").format(finalTableau[ArrayTools.unitColumn(tmpCol)][n+m+1]));
				}
			}
		}
		System.out.println("The other parameters are equal to zero.");
	}
}