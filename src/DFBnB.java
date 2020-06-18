
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

public class DFBnB extends Algorithem{

	private Hashtable<Integer, State> openList;
	private Stack<State> stack;
	private State root;
	private Operator oprate;
	private boolean getGoal;
	private Stack<State> finalChildToInsert;
	private PriorityQueue<State> priorityQueueForChild ;
	private State currentGoal;

	public DFBnB() {
		// TODO Auto-generated constructor stub
		openList = new Hashtable<>();
	}

	@Override
	public void setAlgorithem(State initialState) {
		// TODO Auto-generated method stub
		this.root = initialState;
		this.root.set_HashCode();
		oprate = new Operator(super.getColor());
		getGoal = false;
		stack = new Stack<>(); 
		priorityQueueForChild = new PriorityQueue<>(new StateComparator());
		finalChildToInsert = new Stack<State>();
		currentGoal = new State(initialState.getRow(),initialState.getColumn());
	}

	@Override
	public void runAlgorithem() {
		// TODO Auto-generated method stub
		int number_of_tiles_without_black_tiles = (this.root.getColumn()*this.root.getRow()) - super.getColor().numOfBlack();
		int thresHold = (number_of_tiles_without_black_tiles > 12) ? Integer.MAX_VALUE : factorial(number_of_tiles_without_black_tiles);
		openList.put(this.root.get_hashCode() , this.root);
		stack.add(this.root);
		super.Num++;
		while(!stack.isEmpty() && !getGoal) {
			
			State current = stack.pop();
			int hashCode = current.get_hashCode();
			if(current.isOut()) {
			
				openList.remove(hashCode);
				
			}
			else {
				current.setOut(true);
				stack.add(current);
				boolean [] move = oprate.allowOperator(current);
				//sorted child Node according to f values
				for(int i = 0 ; i < 4 ; i++) {
					if(move[i]) {
						super.Num++;
						State child = oprate.operator(current, i);
						child.setF(child.getPrice() + super.tiles_not_in_their_goal_position(child));
						this.priorityQueueForChild.add(child);
					}
				}
				Iterator<State> it  = this.priorityQueueForChild.iterator();
				boolean continue_ = true;
			
				while (it.hasNext()&& continue_){
					State child = it.next();
					hashCode = child.get_hashCode();
					boolean addToFinalStack = true;
					if(child.getF() >= thresHold) {
						addToFinalStack = false;
						continue_ = false;
						break;
					}
					else if(openList.containsKey(hashCode)) {
						State g_tag = openList.get(hashCode);
						if(!g_tag.isOut()) {//case 2 : next state is on the stack
							if(g_tag.getF() <= child.getF()) {
								//continue;
								addToFinalStack = false;
								continue;
							}else {//no need to switch
								openList.remove(hashCode);
								stack.remove(g_tag);
							}
						}else {//case 1 : next state is already on the path
							addToFinalStack = false;
							continue;
						}
					}

					else if(super.comperTo(child.getCurrentState(), super.getGoal())) {
						thresHold = child.getF();
						//this.priorityQueueForChild.clear();
						continue_ = false;
						currentGoal = child;
						addToFinalStack = false;
					}			
					if(addToFinalStack) {
						finalChildToInsert.add(child);
					}
					
				}
				priorityQueueForChild.clear();
				//add to stack
				while(!finalChildToInsert.isEmpty()) {
					current = finalChildToInsert.pop(); 
					hashCode = current.get_hashCode();
					this.stack.add(current);
					this.openList.put(hashCode, current);
				}	
			}
			if(super.IsOpenList()) {super.printOpenList(openList.values());}
		}
		if(currentGoal.getParent() != null) {
		super.StringPath(currentGoal);
		}
		else {
			super.setExistPath();
		}
	}

	private int factorial(int n) {
		if(n == 0)
			return 1;
		else
			return (n * factorial(n-1));
	} 

}
