
import java.util.Hashtable;


public class DFID extends Algorithem{

	private Hashtable<Integer , State> openList;
	private State root;
	private Operator oprate;
	private boolean isCutOof ;
	private boolean getGoal;
	
	public DFID() {openList = new Hashtable<>();}


	@Override
	public void setAlgorithem(State initialState) {
		this.root = initialState;
		this.root.set_HashCode();
		oprate = new Operator(super.getColor());
		isCutOof = true;
	    getGoal = false;
	}

	@Override
	public void runAlgorithem() {
		super.Num++;
		for(int depth = 1 ; depth < Integer.MAX_VALUE ; depth++) {
		    LimitedDfs(this.root ,depth,0);  // gets Path
		    //System.out.println(super.Num);
			if(isCutOof) {
				continue;
			}
			else {
				if(!getGoal) {super.setExistPath();}
				break;
			}	
		}	
	}

	private boolean LimitedDfs(State state ,int cutoff , int possibleMove) {
		
		boolean ans ;
		if(super.comperTo(state.getCurrentState(), super.getGoal())) {
			super.StringPath(state); 
			getGoal = true;
			return false;
			}
		else if (cutoff == 0 ) {
			return possibleMove == 0;
		}
		else {
			int hashCode = state.get_hashCode() ; 
			openList.put(hashCode , state);
			isCutOof = false;
			boolean [] move = oprate.allowOperator(state);
			possibleMove = getNumOfPossiblelMove(move);
			for(int i = 0 ; i < 4 ; i++) {
				if(move[i]) {
					State child = oprate.operator(state, i);
					super.Num++;
					hashCode = child.get_hashCode();
					if(openList.containsKey(hashCode)) {
						continue;
					}
					else {
						possibleMove--;
					    ans = LimitedDfs(child, --cutoff , possibleMove);
					    cutoff++;
						if(ans) {
							isCutOof = true;
						}
						else if(getGoal) {
							if(super.IsOpenList()) {super.printOpenList(openList.values());}
							return false;
						}
					}
					
				}
			}
			if(super.IsOpenList()) {super.printOpenList(openList.values());}
			hashCode = state.get_hashCode();
			openList.remove(hashCode);
			if(isCutOof) {
				return true;
			}
			return false;
		}
	}
	
	private int getNumOfPossiblelMove(boolean [] move) {
		int ans = 0;
		for(boolean b : move) {
			if(b) {ans++;}
		}
		return ans;
	}

}
 





