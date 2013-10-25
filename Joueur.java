/* 
Exemple d'implémentation d'un joueur d'Othello. Cette implémentation sert uniquement
à démontrer le principe, mais n'implémente aucune intelligence: les coups à jouer sont 
simplement lus à la console!
*/

// Votre version sera dans Participants.<VosNoms>
package Participants.JupilleFrank;

import java.awt.Point;
import java.util.ArrayList;

// Pour l'interopérabilité: il faut une représentation commune des coups!
import Othello.Move;

// Vous devrez étendre Othello.Joueur pour implémenter votre propre joueur...
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

	// Méthode appelée à chaque fois que vous devez jouer un coup.
	// move est le coup joué par l'adversaire
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
		
		// - Mettre à jour votre représentation du jeu en fonction du coup joué par l'adversaire
		// - Décider quel coup jouer (alpha-beta!!)
		// - Remettre à jour votre représentation du jeu
		// - Retourner le coup choisi
		// Mais ici, on se contente de lire à la console:
		
		return result;
	}

}