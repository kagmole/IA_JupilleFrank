package Participants.JupilleFrank;

public class OthelloBox {
	public static final int NO_COLOR= -1;
	public static final int RED = 0;
	public static final int BLUE = 1;
	
	public static final int START_DIRECTION_ID = 10;
	
	public static final int TOP = START_DIRECTION_ID;
	public static final int BOTTOM = START_DIRECTION_ID + 1;
	public static final int LEFT = START_DIRECTION_ID + 2;
	public static final int RIGHT = START_DIRECTION_ID + 3;
	
	public static final int TOP_LEFT = START_DIRECTION_ID + 4;
	public static final int TOP_RIGHT = START_DIRECTION_ID + 5;
	public static final int BOTTOM_LEFT = START_DIRECTION_ID + 6;
	public static final int BOTTOM_RIGHT = START_DIRECTION_ID + 7;
	
	public static final int END_DIRECTION_ID = BOTTOM_RIGHT;
	
	private int color;
	private int value;
	
	private int i;
	private int j;
	
	private OthelloBox topBox;
	private OthelloBox bottomBox;
	private OthelloBox leftBox;
	private OthelloBox rightBox;
	
	private OthelloBox topLeftBox;
	private OthelloBox topRightBox;
	private OthelloBox bottomLeftBox;
	private OthelloBox bottomRightBox;
	
	public OthelloBox(int i, int j, int value) {
		color = NO_COLOR;
		this.value = value;
		
		this.i = i;
		this.j = j;
		
		topBox = null;
		bottomBox = null;
		leftBox = null;
		rightBox = null;
		
		topLeftBox = null;
		topRightBox = null;
		bottomLeftBox = null;
		bottomRightBox = null;
		
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
	
	public OthelloBox getRelatedBox(int direction) {
		switch(direction) {
		case TOP:
			return topBox;
		case BOTTOM:
			return bottomBox;
		case LEFT:
			return leftBox;
		case RIGHT:
			return rightBox;
		case TOP_LEFT:
			return topLeftBox;
		case TOP_RIGHT:
			return topRightBox;
		case BOTTOM_LEFT:
			return bottomLeftBox;
		case BOTTOM_RIGHT:
			return bottomRightBox;
		default:
			return null;
		}
	}
	
	public void setRelatedBox(OthelloBox othelloBox, int direction) {
		switch(direction) {
		case TOP:
			topBox = othelloBox;
			break;
		case BOTTOM:
			bottomBox = othelloBox;
			break;
		case LEFT:
			leftBox = othelloBox;
			break;
		case RIGHT:
			rightBox = othelloBox;
			break;
		case TOP_LEFT:
			topLeftBox = othelloBox;
			break;
		case TOP_RIGHT:
			topRightBox = othelloBox;
			break;
		case BOTTOM_LEFT:
			bottomLeftBox = othelloBox;
			break;
		case BOTTOM_RIGHT:
			bottomRightBox = othelloBox;
			break;
		default:
			/* Nothing to do here */
			break;
		}
	}
}
