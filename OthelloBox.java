package Participants.JupilleFrank;

public class OthelloBox {
	public static final int NO_COLOR= -1;
	public static final int RED = 0;
	public static final int BLUE = 1;
	
	private static final int STANDARD_VALUE = 1;
	
	private int color;
	private int value;
	
	private int i;
	private int j;
	
	public OthelloBox(int i, int j) {		
		this.color = NO_COLOR;
		this.value = STANDARD_VALUE;
		
		this.i = i;
		this.j = j;
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
}
