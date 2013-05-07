
public class TurkerRaterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Parameters
		double minTurkerProb = .6;
		double maxTurkerProb = .99;
		double minConfidence = .999;
		int numTurkers = 50;
		int numQuestions = 20000;
		int numPossibleAnswers = 2;
		int numDuplications = 20;
		int loops = 100;
		boolean reachConfidence = false;
		
		double errAverage = 0.0;
		double answerAverage = 0.0;
		double questionAverage = 0.0;
		
		for(int y = 0; y < loops; y++) {
			Turker[] turkers = TurkerFactory.getRandomTurkers(minTurkerProb, maxTurkerProb, numTurkers);
			Question[] questions = QuestionFactory.getRandomQuestions(numPossibleAnswers, numQuestions);
			Scheduler sched = new RoundRobin(turkers);
			AnswerManager am = new WeightedMajorityVote(minConfidence);
			RatingsManager rm = new SimpleCount();
			
			int questionsAsked = 0;
			int numCorrect = 0;
			if(!reachConfidence) {
				for(Question q: questions) {
					for(int x = 0; x < numDuplications; x++) {
						Turker t = sched.next();
						int answer = t.answerQuestion(q);
						am.recordAnswer(q, t, answer);
						rm.recordAnswer(t, q, answer);
						questionsAsked++;
					}
					am.calculateAnswers(rm);
					rm.calculateRatings(am);
					am.clearResponses(q);
					
					if(q.getRightChoice() == am.getCorrectAnswer(q)) {
						numCorrect++;
					}
				}
			} else {
				for(Question q: questions){
					double confidence = -1;
					while (confidence < minConfidence) {
						Turker t = sched.next();
						int answer = t.answerQuestion(q);
						am.recordAnswer(q, t, answer);
						rm.recordAnswer(t, q, answer);
						am.calculateAnswers(rm);
						confidence = am.getAnswerConfidence(q);
						questionsAsked++;
					}
					rm.calculateRatings(am);
					am.clearResponses(q);
					
					if(q.getRightChoice() == am.getCorrectAnswer(q)) {
						numCorrect++;
					}
				}
			}
				

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
			questionAverage += questionsAsked;
		}
		
		System.out.print("Average difference: ");
		System.out.println(errAverage/loops);
		System.out.print("Average accuracy: ");
		System.out.println(answerAverage / loops);
		System.out.println("Questions Asked: " + questionAverage / loops);
	}
}
