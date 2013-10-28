package Participants.JupilleFrank;

import java.awt.Point;
import java.util.ArrayList;

public class GameGrid {
	public static int EMPTY_BOX = -1;
	public static int RED_BOX = 0;
	public static int BLUE_BOX = 1;
	
	private static int GRID_LIMIT = 8;
	
	private int grid[][];
	
	public GameGrid() {
		initGridValues();
	}
	
	private void initGridValues() {
		grid = new int[GRID_LIMIT][GRID_LIMIT];
		
		for (int x = 0; x < GRID_LIMIT; ++x) {
			for (int y = 0; y < GRID_LIMIT; ++y) {
				grid[x][y] = EMPTY_BOX;
			}
		}
		grid[3][3] = BLUE_BOX;
		grid[4][4] = BLUE_BOX;
		
		grid[3][4] = RED_BOX;
		grid[4][3] = RED_BOX;
	}
	
	private void resetGridValues(int gridValues[][]) {
		for (int x = 0; x < GRID_LIMIT; ++x) {
			for (int y = 0; y < GRID_LIMIT; ++y) {
				grid[x][y] = gridValues[x][y];
			}
		}
	}
	
	/**
	 * Check if the box is empty and at the border of at less one not empty
	 * box.
	 * 
	 * @param x X position of the box
	 * @param y Y position of the box
	 * @return <b>true</b> if it is a valid place, <b>false</b> otherwise
	 */
	private boolean isAtBorder(int x, int y) {		
		if (grid[x][y] == EMPTY_BOX) {
			if (isBoxNotEmpty(x - 1, y - 1)
					|| isBoxNotEmpty(x - 1, y)
					|| isBoxNotEmpty(x - 1, y + 1)
					|| isBoxNotEmpty(x, y - 1)
					|| isBoxNotEmpty(x, y + 1)
					|| isBoxNotEmpty(x + 1, y - 1)
					|| isBoxNotEmpty(x + 1, y)
					|| isBoxNotEmpty(x + 1, y + 1)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean isBoxNotEmpty(int x, int y) {
		if (x >= 0 && x < GRID_LIMIT && y >= 0 && y < GRID_LIMIT) {
			if (grid[x][y] != EMPTY_BOX) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param playerId
	 * @return
	 */
	private boolean isPlayable(int x, int y, int playerId) {
		if (checkForAPlayableLine(x, y, -1, -1, playerId)
				|| checkForAPlayableLine(x, y, -1, 0, playerId)
				|| checkForAPlayableLine(x, y, -1, 1, playerId)
				|| checkForAPlayableLine(x, y, 0, -1, playerId)
				|| checkForAPlayableLine(x, y, 0, 1, playerId)
				|| checkForAPlayableLine(x, y, 1, -1, playerId)
				|| checkForAPlayableLine(x, y, 1, 0, playerId)
				|| checkForAPlayableLine(x, y, 1, 1, playerId)) {
			return true;
		} else {
			return false;
		}
	}
	
	// TODO : Je pourrai mettre un compteur pour le nombre de pièces retournées ! =)
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param factX
	 * @param factY
	 * @param playerId
	 * @return
	 */
	private boolean checkForAPlayableLine(int x, int y, int factX, int factY, int playerId) {		
		int testX = x + factX;
		int testY = y + factY;
		
		/* Check limits */
		if (testX >= 0 && testX < GRID_LIMIT && testY >= 0 && testY < GRID_LIMIT) {
			
			/* The first next box ID must be different */
			if (grid[testX][testY] != playerId && grid[testX][testY] != EMPTY_BOX) {
				testX += factX;
				testY += factY;
				
				/* Search a box with the same ID */
				while (testX >= 0 && testX < GRID_LIMIT && testY >= 0 && testY < GRID_LIMIT) {
					if (grid[testX][testY] == playerId) {
						return true;
					} else if (grid[testX][testY] == EMPTY_BOX) {
						return false;
					}
					
					testX += factX;
					testY += factY;
				}
			}	
		}
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param factX
	 * @param factY
	 * @param playerId
	 */
	private void tryToFillLine(int x, int y, int factX, int factY, int playerId) {
		int testX = x + factX;
		int testY = y + factY;
		
		ArrayList<Point> tempPointsList = new ArrayList<Point>();
		
		/* Check limits */
		if (testX >= 0 && testX < GRID_LIMIT && testY >= 0 && testY < GRID_LIMIT) {
			
			/* The first next box ID must be different */
			if (grid[testX][testY] != playerId && grid[testX][testY] != EMPTY_BOX) {
				tempPointsList.add(new Point(testX, testY));
				
				testX += factX;
				testY += factY;
				
				/* Search a box with the same ID */
				while (testX >= 0 && testX < GRID_LIMIT && testY >= 0 && testY < GRID_LIMIT) {
					if (grid[testX][testY] == playerId) {
						break;
					} else if (grid[testX][testY] == EMPTY_BOX) {
						tempPointsList.clear();
						break;
					} else {
						tempPointsList.add(new Point(testX, testY));
						
						testX += factX;
						testY += factY;
					}
				}
				
				if (testX >= 0 && testX < GRID_LIMIT && testY >= 0 && testY < GRID_LIMIT) {
					for (Point point : tempPointsList) {
						grid[point.x][point.y] = playerId;
					}
				}
			}	
		}
	}
	
	/**
	 * Return a list containing all possibles next plays for the player.
	 * 
	 * @param playerId Current player ID
	 * @return List of possibles points for the next play
	 */
	public ArrayList<Point> getPossiblesNextPlays(int playerId) {
		ArrayList<Point> pointsList = new ArrayList<Point>();
		
		for (int x = 0; x < GRID_LIMIT; ++x) {
			for (int y = 0; y < GRID_LIMIT; ++y) {
				if (!isAtBorder(x, y)) {
					continue;
				}
				if (!isPlayable(x, y, playerId)) {
					continue;
				}
				pointsList.add(new Point(x, y));
			}
		}		
		return pointsList;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param playerId
	 */
	public void fillBox(int x, int y, int playerId) {
		tryToFillLine(x, y, -1, -1, playerId);
		tryToFillLine(x, y, -1, 0, playerId);
		tryToFillLine(x, y, -1, 1, playerId);
		tryToFillLine(x, y, 0, -1, playerId);
		tryToFillLine(x, y, 0, 1, playerId);
		tryToFillLine(x, y, 1, -1, playerId);
		tryToFillLine(x, y, 1, 0, playerId);
		tryToFillLine(x, y, 1, 1, playerId);
		
		grid[x][y] = playerId;
	}
	
	public int[][] getGridValues() {
		return grid;
	}
	
	public GameGrid cloneOf() {
		GameGrid cloneGameGrid = new GameGrid();
		
		cloneGameGrid.resetGridValues(grid);
		
		return cloneGameGrid;
	}
}
