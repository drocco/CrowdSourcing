import java.util.ArrayList;

public class MajorityVote extends AnswerManager {
	
	public MajorityVote(double minConfidence){
		super(minConfidence);
	}

	@Override
	public void calculateAnswers(RatingsManager rm) {
		for (Question q : turkerAnswers.keySet()){
			ArrayList<TurkerAnswer> answerList =  turkerAnswers.get(q);
			
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
			
			answers.put(q, new RatingConfidence(correct + 1, calculateAnswerConfidence(q, correct + 1, rm)));
		}
	}
}
