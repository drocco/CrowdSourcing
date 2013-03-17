import java.util.Random;

public class TurkerFactory {
	
	private static Random rand = new Random();

	//Even Distribution
	public static Turker[] getRandomTurkers(float minRightPercent, float maxRightPercent, int numOfTurkers){
		Turker[] turkers = new Turker[numOfTurkers];
		for(int x = 0; x < numOfTurkers; x++) {
			turkers[x] = new Turker(rand.nextFloat()*(maxRightPercent-minRightPercent)+minRightPercent, 1, 1);
		}
		
		return turkers;
	}
	
}
