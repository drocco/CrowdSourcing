import java.util.ArrayList;

//Answers are calculated by a simple majority vote. Whichever answer has the highest tally wins
public class MajorityVote extends AnswerManager {
	
	public MajorityVote(double minConfidence){
		super(minConfidence);
	}

	//Works for now (because the loop outer loop is always iterating over a collection of size 1) but we may want to change
	//this in the future. Maybe have this only calculate the correct answer for a specified question instead of all questions.
	@Override
	public void calculateAnswers(RatingsManager rm) {
		for (Question q : turkerAnswers.keySet()){
			ArrayList<TurkerAnswer> answerList =  turkerAnswers.get(q);
			
			//Array to hold a tally for each answer choice
			int[] count = new int[q.getNumberOfChoices()];
			
			for(TurkerAnswer ta: answerList) {
				count[ta.answer - 1]++;
			}
			
			int correct = -1;
			int size = -1;
			
			for(int x = 0; x < count.length; x++) {
				if(count[x] > size) {
					correct = x;
					size = count[x];
				}
			}
			
			//Store answer in the cache
			answers.put(q, new RatingConfidence(correct + 1, calculateAnswerConfidence(q, correct + 1, rm)));
		}
	}
}
