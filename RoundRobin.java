
public class RoundRobin extends Scheduler {

	private int index = 0;
	
	public RoundRobin(Turker[] turkers) {
		super(turkers);
	}
	
	@Override
	public Turker next() {
		int indexOld = index;
		index = (index + 1) % turkers.length;
		return turkers[indexOld];
	}

}
