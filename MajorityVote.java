import java.util.ArrayList;


public class MajorityVote extends AnswerManager {

	@Override
	public int getCorrectAnswer(Question q, RatingsManager rm) {
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
		
		return correct + 1;
	}
}
