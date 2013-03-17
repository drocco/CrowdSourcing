import java.util.Random;

public class Turker {
	
	private float rightPercentage;
	@SuppressWarnings("unused")
	private int responseTime;//not used for now
	@SuppressWarnings("unused")
	private int moneyCost;//not used yet
	private static Random rand = new Random();//static because I'm afraid that we might end up with a lot of turkers with same seed
	
	public Turker(float rightPercentage, int responseTime, int moneyCost) {
		super();
		this.rightPercentage = rightPercentage;
		this.responseTime = responseTime;
		this.moneyCost = moneyCost;
	}

	public int answerQuestion(Question q){
		if(rand.nextFloat() <= rightPercentage) {
			return q.getRightChoice();
		} else {//Ugly code but I think it works... should probably improve this
			int pick = rand.nextInt(q.getNumberOfChoices() - 1) + 1;
			if(pick >= q.getRightChoice()) {
				pick++;
			}
			return pick;
		}
	}
	
	public float getRightPercentage() {
		return rightPercentage;
	}
}
