/**
 * File containing the RandomWalk entity definition. 
 */

package pai.pract10.randomwalks.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents a random walk of the RandomWalks program. It was
 * created for the tenth practice of PAI (Programación de Aplicaciones
 * Interactivas) course of ULL (Universidad de la Laguna).
 * 
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 14 abr. 2018
 */
public class RandomWalk {
	
	/** Establishes the amount of rows of the space that wraps the random walk. */
	private int rows;
	/** Establishes the amount of columns of the space that wraps the random walk. */
	private int columns;
	/** The row of the last point of this random walk. */
	private int currentRow;
	/** The column of the last point of this random walk. */
	private int currentColumn;
	/** Establishes if the random walk has finished. */
	private boolean finished;
	/** Establishes if the collisions between random walks are allowed. */
	private boolean allowCollisions;
	/** Specifies the occupied positions. */
	public static boolean[][] occupiedPositions;
	/** The column of previous point of the last point of this random walk. */
	private int oldRow;
	/** The column of previous point of the last point of this random walk. */
	private int oldColumn;
	
	/**
	 * Default constructor.
	 * @param startingRow Starting row of this random walk.
	 * @param startingColumn Starting column of this random walk.
	 * @param rows Total rows.
	 * @param columns Total columns.
	 * @param allowCollisions Establishes if the collisions between random walks are allowed.
	 */
	public RandomWalk(int startingRow, int startingColumn, int rows, int columns, boolean allowCollisions) {
		setRows(rows);
		setColumns(columns);
		setCurrentRow(startingRow);
		setCurrentColumn(startingColumn);
		setOldColumn(-1);
		setOldRow(-1);
		setFinished(false);
		setAllowCollisions(allowCollisions);
		if (!allowCollisions) {
			occupiedPositions[getCurrentColumn()][getCurrentRow()] = true;
		}
	}
	
	/**
	 * Calculates randomly a new point for the random walk.
	 * @return Coordinates of the new point.
	 */
	public Point nextPoint() {
		checkState();
		if (isFinished()) {
			return null;
		}
		ArrayList<Point> possibleNextPoints = getPossibleNextPoints();
		if (possibleNextPoints.size() == 0) {
			setFinished(true);
			return null;
		}
		Random random = new Random();
		Point newPoint = possibleNextPoints.get(random.nextInt(possibleNextPoints.size()));
		setOldRow(getCurrentRow());
		setOldColumn(getCurrentColumn());
		setCurrentRow((int)newPoint.getX());
		setCurrentColumn((int)newPoint.getY());
		if (!allowCollisions) {
			occupiedPositions[getCurrentColumn()][getCurrentRow()] = true;
		}
		return newPoint;
	}
	
	/**
	 * Establishes if the random walk should be finished or not.
	 */
	public void checkState() {
		if(currentRow < 0 || currentColumn < 0 || currentRow > rows || currentColumn > columns) {
			setFinished(true);
		}
	}
	
	/**
	 * Returns the possible next points of this random walk.
	 * @return Possible next points.
	 */
	public ArrayList<Point> getPossibleNextPoints() {
		ArrayList<Point> possibleNextPoints = new ArrayList<Point>();
		if (allowCollisions || (currentRow > 0 && !occupiedPositions[currentRow - 1][currentColumn])) {
			if (allowCollisions || currentRow - 1 != oldRow) {
				possibleNextPoints.add(new Point(currentRow - 1, currentColumn));
			}
		}
		if (allowCollisions || (currentRow < rows - 1 && !occupiedPositions[currentRow + 1][currentColumn])) {
			if (allowCollisions || currentRow + 1 != oldRow) {
				possibleNextPoints.add(new Point(currentRow + 1, currentColumn));
			}
		}
		if (allowCollisions || (currentColumn > 0 && !occupiedPositions[currentRow][currentColumn - 1])) {
			if (allowCollisions || currentColumn - 1 != oldColumn) {
				possibleNextPoints.add(new Point(currentRow , currentColumn - 1));
			}
		}
		if (allowCollisions || (currentColumn < columns - 1 && !occupiedPositions[currentRow][currentColumn + 1])) {
			if (allowCollisions || currentColumn + 1 != oldColumn) {
				possibleNextPoints.add(new Point(currentRow, currentColumn + 1));
			}
		}
		return possibleNextPoints;
	}

	/**
	 * Getter method for rows attribute.
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Getter method for columns attribute.
	 * @return columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Getter method for currentRow attribute.
	 * @return currentRow
	 */
	public int getCurrentRow() {
		return currentRow;
	}

	/**
	 * Getter method for currentColumn attribute.
	 * @return currentColumn
	 */
	public int getCurrentColumn() {
		return currentColumn;
	}

	/**
	 * Setter method for rows attribute.
	 * @param rows 
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Setter method for columns attribute.
	 * @param columns 
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * Setter method for currentRow attribute.
	 * @param currentRow 
	 */
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	/**
	 * Setter method for currentColumn attribute.
	 * @param currentColumn 
	 */
	public void setCurrentColumn(int currentColumn) {
		this.currentColumn = currentColumn;
	}

	/**
	 * Getter method for finished attribute.
	 * @return finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Setter method for finished attribute.
	 * @param finished 
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * Getter method for allowCollisions attribute.
	 * @return allowCollisions
	 */
	public boolean isAllowCollisions() {
		return allowCollisions;
	}

	/**
	 * Setter method for allowCollisions attribute.
	 * @param allowCollisions 
	 */
	public void setAllowCollisions(boolean allowCollisions) {
		this.allowCollisions = allowCollisions;
	}

	/**
	 * Getter method for oldRow attribute.
	 * @return oldRow
	 */
	public int getOldRow() {
		return oldRow;
	}

	/**
	 * Getter method for oldColumn attribute.
	 * @return oldColumn
	 */
	public int getOldColumn() {
		return oldColumn;
	}

	/**
	 * Setter method for oldRow attribute.
	 * @param oldRow 
	 */
	public void setOldRow(int oldRow) {
		this.oldRow = oldRow;
	}

	/**
	 * Setter method for oldColumn attribute.
	 * @param oldColumn 
	 */
	public void setOldColumn(int oldColumn) {
		this.oldColumn = oldColumn;
	}

}
