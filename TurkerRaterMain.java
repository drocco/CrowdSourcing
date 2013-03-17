
public class TurkerRaterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Turker[] turkers = TurkerFactory.getRandomTurkers((float).6, 1, 50);
		Question[] questions = QuestionFactory.getRandomQuestions(2, 100);
		Scheduler sched = new RoundRobin(turkers);
		AnswerManager am = new MajorityVote();
		RatingsManager rm = new SimpleCount();
		
		for(Question q: questions) {
			for(int x = 0; x < 10; x++){
				Turker t = sched.next();
				int answer = t.answerQuestion(q);
				am.recordAnswer(q, t, answer);
				rm.recordAnswer(t, q, answer);
			}
		}

		for(Turker t: turkers) {
			System.out.print(t.getRightPercentage());
			System.out.print(" : ");
			System.out.println(rm.getTurkerRating(t, am));
		}
	}
}
