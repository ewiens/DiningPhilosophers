public class DiningPhilosophers implements Runnable {

	public Thread thread; 
	public static int EATING_TIME = 5*1000; // runs for 5 seconds 5*1000 milliseconds
	public boolean isDining;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		DiningPhilosophers table = new DiningPhilosophers();
		
		delay(EATING_TIME, "");
		

		table.stopDining();
		
	}
	/**
	 * Constructor
	 */
	DiningPhilosophers(){
		thread = new Thread(this,"Table");
		isDining = true;
		thread.start();
	}
	/**
	 * from Plant
	 * @param time
	 * @param errMsg
	 */
	private static void delay(long time, String errMsg) {
		long sleepTime = Math.max(1, time);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			System.err.println(errMsg);
		}
	}
	
	public final int PHILOSOPHER_COUNT = 5;
	public Philosopher[] philosophers;

	@Override
	public void run() {
		// Create the philosophers
		// doing this with a counter to pass in the chopstick numbers
		for (int i=0; i<PHILOSOPHER_COUNT; i++){
			philosophers[i] = new Philosopher(""+i, i, (i+1)%5); //i, i+1mod 5 
		}
		// chill out here
		while(isDining){
			
		}
		// stop the philosophers
		for(Philosopher p: philosophers){
			p.stopPhilosopher();
		}

		
	}
	/*
	 * Stop the table thread
	 */
	public void stopDining(){
		isDining = false;
	}
	
	
}
