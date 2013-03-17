import java.util.ArrayList;


public class SimpleCount extends RatingsManager {

	@Override
	public float getTurkerRating(Turker t, AnswerManager am) {
		ArrayList<QuestionAnswer> answers = questionAnswers.get(t);
		
		if(answers.isEmpty()) {
			return (float) -1;
		}
		
		float numRight = 0;
		
		for (QuestionAnswer qa: answers) {
			if(am.getCorrectAnswer(qa.question, this) == qa.answer) {
				numRight++;
			}
		}
		
		return numRight/(float)answers.size();
	}

	@Override
	public float getTurkerConfidence(Turker t) {
		return 1; //not sure for now.... maybe just use some probablity of his answers vs chance they are random?
	}
}
