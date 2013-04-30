import java.util.ArrayList;


public class SimpleCount extends RatingsManager {

	@Override
	public void calculateRatings(AnswerManager am) {
		for(Turker t: questionAnswers.keySet()) {
			ArrayList<QuestionAnswer> answers = questionAnswers.get(t);
			
			for (QuestionAnswer qa: answers) {
				if(am.getCorrectAnswer(qa.question) == qa.answer) {
					t.setNumRight(t.getNumRight() + 1);
				}
				t.setQuestionsAnswered(t.getQuestionsAnswered() + 1);
			}
		}
		questionAnswers.clear();
	}
	
	@Override
	public double getTurkerConfidence(Turker t) {
		return 1; //not sure for now.... maybe just use some probability of his answers versus chance they are random?
	}
}
