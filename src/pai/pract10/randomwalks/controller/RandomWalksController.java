/**
 * File containing the RandomWalksController entity definition. 
 */

package pai.pract10.randomwalks.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pai.pract10.randomwalks.model.RandomWalksModel;
import pai.pract10.randomwalks.view.RandomWalksView;

/**
 * Class which represents the RandomWalks program controller. It establishes\n
 * the communication between the model and the view. It was created for the
 * tenth practice of PAI (Programación de Aplicaciones Interactivas) course of
 * ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 13 abr. 2018
 */
public class RandomWalksController implements ActionListener, MouseListener, ChangeListener {
	/**
	 * Auxiliary class created to act as the timer action listener. It was created 
	 * for the tenth practice of PAI (Programación de Aplicaciones Interactivas) 
	 * course of ULL (Universidad de la Laguna).
	 * 
	 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
	 * @version 1.0
	 * @since 15 abr. 2018
	 */
	class TimerListener implements ActionListener {
		/**
		 * Handles the events thrown by the timer.
		 * 
		 * @param e Event thrown by the timer.
		 */
    @Override
		public void actionPerformed(ActionEvent e) {
    	simulationSteps++;
			view.getRandomWalksPanel().addNewPoints(model.calculateNextPoints());
			view.getControlPanel().setMessage("Step " + simulationSteps, false);
    }
  }

	/** Model of the RandomWalks program. */
	RandomWalksModel	model;
	/** View of the RandomWalks program. */
	RandomWalksView		view;
	/** Amount of expected random walks to enter. */
	private int				expectedRandomWalks;
	/** Amount of remaining random walks to enter. */
	private int				remainingRandomWalks;
	/** GUI's width. */
	private final int GUI_WIDTH;
	/** GUI's height. */
	private final int GUI_HEIGHT;
	/** Number of iterations simulated. */
	private int simulationSteps;
	/** Timer for the simulation. */
	private Timer timer;
	/** Timer delay. */
	private int timerDelay;
	/** Establishes if the collisions between random walks are allowed. */
	private boolean allowCollisions;

	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the GUI.
	 * @param height
	 *          Height of the GUI.
	 */
	public RandomWalksController(int width, int height) {
		GUI_HEIGHT = height;
		GUI_WIDTH = width;
		view = new RandomWalksView(GUI_WIDTH, GUI_HEIGHT, this, this, this);
		remainingRandomWalks = 0;
		simulationSteps = 0;
		timerDelay = 100;
		allowCollisions = true;
	}
	
	/**
	 *  Starts the simulation. 
	 */
	public void startSimulation() {
		view.getControlPanel().startState();
		timer = new Timer(timerDelay, new TimerListener());
		timer.start();
	}
	
	/**
	 *  Stops the simulation. 
	 */
	public void stopSimulation() {
		view.getControlPanel().stopState();
		timer.stop();
	}
	
	/**
	 * Initializes the model of the RandomWalks program.
	 */
	public void initializeModel() {
		model = new RandomWalksModel(view.getRandomWalksPanel().getRows() + 1, view.getRandomWalksPanel().getColumns() + 1, allowCollisions);
	}

	/**
	 * Handles the events thrown by the GUI components.
	 * 
	 * @param e
	 *          Event thrown.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Restart")) {
			view = new RandomWalksView(GUI_WIDTH, GUI_HEIGHT, this, this, this);
			remainingRandomWalks = 0;
			simulationSteps = 0;
			timerDelay = 100;
		} else if (e.getActionCommand().equals("Change colors")) {
			view.getRandomWalksPanel().changeColors();
		} else if (e.getActionCommand().equals("Grid visibility")) {
			view.getRandomWalksPanel().changeGridVisibility();
		} else if (e.getActionCommand().equals("Allow collisions")) {
			allowCollisions = !allowCollisions;
		} else if (e.getActionCommand().equals("Points density")) {
			int introducedPointsDensity = view.getControlPanel().getPointsDensity();
			if (introducedPointsDensity == -1) {
				return;
			}
			view.getRandomWalksPanel().setAmountOfPoints(introducedPointsDensity);
			initializeModel();
		} else if (e.getActionCommand().equals("Random walks")) {
			int introducedRandomWalks = view.getControlPanel().getRandomWalks();
			if (introducedRandomWalks == -1) {
				return;
			}
			expectedRandomWalks = introducedRandomWalks;
			remainingRandomWalks = expectedRandomWalks;
			view.getControlPanel().setMessage(
					"Click on the \nRandomWalksPanel to \nestablish the initial \nposition of each \nrandom walk ("
							+ remainingRandomWalks + " left)",
					false);
		} else if (e.getActionCommand().equals("Stop introducing")) {
			if (remainingRandomWalks == expectedRandomWalks) {
				view.getControlPanel().setMessage("You must introduce \nat least one \nrandom walk.",
						true);
				return;
			}
			remainingRandomWalks = 0;
			view.getControlPanel().readyState();
		} else if (e.getActionCommand().equals("Generate randomly")) {
			Random random = new Random();
			for (int i = 0; i < remainingRandomWalks; ++i) {
				Point randomPoint = view.getRandomWalksPanel().newRandomWalk(
						random
								.nextInt((int) view.getRandomWalksPanel().getSize().getWidth()),
						random.nextInt(
								(int) view.getRandomWalksPanel().getSize().getHeight()));
				model.addRandomWalk(randomPoint);
				view.getControlPanel().readyState();
			}
		} else if (e.getActionCommand().equals("Stop simulation")) {
			stopSimulation();
		} else if (e.getActionCommand().equals("Start")) {
			startSimulation();
		} else if (e.getActionCommand().equals("Finish")) {
			view.getControlPanel().finishedState();
		} else if (e.getActionCommand().equals("Next")) {
			simulationSteps++;
			view.getRandomWalksPanel().addNewPoints(model.calculateNextPoints());
			view.getControlPanel().setMessage("Step " + simulationSteps, false);
		}
	}

	/**
	 * Handles the mouse events produced when the users clicks on the RandomWalksPanel.
	 * @param e Mouse events thrown by the user.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (remainingRandomWalks > 0) {
			Point initialPoint = view.getRandomWalksPanel().newRandomWalk(e.getX(), e.getY());
			model.addRandomWalk(initialPoint);
			remainingRandomWalks--;
			if (remainingRandomWalks > 0) {
				view.getControlPanel().setMessage(
						"Click on the \nRandomWalksPanel to \nestablish the initial \nposition of each \nrandom walk ("
								+ remainingRandomWalks + " left)",
						false);
			} else {
				view.getControlPanel().readyState();
			}
		}
	}

	/**
	 * Handles the slider events produced when the users moves the slider on the ControlPanel.
	 * @param e ChangeEvent of the slider.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
        timerDelay = source.getValue();
        timer.setDelay(timerDelay);
    }
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		;
	}
}
