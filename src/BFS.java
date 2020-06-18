import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Queue;


public class BFS extends Algorithem{
	private State root;
	private Hashtable<Integer , State> OpenList;
	private HashSet<Integer> CloseList;
	//private Hashtable<Integer , State> CloseList;
	private Queue<State> queue;
	private Operator oprate;

	public BFS() {queue = new ArrayDeque<State>();}
	
	@Override
	public void setAlgorithem(State initialState ) {
		this.root = initialState;
		this.root.set_HashCode();
		OpenList = new Hashtable<Integer , State>();
		CloseList = new HashSet<Integer>();
		//CloseList = new Hashtable<Integer , State>();
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
			//CloseList.put(current.get_hashCode(), current);
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
			if(super.IsOpenList()) {
				super.printOpenList(OpenList.values());
				}
		}
		if(queue.isEmpty() && continue_) {// no path found
		super.setExistPath();
		}
	}

}



