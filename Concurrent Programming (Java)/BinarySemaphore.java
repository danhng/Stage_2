//Author: Alex.Clarke
//Date: 23/11/11
//Purpose: Binary Semaphore Class - Extends Semaphore Class
//limits thread access to a single resource

public class BinarySemaphore extends Semaphore {

		//Fields//
		
		//None - use protected value field in super class
		
		//Constructors//
		
		//Default Constructor - Initialises value to 0 (calls constructor of superclass)
		public BinarySemaphore(){
			
			super();		
		}
		
		//Initialises value to 1 or 0 (given parameter)
		public BinarySemaphore(int initial){
			
		//If parameter is 1 or 0 then initialise value to given parameter, else initialise to 0	
		value = (initial == 0 || initial == 1) ? initial : 0;				
		}		
	}
	
	
