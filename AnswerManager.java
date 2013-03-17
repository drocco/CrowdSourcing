import java.util.ArrayList;
import java.util.HashMap;

public abstract class AnswerManager {

	protected HashMap<Question, ArrayList<TurkerAnswer>> turkerAnswers = new HashMap<Question, ArrayList<TurkerAnswer>>();
	
	public abstract int getCorrectAnswer(Question q, RatingsManager rm);
	
	public void recordAnswer(Question q, Turker t, int answer) {
		ArrayList<TurkerAnswer> answerList = turkerAnswers.get(q);
		if(answerList == null){
			answerList = new ArrayList<TurkerAnswer>();
			turkerAnswers.put(q, answerList);
		}
		answerList.add(new TurkerAnswer(t,answer));
	}
	
	protected static class TurkerAnswer {
		public Turker turk;
		public int answer;
		
		public TurkerAnswer(Turker turk, int answer) {
			super();
			this.turk = turk;
			this.answer = answer;
		}
	}
}
