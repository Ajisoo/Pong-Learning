
/*
 * This class is a list of constants for the Network,
 * whether they are variables or functions.
 * The network is run here.
 */

public class Constants {

	public static final int INPUTS = 3; // bias, x, ballx
	public static final int HIDDEN_LAYERS = 1;
	public static final int NODES_PER_LAYER = 1;
	public static final int OUTPUT = 1; // whether to move up or down.
	public static final double CROSSOVER_RATE = 0.1;
	public static final double MUTATION_RATE = 0.1;

	// Calculation of # of weights needed.
	public static final int WEIGHTS = NODES_PER_LAYER * NODES_PER_LAYER * (HIDDEN_LAYERS - 1)
			+ NODES_PER_LAYER * (INPUTS + OUTPUT);

	// Big population not needed because of simple network.
	public static final int POPULATION_SIZE = 10;

	// Function that takes inputs and weights and returns the singular output.
	public static double takeAction(double[] weights, double[] inputs) {
		int count = 0;

		double[][] nodes = new double[Constants.HIDDEN_LAYERS][Constants.NODES_PER_LAYER];
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {

				int inputsPerNode = Constants.NODES_PER_LAYER;

				if (i == 0)
					inputsPerNode = Constants.INPUTS;

				for (int k = 0; k < inputsPerNode; k++) {
					if (i == 0) {
						nodes[i][j] += weights[count] * inputs[k];
					} else {
						nodes[i][j] += weights[count] * nodes[i - 1][k];
					}
					count++;
				}
				// Sigmoid.
				nodes[i][j] = 2 / (1 + Math.pow(Math.E, -nodes[i][j])) - 1;
			}
		}

		double end = 0;

		for (int i = 0; i < Constants.NODES_PER_LAYER; i++) {
			end += nodes[nodes.length - 1][i] * weights[count];
			count++;
		}

		// Not sigmoid in order to cap probabilities.
		end = Math.min(1, Math.max(-1, 0.5 * end + 0.5));
		return end;
	}
}
