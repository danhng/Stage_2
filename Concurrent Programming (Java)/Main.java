//Author: Alex.Clarke
//Date: 23/11/11
//Purpose: Main class


//Overview of Semaphore usage
//Thread t only increments the 'global' T Semaphore which the R and S threads
//decrease, thread t has no need for a binary semaphore as there are no constraints
//placed upon when T can be printed.

//Threads s and r decrease the global semaphore, this ensures that the amount
//of R's and S's does not exceed the number of T's

//Threads s and r also decrement/increment two binary semaphores, this allows
//for alternate printing

//E.g. Rlock is set to 1 - R can print, after R prints Rlock is set to 0 and Slock
//is set to 1, S can now print - settings Slock to 0 and Rlock to 1, allowing alternation.




public class Main {


	public static void main(String[] args) throws InterruptedException {		
		
			//Create Binary Semaphores\\			
		
			//Set to 1, R prints first
			BinarySemaphore RLock = new BinarySemaphore(1);
			//Set to 0, S prints after R
			BinarySemaphore SLock = new BinarySemaphore(0);
			
			//Create Counting Semaphore\\
			
			//Global semaphore - accessed by all threads
			Semaphore global = new Semaphore(0);	
						
			//Create three printing threads\\
			
			//Thread t - prints T repeatedly and increments the global T semaphore each time.
			Thread t = new PrintT(global);
			
			//Thread r - prints R, decrements T, increments Slock and decrements Rlock
			Thread r = new PrintR(RLock, SLock, global);
			
			//Thread s - Prints S, decrements T semaphore, increments Rlock and decrements Slock
			Thread s = new PrintS(SLock, RLock, global);		
			
			//Start threads	
			
			
			t.start();												
			r.start();
			s.start();	
			
			
			//Sleep main
			Thread.sleep(550);							
		
			//End
			System.exit(0);	
	}
}
