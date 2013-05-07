import java.util.ArrayList;
import java.util.HashMap;

public abstract class RatingsManager {
	
	protected HashMap<Turker,ArrayList<QuestionAnswer>> questionAnswers = new HashMap<Turker,ArrayList<QuestionAnswer>>();
	
	public double getTurkerRating(Turker t) {
		if(t.getQuestionsAnswered() <= 10) {
			return -1.0;
		}
		return t.getNumRight() / (double) t.getQuestionsAnswered();
	}
	
	public abstract void calculateRatings(AnswerManager am);
	
	public abstract double getTurkerConfidence(Turker t);
	
	public void recordAnswer(Turker t, Question q, int answer) {
		ArrayList<QuestionAnswer> answerList = questionAnswers.get(t);
		if(answerList == null) {
			answerList = new ArrayList<QuestionAnswer>();
			questionAnswers.put(t, answerList);
		}
		answerList.add(new QuestionAnswer(q,answer));
	}
	
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
