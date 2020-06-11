import java.util.Hashtable;
import java.util.Stack;

/**
 * ------------------------------Pseudo code------------------------------ 
IDA*(Node start, Vector Goals)
1. L <- make_stack and H  make_hash_table
2. t <- h(start)
3. While t ≠ ∞
	1. minF <- ∞
	2. L.insert(start) and H.insert(start)
	3. While L is not empty
		1. n <- L.remove_front()
		2. If n is marked as “out”
			1. H.remove(n)
		2. Else
			2. mark n as “out” and L.insert(n)
			3. For each allowed operator on n
			4. g <- operator(n)
				1. If f(g) > t
					1. minF  min(minF, f(g))
					2. continue with the next operator
				2. If H contains g’=g and g’ marked “out”
					1. continue with the next operator
				3. If H contains g’=g and g’ not marked “out”
					1. If f(g’)>f(g)
						1. remove g’ from L and H
					2. Else
						1. continue with the next operator

				4. If goal(g) then return path(g) //all the “out” nodes in L
				5. L.insert(g) and H.insert(g)

		4. t <- minF
4. Return
 **/
public class IDA_STAR extends Algorithem {

	private Hashtable<Integer, State> openList;
	private Stack<State> stack;
	private State root;
	private Operator oprate;

	private boolean getGoal;


	public IDA_STAR() {openList = new Hashtable<>();}

	@Override
	public void setAlgorithem(State initialState) {
		// TODO Auto-generated method stub
		this.root = initialState;
		this.root.set_HashCode();
		oprate = new Operator(super.getColor());
		getGoal = false;
		stack = new Stack<>(); 
	}

	@Override
	public void runAlgorithem() {
		// TODO Auto-generated method stub
		int thresHold = super.tiles_not_in_their_goal_position(this.root);
		super.Num++;
		while(thresHold != Integer.MAX_VALUE && !getGoal) {
			int minF = Integer.MAX_VALUE;
			stack.add(this.root);
			int hashCode = this.root.get_hashCode();
			openList.put(hashCode, this.root);
			while(!stack.isEmpty() && !getGoal) {
				State current = stack.pop();
				hashCode = current.get_hashCode();
				if(current.isOut()) {
					openList.remove(hashCode);
				}
				else {
					current.setOut(true);
					boolean [] move = oprate.allowOperator(current);
					for(int i = 0 ; i < 4 ; i++) {
						if(move[i]) {
							super.Num++;
							State child = oprate.operator(current, i);
							hashCode = child.get_hashCode();
							child.setF(child.getPrice() + super.tiles_not_in_their_goal_position(child));
							if(child.getF() > thresHold) {
								minF = Math.min(minF, child.getF());
								continue;
							}

							if(openList.containsKey(hashCode) ) {	
								State g_tag = openList.get(hashCode);//if(g_tag.isOut())case 1 : next state is already on the path -> continue
								if(!g_tag.isOut()) {//case 2 : next state is on the stack
									if(g_tag.getF() > child.getF()) {
										openList.remove(hashCode);
										stack.remove(g_tag);
									}
								}
								continue;
							}
							if(super.comperTo(child.getCurrentState(), super.getGoal())) {
								super.StringPath(child);
								getGoal = true;
								break;
							}
							else {
								openList.put(hashCode, child);
								stack.add(child);
							}
						}
					}
				}
				if(InputReder.openList) {super.printOpenList(openList.values());}
			}
			thresHold = minF;
		}
		if(!getGoal) {// no path found
			super.setExistPath();
		}
	}
}















