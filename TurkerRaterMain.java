
public class TurkerRaterMain {

	public static void main(String[] args) {
		//Parameters
		double minTurkerProb = .7;
		double maxTurkerProb = .95;
		int numTurkers = 500;
		int numQuestions = 20000;
		int numPossibleAnswers = 4;
		int numDuplications = 5;
		double desiredConfidence = .95;
		int loops = 100;
		boolean reachConfidence = false;
		
		//Performance variables
		double errAverage = 0.0;
		double answerAverage = 0.0;
		double questionAverage = 0.0;
		
		//Loops many times to smooth out answers
		for(int y = 0; y < loops; y++) {
			
			Turker[] turkers = TurkerFactory.getRandomTurkers(minTurkerProb, maxTurkerProb, numTurkers);
			Question[] questions = QuestionFactory.getRandomQuestions(numPossibleAnswers, numQuestions);
			Scheduler sched = new MTurkScheduler(turkers);
			AnswerManager am = new WeightedMajorityVote(desiredConfidence);
			RatingsManager rm = new RatingsManager();
			int questionsAsked = 0;
			int numCorrect = 0;
			double err = 0;
			
			//Naive loop: Ask each question a fixed number of times
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
				//Loop until desired confidence in answer has been reached
				for(Question q: questions){
					double confidence = -1;
					while (confidence < desiredConfidence) {
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
			
			//Calculate average difference between the computed accuracy and the actual accuracy of Turkers
			for(Turker t: turkers) {
				double real = t.getRightPercentage(); 
				double calc = rm.getTurkerRating(t);
				err += (Math.abs(real - calc));
			}
		
			errAverage += err/turkers.length;
			answerAverage += numCorrect/(double)questions.length;
			questionAverage += questionsAsked;
		}
		
		System.out.println("Average difference: " + errAverage/loops);
		System.out.println("Average accuracy: " + answerAverage / loops);
		System.out.println("Questions Asked: " + questionAverage / loops);
	}
}
