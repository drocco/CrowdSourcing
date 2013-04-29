
public class TurkerRaterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Parameters
		double minTurkerProb = .6;
		double maxTurkerProb = .85;
		int numTurkers = 25;
		int numQuestions = 10000;
		int numPossibleAnswers = 2;
		int numDuplications = 10;
		int loops = 100;
		
		double errAverage = 0.0;
		double answerAverage = 0.0;
		
		for(int y = 0; y < loops; y++) {
			Turker[] turkers = TurkerFactory.getRandomTurkers(minTurkerProb, maxTurkerProb, numTurkers);
			Question[] questions = QuestionFactory.getRandomQuestions(numPossibleAnswers, numQuestions);
			Scheduler sched = new RoundRobin(turkers);
			AnswerManager am = new WeightedMajorityVote();
			RatingsManager rm = new SimpleCount();
			
			int numCorrect = 0;
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
				if(iter % 10 == 0) {
					rm.calculateRating(am);
				}
				
				if(q.getRightChoice() == am.getCorrectAnswer(q)) {
					numCorrect++;
				}
			}
			
			am.calculateAnswers(rm);
			rm.calculateRating(am);
	
			double err = 0;
			for(Turker t: turkers) {
				double real = t.getRightPercentage(); 
				double calc = rm.getTurkerRating(t);
				err += (Math.abs(real - calc));
//				System.out.print(real);
//				System.out.print(" : ");
//				System.out.println(calc);
			}
		
			errAverage += err/turkers.length;
			answerAverage += numCorrect/(double)questions.length;
		}
		
		System.out.print("Average difference: ");
		System.out.println(errAverage/loops);
		System.out.print("Average accuracy: ");
		System.out.println(answerAverage / loops);
	}
}
