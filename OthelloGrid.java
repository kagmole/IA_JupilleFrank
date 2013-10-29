package Participants.JupilleFrank;

import java.util.ArrayList;
import java.util.HashSet;

public class OthelloGrid {
	
	private static final int GRID_SIZE = 8;
	
	private ArrayList<OthelloBox> redPossiblesPlays;
	private ArrayList<OthelloBox> bluePossiblesPlays;
	
	private HashSet<OthelloBox> boxesAtBorder;
	
	private OthelloBox[][] othelloBoxes;
	
	
	public OthelloGrid() {
		initOthelloBoxes();
		initOthelloStartConfiguration();
	}
	
	private void initOthelloBoxes() {
		othelloBoxes = new OthelloBox[GRID_SIZE][GRID_SIZE];
		
		for (int i = 0; i < GRID_SIZE; ++i) {
			for (int j = 0; j < GRID_SIZE; ++j) {
				othelloBoxes[i][j] = new OthelloBox(i, j);
			}
		}
	}
	
	private void initOthelloStartConfiguration() {		
		othelloBoxes[3][3].setColor(OthelloBox.BLUE);
		othelloBoxes[4][4].setColor(OthelloBox.BLUE);
		
		othelloBoxes[4][3].setColor(OthelloBox.RED);
		othelloBoxes[3][4].setColor(OthelloBox.RED);
		
		boxesAtBorder = new HashSet<OthelloBox>();

		boxesAtBorder.add(othelloBoxes[2][2]);
		boxesAtBorder.add(othelloBoxes[3][2]);
		boxesAtBorder.add(othelloBoxes[4][2]);
		boxesAtBorder.add(othelloBoxes[5][2]);
		
		boxesAtBorder.add(othelloBoxes[2][3]);
		boxesAtBorder.add(othelloBoxes[5][3]);
		
		boxesAtBorder.add(othelloBoxes[2][4]);
		boxesAtBorder.add(othelloBoxes[5][4]);
		
		boxesAtBorder.add(othelloBoxes[2][5]);
		boxesAtBorder.add(othelloBoxes[3][5]);
		boxesAtBorder.add(othelloBoxes[4][5]);
		boxesAtBorder.add(othelloBoxes[5][5]);
		
		bluePossiblesPlays = new ArrayList<OthelloBox>();
		
		bluePossiblesPlays.add(othelloBoxes[4][2]);
		bluePossiblesPlays.add(othelloBoxes[5][3]);
		bluePossiblesPlays.add(othelloBoxes[2][4]);
		bluePossiblesPlays.add(othelloBoxes[3][5]);
		
		redPossiblesPlays = new ArrayList<OthelloBox>();
		
		redPossiblesPlays.add(othelloBoxes[3][2]);
		redPossiblesPlays.add(othelloBoxes[2][3]);
		redPossiblesPlays.add(othelloBoxes[5][4]);
		redPossiblesPlays.add(othelloBoxes[4][5]);
	}
	
	public ArrayList<OthelloBox> getPossiblesPlays(int playerID) {
		if (playerID == OthelloBox.BLUE) {
			return bluePossiblesPlays;
		} else {
			return redPossiblesPlays;
		}
	}
	
	public void playAndFillBoxes(int i, int j, int playerID) {
		playAndFillBoxes(othelloBoxes[i][j], playerID);
	}
	
	public void playAndFillBoxes(OthelloBox nextPlay, int playerID) {
		if (playerID == OthelloBox.BLUE){
			bluePossiblesPlays.remove(nextPlay);
		} else {
			redPossiblesPlays.remove(nextPlay);
		}
		changeBoxesColor(nextPlay, playerID);
		refreshBoxesAtBorder(nextPlay);
		refreshOldPossiblesPlays();
		
		for (int x = 0; x < GRID_SIZE; ++x) {
			for (int y = 0; y < GRID_SIZE; ++y) {
				if (othelloBoxes[y][x].getColor() != -1)
				System.out.print(othelloBoxes[y][x].getColor() + " ");
				else
					System.out.print("X ");
			}
			System.out.println();
		}
	}
	
	private void changeBoxesColor(OthelloBox nextPlay, int playerID) {
		nextPlay.setColor(playerID);
		
		tryToFillLines(nextPlay, -1, -1);
		tryToFillLines(nextPlay, 0, -1);
		tryToFillLines(nextPlay, 1, -1);
		
		tryToFillLines(nextPlay, -1, 0);
		tryToFillLines(nextPlay, 1, 0);
		
		tryToFillLines(nextPlay, -1, 1);
		tryToFillLines(nextPlay, 0, 1);
		tryToFillLines(nextPlay, 1, 1);
	}
	
	private void tryToFillLines(OthelloBox nextPlay, int incrI, int incrJ) {
		int i = nextPlay.getI();
		int j = nextPlay.getJ();
		
		int color = nextPlay.getColor();
		
		i += incrI;
		j += incrJ;
		
		int nbIterations = 0;
		
		while (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
			if (othelloBoxes[i][j].getColor() == OthelloBox.NO_COLOR) {
				break;
			}
			
			if (othelloBoxes[i][j].getColor() == color) {
				while (nbIterations > 0) {
					i -= incrI;
					j -= incrJ;
					--nbIterations;
					
					othelloBoxes[i][j].setColor(color);
				}
				break;
			}
			i += incrI;
			j += incrJ;
			++nbIterations;
		}
	}
	
	private void refreshBoxesAtBorder(OthelloBox nextPlay) {
		int i = nextPlay.getI();
		int j = nextPlay.getJ();
		
		boxesAtBorder.remove(nextPlay);
		
		if (i > 0) {
			if (othelloBoxes[i - 1][j].getColor() == OthelloBox.NO_COLOR)
				boxesAtBorder.add(othelloBoxes[i - 1][j]);
			
			if (j > 0) {
				if (othelloBoxes[i - 1][j - 1].getColor() == OthelloBox.NO_COLOR)
					boxesAtBorder.add(othelloBoxes[i - 1][j - 1]);
			}
			
			if (j < GRID_SIZE - 1) {
				if (othelloBoxes[i - 1][j + 1].getColor() == OthelloBox.NO_COLOR)
					boxesAtBorder.add(othelloBoxes[i - 1][j + 1]);
			}
		}
		
		if (i < GRID_SIZE - 1) {
			if (othelloBoxes[i + 1][j].getColor() == OthelloBox.NO_COLOR)
				boxesAtBorder.add(othelloBoxes[i + 1][j]);
			
			if (j > 0) {
				if (othelloBoxes[i + 1][j - 1].getColor() == OthelloBox.NO_COLOR)
					boxesAtBorder.add(othelloBoxes[i + 1][j - 1]);
			}
			
			if (j < GRID_SIZE - 1) {
				if (othelloBoxes[i + 1][j + 1].getColor() == OthelloBox.NO_COLOR)
					boxesAtBorder.add(othelloBoxes[i + 1][j + 1]);
			}
		}
		
		if (j > 0) {
			if (othelloBoxes[i][j - 1].getColor() == OthelloBox.NO_COLOR)
				boxesAtBorder.add(othelloBoxes[i][j - 1]);
		}
		
		if (j < GRID_SIZE - 1) {
			if (othelloBoxes[i][j + 1].getColor() == OthelloBox.NO_COLOR)
				boxesAtBorder.add(othelloBoxes[i][j + 1]);
		}
	}
	
	private void refreshOldPossiblesPlays() {
		bluePossiblesPlays.clear();
		redPossiblesPlays.clear();
		
		for (OthelloBox othelloBox : boxesAtBorder) {
			if (isAPossiblePlay(othelloBox, OthelloBox.BLUE)) {
				bluePossiblesPlays.add(othelloBox);
			} else if (isAPossiblePlay(othelloBox, OthelloBox.RED)){
				redPossiblesPlays.add(othelloBox);
			}
		}
	}
	
	private boolean isAPossiblePlay(OthelloBox othelloBox, int playerID) {
		return (ableToFillLine(othelloBox, -1, -1, playerID)
				|| ableToFillLine(othelloBox, 0, -1, playerID)
				|| ableToFillLine(othelloBox, 1, -1, playerID)
				|| ableToFillLine(othelloBox, -1, 0, playerID)
				|| ableToFillLine(othelloBox, 1, 0, playerID)
				|| ableToFillLine(othelloBox, -1, 1, playerID)
				|| ableToFillLine(othelloBox, 0, 1, playerID)
				|| ableToFillLine(othelloBox, 1, 1, playerID));
	}
	
	private boolean ableToFillLine(OthelloBox othelloBox, int incrI, int incrJ, int playerID) {
		int i = othelloBox.getI();
		int j = othelloBox.getJ();
		
		i += incrI;
		j += incrJ;
		
		if (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
			if (othelloBoxes[i][j].getColor() != OthelloBox.NO_COLOR
					&& othelloBoxes[i][j].getColor() != playerID) {
				i += incrI;
				j += incrJ;
				
				while (i >= 0 && i < GRID_SIZE && j >= 0 && j < GRID_SIZE) {
					if (othelloBoxes[i][j].getColor() == OthelloBox.NO_COLOR) {
						break;
					}
					
					if (othelloBoxes[i][j].getColor() == playerID) {
						return true;
					}
					i += incrI;
					j += incrJ;
				}
			}
		}
		return false;
	}
}
