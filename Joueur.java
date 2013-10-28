package Participants.JupilleFrank;

import java.awt.Point;
import java.util.ArrayList;

import Othello.Move;

public class Joueur extends Othello.Joueur {
	
	private int rivalID;
	
	private GameGrid effectiveGameGrid;

	/**
	 * @param depth Alpha-beta algorithm depth level
	 * @param playerID 0 = red, 1 = blue
	 */
	public Joueur(int depth, int playerID) {
		super(depth, playerID);
		
		initJoueur();
	}
	
	private void initJoueur() {
		rivalID = (playerID == 0) ? 1 : 0;
		
		effectiveGameGrid = new GameGrid();
	}
	
	private GameGridTree buildGameGridTree(GameGrid gameGrid, int depth, int id) {
		GameGridTree gameGridTree = new GameGridTree(gameGrid);
		
		ArrayList<Point> pointsList = gameGrid.getPossiblesNextPlays(id);
		
		for (Point point : pointsList) {
			GameGrid childrenGameGrid = gameGrid.cloneOf();
			
			childrenGameGrid.fillBox(point.x, point.y, id);
			
			if (depth > 1) {
				gameGridTree.addChildren(buildGameGridTree(childrenGameGrid, depth - 1, (id == 0) ? 1 : 0));
			}
		}
		return gameGridTree;
	}

	public Move nextPlay(Move move) {
		ArrayList<Point> pointsList = null;
		Move result = null;
		
		if (move != null) {
			effectiveGameGrid.fillBox(move.i, move.j, rivalID);
		}
		
		GameGridTree gameGridTree = buildGameGridTree(effectiveGameGrid, depth, playerID);
		
		pointsList = effectiveGameGrid.getPossiblesNextPlays(playerID);
		
		// TODO : Remplacer le 0 par la fonction qui détermine quel est le meilleur choix
		int choice = 0;
		
		if (!pointsList.isEmpty()) {
			result = new Move(pointsList.get(choice).x, pointsList.get(choice).y);
			
			effectiveGameGrid.fillBox(result.i, result.j, playerID);
		}		
		return result;
	}

}