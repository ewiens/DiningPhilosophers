
public class Philosopher implements Runnable{
	
	private Thread philosopherThread;
	private Boolean eating;
	private int eatCount;
	private int chopstick1;
	private int chopstick2;
	
	Philosopher(String name,int chop1, int chop2){ //passed in  chop1,  chop2
		if (chop1>chop2){
			chopstick2 = chop1;
			chopstick1 = chop2;
		}else{
			chopstick1 = chop1;
			chopstick2 = chop2;
		}
		eatCount = 0;
		
		eating = true;
		philosopherThread = new Thread(this,"Philosopher "+name);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(eating){
			try{
//				chopstick1.acquire();
//				chopstick2.acquire();
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
		try {
			philosopherThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
