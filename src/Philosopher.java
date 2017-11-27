
public class Philosopher implements Runnable{
	
	private static final int WAIT_TIME_MAX = 10;
	
	private Thread philosopherThread;
	private volatile Boolean dining;
	private int eatCount;
	private Chopstick chopstick1;
	private Chopstick chopstick2;
	
	
	/**
	 * creates a Philosoper
	 * @param name - the name of the philosopher
	 * @param allChopsticks - the set of all chopsticks. Should have been passed to all philosophers
	 */
	Philosopher(int name, Chopstick[] allChopsticks){ 
		int numberOfChopstickOne = name;
		int numberOfChopstickTwo = (name+1)%5;
		
		if(numberOfChopstickOne>numberOfChopstickTwo){
			chopstick1 = allChopsticks[numberOfChopstickTwo];
			chopstick2 = allChopsticks[numberOfChopstickOne];
		}else{
			chopstick1 = allChopsticks[numberOfChopstickOne];
			chopstick2 = allChopsticks[numberOfChopstickTwo];
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
	 * acquires two chopsticks. Does so by checking the left chopstick. If left is not acquired, grab it and the right. 
	 * This avoids deadlock because this method is synchronized. No two philosophers will attempt to grab chop sticks at the same time.
	 * If one of the chopsticks is currently acquired, the philosopher will wait for it. There is no circular dependency because
	 * the first philosopher will always get his/her two chopsticks.
	 */
	private synchronized void getSomeChopsticks() {
		//acquire the left and right chopstick
		chopstick1.acquire();
		chopstick2.acquire();
	}
}
