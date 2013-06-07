import java.util.ArrayList;

//Answers are calculated by a weighted majority vote. Tallies are weighted by the computed accuracy of the Turker answering the question.
public class WeightedMajorityVote extends AnswerManager {
	
	public WeightedMajorityVote(double minConfidence){
		super(minConfidence);
	}

	//Works for now (because the loop outer loop is always iterating over a collection of size 1) but we may want to change
	//this in the future. Maybe have this only calculate the correct answer for a specified question instead of all questions.
	@Override
	public void calculateAnswers(RatingsManager rm) {
		for (Question q : turkerAnswers.keySet()){
			ArrayList<TurkerAnswer> answerList =  turkerAnswers.get(q);
			
			double[] count = new double[q.getNumberOfChoices()];
			
			for(TurkerAnswer ta: answerList) {
				double rating = rm.getTurkerRating(ta.turker);
				if(rating < 0) {
					rating = 1.0/q.getNumberOfChoices() + (1.0/q.getNumberOfChoices() *.1);
				}
				count[ta.answer - 1] += rating;
			}
			
			int correct = -1;
			double size = -1;
			
			for(int x = 0; x < count.length; x++) {
				if(count[x] > size) {
					correct = x;
					size = count[x];
				}
			}
			
			answers.put(q, new RatingConfidence(correct + 1, calculateAnswerConfidence(q, correct + 1, rm)));
		}
	}
}
