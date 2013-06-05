//Author: Alex.Clarke
//Date: 23/11/11
//Purpose: Prints R repeatedly, decrements global semaphore & S binary semaphore / increments R thread's binary semaphore

public class PrintR extends Thread {

	
	//Fields\\	
	private BinarySemaphore rlock;
	private BinarySemaphore slock;	
	private Semaphore decreaseT;
	
	private String letter = "R";
	
	//Constructor\\
	public PrintR(BinarySemaphore rl, BinarySemaphore sl, Semaphore d){	
	
		decreaseT = d;
		rlock = rl;
		slock = sl;		
	}		
	
	//Methods\\
	public void run(){
		
		//Loop through random number of times.
		for(int i=0; i<Math.random()*75;i++){
				
			//Now, check if the global semaphore can be decreased (CountT > 0)
			try {
				decreaseT.P();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Then check the RS binarySemaphore (rlock == 1)
			try {
				rlock.P();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Sleep for a random amount of milliseconds
			try {
				sleep((long)(Math.random()*50));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}					
					
			//Now print R
			System.out.print(letter);	
			
			//Lastly increment alternate thread's BinarySemaphore (slock)
			slock.V();		
		}	
	}		
}
	

