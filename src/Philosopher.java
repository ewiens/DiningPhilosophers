
public class Philosopher implements Runnable{
	
	private Thread philosopherThread;
	private volatile Boolean eating;
	private int eatCount;
	private int chopstick1;
	private int chopstick2;
	
	Philosopher(String name){ //passed in  chop1,  chop2
		//Asked for the smaller numbered chopstick first
//		if (chop1>chop2){
//			chopstick2 = chop1;
//			chopstick1 = chop2;
//		}else{
//			chopstick1 = chop1;
//			chopstick2 = chop2;
//		}
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
//				chopstick1.acquire();
//				chopstick2.acquire();
//				System.out.println(philosopherThread.getName()+" ate");
				eatCount++;
			}catch(Exception e){
				System.out.println(philosopherThread.getName()+" could not get a chopstick");
			}finally{
//				chopstick1.release();
//				chopstick2.release();
				
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

}
