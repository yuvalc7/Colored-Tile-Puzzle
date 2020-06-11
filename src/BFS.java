import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Queue;

/**
 * ------------------------------Pseudo code------------------------------ 
 * BFS(Node start, Vector Goals)
1. L <- make_queue(start) and make_hash_table
2. C <- make_hash_table
3. While L not empty loop
	1. n <- L.remove_front()
	2. C <- n
	3. For each allowed operator on n
		1. g <- operator(n)
		2. If g not in C and not in L
			1. If goal(g) return path(g)
			2. L.insert(g)

4. Return false
 */
public class BFS extends Algorithem{
	private State root;
	private Hashtable<Integer , State> OpenList;
	private HashSet<Integer> CloseList;
	private Queue<State> queue;
	private Operator oprate;

	public BFS() {queue = new ArrayDeque();}
	
	@Override
	public void setAlgorithem(State initialState ) {
		this.root = initialState;
		this.root.set_HashCode();
		OpenList = new Hashtable();
		CloseList = new HashSet<Integer>();
		oprate = new Operator(super.getColor()); 
	}
	
	@Override
	public void runAlgorithem() {
		queue.add(root);
		OpenList.put(root.get_hashCode(), root);
		boolean continue_ = true;
		super.Num ++;
		if(super.comperTo(this.root.getCurrentState() , super.getGoal()))
		{queue.remove(root); continue_ = false;}
		while(!queue.isEmpty() && continue_) {
			State current = queue.poll();
			int hashCode = current.get_hashCode();
			OpenList.remove(hashCode);
			CloseList.add(current.get_hashCode());
			boolean [] move = oprate.allowOperator(current);
			for(int i = 0 ; i < 4 && continue_; i++) {
				if(move[i]) {
					super.Num++;
					State child = oprate.operator(current, i);
					hashCode = child.get_hashCode();
					if(!(OpenList.containsKey(hashCode) || CloseList.contains(hashCode))) {
						if(super.comperTo(child.getCurrentState(), super.getGoal())) {
							super.StringPath(child);
							continue_ = false;
						}
						else {
							OpenList.put(hashCode,child);
							queue.add(child);
						}
					}
				}
			}
			if(InputReder.openList) {super.printOpenList(OpenList.values());}
		}
		if(queue.isEmpty()) {// no path found
		super.setExistPath();
		}
	}
}



