/**
 * File containing the RandomWalksModel entity definition. 
 */

package pai.pract10.randomwalks.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Class which represents the RandomWalks program model. It was created for the
 * tenth practice of PAI (Programación de Aplicaciones Interactivas) course of
 * ULL (Universidad de la Laguna).
 * 
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 13 abr. 2018
 */
public class RandomWalksModel {

	/** Random walks of the program. */
	ArrayList<RandomWalk>	randomWalks;
	/** Establishes the amount of rows of the space that wraps the random walks. */
	private int rows;
	/** Establishes the amount of columns of the space that wraps the random walks. */
	private int columns;
	/** Establishes if the collisions between random walks are allowed. */
	private boolean allowCollisions;

	/**
	 * Default constructor. Initializes the random walks.
	 * @param rows Amount of rows of the space that wraps the random walks.
	 * @param columns Amount of columns of the space that wraps the random walks.
	 * @param allowCollisions Establishes if the collisions between random walks are allowed.
	 */
	public RandomWalksModel(int rows, int columns, boolean allowCollisions) {
		this.rows = rows;
		this.columns = columns;
		this.allowCollisions = allowCollisions;
		randomWalks = new ArrayList<RandomWalk>();
		RandomWalk.occupiedPositions = new boolean[rows][columns];
	}
	
	/**
	 * Simulates an iteration, every random walk calculates a new point.
	 * @return New final points.
	 */
	public ArrayList<Point> calculateNextPoints() {
		ArrayList<Point> nextPoints = new ArrayList<Point>();
		for (RandomWalk randomWalk: randomWalks) {
			
			Point nextPoint = randomWalk.nextPoint();
			if (nextPoint != null) {
				nextPoints.add(nextPoint);
			} else {
				nextPoints.add(new Point(-1, -1));
			}
		}
		return nextPoints;
	}

	/**
	 * Adds a new random walk.
	 * @param startingPoint Starting position of the new random walk.
	 */
	public void addRandomWalk(Point startingPoint) {
		if (!RandomWalk.occupiedPositions[(int) startingPoint.getX()][(int) startingPoint.getY()]) {
			randomWalks.add(new RandomWalk((int) startingPoint.getX(), (int) startingPoint.getY(), rows, columns, allowCollisions));
		}
	}
}
