import java.util.ArrayList;
import java.util.HashMap;

//Contains the logic for calculating correct answers and answer confidence. This is extended to supply logic to calculate correct answers
public abstract class AnswerManager {

	//Mapping of questions to a list of recorded answers and the turker who answered
	//Current version of simulator never has more than one question->answerList mapping at a time, but we should keep it
	//as a mapping in case we want to thread this in the future. (Ask a bunch of questions at once and analyze responses as they come in)
	protected HashMap<Question, ArrayList<TurkerAnswer>> turkerAnswers = new HashMap<Question, ArrayList<TurkerAnswer>>();
	//Mapping of questions to calculated answers and confidence
	protected HashMap<Question, RatingConfidence> answers = new HashMap<Question, RatingConfidence>();
	
	protected double minConfidence;
	
	protected AnswerManager (double minConfidence) {
		this.minConfidence = minConfidence;
	}
	
	//How we calculate the answer TODO: have this method only calculate answer for a specified question, not all
	public abstract void calculateAnswers(RatingsManager rm);
	
	//Set responses for garbage collection when we no longer need them
	public void clearResponses(Question q) {
		turkerAnswers.remove(q);
	}
	
	//Return the previously computed confidence
	public double getAnswerConfidence(Question q) {
		RatingConfidence x = answers.get(q);
		if (x == null) {
			return -1;
		}
		return x.confidence;
	}
	
	//Compute and store confidence values. This is separate from the getAnswerConfidence method so that we don't go crazy
	//recomputing this all the time. We only call this when needed and relay on the cached answer the rest of the time
	public double calculateAnswerConfidence(Question q, int answer, RatingsManager rm) {
		ArrayList<TurkerAnswer> answerList = turkerAnswers.get(q);
		
		if (answerList == null) {
			return -1;
		}
		
		//Worked this out on paper. Simple Bayesian probability 
		double probAssumingCorrect = 1.0/q.getNumberOfChoices();
		double probAssumingIncorrect = 1 - probAssumingCorrect;
		for (TurkerAnswer ta : answerList) {
			double rating = rm.getTurkerRating(ta.turker);
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
	
	//Return the computed correct answer. Also separate from the calculateCorrectAnswer for same reason as above^
	public int getCorrectAnswer(Question q) {
		RatingConfidence x = answers.get(q);
		if (x == null) {
			 return -1;
		}
		return x.answer;
	}
	
	//Stuff the answer into the list
	public void recordAnswer(Question q, Turker t, int answer) {
		ArrayList<TurkerAnswer> answerList = turkerAnswers.get(q);
		if (answerList == null){
			answerList = new ArrayList<TurkerAnswer>();
			turkerAnswers.put(q, answerList);
		}
		answerList.add(new TurkerAnswer(t,answer));
	}
	
	
	
	//Private classes to group information together to fit in the arrayLists
	protected static class TurkerAnswer {
		public Turker turker;
		public int answer;
		
		public TurkerAnswer(Turker turker, int answer) {
			super();
			this.turker = turker;
			this.answer = answer;
		}
	}
	
	protected static class RatingConfidence {
		public int answer;
		public double confidence;
		
		public RatingConfidence(int answer, double confidence) {
			super();
			this.answer = answer;
			this.confidence = confidence;
		}
	}
}
