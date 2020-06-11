
import java.util.Collection;
import java.util.Iterator;


/**
 * this class contains abstract function and necessary function for the 5 algorithm
 * @author yuval cohen
 */

public abstract class Algorithem {
	
	private  boolean existPath = true;
	private String path = "";
	public int Num = 0;
	private int totalPrice = 0;
	private double second = 0;
	private boolean IsOpenList = true;
	private Color color;
    private int [][] Goal;
    private String Time = "";
   
	
	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public abstract void runAlgorithem();
	
	public abstract void setAlgorithem(State initialState);
		
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	public boolean IsOpenList() {
		return IsOpenList;
	}

	public void setIsOpenList(boolean isOpenList) {
		IsOpenList = isOpenList;
	}
	
	public boolean isExistPath() {
		return existPath;
	}

	public String getPath() {
		return path;
	}

	public int getNum() {
		return Num;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public double getSecond() {
		return second;
	}
 	
    public  void setGoal(int row , int column) {
		this.Goal = new int[row][column] ;
		int num = 1;
		for(int i = 0 ; i < row; i++) {
			for(int j = 0 ; j < column ; j++) {
				if((i+1) == row && (j+1) ==  column) {this.Goal[i][j] = 0;}
				else{this.Goal[i][j] = num++;} 
			}
		}
	}
	
    public int[][] getGoal(){return Goal;}
	
    public boolean comperTo(int [][] s_1 , int [][] s_2) {
	
		for(int i = 0 ; i < s_1.length ; i++) {
			for(int j = 0 ; j < s_1[0].length ; j++) {
				if(s_1[i][j]!= s_2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
    
    public void StringPath(State goal) {
    	
    	int count = 0 ;
    	totalPrice = goal.getPrice();
    	while(goal.getParent()!= null) {
    		if(count == 0) {
    			path = goal.getStringParent();	
    		}
    		else {
    		path = goal.getStringParent() + "-" + path;
    		}
    		goal = goal.getParent();
    		count++;
    	}
    }
    
    public void setExistPath() {this.existPath = false;}
    
    public void printOpenList(Collection<State> openList) {
    	 Iterator <State> it = openList.iterator();
    	while(it.hasNext()) {
    		State state = it.next();
    		state.StringtoString();
    	}
    	System.out.println("---------------------");
    }
 
    /**
     * using findPosition function
     * @param array - [0] = row of currentState value , [1] = column of currentState value ,[2] = the value
     * @return number of Manhattan distance
     */
    public int Heuristic(int [] array) {
    	int [] position = findPosition(array[2]);
    	return Math.abs(array[0] - position[0])+ Math.abs(array[1] - position[1]);
    }
    
    /**
     * @param value - the number 
     * @return position of tile with this value in goal. [0] - number row , [1] - number column 
     */
    public int[] findPosition(int value) {
		int [] arr = new int[2]; 
		for(int i = 0 ; i < this.Goal.length ; i++) {
			for(int j = 0; j < this.Goal[0].length ; j++) {
				if(this.Goal[i][j] == value) {
					arr[0]= i; arr[1] = j;
					return arr;
				}
			}
		}
		return null;
	}
    
	public State StateToRemove(Iterator<State> it , int hashCode) {
		Iterator<State> it1 = it;
		while(it1.hasNext()) {
			State state = it1.next();
			if(state.get_hashCode() == hashCode) {
				return state;
			}
		}
		return null;
	}

	/**
	 * using Heuristic function 
	 * @param state - current state
	 * @return sum of (Manhattan distance*their color price) of non-blank tiles not in their goal position
	 */
	public int tiles_not_in_their_goal_position(State state) {
		int final_cost = 0; 
		int sum = 0;
		int [] val = new int[3];
		int [][] current = state.getCurrentState();
		for(int i = 0 ; i < current.length ; i++) {
			for(int j = 0 ; j < current[0].length ; j++) {
				if(!(current[i][j] == this.Goal[i][j]) && current[i][j] != 0) {
					val[0] = i;
					val[1] = j;
					val[2] = current[i][j];
					sum = Heuristic(val)*color.getPrice(val[2]);
					final_cost += sum;
				}
			}
		}
	    return final_cost;
	}

}
