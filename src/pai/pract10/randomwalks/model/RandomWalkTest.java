/**
 * File containing the RandomWalkTest entity definition. 
 */

package pai.pract10.randomwalks.model;

import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Class which tests the behavior of the RandomWalk class.
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 14 abr. 2018
 */
public class RandomWalkTest {

	/**
	 * Test method for
	 * {@link pai.pract10.randomwalks.model.RandomWalk#nextPoint()}.
	 */
	@Test
	public final void testNextPoint() {
		final int ROWS = 3;
		final int COLUMNS = 3;
		Point[] possibleNextPoints = { new Point(0, 1), new Point(1, 0), new Point(-1, 0), new Point(0, -1) };
		RandomWalk randomWalk = new RandomWalk(0, 0, ROWS, COLUMNS, true);
		Point nextPoint = randomWalk.nextPoint();
		boolean checker = (nextPoint.equals(possibleNextPoints[0])
				|| nextPoint.equals(possibleNextPoints[1])
				|| nextPoint.equals(possibleNextPoints[2])
				|| nextPoint.equals(possibleNextPoints[3]));
		assertTrue(checker);
	}
	
	/**
	 * Test method for
	 * {@link pai.pract10.randomwalks.model.RandomWalk#getPossibleNextPoints()}.
	 */
	@Test
	public final void testGetPossibleNextPoints() {
		final int ROWS = 12;
		final int COLUMNS = 12;
		final int currentColumn = 3;
		final int currentRow = 0;
		ArrayList<Point> possibleNextPoints = new ArrayList<Point>();
		possibleNextPoints.add(new Point(-1, 3));
		possibleNextPoints.add(new Point(1, 3));
		possibleNextPoints.add(new Point(0, 2));
		possibleNextPoints.add(new Point(0, 4));
		RandomWalk randomWalk = new RandomWalk(currentRow, currentColumn, ROWS, COLUMNS, true);
		assertTrue(possibleNextPoints.equals(randomWalk.getPossibleNextPoints()));
		final int NEW_ROW = 4;
		final int NEW_COLUMN = 11;
		randomWalk.setCurrentRow(NEW_ROW);
		randomWalk.setCurrentColumn(NEW_COLUMN);
		possibleNextPoints.clear();
		possibleNextPoints.add(new Point(3, 11));
		possibleNextPoints.add(new Point(5, 11));
		possibleNextPoints.add(new Point(4, 10));
		possibleNextPoints.add(new Point(4, 12));
		assertTrue(possibleNextPoints.equals(randomWalk.getPossibleNextPoints()));
	}

}
