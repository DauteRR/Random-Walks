/**
 * File containing the RandomWalksPanel entity definition. 
 */

package pai.pract10.randomwalks.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class which represents a panel to draw a random walk. It shows the points of
 * each walk. It was created for the tenth practice of PAI (Programación de
 * Aplicaciones Interactivas) course of ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 13 abr. 2018
 */
public class RandomWalksPanel extends JPanel {

	/** Default serial version ID. */
	private static final long		serialVersionUID	= 1L;
	/** Establishes the approximate amount of points of the RandomWalksPanel. */
	private int									amountOfPoints;
	/** Establishes the amount of rows of the RandomWalksPanel. */
	private int									rows;
	/** Establishes the amount of columns of the RandomWalksPanel. */
	private int									columns;
	/** Stores the different walks to draw, with their respective points. */
	private ArrayList<Polygon>	walks;
	/** Establishes the size in pixels of a walk portion in X axis. */
	private int									xSegmentSize;
	/** Establishes the size in pixels of a walk portion in Y axis. */
	private int									ySegmentSize;
	/** Available colors for walks representation. */
	private final Color[]				AVAILABLE_COLORS	= { Color.BLUE, Color.RED,
			Color.CYAN, Color.YELLOW, Color.ORANGE, Color.GREEN, Color.MAGENTA };
	/** Establishes the color of the walks. */
	private int									FIRST_WALK_INDEX_COLOR;
	/** Attribute used to draw the walks. */
	private int						DIAMETER;
	/** Attribute used to draw the walks. */
	private int						RADIUS;
	/** Epsilon for floating point numbers comparison. */
	private final double				EPSILON						= 0.0001;
	/** Establishes if the grid must be drawn. */
	private boolean visibleGrid;

	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the shape panel.
	 * @param height
	 *          Height of the shape panel.
	 * @param panelName
	 *          Name of the panel.
	 * @param mouseListener
	 *          Mouse listener object for control RandomWalksPanel clicks.
	 */
	public RandomWalksPanel(int width, int height, String panelName,
			MouseListener mouseListener) {
		visibleGrid = false;
		setName(panelName);
		setPreferredSize(new Dimension(width, height));
		addMouseListener(mouseListener);
		changeColors();
		walks = new ArrayList<Polygon>();
	}

	/**
	 * Adds a new Random Walk to the RandomWalksPanel.
	 * 
	 * @param xStartPosition
	 *          X coordinate of the starting position of the new Random Walk.
	 * @param yStartPosition
	 *          Y coordinate of the starting position of the new Random Walk.
	 */
	public void addRandomWalk(int xStartPosition, int yStartPosition) {
		if (xStartPosition > -1 && xStartPosition <= getColumns() * xSegmentSize
				&& yStartPosition > -1 && yStartPosition <= getRows() * ySegmentSize) {
			Polygon newWalk = new Polygon();
			newWalk.addPoint(xStartPosition, yStartPosition);
			walks.add(newWalk);
		} else {
			throw new IllegalArgumentException(
					"Invalid starting position for a new RandomWalk : (" + xStartPosition
							+ ", " + yStartPosition + ")");
		}
		repaint();
	}

	/**
	 * Adds a point to each walk excepting those which has finished.
	 * 
	 * @param newPoints
	 *          Points to add.
	 */
	public void addNewPoints(ArrayList<Point> newPoints) {
		final int FINISHED_WALK_COORD = -1;
		for (int i = 0; i < newPoints.size(); ++i) {
			Polygon currentWalk = walks.get(i);
			if (newPoints.get(i).getX() == FINISHED_WALK_COORD && newPoints.get(i).getY() == FINISHED_WALK_COORD) {
				continue;
			} else {
				currentWalk.addPoint((int) newPoints.get(i).getX() * xSegmentSize,
						(int) newPoints.get(i).getY() * ySegmentSize);
			}
		}
		repaint();
	}

	/**
	 * Checks if two given points can conform a walk portion.
	 * 
	 * @param prevPoint
	 *          First point.
	 * @param nextPoint
	 *          Second point.
	 * @return Result.
	 */
	public boolean isValidNextPoint(Point prevPoint, Point nextPoint) {
		System.out.println(prevPoint);
		System.out.println(nextPoint);
		boolean result = false;
		if (nextPoint.getX() < 0 || nextPoint.getY() < 0) {
			return result;
		}
		if (Math.abs(prevPoint.getX() - nextPoint.getX()) < EPSILON
				&& Math
						.abs(prevPoint.getY() + ySegmentSize - nextPoint.getY()) < EPSILON
				&& (nextPoint.getY() < getPreferredSize().getHeight() || Math.abs(
						nextPoint.getY() - getPreferredSize().getHeight()) < EPSILON)) {
			result = true;
		} else if (Math.abs(prevPoint.getX() - nextPoint.getX()) < EPSILON
				&& Math
						.abs(prevPoint.getY() - ySegmentSize - nextPoint.getY()) < EPSILON
				&& (nextPoint.getY() < getPreferredSize().getHeight() || Math.abs(
						nextPoint.getY() - getPreferredSize().getHeight()) < EPSILON)) {
			result = true;
		} else if (Math.abs(prevPoint.getY() - nextPoint.getY()) < EPSILON
				&& Math
						.abs(prevPoint.getX() + xSegmentSize - nextPoint.getX()) < EPSILON
				&& (nextPoint.getX() < getPreferredSize().getWidth() || Math
						.abs(nextPoint.getX() - getPreferredSize().getWidth()) < EPSILON)) {
			result = true;
		} else if (Math.abs(prevPoint.getY() - nextPoint.getY()) < EPSILON
				&& Math
						.abs(prevPoint.getX() - xSegmentSize - nextPoint.getX()) < EPSILON
				&& (nextPoint.getX() < getPreferredSize().getWidth() || Math
						.abs(nextPoint.getX() - getPreferredSize().getWidth()) < EPSILON)) {
			result = true;
		}
		return result;
	}

	/**
	 * Paints the panel in the graphics object given as a parameter.
	 * 
	 * @param g
	 *          Graphic object where the walks will be painted.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if (amountOfPoints < 4) {
			return;
		}
		if (isVisibleGrid()) {
			paintGrid(g);	
		}
		for (int i = 0; i < walks.size(); ++i) {
			g.setColor(AVAILABLE_COLORS[(FIRST_WALK_INDEX_COLOR + i)
					% AVAILABLE_COLORS.length]);
			drawWalk(g, walks.get(i));
		}
	}

	/**
	 * Paints the grid in the graphics object given as a parameter.
	 * 
	 * @param g
	 *          Graphic object where the walks will be painted.
	 */
	public void paintGrid(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < rows; ++i) {
			g.drawLine(0, i * ySegmentSize, rows * xSegmentSize,i * ySegmentSize);
		}
		for (int i = 0; i < columns; ++i) {
			g.drawLine(i * xSegmentSize, 0, i * xSegmentSize,columns* ySegmentSize);
		}
		
	}

	/**
	 * Draws a walk.
	 * 
	 * @param g
	 *          Graphic object where the walks will be painted.
	 * @param walk
	 *          Walk to draw.
	 */
	public void drawWalk(Graphics g, Polygon walk) {
		int[] xPoints = walk.xpoints;
		int[] yPoints = walk.ypoints;
		if (walk.npoints < 2) {
			g.fillOval(xPoints[0] - RADIUS, yPoints[0] - RADIUS, DIAMETER, DIAMETER);
			return;
		}
		int xOriginPoint, yOriginPoint, xDestinationPoint, yDestinationPoint;
		for (int i = 0; i < walk.npoints - 1; ++i) {
			
			if (xPoints[i + 1] == -1 && yPoints[i + 1] == -1) {
				break;
			}
			
			if (xPoints[i] == xPoints[i + 1]) {
				if (yPoints[i] < yPoints[i + 1]) {
					yOriginPoint = yPoints[i];
					yDestinationPoint = yPoints[i + 1];
				} else {
					yOriginPoint = yPoints[i + 1];
					yDestinationPoint = yPoints[i];
				}
				xOriginPoint = xDestinationPoint = xPoints[i];
			} else if (yPoints[i] == yPoints[i + 1]) {
				if (xPoints[i] < xPoints[i + 1]) {
					xOriginPoint = xPoints[i];
					xDestinationPoint = xPoints[i + 1];
				} else {
					xOriginPoint = xPoints[i + 1];
					xDestinationPoint = xPoints[i];
				}
				yOriginPoint = yDestinationPoint = yPoints[i];
			} else {
				System.err.println(xPoints[i] + " " + yPoints[i]);
				System.err.println(xPoints[i + 1] + " " + yPoints[i + 1]);
				throw new IllegalArgumentException("Trying to draw a strange walk!");
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(RADIUS));
			g2.draw(new Line2D.Float(xOriginPoint, yOriginPoint, xDestinationPoint,
					yDestinationPoint));
		}
	}
	
	/**
	 * Calculates the equivalent starting point and adds a new random walk.
	 * @param xStartingPosition X axis starting position point.
	 * @param yStartingPosition Y axis starting position point.
	 * @return Model starting position.
	 */
	public Point newRandomWalk(int xStartingPosition, int yStartingPosition) { 
		int row = 0, column = 0;
		for (int i = 0; i <= columns; ++i) {
			if (i * xSegmentSize >= xStartingPosition) {
				column = i - 1;
				break;
			}
		}
		for (int j = 0; j <= rows; ++j) {
			if (j * ySegmentSize >= yStartingPosition) {
				row = j - 1;
				break;
			}
		}
		final int X_SEGMENT_MIDDLE = xSegmentSize / 2;
		final int Y_SEGMENT_MIDDLE = ySegmentSize / 2;
		if (xStartingPosition - (column * xSegmentSize) > X_SEGMENT_MIDDLE) {
			column++;
		}
		if (yStartingPosition - (row * ySegmentSize) > Y_SEGMENT_MIDDLE) {
			row++;
		}
		addRandomWalk(column * xSegmentSize, row * ySegmentSize);
		return new Point(column, row);
	}

	/**
	 * Getter method for amountOfPoints attribute.
	 * 
	 * @return amountOfPoints
	 */
	public int getAmountOfPoints() {
		return amountOfPoints;
	}

	/**
	 * Getter method for rows attribute.
	 * 
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Getter method for columns attribute.
	 * 
	 * @return columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Setter method for amountOfPoints attribute.
	 * 
	 * @param amountOfPoints
	 */
	public void setAmountOfPoints(int amountOfPoints) {
		if (amountOfPoints < 4) {
			throw new IllegalArgumentException("Wrong amount of points.");
		}
		this.amountOfPoints = amountOfPoints;
		final int NEEDED_ROWS_AND_COLUMNS = (int) Math.sqrt(amountOfPoints) - 1;
		setColumns(NEEDED_ROWS_AND_COLUMNS);
		setRows(NEEDED_ROWS_AND_COLUMNS);
		setxSegmentSize((int) (getPreferredSize().getWidth() / getColumns()));
		setySegmentSize((int) (getPreferredSize().getHeight() / getRows()));
		DIAMETER = (int) (getxSegmentSize() * 0.4);
		RADIUS = DIAMETER / 2;
		repaint();
	}

	/**
	 * Changes randomly the colors of this RandomWalksPanel.
	 */
	public void changeColors() {
		FIRST_WALK_INDEX_COLOR++;
		repaint();
	}

	/**
	 * Setter method for rows attribute.
	 * 
	 * @param rows
	 */
	private void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Setter method for columns attribute.
	 * 
	 * @param columns
	 */
	private void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * Getter method for xSegmentSize attribute.
	 * 
	 * @return xSegmentSize
	 */
	public int getxSegmentSize() {
		return xSegmentSize;
	}

	/**
	 * Getter method for ySegmentSize attribute.
	 * 
	 * @return ySegmentSize
	 */
	public int getySegmentSize() {
		return ySegmentSize;
	}

	/**
	 * Setter method for xSegmentSize attribute.
	 * 
	 * @param xSegmentSize
	 */
	public void setxSegmentSize(int xSegmentSize) {
		this.xSegmentSize = xSegmentSize;
	}

	/**
	 * Setter method for ySegmentSize attribute.
	 * 
	 * @param ySegmentSize
	 */
	public void setySegmentSize(int ySegmentSize) {
		this.ySegmentSize = ySegmentSize;
	}

	/**
	 * Getter method for visibleGrid attribute.
	 * @return visibleGrid
	 */
	public boolean isVisibleGrid() {
		return visibleGrid;
	}

	/**
	 * Setter method for visibleGrid attribute.
	 */
	public void changeGridVisibility() {
		visibleGrid = !visibleGrid;
		repaint();
	}
}
