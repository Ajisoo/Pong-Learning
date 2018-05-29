import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * This is the class that holds all the data for the pong game itself.
 */

public class GameState {

	// Constants for game constants.
	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;
	private static final int PADDLE = 20;
	private static final int SPEED = 2;
	private static final int BALL_START_SPEED = 3;

	// Angle to start the ball.
	private static final double BALL_START_RANGE = Math.PI / 8;
	private static final int BALL_RAD = 5;

	private double[] leftWeights;
	private double[] rightWeights;

	private double x;
	private double y;
	private double xv;
	private double yv;
	private double l;
	private double r;
	private double lScore;
	private int rScore;

	public double getLeftScore() {
		return lScore;
	}

	public double getRightScore() {
		return rScore;
	}

	public void update() {
		double lToMove = Constants.takeAction(leftWeights,
				new double[] { 0, 2 * (l - HEIGHT / 2) / HEIGHT, 2 * (y - HEIGHT / 2) / HEIGHT });// *
																									// SPEED;
		boolean random = Math.random() < lToMove;
		if (random) {
			lToMove = 1;
		} else {
			lToMove = -1;
		}

		// uncomment and remove below if both sides should use the weights.
		// double rToMove = Constants.takeAction(rightWeights,new double[]{r /
		// HEIGHT,y / HEIGHT}) * 2 * SPEED - SPEED;
		double rToMove = 0.75;
		if (r > y) {
			rToMove = -0.75;
		}

		// update paddle
		l = Math.min(Math.max(l + lToMove, PADDLE / 2), HEIGHT - PADDLE / 2);
		r = Math.min(Math.max(r + rToMove, PADDLE / 2), HEIGHT - PADDLE / 2);

		// update ball
		moveBall();

		// Check results of update.
		if (x < 0) {
			rScore++;
			reset(false);
		}
		if (x > WIDTH) {
			lScore++;
			reset(true);
		}
	}

	private void moveBall() {
		// Bounces off edge or in goal.
		if (x + xv > WIDTH) {
			if (y + yv * (WIDTH - x) / xv + BALL_RAD >= r - PADDLE / 2
					&& y + yv * (WIDTH - x) / xv - BALL_RAD <= r + PADDLE / 2) {
				xv *= -1.05;
				yv *= 1.05;
				x = 2 * WIDTH - x + xv;
			} else {
				x += xv;
			}
		}

		// Bounces off edge or in goal.
		else if (x + xv < 0) {
			if (y + yv * x / xv + BALL_RAD >= l - PADDLE / 2 && y + yv * x / xv - BALL_RAD <= l + PADDLE / 2) {
				xv *= -1.05;
				yv *= 1.05;

				x = xv - x;
			} else {
				x += xv;
			}
		} else {
			x += xv;
		}

		// Bounces off roof and floor.
		if (y + yv > HEIGHT) {
			yv *= -1;
			y = 2 * HEIGHT + yv - y;
		} else if (y + yv < 0) {
			yv *= -1;
			y = yv - y;
		} else {
			y += yv;
		}
	}

	// resets after a goal is scored.
	private void reset(boolean swap) {
		x = WIDTH / 2;
		y = HEIGHT / 2;
		l = HEIGHT / 2;
		r = HEIGHT / 2;
		double ang = Math.random() * BALL_START_RANGE * 2 - BALL_START_RANGE;
		xv = Math.cos(ang) * BALL_START_SPEED;
		yv = Math.sin(ang) * BALL_START_SPEED;
		// which way the ball intially starts.
		if (swap)
			xv *= -1;
	}

	// draws the gameState.
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);

		int paddleWidth = 5;

		g.setColor(Color.white);
		g.fillRect(x, (int) (y + (l - PADDLE / 2) * height / HEIGHT), paddleWidth * width / WIDTH,
				PADDLE * height / HEIGHT);
		g.fillRect(x + width - paddleWidth * width / WIDTH, (int) (y + (r - PADDLE / 2) * height / HEIGHT),
				paddleWidth * width / WIDTH, PADDLE * height / HEIGHT);

		g.fillOval((int) (x + (this.x - BALL_RAD) * width / WIDTH), (int) (y + (this.y - BALL_RAD) * height / HEIGHT),
				BALL_RAD * 2 * width / WIDTH, BALL_RAD * 2 * height / HEIGHT);

		Font f = new Font("timesroman", Font.PLAIN, width / 10);
		g.drawString("" + lScore, width / 2 - width / 5, width / 5);
		g.drawString("" + rScore, width / 2 + width / 5, width / 5);
	}

	// Gamestate constructor with params being the weight index.
	public GameState(int left, int right) {
		leftWeights = new double[Constants.WEIGHTS];
		rightWeights = new double[Constants.WEIGHTS];
		assignWeights(leftWeights, left);
		assignWeights(rightWeights, right);
		reset(Math.random() > 0.5);
	}

	private void assignWeights(double[] weights, int num) {
		String file;
		file = "weights/Weights" + num + ".txt";
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			for (int i = 0; i < Constants.WEIGHTS; i++) {
				String s = bufferedReader.readLine();
				weights[i] = Double.parseDouble(s);
			}
			bufferedReader.close();

		} catch (IOException e) {
			System.out.println("Unable to open file '" + file + "'.");
		}
	}
}
