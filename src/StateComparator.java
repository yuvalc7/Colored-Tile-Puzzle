import java.util.Comparator;

public class StateComparator implements Comparator<State>{

	@Override
	public int compare(State s0, State s1) {
		if(s0.getF() > s1.getF()) {
			return 1;
		}
		else if(s0.getF() < s1.getF()) {
			return -1;
		}
		return 0;
	}

}
