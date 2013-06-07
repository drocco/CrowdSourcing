//Abstract Scheduler. Basically just an infinite iterator over the collection of Turkers
public abstract class Scheduler {
	
	protected Turker[] turkers;

	public Scheduler(Turker[] turkers) {
		super();
		this.turkers = turkers;
	}

	abstract public Turker next();
}
