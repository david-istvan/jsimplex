package simplex;

import simplex.exceptions.LPFormatException;
import simplex.exceptions.SyntaxErrorException;

/**
 * Runs the program.
 * @author idavid
 *
 */
public class Main {
	public static void main(String[] args) {
		try {
			Config c = new Config(args[0]);
			//Config c = new Config("inputs/input14-unbound.txt");
			c.readConfig();
			Simplex simplex = new Simplex(c.getTableau(), c.getN(), c.getM());
			simplex.run();
		} catch (SyntaxErrorException ex1) {
			System.out.println("ERROR (Syntax)!");
			System.out.println(ex1.getMessage());
		} catch (LPFormatException ex2) {
			System.out.println("ERROR (Formatting)!");
			System.out.println(ex2.getMessage());
		} catch (Exception ex3) {
			System.out.println("ERROR!");
			System.out.println(ex3.getMessage());
			//ex3.printStackTrace();
			return;
		}
	}
}