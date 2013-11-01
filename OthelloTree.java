package Participants.JupilleFrank;

import java.util.ArrayList;

/**
 * Tree structure used to contain all possibles next plays since the root node.
 * 
 * @author Dany Jupille
 * @author Etienne Frank
 * @version 1.0
 */
public class OthelloTree {
	
	/** Node's childrens */
	private ArrayList<OthelloTree> childrendsOthelloTrees;
	
	/** Used by the alpha-beta pruning */
	public int alphabetaPoints;
	
	/** Root node */
	private OthelloGrid rootOthelloGrid;
	
	/**
	 * @param rootOthelloGrid Grid to be wrapped into the root node
	 * @param playerID Used to get the player's points in this grid
	 */
	public OthelloTree(OthelloGrid rootOthelloGrid, int playerID) {
		this.rootOthelloGrid = rootOthelloGrid;
		
		alphabetaPoints = rootOthelloGrid.getPlayerPoints(playerID);
		
		childrendsOthelloTrees = new ArrayList<OthelloTree>();
	}
	
	public OthelloGrid getRoot() {
		return rootOthelloGrid;
	}
	
	public void setRoot(OthelloGrid rootOthelloGrid) {
		this.rootOthelloGrid = rootOthelloGrid;
	}
	
	public ArrayList<OthelloTree> getChildrens() {
		return childrendsOthelloTrees;
	}
	
	public void addChildren(OthelloTree childrenOthelloTree) {
		childrendsOthelloTrees.add(childrenOthelloTree);
	}
	
	/**
	 * Check if this node is a leaf.
	 * 
	 * @return <b>true</b> if it is a leaf, <b>false</b> otherwise
	 */
	public boolean isTerminal() {
		return childrendsOthelloTrees.isEmpty();
	}
}
