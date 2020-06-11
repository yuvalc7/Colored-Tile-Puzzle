
import java.util.Hashtable;

/**
 * ------------------------------Pseudo code------------------------------ 
 * DFID(Node start, Vector Goals)
 *  1. For depth=1 to ∞
 *   1. H <- make_hash_table ->  know if the vertex in the path
 *   2. result = Limited_DFS(start,Goals,depth,H) 
 *   3. If result ≠ cutoff then return result
    Limited_DFS(Node n, Vector Goals, int limit, hash H) -> return path or cutoff or false
     1. If goal(n) then return path(n)  //use the back pointers or the recursion tail
     2. Else if limit = 0 then return cutoff
     3. Else
        1. H.insert(n)
        2. isCutoff = false 
        3. For each allowed operator on n 
            1. g <- operator(n) 
            2. If H contains g 
                  1. continue with the next operator 
            3. result <- Limited_DFS(g,Goals,limit-1,H) 
            4. If result = cutoff 
                  1. isCutoff  true 
            5. Else if result ≠ fail
                  1. return result
         4. H.remove(n) //the memory for n should be also released 
         5. If isCutoff = true 
                  1. return cutoff
         6. Else 
                  1. return fail
 */
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
			boolean ans = LimitedDfs(this.root ,depth,0);  // gets Path
		    System.out.println(super.Num);
			if(isCutOof) {
				continue;
			}
			else {
				break;
			}	
		}	
	}

	private boolean LimitedDfs(State state ,int cutoff , int possibleMove) {
		//super.Num++;	
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
							return false;
						}
					}
					
				}
			}
			if(InputReder.openList) {super.printOpenList(openList.values());}
			hashCode = state.get_hashCode();
			openList.remove(hashCode);
			if(isCutOof) {
				return true;
			}
			// no path
			super.setExistPath();
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
 





