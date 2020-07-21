import java.util.Hashtable;
import java.util.Stack;


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
		        this.root.setOut(false);
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
					stack.add(current);
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
							openList.put(hashCode, child);
							stack.add(child);

						}
					}
				}
				if(super.IsOpenList()) {super.printOpenList(openList.values());}
			}
			thresHold = minF;
		}
		if(!getGoal) {// no path found
			super.setExistPath();
		}
	}

}















