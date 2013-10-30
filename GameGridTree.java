package Participants.JupilleFrank;

import java.util.ArrayList;

public class GameGridTree {
	
	public ArrayList<GameGridTree> childrensGameGrids;
	
	public OthelloGrid rootGameGrid;
	
	public GameGridTree(OthelloGrid rootGameGrid) {
		this.rootGameGrid = rootGameGrid;
		
		childrensGameGrids = new ArrayList<GameGridTree>();
	}

	public GameGridTree() {
		this(null);
	}
	
	public OthelloGrid getRoot() {
		return rootGameGrid;
	}
	
	public void setRoot(OthelloGrid rootGameGrid) {
		this.rootGameGrid = rootGameGrid;
	}
	
	public ArrayList<GameGridTree> getChildrens() {
		return childrensGameGrids;
	}
	
	public void addChildren(GameGridTree childrenGameGridTree) {
		childrensGameGrids.add(childrenGameGridTree);
	}
	
	public boolean isTerminal() {
		return childrensGameGrids.isEmpty();
	}
}
