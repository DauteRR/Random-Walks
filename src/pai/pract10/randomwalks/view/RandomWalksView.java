/**
 * File containing the RandomWalksView entity definition. 
 */

package pai.pract10.randomwalks.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

/**
 * Class which represents the RandomWalks program view. It shows the GUI of the
 * program. It was created for the tenth practice of PAI (Programación de
 * Aplicaciones Interactivas) course of ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 13 abr. 2018
 */
public class RandomWalksView extends JFrame {
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	/** Random walks panel. */
	private RandomWalksPanel randomWalksPanel;
	/** Control panel. */
	private ControlPanel controlPanel;

	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the GUI.
	 * @param height
	 *          Height of the GUI.
	 * @param actionListener
	 * 					Action listener object for control panel buttons.
	 * @param mouseListener
	 * 					Mouse listener object for control RandomWalksPanel clicks.
	 * @param changeListener
	 * 					Listener object for control panel slider.
	 */
	public RandomWalksView(int width, int height, ActionListener actionListener, MouseListener mouseListener, ChangeListener changeListener) {
		setName("Random Walks!");
		setPreferredSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		final int RANDOM_WALKS_WIDTH = (int) (width * 0.8);
		randomWalksPanel = new RandomWalksPanel(RANDOM_WALKS_WIDTH, height,
				"RandomWalksPanel", mouseListener);
		mainPanel.add(randomWalksPanel);
		controlPanel = new ControlPanel(width - RANDOM_WALKS_WIDTH, height, "Control panel", actionListener, changeListener);
		mainPanel.add(controlPanel);
		pack();
		this.add(mainPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Returns the random walks panel.
	 * @return Random walks panel.
	 */
	public RandomWalksPanel getRandomWalksPanel() {
		return randomWalksPanel;
	}

	/**
	 * Getter method for controlPanel attribute.
	 * @return controlPanel
	 */
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	
}
