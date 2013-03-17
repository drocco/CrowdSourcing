import java.util.Random;

public class QuestionFactory {
	private static Random rand = new Random();
	
	public static Question[] getRandomQuestions(int numChoices, int numQuestions) {
		
		Question[] qs = new Question[numQuestions];
		
		for(int x = 0; x < numQuestions; x++) {
			qs[x] = new Question(numChoices, rand.nextInt(numChoices) + 1);
		}
		
		return qs;
	}
	
}
