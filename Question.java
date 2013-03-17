public class Question {

	private int numberOfChoices;
	private int rightChoice;
	
	public Question(int numberOfChoices, int rightChoice) {
		super();
		this.numberOfChoices = numberOfChoices;
		this.rightChoice = rightChoice;
	}

	public int getNumberOfChoices() {
		return numberOfChoices;
	}

	public int getRightChoice() {
		return rightChoice;
	}
	
}
