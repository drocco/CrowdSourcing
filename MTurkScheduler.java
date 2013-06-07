import java.util.Random;

//Scheduler meant to imitate MTurk: 20% of Turkers answer 80% of questions. Based off of one of the papers
public class MTurkScheduler extends Scheduler {
	
	private static Random random = new Random();
	private int smallBound;

	public MTurkScheduler(Turker[] turkers) {
		super(turkers);
		
		smallBound = turkers.length / 5;
	}

	@Override
	public Turker next() {
		if (random.nextDouble() < .8) {
			return turkers[random.nextInt(smallBound)];
		} else {
			return turkers[random.nextInt(turkers.length - smallBound) + smallBound];
		}
	}

}
