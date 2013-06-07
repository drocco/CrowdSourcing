import java.util.Random;

//Factory to make a lot of Turkers
public class TurkerFactory {
	
	private static Random rand = new Random();

	//Even Distribution
	public static Turker[] getRandomTurkers(double minRightPercent, double maxRightPercent, int numOfTurkers){
		Turker[] turkers = new Turker[numOfTurkers];
		for(int x = 0; x < numOfTurkers; x++) {
			turkers[x] = new Turker(rand.nextDouble()*(maxRightPercent-minRightPercent)+minRightPercent);
		}
		
		return turkers;
	}
	
}
