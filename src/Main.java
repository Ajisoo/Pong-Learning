import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	// Main driver for learning.
	public static void main(String[] args) {
		CreateWeights.main(args);

		int generations = 5001;
		for (int i = 0; i < generations; i++) {
			System.out.println("Starting Generation " + i + ".");
			double[] scores = new double[Constants.POPULATION_SIZE];
			playGames(scores);
			System.out.println("Generation " + i + " games played.");
			reproduce(scores);
		}

	}

	public static void playGames(double[] scores) {

		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			GameState gameState = new GameState(i, 0);

			// Uncomment for when bots play against each other.
			
			// GameState gameState = new GameState(2*i, 2*i+1);

			// update 10,000 ticks in game
			for (int j = 0; j < 10000; j++) {
				gameState.update();
			}

			scores[i] = gameState.getLeftScore() - gameState.getRightScore();

			// uncomment for when the bots play against each other.
			
			// scores[2*i] += gameState.getLeftScore() -
			// gameState.getRightScore();
			// scores[2*i+1]+=gameState.getRightScore()-
			// gameState.getLeftScore();
		}
	}

	// Games have been played, so reproduce based on who did the best.
	public static void reproduce(double[] scores) {
		double[][] weights = new double[Constants.POPULATION_SIZE][Constants.WEIGHTS];

		// Get weights into a 2D array.
		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			String file;
			file = "weights/Weights" + i + ".txt";
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				for (int j = 0; j < Constants.WEIGHTS; j++) {
					String s = bufferedReader.readLine();
					weights[i][j] = Double.parseDouble(s);
				}
				bufferedReader.close();

			} catch (IOException e) {
				System.out.println("Unable to open file '" + file + "'.");
			}
		}

		// Find data on scores.
		double minScore = Double.MAX_VALUE;
		double maxScore = -Double.MAX_VALUE;
		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			minScore = Math.min(minScore, scores[i]);
			maxScore = Math.max(maxScore, scores[i]);
		}
		
		// care for 0.
		minScore -= 0.01;
		
		// Make all scores positive for weighted randomness.
		System.out.println("Minimum score: " + minScore);
		double totalScore = 0;
		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			scores[i] -= minScore;
			scores[i] *= scores[i];
			totalScore += scores[i];
		}

		System.out.println("AverageScore: " + (totalScore / Constants.POPULATION_SIZE + minScore));
		System.out.println("Maximum Score: " + maxScore);

		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			try {
				
				// Crossover from two parents. Use weighted randomness
				// to find parents.
				double random = Math.random() * totalScore;
				int index = -1;
				while (random > 0) {
					random -= scores[++index];
				}
				double random2 = Math.random() * totalScore;
				int index2 = -1;
				while (random2 > 0) {
					random2 -= scores[++index2];
				}
				FileWriter fileWriter = new FileWriter("weights/Weights" + i + ".txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				for (int j = 0; j < Constants.WEIGHTS; j++) {
					
					// decide which parent to use when.
					double weight = weights[index][j];
					boolean use1 = true;
					if (Math.random() < Constants.CROSSOVER_RATE) {
						use1 = !use1;
					}
					if (!use1) {
						weight = weights[index2][j];
					}
					
					// Mutation.
					double randomMutation = Math.random();
					if (randomMutation < Constants.MUTATION_RATE) {
						weight = weight + Math.random() * 0.2 - 0.1; 
					}
					
					// Write the weight to the file.
					bufferedWriter.write(String.format("%f", weight));
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error writing to file " + i + ".");
			}
		}
	}
}
