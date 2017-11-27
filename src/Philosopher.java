
public class Philosopher implements Runnable{
	
	private static final int WAIT_TIME_MAX = 10;
	
	private Thread philosopherThread;
	private volatile Boolean dining;
	private int eatCount;
	private static Chopstick[] chopsticks;
	private Chopstick chopstick1;
	private Chopstick chopstick2;
	
	
	/**
	 * creates a Philosoper
	 * @param name - the name of the philosopher
	 * @param allChopsticks - the set of all chopsticks. Should have been passed to all philosophers
	 */
	Philosopher(int name, Chopstick[] allChopsticks){ 
		chopsticks = allChopsticks;
		int myChopstick1 = name;
		int myChopstick2 = (name+1)%5;
		
		if(myChopstick1>myChopstick2){
			chopstick1 = chopsticks[myChopstick2];
			chopstick2 = chopsticks[myChopstick1];
		}else{
			chopstick1 = chopsticks[myChopstick1];
			chopstick2 = chopsticks[myChopstick2];
		}

		eatCount = 0;
		
		dining = true;
		philosopherThread = new Thread(this,"Philosopher "+name);
		philosopherThread.start();
		
	}

	@Override
	/**
	 * while we haven't been told to stop dining, philosophize and eat.
	 */
	public void run() {
//		System.out.println(philosopherThread.getName()+" has started ");
		while(dining) {
			philosophize();
			eat();
		}
			
	}
	
	/**
	 * to simulate philosophinzing, we wait for a random amount of time.
	 */
	public void philosophize() {
		long randomTime = getTime();
		DiningPhilosophers.delay(randomTime, "error while philosopher " + philosopherThread.getName()+ "tried to philosophize");
	}
	
	/**
	 * get chopsticks, then to simulate eating, wait for a random amount of time. After eating, release the chopsticks.
	 */
	public void eat() {
		long randomTime = getTime();
		try{
			getSomeChopsticks();
//					System.out.println(philosopherThread.getName()+" ate");
			eatCount++;
			DiningPhilosophers.delay(randomTime, "error while philosopher " + philosopherThread.getName()+ "tried to eat");
		}catch(Exception e){
			System.out.println(philosopherThread.getName()+" could not get a chopstick");
			e.printStackTrace();
		}finally{
			chopstick1.release();
			chopstick2.release();			
		}
	}
	
	/** 
	 * sets dining to false, which causes the main loop to stop.
	 * @see #run()
	 */
	public void stopPhilosopher(){
		dining = false;
	}
	
	/**
	 * prints and returns the number of times this philosopher has eaten.5
	 * @return the number of times this philosopher has eaten
	 */
	public int getEatCount(){
		System.out.println(philosopherThread.getName()+" ate "+eatCount+" times.");
		return eatCount;
	}
	
	/**
	 * stops this thread
	 */
	public void waitToStopPhilosopher(){
		try {
			philosopherThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @return a random long between 0 and WAIT_TIME_MAX
	 */
	private long getTime() {
		//random time between 0 and WAIT_TIME_MAX
		return (long) (Math.random()*WAIT_TIME_MAX);
	}
	
	/**
	 * acquires two chopsticks. Does so by checking all even indexes of chopsticks. If the even index is not acquired, then acquire that
	 * chopstick and the next one as well. This gives an atomic interaction between the philosopher. 
	 * The philosopher will keep trying to get a chopstick until they are successful.
	 */
	private synchronized void getSomeChopsticks() {
		boolean iDontHaveChopsticks = true;
		//the philosopher will just keep trying until he/she gets two chopsticks
		while (iDontHaveChopsticks) {
			//if it isn't acquired
			if(!(chopstick1.isAcquired())) {
				//acquire this chopstick and the next one
				chopstick1.acquire();
				chopstick2.acquire(); //mod 5 since there is no chopstick 6
				
				iDontHaveChopsticks = false;
				break; // We need to break out of the for loop (we don't need to check the rest of the chopsticks if we have a set)
			}
		}
				
	}
}
