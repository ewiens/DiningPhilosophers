
public class Chopstick {
	public int chopStickNum;
	private boolean acquired;
	
	Chopstick(int n) {
		chopStickNum = n;
		acquired = false;
	}
	
	public synchronized boolean acquire() {
		if (acquired) {
			return false;
		}
		acquired = true;	
		return true;
	}
	
	
	public synchronized void release() {
		if (!acquired) {
			System.err.println("Something isn't right here, you're attempting to release chopsticks that aren't acquired");
		}
		acquired = false;
	}
	
	public synchronized boolean isAcquired() {
		return acquired;
	}
	
}
