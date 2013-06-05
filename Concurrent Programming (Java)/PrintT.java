//Author: Alex.Clarke
//Date: 23/11/11
//Purpose: Prints T repeatedly, increments global semaphore


public class PrintT extends Thread{
	
	//Fields\\
	
	//Global semaphore - increased when T is printed
	private Semaphore increase;		
	private String letter = "T";
	
	//Constructor\\
	public PrintT(Semaphore i){
	
		increase = i;		
	}	
	
	public void run(){		
		
		//Print repeatedly
		while(true){		
				
			//Sleep for a random amount of milliseconds
			try {
				Thread.sleep((int)(Math.random()*50));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Print the letter T
			System.out.print(letter);
			
			//Increment the global T semaphore
			increase.V();		
			

		}		
	}	
}
