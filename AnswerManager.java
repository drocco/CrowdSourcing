import java.util.ArrayList;
import java.util.HashMap;

public abstract class AnswerManager {

	protected HashMap<Question, ArrayList<TurkerAnswer>> turkerAnswers = new HashMap<Question, ArrayList<TurkerAnswer>>();
	
	protected HashMap<Question, RatingConfidence> answers = new HashMap<Question, RatingConfidence>();
	
	protected double minConfidence;
	
	public abstract void calculateAnswers(RatingsManager rm);
	
	protected AnswerManager (double minConfidence) {
		this.minConfidence = minConfidence;
	}
	
	public void clearResponses(Question q) {
		turkerAnswers.remove(q);
	}
	//returns the confidence of an answer, clears the question -> responses map when confidence threshold is reached
	public double getAnswerConfidence(Question q) {
		RatingConfidence x = answers.get(q);
		if (x == null) {
			return 0;
		}
		return x.confidence;
	}
	
	public double calculateAnswerConfidence(Question q, int answer, RatingsManager rm) {
		ArrayList<TurkerAnswer> answerList = turkerAnswers.get(q);
		
		if (answerList == null) {
			return 0;
		}
		
		double probAssumingCorrect = 1.0/q.getNumberOfChoices();
		double probAssumingIncorrect = 1 - probAssumingCorrect;
		for (TurkerAnswer ta : answerList) {
			double rating = rm.getTurkerRating(ta.turk);
			if (rating < 0){
				rating = 1.0/q.getNumberOfChoices() + (1.0/q.getNumberOfChoices() * .1);
			}
			
			if (ta.answer == answer) {
				probAssumingCorrect *= rating;
				probAssumingIncorrect *= (1 - rating) / (q.getNumberOfChoices() - 1.0);
			} else {
				probAssumingCorrect *= (1 - rating) / (q.getNumberOfChoices() - 1.0);
				probAssumingIncorrect *= (1.0 / (q.getNumberOfChoices() - 1)) * (rating + ((1 - rating) / (q.getNumberOfChoices() - 1.0)) * (q.getNumberOfChoices() - 2));
			}
		}
		return probAssumingCorrect / (probAssumingCorrect + probAssumingIncorrect);
	}
	
	public int getCorrectAnswer(Question q) {
		RatingConfidence x = answers.get(q);
		if (x == null) {
			 return -1;
		}
		return x.rating;
	}
	
	public void recordAnswer(Question q, Turker t, int answer) {
		ArrayList<TurkerAnswer> answerList = turkerAnswers.get(q);
		if (answerList == null){
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
	
	protected static class RatingConfidence {
		public int rating;
		public double confidence;
		
		public RatingConfidence(int rating, double confidence) {
			super();
			this.rating = rating;
			this.confidence = confidence;
		}
	}
}
