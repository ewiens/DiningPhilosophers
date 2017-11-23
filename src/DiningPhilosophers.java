public class DiningPhilosophers implements Runnable {

 
	public static int EATING_TIME = 1*1000; // runs for x seconds x*1000 milliseconds


	/**
	 * makes a new DiningPhilosopher class and delays for the amojnt of time specified. Then stops the threads
	 * @param args
	 */
	public static void main(String[] args){
		DiningPhilosophers table = new DiningPhilosophers();
		
		delay(EATING_TIME, "");
		
		table.stopDining();
		table.waitToStop();
		
	}
	
	/**
	 * from Plant
	 * 
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
	
	public Thread thread;
	public volatile boolean isDining;
	public final int PHILOSOPHER_COUNT = 5;
	public Philosopher[] philosophers;
	public Chopstick[] chopsticks;
	
	/**
	 * Constructor
	 */
	DiningPhilosophers(){
		thread = new Thread(this,"table");
		isDining = true;
		thread.start();
	}

	@Override
	public void run() {
		System.out.println("Main thread started");
		
		Chopstick[] chopsticks = new Chopstick[PHILOSOPHER_COUNT];
		for (int i = 0; i<PHILOSOPHER_COUNT; i++){
			chopsticks[i] = new Chopstick(i);
		}

		// Create the philosophers
		// doing this with a counter to pass in the chopstick numbers
		Philosopher[] philosophers = new Philosopher[PHILOSOPHER_COUNT];
		for (int i = 0; i<PHILOSOPHER_COUNT; i++){
			philosophers[i] = new Philosopher(""+i,chopsticks); //i, i+1 mod 5 
			
		}
		// chill out here
		while(isDining){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// stop the philosophers
		for(Philosopher p: philosophers){
			p.stopPhilosopher();
		}
		
		for(Philosopher p: philosophers){
			p.waitToStopPhilosopher();
		}

		
	}
	
	/**
	 * Stop the table thread
	 */
	public void stopDining(){
		isDining = false;
		this.notifyAll();
	}
	
	public void waitToStop() {
		try {
			thread.join();
			System.out.println("Thread joined");
		} catch (InterruptedException e) {
			System.err.println(thread.getName() + " stop malfunction");
		}

	}
	
}
