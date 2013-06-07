import java.util.Random;

//Representation of Turkers. They have a set "correct" percentage. When a question is asked, they check to see if they will answer
//right or wrong. If they answer wrong, they have an equal chance to return any of the wrong answers.
public class Turker {
	
	private double rightPercentage;
	@SuppressWarnings("unused")
	private int responseTime;//not used for now
	@SuppressWarnings("unused")
	private int moneyCost;//not used yet
	private static Random rand = new Random();
	//Used to calculate accuracy
	private int numRight;
	private int questionsAnswered;
	
	public Turker(double rightPercentage) {
		this(rightPercentage, 1, 1);
	}
	
	//We don't use repsonseTime or moneyCost yet, but may in future
	public Turker(double rightPercentage, int responseTime, int moneyCost) {
		super();
		this.rightPercentage = rightPercentage;
		this.responseTime = responseTime;
		this.moneyCost = moneyCost;
		this.numRight = 0;
		this.questionsAnswered = 0;
	}

	//Generate an answer to the question
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
