
public class Chopstick {
	private int chopStickNum;
	private boolean acquired;
	
	Chopstick(int n) {
		chopStickNum = n;
		acquired = false;
	}
	
	public synchronized void acquire() {
		while (acquired) {
			try {
				wait();
			} catch (InterruptedException ignore) {ignore.printStackTrace();}
		}
		acquired = true;	
	}
	
	
	public synchronized void release() {
		acquired = false;
		notifyAll();	
	}
	
	public synchronized boolean isAcquired() {
		return acquired;
	}
	
}
