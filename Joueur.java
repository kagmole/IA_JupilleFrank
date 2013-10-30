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
		return result;
	}
}