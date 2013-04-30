import java.util.ArrayList;

public class WeightedMajorityVote extends AnswerManager {
	
	public WeightedMajorityVote(double minConfidence){
		super(minConfidence);
	}

	@Override
	public void calculateAnswers(RatingsManager rm) {
		for (Question q : turkerAnswers.keySet()){
			ArrayList<TurkerAnswer> answerList =  turkerAnswers.get(q);
			
			double[] count = new double[q.getNumberOfChoices()];
			
			for(TurkerAnswer ta: answerList) {
				double rating = rm.getTurkerRating(ta.turk);
				if(rating < 0) {
					rating = 1.0/q.getNumberOfChoices();
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
