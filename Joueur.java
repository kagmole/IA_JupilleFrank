/* 
Exemple d'impl�mentation d'un joueur d'Othello. Cette impl�mentation sert uniquement
� d�montrer le principe, mais n'impl�mente aucune intelligence: les coups � jouer sont 
simplement lus � la console!
*/

// Votre version sera dans Participants.<VosNoms>
package Participants.JupilleFrank;

import java.awt.Point;
import java.util.ArrayList;

// Pour l'interop�rabilit�: il faut une repr�sentation commune des coups!
import Othello.Move;

// Vous devrez �tendre Othello.Joueur pour impl�menter votre propre joueur...
public class Joueur extends Othello.Joueur {
	
	private int rivalId;
	
	private GameGrid gameGrid;

	/**
	 * @param depth Alpha-beta algorithm depth level
	 * @param playerID 0 = red, 1 = blue
	 */
	public Joueur(int depth, int playerID) {
		super(depth, playerID);
		
		initJoueur();
	}
	
	private void initJoueur() {
		rivalId = (playerID == 0) ? 1 : 0;
		
		gameGrid = new GameGrid();
	}

	// M�thode appel�e � chaque fois que vous devez jouer un coup.
	// move est le coup jou� par l'adversaire
	public Move nextPlay(Move move) {
		ArrayList<Point> pointsList = null;
		Move result = null;
		
		if (move != null) {
			System.out.println("PLAYER:");
			gameGrid.fillBox(move.i, move.j, rivalId);
			System.out.println();
		}
		
		pointsList = gameGrid.getPossiblesNextPlays(playerID);
		
		if (!pointsList.isEmpty()) {
			result = new Move(pointsList.get(0).x, pointsList.get(0).y);
			
			System.out.println("IA:");
			gameGrid.fillBox(result.i, result.j, playerID);
			System.out.println();
		}
		
		// - Mettre � jour votre repr�sentation du jeu en fonction du coup jou� par l'adversaire
		// - D�cider quel coup jouer (alpha-beta!!)
		// - Remettre � jour votre repr�sentation du jeu
		// - Retourner le coup choisi
		// Mais ici, on se contente de lire � la console:
		
		return result;
	}

}