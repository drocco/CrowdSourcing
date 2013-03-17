import java.util.Random;

public class RandomTurk extends Scheduler {

	private static Random random = new Random();
	
	public RandomTurk(Turker[] turkers) {
		super(turkers);
	}

	@Override
	public Turker next() {
		return turkers[random.nextInt(turkers.length)];
	}

}
