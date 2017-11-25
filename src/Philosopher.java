
public class Philosopher implements Runnable{
	
	private Thread philosopherThread;
	private volatile Boolean eating;
	private int eatCount;
	private static Chopstick[] chopsticks;
	private Chopstick chopstick1;
	private Chopstick chopstick2;
	
	Philosopher(String name, Chopstick[] allChopsticks){ 
		chopsticks = allChopsticks;
		eatCount = 0;
		
		eating = true;
		philosopherThread = new Thread(this,"Philosopher "+name);
		philosopherThread.start();
		
	}

	@Override
	public void run() {
//		System.out.println(philosopherThread.getName()+" has started ");
		while(eating){
			try{
				getSomeChopsticks();
//					System.out.println(philosopherThread.getName()+" ate");
				eatCount++;
			}catch(Exception e){
				System.out.println(philosopherThread.getName()+" could not get a chopstick");
				e.printStackTrace();
			}finally{
				if(chopstick1.isAcquired()){
					chopstick1.release();
					chopstick2.release();			
				}
			}
		}			
	}
	
	public void stopPhilosopher(){
		eating = false;
	}
	
	public int getEatCount(){
		System.out.println(philosopherThread.getName()+" ate "+eatCount+" times.");
		return eatCount;
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
					// &&chopsticks[(i+1)%5].isAcquired()
					//acquire this chopstick and the next one
					
//					System.out.println(philosopherThread.getName()+" got chopsticks "+i+(i+1)%5);
					
					chopsticks[i].acquire();
					chopsticks[(i+1)%5].acquire(); //mod 5 since there is no chopstick 6

					chopstick1 = chopsticks[i];
					chopstick2 = chopsticks[(i+1)%5];
					
					iDontHaveChopsticks = false;
					break; // We need this to break out of the for loop to stop the while loop
				}
			}
				
		}
	}
}
