import java.util.ArrayList;
import java.util.HashMap;

public abstract class RatingsManager {
	
	protected HashMap<Turker,ArrayList<QuestionAnswer>> questionAnswers = new HashMap<Turker,ArrayList<QuestionAnswer>>();
	
	public abstract float getTurkerRating(Turker t, AnswerManager am);
	
	public abstract float getTurkerConfidence(Turker t);
	
	public void recordAnswer(Turker t, Question q, int answer) {
		ArrayList<QuestionAnswer> answerList = questionAnswers.get(t);
		if(answerList == null){
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
