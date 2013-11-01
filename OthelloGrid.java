package Participants.JupilleFrank;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Class used to represent the situation of the game. It contains the grid
 * values and has some useful methods for the JupilleFrank's artificial
 * intelligence.
 * 
 * @author Dany Jupille
 * @author Etienne Frank
 * @version 1.0
 */
public class OthelloGrid {
	
	/** Player blue */
	private static final int BLUE = 1;
	
	/** Player red */
	private static final int RED = 0;
	
	/** Empty box */
	private static final int NO_COLOR = -1;

	/** Othello's grid height and width */
	private static final int GRID_SIZE = 8;
	
	/** Matrix containing standard weightings to determine how many points got a player */
	private static final int[][] STANDARD_WEIGHTINGS = {
		{
			7, 2, 5, 4, 4, 5, 2, 7
		},
		{
			2, 1, 3, 3, 3, 3, 1, 2
		},
		{
			5, 3, 6, 5, 5, 6, 3, 5
		},
		{
			4, 3, 5, 6, 6, 5, 3, 4
		},
		{
			4, 3, 5, 6, 6, 5, 3, 4
		},
		{
			5, 3, 6, 5, 5, 6, 3, 5
		},
		{
			2, 1, 3, 3, 3, 3, 1, 2
		},
		{
			7, 2, 5, 4, 4, 5, 2, 7
		}
	};
	
	/** Current grid situation */
	private int[][] grid;

	public OthelloGrid() {		
		initGridValues();
	}
	
	/** Copy constructor */
	public OthelloGrid(OthelloGrid clone) {				
		this();
		
		for (int j = 0; j < GRID_SIZE; ++j) {
			for (int i = 0; i < GRID_SIZE; ++i) {
				grid[i][j] = clone.grid[i][j];
			}
		}
	}
	
	/**
	 * Base configuration for an Othello game.
	 */
	private void initGridValues() {
		grid = new int[GRID_SIZE][GRID_SIZE];
		
		for (int j = 0; j < GRID_SIZE; ++j) {
			for (int i = 0; i < GRID_SIZE; ++i) {
				grid[i][j] = NO_COLOR;
			}
		}
		grid[3][3] = BLUE;
		grid[4][4] = BLUE;
		
		grid[3][4] = RED;
		grid[4][3] = RED;
	}
	
	/**
	 * Check if the box is empty and at the border of at less one not empty
	 * box.
	 * 
	 * @param i I position of the box
	 * @param j J position of the box
	 * @return <b>true</b> if it is an empty box at border, <b>false</b> otherwise
	 */
	private boolean isAtBorder(int i, int j) {
		if (grid[i][j] == NO_COLOR) {
			
			/* We must check its eight neighbors */
			if (isBoxNotEmpty(i - 1, j - 1)
					|| isBoxNotEmpty(i - 1, j)
					|| isBoxNotEmpty(i - 1, j + 1)
					|| isBoxNotEmpty(i, j - 1)
					|| isBoxNotEmpty(i, j + 1)
					|| isBoxNotEmpty(i + 1, j - 1)
					|| isBoxNotEmpty(i + 1, j)
					|| isBoxNotEmpty(i + 1, j + 1)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Check if the box is not empty.
	 * 
	 * @param i I position of the box
	 * @param j J position of the box
	 * @return <b>true</b> if it is not empty, <b>false</b> otherwise
	 */
	private boolean isBoxNotEmpty(int i, int j) {
		if (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
			if (grid[i][j] != NO_COLOR) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the player can fill at least one line by playing this box.
	 * 
	 * @param i I position of the box
	 * @param j J position of the box
	 * @param playerID Player who wants to play
	 * @return
	 */
	private boolean isPlayable(int i, int j, int playerID) {
		
		/* Look for at least one possible line to fill */
		if (checkForAPlayableLine(i, j, -1, -1, playerID)
				|| checkForAPlayableLine(i, j, -1, 0, playerID)
				|| checkForAPlayableLine(i, j, -1, 1, playerID)
				|| checkForAPlayableLine(i, j, 0, -1, playerID)
				|| checkForAPlayableLine(i, j, 0, 1, playerID)
				|| checkForAPlayableLine(i, j, 1, -1, playerID)
				|| checkForAPlayableLine(i, j, 1, 0, playerID)
				|| checkForAPlayableLine(i, j, 1, 1, playerID)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Algorithm to check if the line would be fill by the player when playing
	 * this box.
	 * 
	 * @param i I position of the box
	 * @param j J position of the box
	 * @param factI -1 = neighbor at left, 0 = neighbor in the same column,
	 * 1 = neighbor at right
	 * @param factJ -1 = neighbor at top, 0 = neighbor in the same row,
	 * 1 = neighbor at bottom
	 * @param playerID Player who wants to play
	 * @return <b>true</b> if the line would be fill, <b>false</b> otherwise
	 */
	private boolean checkForAPlayableLine(int i, int j, int factI, int factJ, int playerID) {		
		i += factI;
		j += factJ;
		
		/* Check limits */
		if (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
			
			/* The first next box ID must be different */
			if (grid[i][j] != playerID && grid[i][j] != NO_COLOR) {
				i += factI;
				j += factJ;
				
				/* Search a box with the same ID */
				while (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
					if (grid[i][j] == playerID) {
						return true;
					} else if (grid[i][j] == NO_COLOR) {
						return false;
					}
					
					i += factI;
					j += factJ;
				}
			}	
		}
		return false;
	}
	
	/**
	 * Algorithm to fill a line if it is possible with the choice of the player.
	 * 
	 * @param i I position of the box
	 * @param j J position of the box
	 * @param factI -1 = neighbor at left, 0 = neighbor in the same column,
	 * 1 = neighbor at right
	 * @param factJ -1 = neighbor at top, 0 = neighbor in the same row,
	 * 1 = neighbor at bottom
	 * @param playerID Player who is playing
	 */
	private void tryToFillLine(int i, int j, int factI, int factJ, int playerID) {
		i += factI;
		j += factJ;
		
		int nbIterations = 0;
		
		/* Check limits */
		if (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
			
			/* The first next box ID must be different */
			if (grid[i][j] != playerID && grid[i][j] != NO_COLOR) {
				i += factI;
				j += factJ;
				
				++nbIterations;
				
				/* Search a box with the same ID */
				while (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
					if (grid[i][j] == NO_COLOR) {
						break;
					} else if (grid[i][j] == playerID) {
						
						/* Box with the same ID found : fill previous boxes */
						while (nbIterations > 0) {
							i -= factI;
							j -= factJ;
							
							--nbIterations;
							
							grid[i][j] = playerID;
						}
						break;
					}
					i += factI;
					j += factJ;
					
					++nbIterations;
				}
			}	
		}
	}
	
	/**
	 * Return a list containing all possibles next plays for the player.
	 * 
	 * @param playerID Current player ID
	 * @return List of possibles points for the next play
	 */
	public ArrayList<Point> getPossiblesPlays(int playerID) {
		ArrayList<Point> pointsList = new ArrayList<Point>();
		
		for (int j = 0; j < GRID_SIZE; ++j) {
			for (int i = 0; i < GRID_SIZE; ++i) {
				if (!isAtBorder(j, i)) {
					continue;
				}
				if (!isPlayable(j, i, playerID)) {
					continue;
				}
				pointsList.add(new Point(j, i));
			}
		}		
		return pointsList;
	}
	
	/**
	 * Play the choice of the player. Grid will be amended accordingly.
	 * 
	 * @param i I position of the box
	 * @param j J position of the box
	 * @param playerID Player currently playing
	 */
	public void playAndFillBoxes(int i, int j, int playerID) {
		
		/* Try to fill lines in each direction */
		tryToFillLine(i, j, -1, -1, playerID);
		tryToFillLine(i, j, -1, 0, playerID);
		tryToFillLine(i, j, -1, 1, playerID);
		tryToFillLine(i, j, 0, -1, playerID);
		tryToFillLine(i, j, 0, 1, playerID);
		tryToFillLine(i, j, 1, -1, playerID);
		tryToFillLine(i, j, 1, 0, playerID);
		tryToFillLine(i, j, 1, 1, playerID);
		
		/* Finally, fill the choice either */
		grid[i][j] = playerID;
	}

	/**
	 * Return player's points according to the standards weightings.
	 * 
	 * @param playerID Player who wants to know his points
	 * @return Player's points
	 */
	public int getPlayerPoints(int playerID) {
		int points = 0;
		
		for (int j = 0; j < GRID_SIZE; ++j) {
			for (int i = 0; i < GRID_SIZE; ++i) {
				if (grid[i][j] == playerID) {
					points += STANDARD_WEIGHTINGS[i][j];
				}
			}
		}
		return points;
	}
}
