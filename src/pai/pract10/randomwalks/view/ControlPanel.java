/**
 * File containing the ControlPanel entity definition. 
 */

package pai.pract10.randomwalks.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 * Class which represents the panel where the control buttons of the RandomWalks
 * program GUI will be located. It was created for the tenth practice of PAI
 * (Programación de Aplicaciones Interactivas) course of ULL (Universidad de la
 * Laguna).
 * 
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 15 abr. 2018
 */
public class ControlPanel extends JPanel {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	/** Start button of the control panel. */
	private JButton startButton;
	/** Finish button of the control panel. */
	private JButton finishButton;
	/** Next button of the control panel. */
	private JButton nextButton;
	/** Stop button of the control panel. */
	private JButton stopButton;
	/** Random button of the control panel. */
	private JButton randomButton;
	/** Visible grid check box of the control panel. */ 
	private JCheckBox visibleGridCheckBox;
	/** Points density text of the control panel. */
	private JLabel pointsDensityLabel;
	/** Points density text field of the control panel. */
	private JTextField pointsDensityTextField;
	/** Random walks text of the control panel. */
	private JLabel randomWalksLabel;
	/** Random walks text field of the control panel. */
	private JTextField randomWalksTextField;
	/** Enter button of the control panel. */
	private JButton enterButton;
	/** Change colors button of the control panel. */
	private JButton changeColorsButton;
	/** Message area of the control panel. */
	private JTextArea messagesTextArea;
	/** Restart button of the control panel. */
	private JButton restartButton;
	/** Timer label of the control panel. */
	private JLabel timerLabel;
	/** Timer slider of the control panel. */
	private JSlider timerSlider;
	
	/**
	 * Default constructor.
	 * 
	 * @param width
	 *          Width of the shape panel.
	 * @param height
	 *          Height of the shape panel.
	 * @param panelName
	 *          Name of the panel.
	 * @param actionListener
	 * 					Listener object for control panel buttons.
	 * @param changeListener
	 * 					Listener object for control panel slider.
	 */
	public ControlPanel(int width, int height, String panelName, ActionListener actionListener, ChangeListener changeListener) {
		setName(panelName);
		setPreferredSize(new Dimension(width, height));
		
		startButton = new JButton("Start");
		startButton.addActionListener(actionListener);
		
		finishButton = new JButton("Finish");
		finishButton.addActionListener(actionListener);

		nextButton = new JButton("Next");
		nextButton.addActionListener(actionListener);
		
		stopButton = new JButton("Stop introducing");
		stopButton.setActionCommand("Stop introducing");
		stopButton.addActionListener(actionListener);
		
		randomButton = new JButton("Generate randomly");
		randomButton.addActionListener(actionListener);
		
		visibleGridCheckBox = new JCheckBox("Visible grid");
		visibleGridCheckBox.addActionListener(actionListener);
		visibleGridCheckBox.setActionCommand("Grid visibility");
		visibleGridCheckBox.setSelected(false);
		
		timerLabel = new JLabel("Iterations timer(ms)");
		final int MS_MIN = 1;
		final int MS_MAX = 1001;
		final int MS_STARTING = 100;
		final int MINOR_SPACING = 50;
		final int MAJOR_SPACING = 200;
		timerSlider = new JSlider(MS_MIN, MS_MAX, MS_STARTING);
		timerSlider.setMajorTickSpacing(MAJOR_SPACING);
		timerSlider.setMinorTickSpacing(MINOR_SPACING);
		timerSlider.setPaintTicks(true);
		timerSlider.setPaintLabels(true);
		timerSlider.addChangeListener(changeListener);
		
		pointsDensityLabel = new JLabel("Points density:");
		final int TEXTFIELD_COLUMNS = 4;
		pointsDensityTextField = new JTextField(TEXTFIELD_COLUMNS);
		pointsDensityTextField.addActionListener(actionListener);
		pointsDensityTextField.setText("160000");
		
		randomWalksLabel = new JLabel("Random walks:");
		final int TEXTFIELD_COLUMNS2 = 2;
		randomWalksTextField = new JTextField(TEXTFIELD_COLUMNS2);
		randomWalksTextField.addActionListener(actionListener);
		randomWalksTextField.setText("50");
		
		enterButton = new JButton("Enter");
		enterButton.setActionCommand("Points density");
		enterButton.addActionListener(actionListener);
		
		changeColorsButton = new JButton("Change colors");
		changeColorsButton.addActionListener(actionListener);
		
		final int ROWS = 3;
		final int COLUMNS = 12;
		messagesTextArea = new JTextArea(ROWS, COLUMNS);
		
		restartButton = new JButton("Restart");
		restartButton.addActionListener(actionListener);
		
		this.add(startButton);
		this.add(finishButton);
		this.add(nextButton);
		this.add(stopButton);
		this.add(randomButton);
		this.add(visibleGridCheckBox);
		this.add(timerLabel);
		this.add(timerSlider);
		this.add(pointsDensityLabel);
		this.add(pointsDensityTextField);
		this.add(randomWalksLabel);
		this.add(randomWalksTextField);
		this.add(enterButton);
		this.add(changeColorsButton);
		this.add(restartButton);
		this.add(messagesTextArea);
		
		startButton.setEnabled(false);
		finishButton.setEnabled(false);
		nextButton.setEnabled(false);
		stopButton.setEnabled(false);
		randomButton.setEnabled(false);
		visibleGridCheckBox.setEnabled(false);
		timerSlider.setEnabled(false);
		randomWalksTextField.setEnabled(false);
		changeColorsButton.setEnabled(false);
		messagesTextArea.setEditable(false);
		setMessage("Enter a point density \n(positive integer \ngreater than 4).", false);
		messagesTextArea.setLineWrap(true);
		restartButton.setEnabled(false);
		
	}
	
	/**
	 * Establishes the needed changes on the control panel 
	 * components when a valid density points have been entered.
	 */
	public void validPointsDensityEntered() {
		enterButton.setActionCommand("Random walks");
		setMessage("Enter a quantity of\nrandom walks (positive\ninteger greater than 0)", false);
		randomWalksTextField.setEnabled(true);
		pointsDensityTextField.setEnabled(false);
		restartButton.setEnabled(true);
		visibleGridCheckBox.setEnabled(true);
	}
	
	/**
	 * Establishes the needed changes on the control panel 
	 * components when a valid density points have been entered.
	 */
	public void validAmountOfRandomWalks() {
		stopButton.setEnabled(true);
		enterButton.setEnabled(false);
		randomButton.setEnabled(true);
	}
	/**
	 * Sets a message in the messages text area.
	 * 
	 * @param message Message to set.
	 * @param error Indicates if the message is an error message.
	 */
	public void setMessage(String message, boolean error) {
		if (error) {
			messagesTextArea.setForeground(Color.RED);
		} else {
			messagesTextArea.setForeground(Color.BLACK);
		}
		messagesTextArea.setText(message);
	}
	
	/**
	 * Returns the introduced points density.
	 * @return Introduced points density.
	 */
	public int getPointsDensity() {
		try {
			int pointsDensity = Integer.parseInt(pointsDensityTextField.getText());
			if (pointsDensity < 4) {
				throw new NumberFormatException();
			}
			validPointsDensityEntered();
			return pointsDensity;
		} catch (NumberFormatException e) {
			setMessage("Enter a valid point\ndensity (positive \ninteger greater than 4).", true);
			return -1;
		}
	}
	
	/**
	 * Returns the introduced amount of random walks.
	 * @return Introduced amount of random walks.
	 */
	public int getRandomWalks() {
		try {
			int randomWalks = Integer.parseInt(randomWalksTextField.getText());
			if (randomWalks < 1) {
				throw new NumberFormatException();
			}
			validAmountOfRandomWalks();
			return randomWalks;
		} catch (NumberFormatException e) {
			setMessage("Enter a valid amount of\nrandom walks (positive\ninteger greater than 0)", true);
			return -1;
		}
	}
	
	/**
	 * Prepares everything for start the simulation.
	 */
	public void readyState() {
		stopButton.setText("Stop");
		stopButton.setActionCommand("Stop simulation");
		stopButton.setEnabled(false);
		startButton.setEnabled(true);
		finishButton.setEnabled(true);
		nextButton.setEnabled(true);
		randomButton.setEnabled(false);
		pointsDensityTextField.setEnabled(false);
		randomWalksTextField.setEnabled(false);
		changeColorsButton.setEnabled(true);
		messagesTextArea.setEditable(false);
		setMessage("The simulation can \nstart!", false);
	}
	
	/**
	 * Represents the final state of the GUI.
	 */
	public void finishedState() {
		visibleGridCheckBox.setEnabled(false);
		setMessage("Simulation finished, \npress restart to \nrestart the simulation.", false);
		startButton.setEnabled(false);
		finishButton.setEnabled(false);
		nextButton.setEnabled(false);
		stopButton.setEnabled(false);
		changeColorsButton.setEnabled(false);
	}
	
	/**
	 * Represents the simulation state of the GUI.
	 */
	public void startState() {
		visibleGridCheckBox.setEnabled(true);
		setMessage("Starting simulation", false);
		startButton.setEnabled(false);
		finishButton.setEnabled(false);
		nextButton.setEnabled(false);
		stopButton.setEnabled(true);
		timerSlider.setEnabled(true);
		changeColorsButton.setEnabled(true);
		restartButton.setEnabled(false);
	}
	
	/**
	 * Represents the stopped simulation state of the GUI.
	 */
	public void stopState() {
		setMessage("Stopped simulation", false);
		startButton.setEnabled(true);
		finishButton.setEnabled(true);
		nextButton.setEnabled(true);
		stopButton.setEnabled(false);
		changeColorsButton.setEnabled(true);
		restartButton.setEnabled(true);
	}

}
