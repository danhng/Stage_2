//Initial Author: ?
//Current Author: Alex.Clarke
//Date: 23/11/11
//Purpose: Semaphore class, limits thread access to resources


public class Semaphore {
	
	//Fields//
	
	//Variable which counts available resources
	protected int value;
		
	//Constructors//
	
	//Default Constructor - Initialises value to 0 (No available resources)
	public Semaphore(){
		
		value = 0;
	}
		
	//Initialises value to given parameter (providing parameter is >= 0, else initialises to 0)
	public Semaphore(int initial){
		
		value = (initial>=0) ? initial : 0;
	}
	
	//Methods//
	
	//Lower - Called before Critical Section
	public synchronized void P() throws	InterruptedException{
		
		//While no resources are available
		while (value==0){
			
			//Thread waits
			wait();			
		}
		
		//If resources are available (value > 0) then decrease value (resource being used).		
		value--;
	}	
	
	//Raise - Called after Critical Section
	public synchronized void V(){
		
		//After CS is complete, increment value (resource now available)
		value++;
		//Notify threads waiting to use resource
		notify();
	}
		
}
	
