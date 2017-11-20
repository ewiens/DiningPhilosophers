
public class Philosopher implements Runnable{
	
	Thread philosopherThread;
	
	Philosopher(int chop1, int chop2){
		
		philosopherThread = new Thread(this,"Philosopher"+chop1);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
