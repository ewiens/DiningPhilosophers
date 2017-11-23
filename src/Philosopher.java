
public class Philosopher implements Runnable{
	
	private Thread philosopherThread;
	private volatile Boolean eating;
	private int eatCount;
	private static Chopstick[] chopsticks;
	private Chopstick chopstick1;
	private Chopstick chopstick2;
	
	Philosopher(String name, Chopstick[] allChopsticks){ 
		//passed in  chop1,  chop2
		//Asked for the smaller numbered chopstick first
		//		if (chop1>chop2){
		//			chopstick2 = chop1;
		//			chopstick1 = chop2;
		//		}else{
		//			chopstick1 = chop1;
		//			chopstick2 = chop2;
		//		}
		chopsticks = allChopsticks;
		eatCount = 0;
		
		eating = true;
		philosopherThread = new Thread(this,"Philosopher "+name);
		philosopherThread.start();
		
	}

	@Override
	public void run() {
		System.out.println(philosopherThread.getName()+" has started ");
		while(eating){
			try{
				getSomeChopsticks();
				System.out.println(philosopherThread.getName()+" ate");
				eatCount++;
			}catch(Exception e){
				System.out.println(philosopherThread.getName()+" could not get a chopstick");
			}finally{
				chopstick1.release();
				chopstick2.release();
				
			}
		}
	}
	
	public void stopPhilosopher(){
		eating = false;
	}
	
	public void waitToStopPhilosopher(){
		try {
			philosopherThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private synchronized void getSomeChopsticks() {
		boolean iDontHaveChopsticks = true;

		//the philosopher will just keep trying until he/she gets two chopsticks
		while (iDontHaveChopsticks) {
			//if we just go up by two every time and check that way, we are essentially treating  it like it is ordered and atomic
			for (int i = 0;i<chopsticks.length;i+=2) {
				//if it isn't acquired
				if(!(chopsticks[i].isAcquired())) {
					//acquire this chopstick and the next one
					chopsticks[i].acquire();
					chopsticks[i+1].acquire();
				}
						
			}
		}
	}
}
