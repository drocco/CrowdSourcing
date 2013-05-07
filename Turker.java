import java.util.Random;

public class Turker {
	
	private double rightPercentage;
	@SuppressWarnings("unused")
	private int responseTime;//not used for now
	@SuppressWarnings("unused")
	private int moneyCost;//not used yet
	private static Random rand = new Random();//static because I'm afraid that we might end up with a lot of turkers with same seed
	private int numRight;
	private int questionsAnswered;
	
	public Turker(double rightPercentage) {
		this(rightPercentage, 1, 1);
	}
	
	public Turker(double rightPercentage, int responseTime, int moneyCost) {
		super();
		this.rightPercentage = rightPercentage;
		this.responseTime = responseTime;
		this.moneyCost = moneyCost;
		this.numRight = 0;
		this.questionsAnswered = 0;
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
	
	public double getRightPercentage() {
		return rightPercentage;
	}

	public int getNumRight() {
		return numRight;
	}

	public void setNumRight(int numRight) {
		this.numRight = numRight;
	}

	public int getQuestionsAnswered() {
		return questionsAnswered;
	}

	public void setQuestionsAnswered(int questionsAnswered) {
		this.questionsAnswered = questionsAnswered;
	}
}
