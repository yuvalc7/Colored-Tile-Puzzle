import java.util.Arrays;
import java.util.Hashtable;

public class State {
    private State Parent;
	private int [][] currentState;
	private int row , column;
	private String Value_move;
	private int move;
	private int number;
	private int Price;
	private int F; 
	private int hashCode;
	private boolean isOut;
	
	//constructor 
    public State(int row , int column) {
		this.isOut = false;
    	this.Parent = null;
		this.row = row;
		this.column = column;
		this.Price = 0;
		currentState = new int[row][column];
		Value_move = "";
		move = -1;
		number = 0;
		hashCode = 0;
	}

    //copy constructor
	public State(State s) {
		this.isOut = false;
		this.hashCode = s.hashCode;
		this.row = s.row;
		this.column = s.column;
		this.Value_move = s.Value_move;
		this.move = s.move;
		this.Price = 0;
		this.number = s.number;
		this.currentState = new int [this.row][this.column];
		for(int i = 0 ; i < s.row ; i++) {
			for(int j = 0 ; j < s.column ; j++) {
				this.currentState[i][j] = s.currentState[i][j];
			}
		}
	}

	public boolean isOut() {
		return isOut;
	}

	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}

	public int[][] getCurrentState() {
		return currentState;
	}    
	
    public void swap(int [] blank , int [] value) {
		int temp = currentState[blank[0]][blank[1]];
		currentState[blank[0]][blank[1]] = currentState[value[0]][value[1]] ;
		currentState[value[0]][value[1]] = temp;

	}	
	
    public void insert(int row , int column , int value) {
		this.currentState[row][column] = value;
	} 
	
    public int getRow() {
		return row;
	}
	
    public int getColumn() {
		return column;
	}
	
    public void setStringParent(String p) {
    	this.Value_move = p;
    	}
	
    public String getStringParent() {
    	return Value_move;
    	}
	
    public void setParent(State parent) {
    	this.Parent = parent;
    	}
	
    public State getParent() {
    	return Parent;
    	}
    
    public void set_HashCode() {
    	 //hashCode = 0;
    	for(int i = 0; i < row ; i++) {
    		hashCode += Arrays.hashCode(currentState[i]);
    	}
    } 
    
    public int get_hashCode() {
    	return hashCode;
    } 
    
    public void setMove(int move) {
    	this.move = move;
    	}
	
    public int getMove() {
    	return this.move;
    	}

    public int[] findPosition(int value) {
		int [] arr = new int[2]; 
		for(int i = 0 ; i < this.row ; i++) {
			for(int j = 0; j < this.column ; j++) {
				if(this.currentState[i][j] == value) {
					arr[0]= i; arr[1] = j;
					return arr;
				}
			}
		}
		return null;
	}
	
    public void setPrice(int price) {
    	this.Price = price;
    	}
	
    public int getPrice() {
    	return this.Price;
    	}
	
    public int getValue(int row , int column) {
		return currentState[row][column];
	}
    
    public void StringtoString() {
    	//System.out.println("State " + this.number + ":");
    	String number = "";
    	for(int i = 0 ; i < this.row ; i++) {
    		for (int j = 0 ; j < this.column ; j++) {
    			if(currentState[i][j] != 0) {
    			number = number + " " + currentState[i][j];
    			}
    			else {number = number + " _ ";}
    		}
    		System.out.println(number);
    		number = "";
    	}
    	System.out.println();
    }
	
    /**
     * @param f = price of state (cost from initial state -> current state) +  Heuristic function  
     */
    public void setF(int f) {
    	this.F = f;
    }
 
	public int getF() {
		return F;
		}
	
}

