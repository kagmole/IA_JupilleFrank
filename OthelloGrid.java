package Participants.JupilleFrank;

import java.util.ArrayList;
import java.util.HashSet;

public class OthelloGrid {

	private static final int GRID_SIZE = 8;
	
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

	private HashSet<OthelloBox> boxesAtBorder;
	
	private int[] playersPoints;

	private OthelloBox[][] othelloBoxes;

	public OthelloGrid() {
		playersPoints = new int[2];
		playersPoints[OthelloBox.BLUE] = 12;
		playersPoints[OthelloBox.RED]= 12;
		
		initOthelloBoxes();
		initOthelloStartConfiguration();
	}

	private void initOthelloBoxes() {
		othelloBoxes = new OthelloBox[GRID_SIZE][GRID_SIZE];

		for (int j = 0; j < GRID_SIZE; ++j) {
			for (int i = 0; i < GRID_SIZE; ++i) {
				othelloBoxes[i][j] = new OthelloBox(i, j, STANDARD_WEIGHTINGS[i][j]);
			}
		}

		/*
		 * First and last rows relations creation - nothing to do with
		 * top/bottom
		 */
		for (int i = 1; i < GRID_SIZE; ++i) {
			othelloBoxes[i - 1][0].setRelatedBox(othelloBoxes[i][0],
					OthelloBox.RIGHT);
			othelloBoxes[i][0].setRelatedBox(othelloBoxes[i - 1][0],
					OthelloBox.LEFT);

			othelloBoxes[i - 1][GRID_SIZE - 1].setRelatedBox(
					othelloBoxes[i][GRID_SIZE - 1], OthelloBox.RIGHT);
			othelloBoxes[i][GRID_SIZE - 1].setRelatedBox(
					othelloBoxes[i - 1][GRID_SIZE - 1], OthelloBox.LEFT);
		}

		/*
		 * First and last columns relations creation - nothing to do with
		 * left/right
		 */
		for (int j = 1; j < GRID_SIZE; ++j) {
			othelloBoxes[0][j - 1].setRelatedBox(othelloBoxes[0][j],
					OthelloBox.BOTTOM);
			othelloBoxes[0][j].setRelatedBox(othelloBoxes[0][j - 1],
					OthelloBox.TOP);

			othelloBoxes[GRID_SIZE - 1][j - 1].setRelatedBox(
					othelloBoxes[GRID_SIZE - 1][j], OthelloBox.BOTTOM);
			othelloBoxes[GRID_SIZE - 1][j].setRelatedBox(
					othelloBoxes[GRID_SIZE - 1][j - 1], OthelloBox.TOP);
		}

		/* Remaining boxes but last row and column relations creation */
		for (int j = 1; j < GRID_SIZE - 1; ++j) {
			for (int i = 1; i < GRID_SIZE - 1; ++i) {
				/* Top-left relation */
				othelloBoxes[i - 1][j - 1].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.BOTTOM_RIGHT);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i - 1][j - 1],
						OthelloBox.TOP_LEFT);

				/* Top relation */
				othelloBoxes[i][j - 1].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.BOTTOM);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i][j - 1],
						OthelloBox.TOP);

				/* Top-Right relation */
				othelloBoxes[i + 1][j - 1].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.BOTTOM_LEFT);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i + 1][j - 1],
						OthelloBox.TOP_RIGHT);

				/* Left relation */
				othelloBoxes[i - 1][j].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.RIGHT);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i - 1][j],
						OthelloBox.LEFT);

				/* Right relation */
				othelloBoxes[i + 1][j].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.LEFT);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i + 1][j],
						OthelloBox.RIGHT);

				/* Bottom-left relation */
				othelloBoxes[i - 1][j + 1].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.TOP_RIGHT);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i - 1][j + 1],
						OthelloBox.BOTTOM_LEFT);

				/* Bottom relation */
				othelloBoxes[i][j + 1].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.TOP);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i][j + 1],
						OthelloBox.BOTTOM);

				/* Bottom-right relation */
				othelloBoxes[i + 1][j + 1].setRelatedBox(othelloBoxes[i][j],
						OthelloBox.TOP_LEFT);
				othelloBoxes[i][j].setRelatedBox(othelloBoxes[i + 1][j + 1],
						OthelloBox.BOTTOM_RIGHT);
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
	}

	
	public ArrayList<OthelloBox> getPossiblesPlays(int playerID) {
		ArrayList<OthelloBox> possiblesPlays = new ArrayList<OthelloBox>();
		OthelloBox tempBox;

		for (OthelloBox othelloBox : boxesAtBorder) {
			for (int direction = OthelloBox.START_DIRECTION_ID; direction <= OthelloBox.END_DIRECTION_ID; ++direction) {
				tempBox = othelloBox.getRelatedBox(direction);

				if (tempBox != null
						&& tempBox.getColor() != OthelloBox.NO_COLOR
						&& tempBox.getColor() != playerID) {
					tempBox = tempBox.getRelatedBox(direction);

					while (tempBox != null
							&& tempBox.getColor() != OthelloBox.NO_COLOR) {
						if (tempBox.getColor() == playerID) {
							possiblesPlays.add(othelloBox);
							break;
						}
						tempBox = tempBox.getRelatedBox(direction);
					}
				}
			}
		}
		return possiblesPlays;
	}

	public void playAndFillBoxes(int i, int j, int playerID) {
		playAndFillBoxes(othelloBoxes[i][j], playerID);
	}

	public void playAndFillBoxes(OthelloBox nextPlay, int playerID) {
		changeBoxesColor(nextPlay, playerID);
		refreshBoxesAtBorder(nextPlay);
	}

	private void changeBoxesColor(OthelloBox nextPlay, int playerID) {
		nextPlay.setColor(playerID);
		
		playersPoints[playerID] += nextPlay.getValue();

		int nbIterations = 0;
		int rivalID = (playerID == OthelloBox.BLUE) ? OthelloBox.RED : OthelloBox.BLUE;
		OthelloBox tempPlay = nextPlay.getRelatedBox(OthelloBox.TOP_LEFT);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.BOTTOM_RIGHT);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.TOP_LEFT);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.TOP);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.BOTTOM);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.TOP);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.TOP_RIGHT);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.BOTTOM_LEFT);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.TOP_RIGHT);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.LEFT);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.RIGHT);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.LEFT);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.RIGHT);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.LEFT);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.RIGHT);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.BOTTOM_LEFT);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.TOP_RIGHT);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.BOTTOM_LEFT);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.BOTTOM);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.TOP);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.BOTTOM);
			++nbIterations;
		}
		nbIterations = 0;
		tempPlay = nextPlay.getRelatedBox(OthelloBox.BOTTOM_RIGHT);

		while (tempPlay != null) {
			if (tempPlay.getColor() == OthelloBox.NO_COLOR) {
				break;
			}

			if (tempPlay.getColor() == playerID) {
				while (nbIterations > 0) {
					tempPlay = tempPlay.getRelatedBox(OthelloBox.TOP_LEFT);
					tempPlay.setColor(playerID);
					
					playersPoints[playerID] += tempPlay.getValue();
					playersPoints[rivalID] -= tempPlay.getValue();

					--nbIterations;
				}
				break;
			}
			tempPlay = tempPlay.getRelatedBox(OthelloBox.BOTTOM_RIGHT);
			++nbIterations;
		}
	}

	private void refreshBoxesAtBorder(OthelloBox nextPlay) {
		boxesAtBorder.remove(nextPlay);

		OthelloBox tempPlay = nextPlay.getRelatedBox(OthelloBox.TOP_LEFT);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.TOP);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.TOP_RIGHT);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.LEFT);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.RIGHT);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.BOTTOM_LEFT);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.BOTTOM);

		if (tempPlay != null && tempPlay.getColor() == OthelloBox.NO_COLOR) {
			boxesAtBorder.add(tempPlay);
		}
		tempPlay = nextPlay.getRelatedBox(OthelloBox.BOTTOM_RIGHT);
	}
	
	public int getPlayerPoints(int playerID) {
		return playersPoints[playerID];
	}
}
