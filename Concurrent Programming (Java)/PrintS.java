//Author: Alex.Clarke
//Date: 23/11/11
//Purpose: Prints R repeatedly, decrements global semaphore & R binary semaphore / increments S thread's binary semaphore

public class PrintS extends Thread{
	
	//Fields\\	
	private BinarySemaphore slock;
	private BinarySemaphore rlock;
	private Semaphore decreaseT;
	private String letter = "S";
	
	//Constructor\\
	public PrintS(BinarySemaphore sl, BinarySemaphore rl, Semaphore d){	
		
		slock = sl;
		rlock = rl;
		decreaseT = d;		
	}
	
	public void run(){
		
		//Loop through a random number of times
		while(true){
				

			//Now check if the global semaphore can be decreased (CountT > 0)
			try {
				decreaseT.P();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Check if the BinarySemaphore can be decreased (Slock == 1)
			try {
				slock.P();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
			
			//Sleep for random amount of milliseconds
			try {
				sleep((int)(Math.random()*50));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//Then print S
			System.out.print(letter);
				
			//Lastly, increment the Binarysemaphore for the alternate thread
			rlock.V();			
		}
	}		
}
	
	

