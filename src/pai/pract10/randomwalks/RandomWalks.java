/**
 * File containing the RandomWalks entity definition. 
 */

package pai.pract10.randomwalks;

import pai.pract10.randomwalks.controller.RandomWalksController;

/**
 * Class which contains the main method of the RandomWalks program. It was
 * created for the tenth practice of PAI (Programación de Aplicaciones
 * Interactivas) course of ULL (Universidad de la Laguna).
 * 
 * @author Daute Rodríguez Rodríguez (alu0100973914@ull.edu.es)
 * @version 1.0
 * @since 13 abr. 2018
 */
public class RandomWalks {

	/**
	 * Main method.
	 * 
	 * @param args
	 *          Arguments given to the program.
	 */
	public static void main(String[] args) {
		final int WIDTH = 1000;
		final int HEIGHT = 800;
		RandomWalksController controller = new RandomWalksController(WIDTH, HEIGHT);

	}

}
