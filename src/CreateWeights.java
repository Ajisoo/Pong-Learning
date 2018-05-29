import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Each part of the learning process is split between files.
 * This file is for the creation of the random weights in the beginning.
 */

public class CreateWeights {
	public static void main(String[] args) {
		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			try {
				FileWriter fileWriter = new FileWriter("weights/Weights" + i + ".txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				for (int j = 0; j < Constants.WEIGHTS; j++) {
					// Between -0.5 and 0.5
					bufferedWriter.write(String.format("%f", Math.random() * 1 - 0.5));
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println(
						"IOException occurred when writing to file \"weights/Weights" + i + ".txt\"");
				System.exit(1);
			}
		}
		System.out.println("Finished Initializing Weights!");
	}
}
