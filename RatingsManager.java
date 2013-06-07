import java.util.ArrayList;
import java.util.HashMap;

//Logic that keeps track of and computes ratings of each turker
public class RatingsManager {
	
	//Keep track of each Turker and which questions they answered and what they answered
	protected HashMap<Turker,ArrayList<QuestionAnswer>> questionAnswers = new HashMap<Turker,ArrayList<QuestionAnswer>>();
	
	//Return rating. Simple computation of numCorrectAnswers/totalQuestionsAnswered
	public double getTurkerRating(Turker t) {
		if(t.getQuestionsAnswered() <= 10) {
			return -1.0;
		}
		return t.getNumRight() / (double) t.getQuestionsAnswered();
	}
	
	//Called when a question answer is settled. This method records which turkers got it right
	//and which got it wrong. TODO change how this works if we want to multithread this
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
	
	//Stuff some data into the structures
	public void recordAnswer(Turker t, Question q, int answer) {
		ArrayList<QuestionAnswer> answerList = questionAnswers.get(t);
		if(answerList == null) {
			answerList = new ArrayList<QuestionAnswer>();
			questionAnswers.put(t, answerList);
		}
		answerList.add(new QuestionAnswer(q,answer));
	}
	
	//Private class to group questions with the answer the turker gave
	protected static class QuestionAnswer {
		public Question question;
		public int answer;
		
		public QuestionAnswer(Question q, int answer) {
			super();
			this.question = q;
			this.answer = answer;
		}
	}
}
