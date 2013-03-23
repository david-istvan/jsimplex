package simplex;

import java.io.BufferedReader;
import java.io.FileReader;

import simplex.exceptions.LPFormatException;
import simplex.exceptions.SyntaxErrorException;

/**
 * The class serves for configuring the simplex algorithm, i.e. parses the input
 * file and initializes the tableau.
 * 
 * @author idavid
 * 
 */
public class Config {
	private int n;
	private int m;
	private double[][] A;
	private double[] c;
	private double[] b;
	private String fileName;
	private double[] slack;
	private double[][] tableau;

	public Config(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Parses the input file.
	 * @throws SyntaxErrorException
	 * @throws LPFormatException
	 * @throws Exception
	 */
	public void readConfig() throws SyntaxErrorException, LPFormatException, Exception {
		String line = "";
		FileReader fr = new FileReader(this.fileName);
		BufferedReader br = new BufferedReader(fr);

		int rowId = 0;

		while ((line = br.readLine()) != null) {
			// the only permitted characters: digits, ".", "-" and space
			for (int i = 0; i < line.trim().length(); i++) {
				if ((!Character.isDigit(line.trim().charAt(i)))
						&& (line.trim().charAt(i) != '.')
						&& (line.trim().charAt(i) != '-')
						&& (line.trim().charAt(i) != ' '))
					throw new SyntaxErrorException();
			}

			// the first line always contains the N parameter
			if (rowId == 0) {
				String tmp = line.trim();
				n = Integer.parseInt(tmp);
				rowId++;
			}

			// the second line always contains the M parameter
			else if (rowId == 1) {
				m = Integer.parseInt(line.trim());
				rowId++;

				A = new double[m][n];
				c = new double[n];
				b = new double[m];
				slack = new double[m];
				for (int i = 0; i < slack.length; i++) {
					slack[i] = 1;
				}
				tableau = new double[m + 1][m + n + 2];
			}

			// the lines from 3 to 3+m are associated with the A matrix
			else if (rowId >= 3 && rowId < 3 + m) {
				try {
					String[] tmp = line.trim().split(" ");
					for (int i = 0; i < tmp.length; i++) {
						A[rowId - 3][i] = Double.parseDouble(tmp[i]);
						tableau[rowId - 3][i] = Double.parseDouble(tmp[i]);
						tableau[rowId - 3][tmp.length + rowId - 3] = 1;
					}
					rowId++;
				} catch (Exception ex) {
					throw new LPFormatException();
				}
			}

			// the line 3+m+1 is associated with the c vector
			else if (rowId == 3 + m + 1) {
				try {
					String[] tmp = line.trim().split(" ");
					for (int i = 0; i < tmp.length; i++) {
						c[i] = Double.parseDouble(tmp[i]);
						tableau[m][i] = (-1) * Double.parseDouble(tmp[i]);
					}

					tableau[m][m + n] = 1;
					rowId++;
				} catch (Exception ex) {
					throw new LPFormatException();
				}
			}

			// the line 3+m+3 is associated with the b vector
			else if (rowId == 3 + m + 3) {
				try {
					String[] tmp = line.trim().split(" ");
					for (int i = 0; i < tmp.length; i++) {
						b[i] = Double.parseDouble(tmp[i]);
						tableau[i][m + n + 1] = Double.parseDouble(tmp[i]);
					}
					rowId++;
				} catch (Exception ex) {
					throw new LPFormatException();
				}
			}

			// all the other lines must be empty lines
			// (nothing to read in this case)
			else {
				rowId++;
			}
		}
	}

	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}

	public double[][] getA() {
		return A;
	}

	public double[] getC() {
		return c;
	}

	public double[] getB() {
		return b;
	}

	public double[] getSlack() {
		return slack;
	}

	public double[][] getTableau() {
		return tableau;
	}
}