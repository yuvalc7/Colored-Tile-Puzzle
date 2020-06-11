
public class Operator {
    
	
	private Color color;
	private int number_value;
	private int [] blank_position_before_swap ;
	
	public Operator(Color color) {this.color = color;}

	public boolean[] allowOperator(State n) {
		boolean [] legalMove = new boolean[4];
		int [] position = n.findPosition(0);
		int blank_row = position[0];
		int blank_col = position[1];
		int privies = n.getMove();
		int value;
		
		//left side 
		if(blank_col < n.getColumn()-1 && privies != 0 ) {
			value = n.getValue(blank_row , blank_col+1);
			legalMove[0] = (color.isBlack(value)) ? false : true;
		}
		//up side
		if(blank_row < n.getRow()-1 && privies != 1 ) {
			value = n.getValue(blank_row+1 , blank_col);
			legalMove[1] = (color.isBlack(value)) ? false : true;
		}
		//right side 
		if(blank_col > 0 && privies != 2  ) {
			value = n.getValue(blank_row , blank_col-1);
			legalMove[2] = (color.isBlack(value)) ? false : true;
		}
		//down side
		if(blank_row > 0 && privies != 3 ) {
			value = n.getValue(blank_row-1 , blank_col);
			legalMove[3] = (color.isBlack(value)) ? false : true;
		}
		return legalMove;
	}
	
    public State operator(State parent , int move) {
		State child = new State(parent);
		blank_position_before_swap  = parent.findPosition(0);
		int [] value_Position = new int[2];
		switch (move) {
		case 0://left
			value_Position[0] = blank_position_before_swap [0];
			value_Position[1] = blank_position_before_swap [1]+1;
			number_value = child.getValue(value_Position[0], value_Position[1]);
			child.swap(blank_position_before_swap ,value_Position);
			child.setMove(2);
			child.setStringParent(""+number_value+"L");
			child.setPrice(color.getPrice(number_value)+parent.getPrice());
			child.set_HashCode();
			break;
		case 1://up
			value_Position[0] = blank_position_before_swap [0]+1;
			value_Position[1] = blank_position_before_swap [1];
		    number_value = child.getValue(value_Position[0], value_Position[1]);
			child.swap(blank_position_before_swap ,value_Position);
			child.setMove(3);
			child.setStringParent(""+number_value+"U");
			child.setPrice(color.getPrice(number_value)+parent.getPrice());
			child.set_HashCode();
			break;
		case 2://right
			value_Position[0] = blank_position_before_swap [0];
			value_Position[1] = blank_position_before_swap [1]-1;
			number_value = child.getValue(value_Position[0], value_Position[1]);
			child.swap(blank_position_before_swap ,value_Position);
			child.setMove(0);
			child.setStringParent(""+number_value+"R");
			child.setPrice(color.getPrice(number_value)+parent.getPrice());
			child.set_HashCode();
			break;
		case 3://down
			value_Position[0] = blank_position_before_swap [0]-1;
			value_Position[1] = blank_position_before_swap [1];
			number_value = child.getValue(value_Position[0], value_Position[1]);
			child.swap(blank_position_before_swap ,value_Position);
			child.setMove(1);
			child.setStringParent(""+number_value+"D");
			child.setPrice(color.getPrice(number_value)+parent.getPrice());
			child.set_HashCode();
			break;
		default:
			break;
		}
		child.setParent(parent);
		return child;
	}

}
