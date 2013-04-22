
public class TurkerRaterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double minTurkerProb = .6;
		double maxTurkerProb = .85;
		int numTurkers = 25;
		int numQuestions = 10000;
		int numPossibleAnswers = 2;
		int numDuplications = 10;
		
		Turker[] turkers = TurkerFactory.getRandomTurkers(minTurkerProb, maxTurkerProb, numTurkers);
		Question[] questions = QuestionFactory.getRandomQuestions(numPossibleAnswers, numQuestions);
		Scheduler sched = new RoundRobin(turkers);
		AnswerManager am = new WeightedMajorityVote();
		RatingsManager rm = new SimpleCount();
		
		int iter = 0;
		for(Question q: questions) {
			iter++;
			for(int x = 0; x < numDuplications; x++) {
				Turker t = sched.next();
				int answer = t.answerQuestion(q);
				am.recordAnswer(q, t, answer);
				rm.recordAnswer(t, q, answer);
			}
			am.calculateAnswers(rm);
			if(iter % 100 == 0) {
				rm.calculateRating(am);
			}
		}
		
		am.calculateAnswers(rm);
		rm.calculateRating(am);

		double err = 0;
		for(Turker t: turkers) {
			double real = t.getRightPercentage(); 
			double calc = rm.getTurkerRating(t);
			err += (Math.abs(real - calc));
			System.out.print(real);
			System.out.print(" : ");
			System.out.println(calc);
		}
		
		System.out.print("Average difference: ");
		System.out.print(err/turkers.length);
	}
}
