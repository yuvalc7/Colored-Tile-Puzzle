
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;



/**
 * ------------------------------Pseudo code------------------------------ 
 * DFBnB(Node start, Vector Goals)
1. L <- make_stack(start) and H <- make_hash_table(start)
2. result <- null, t <- ∞ // should be set to a strict upper bound in an infinite graph
3. While L is not empty
	1. n <- L.remove_front()
	2. If n is marked as “out”
		1. H.remove(n)
	3. Else
		1. mark n as “out” and L.insert(n)
		2. N <- apply all of the allowed operators on n
		3. sort the nodes in N according to their f values (increasing order)
		4. For each node g from N according to the order of N
			1. If f(g) >= t
				1. remove g and all the nodes after it from N
			2. Else If H contains g’=g and g’ is marked as “out”
				1. remove g from N
			3. Else If H contains g’=g and g’ is not marked as “out”
				1. If f(g’)<=f(g)
					1. remove g from N
				2. Else
					1. remove g’ from L and from H
			4. Else If goal(g) // if we reached here, f(g) < t
				1. t <- f(g)
				2. result <- path(g) // all the “out” nodes in L
				3. remove g and all the nodes after it from N
		5. insert N in a reverse order to L and H
4. Return result
 */
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
				while(!this.priorityQueueForChild.isEmpty()) {
					State child = this.priorityQueueForChild.poll();
					hashCode = child.get_hashCode();
					boolean addToFinalStack = true;
					if(child.getF() >= thresHold) {
						addToFinalStack = false;
						this.priorityQueueForChild.clear();
					}
					else if(openList.contains(hashCode)) {
						State g_tag = openList.get(hashCode);
						if(!g_tag.isOut()) {//case 2 : next state is on the stack
							if(g_tag.getF() > child.getF()) {
								openList.remove(hashCode);
								stack.remove(g_tag);
							}else {//no need to switch
								addToFinalStack = false;
							}
						}else {//case 1 : next state is already on the path
							addToFinalStack = false;
						}
					}

					else if(super.comperTo(child.getCurrentState(), super.getGoal())) {
						thresHold = child.getF();
						this.priorityQueueForChild.clear();
						currentGoal = child;
						addToFinalStack = false;
					}
					
					if(addToFinalStack) {
						finalChildToInsert.add(child);
					}
				}
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