package Participants.JupilleFrank;

import java.util.ArrayList;

public class OthelloGridTree {
	
	public ArrayList<OthelloGridTree> childrendsOthelloGridsTree;
	
	public OthelloGrid rootOthelloGrid;
	
	public OthelloGridTree(OthelloGrid rootOthelloGrid) {
		this.rootOthelloGrid = rootOthelloGrid;
		
		childrendsOthelloGridsTree = new ArrayList<OthelloGridTree>();
	}

	public OthelloGridTree() {
		this(null);
	}
	
	public OthelloGrid getRoot() {
		return rootOthelloGrid;
	}
	
	public void setRoot(OthelloGrid rootOthelloGrid) {
		this.rootOthelloGrid = rootOthelloGrid;
	}
	
	public ArrayList<OthelloGridTree> getChildrens() {
		return childrendsOthelloGridsTree;
	}
	
	public void addChildren(OthelloGridTree childrenOthelloGridTree) {
		childrendsOthelloGridsTree.add(childrenOthelloGridTree);
	}
	
	public boolean isTerminal() {
		return childrendsOthelloGridsTree.isEmpty();
	}
}
