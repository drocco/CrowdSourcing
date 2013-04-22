import java.util.ArrayList;
import java.util.HashMap;

public abstract class AnswerManager {

	protected HashMap<Question, ArrayList<TurkerAnswer>> turkerAnswers = new HashMap<Question, ArrayList<TurkerAnswer>>();
	
	protected HashMap<Question, Integer> answers = new HashMap<Question, Integer>();
	
	public abstract void calculateAnswers(RatingsManager rm);
	
	public double getAnswerConfidence(Question q) {
		return -1;
	}
	
	public int getCorrectAnswer(Question q) {
		Integer x = answers.get(q);
		if (x == null) {
			x = -1;
		}
		return x;
	}
	
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
