package Participants.JupilleFrank;

import java.awt.Point;
import java.util.ArrayList;

import Othello.Move;

/**
 * "Joueur" class implementation for the artificial intelligence of the
 *  JupilleFrank's group. Its logic is based on values for the grid's boxes.
 * 
 * @author Dany Jupille
 * @author Etienne Frank
 * @version 1.0
 */
public class Joueur extends Othello.Joueur {
	/** ID of the opponent */
	private int rivalID;
	
	/** Game's representation */
	private OthelloGrid effectiveGameGrid;

	/**
	 * @param depth Alpha-beta algorithm depth level
	 * @param playerID 0 = red, 1 = blue
	 */
	public Joueur(int depth, int playerID) {
		super(depth, playerID);
		
		rivalID = (playerID == 0) ? 1 : 0;
		
		effectiveGameGrid = new OthelloGrid();
	}

	/**
	 * Automatically call by the main program to get the choice of the
	 * artificial intelligence.
	 * 
	 * @param move Last opponent move - it equals <b>null</b> if he did not play
	 * @return Artificial intelligence's choice - it equals <b>null</b> if it
	 * has no choice
	 */
	public Move nextPlay(Move move) {
		if (move != null) {
			effectiveGameGrid.playAndFillBoxes(move.i, move.j, rivalID);
		}
		ArrayList<Point> possiblesPlays = effectiveGameGrid.getPossiblesPlays(playerID);
		Move result = null;
		
		/* If it has at least one choice */
		if (!possiblesPlays.isEmpty()) {
			/* Build all possibles next plays in the game, according to the depth */
			OthelloTree othelloTree = buildOthelloTree(effectiveGameGrid, depth, playerID);
			
			/* Artificial intelligence's choice using alpha-beta pruning */
			Point choice = choosePlay(othelloTree);
			
			result = new Move(choice.x, choice.y);			
			effectiveGameGrid.playAndFillBoxes(choice.x, choice.y, playerID);
		}
		return result;
	}
	
	/**
	 * Recursive function creating all possibles next plays in the game,
	 * according to the depth.
	 * 
	 * @param othelloGrid Grid where the plays are done
	 * @param depth Alpha-beta pruning depth
	 * @param id Current player ID
	 * @return Tree structure containing all possibles next plays in the game
	 */
	private OthelloTree buildOthelloTree(OthelloGrid othelloGrid, int depth, int id) {
		OthelloTree othelloTree = new OthelloTree(othelloGrid, playerID);
		
		ArrayList<Point> possiblesPlays = othelloGrid.getPossiblesPlays(id);
		
		/* Create a new leaf for each possible play */
		for (Point possiblePlay : possiblesPlays) {
			OthelloGrid childrenOthelloGrid = new OthelloGrid(othelloGrid);
			
			childrenOthelloGrid.playAndFillBoxes(possiblePlay.x, possiblePlay.y, id);
			
			if (depth > 0) {
				othelloTree.addChildren(buildOthelloTree(childrenOthelloGrid, depth - 1, (id == 0) ? 1 : 0));
			}
		}
		return othelloTree;
	}
	
	/**
	 * Choose the best play according to the JupilleFrank's artificial
	 * intelligence using alpha-beta pruning.
	 * 
	 * @param othelloTree Tree structure containing all possibles next grid's
	 * situations in the game
	 * @return Best play - it is an error if it returns <b>null</b>
	 */
	private Point choosePlay(OthelloTree othelloTree) {
		/* Alpha-beta pruning */
		int result = alphabeta(othelloTree, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		
		ArrayList<Point> possiblesPlays = othelloTree.getRoot().getPossiblesPlays(playerID);
		int nbIterations = 0;
		
		/* Look for the play who has the same value than the alpha-beta pruning choice */
		for (OthelloTree childrenOthelloTree : othelloTree.getChildrens()) {			
			if (childrenOthelloTree.alphabetaPoints == result) {
				return possiblesPlays.get(nbIterations);
			}
			++nbIterations;
		}
		return null;
	}
	
	/**
	 * Alpha-beta pruning. It uses the minimax algorithm in its search tree.
	 * 
	 * @param node Tree structure containing all possibles next grid's
	 * situations in the game
	 * @param depth Alpha-beta pruning's depth
	 * @param alpha Memory value used for the max function
	 * @param beta Memory value used for the min function
	 * @param maximizingPlayer Define if it is maximizing or minimizing the
	 * current player
	 * @return Alpha-beta pruning's value
	 */
	public int alphabeta(OthelloTree node, int depth, int alpha, int beta, boolean maximizingPlayer) {
		
		/* Look if it must stop the research */
	    if (depth == 0 || node.isTerminal()) {
	    	/* If it stops, the function returns the player's points in this grid */
		    return node.alphabetaPoints;
	    }
	    
	    /* Are we maximizing or minimizing the player ? */
		if (maximizingPlayer) {
			
			/* For each next possibles plays, we must compare the value */
		    for (OthelloTree child : node.getChildrens()) {
		    	
		    	/* When maximizing, we must memorized the biggest value */
		        alpha = Math.max(alpha, alphabeta(child, depth - 1, alpha, beta, false));
		        
		        /* No need to check next childs if we cannot maximize more */
		        if (beta <= alpha)
		            break;
		    }
		    node.alphabetaPoints = alpha;
		    return alpha;
		} else {
		    for (OthelloTree child : node.getChildrens()) {
		    	
		    	/* When minimizing, we must memorized the lowest value */
		        beta = Math.min(beta, alphabeta(child, depth - 1, alpha, beta, true));
		        
		        /* No need to check next childs if we cannot minimizing more */
		        if (beta <= alpha)
		            break;
		    }
		    node.alphabetaPoints = beta;
		    return beta;		    
		}
	}

}
