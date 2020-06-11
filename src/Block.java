
// this class represent a block on the board  

public class Block {
    // color:  0 = black , 1 = red , 2 = green , -1 = blank
	// number 0 is for empty Block
	private int number , color ;
	
	public Block(int number , int color) {
		this.number = number;
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public int getColor() {
		return color;
	}
	
	//need to write get position function
}
