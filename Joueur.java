package Participants.JupilleFrank;

import java.util.ArrayList;

import Othello.Move;

public class Joueur extends Othello.Joueur {
	
	private int rivalID;
	
	private OthelloGrid effectiveGameGrid;

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
		
		effectiveGameGrid = new OthelloGrid();
	}

	public Move nextPlay(Move move) {
		if (move != null) {
			effectiveGameGrid.playAndFillBoxes(move.i, move.j, rivalID);
		}
		ArrayList<OthelloBox> possiblesPlays = effectiveGameGrid.getPossiblesPlays(playerID);
		Move result = null;
		
		if (!possiblesPlays.isEmpty()) {
			result = new Move(possiblesPlays.get(0).getI(), possiblesPlays.get(0).getJ());
			effectiveGameGrid.playAndFillBoxes(possiblesPlays.get(0), playerID);
		}
		
		GameGridTree gameGridTree = buildGameGridTree(effectiveGameGrid, depth, playerID);
		
		pointsList = effectiveGameGrid.getPossiblesNextPlays(playerID);
		
		//alphabeta(gameGridTree, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		
		// TODO : Remplacer le 0 par la fonction qui d�termine quel est le meilleur choix
		int choice = 0;
		
		if (!pointsList.isEmpty()) {
			result = new Move(pointsList.get(choice).x, pointsList.get(choice).y);
			
			effectiveGameGrid.fillBox(result.i, result.j, playerID);
		}		
		return result;
	}
	
	public static int alphabeta(GameGridTree node, int depth, int alpha, int beta, boolean maximizingPlayer){
	    if (depth == 0 || node.isTerminal()){
		    return node.value;
	    }
		if (maximizingPlayer){
		    for (GameGridTree child:node.getChildrens()){
		        alpha = Math.max(alpha, alphabeta(child, depth - 1, alpha, beta, false));
		        if (beta <= alpha)
		            break;
		    }
		    //node.value = alpha;
		    return alpha;
		}else{
		    for (GameGridTree child:node.getChildrens()){
		        beta = Math.min(beta, alphabeta(child, depth - 1, alpha, beta, true));
		        if (beta <= alpha)
		            break;
		    }
		    //node.value = beta;
		    return beta;		    
		}
	}

}
