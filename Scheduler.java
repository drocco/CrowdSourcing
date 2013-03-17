
public abstract class Scheduler {
	
	protected Turker[] turkers;

	public Scheduler(Turker[] turkers) {
		super();
		this.turkers = turkers;
	}

	abstract public Turker next();
	
}
