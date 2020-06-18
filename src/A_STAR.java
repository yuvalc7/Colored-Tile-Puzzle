import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;




public class A_STAR extends Algorithem {

	private State root;
	private PriorityQueue<State> priorityQueue ;
	private HashSet<Integer> closeList;
	private Hashtable<Integer , State> openList;
	private Operator oprate;

	public A_STAR() {priorityQueue = new PriorityQueue<>(new StateComparator());}


	@Override
	public void setAlgorithem(State initialState) {
		
		this.root = initialState;
		this.root.set_HashCode();
		openList = new Hashtable<Integer, State>();
		closeList = new HashSet<Integer>();
		oprate = new Operator(super.getColor());
	}

	@Override
	public void runAlgorithem() {
		
		openList.put(root.get_hashCode() , root);
		priorityQueue.add(root);
		boolean stop = false;
		super.Num++;
		while(!priorityQueue.isEmpty() && !stop) {
			State current = priorityQueue.poll();
			int hashCode = current.get_hashCode();
			openList.remove(hashCode);
			if(super.comperTo(current.getCurrentState(), super.getGoal())) {
				super.StringPath(current);
				stop = true;
			}
			else {
				closeList.add(hashCode);
				boolean [] move = oprate.allowOperator(current);
				for(int i = 0; i < 4 ; i++) {
					if(move[i]) {
						super.Num++;
						State child = oprate.operator(current, i);
						hashCode = child.get_hashCode();
						if(!(openList.containsKey(hashCode) || closeList.contains(hashCode))) {
							child.setF(child.getPrice() + super.tiles_not_in_their_goal_position(child));
							openList.put(hashCode , child);
							priorityQueue.add(child);
						}
						else if(openList.containsKey(hashCode)) {
							double old_f = openList.get(hashCode).getF();
							double new_f = child.getPrice() + super.tiles_not_in_their_goal_position(child);
							if(new_f < old_f) {
								priorityQueue.remove(super.StateToRemove(priorityQueue.iterator() , hashCode));
								priorityQueue.add(child);
							}
						}
					}
				}
			}
			if(super.IsOpenList()) {super.printOpenList(openList.values());}
		}
		if(priorityQueue.isEmpty() && !stop) {
			super.setExistPath();
		}
	}


}

