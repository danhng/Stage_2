package cw2;

import java.util.ArrayList;
import java.util.ListIterator;


//Read in order
//Read in through all stock - find smallest pipe
//Cut order

// 1) No pipe match? Order a new one, check new pipe 
// 2) Cut if possible
// 3) Else go to 1)


public class BestFit {
	
	public static void runBest(ArrayList<Pipe> stock, ArrayList<Pipe> wholesaler, ArrayList<Pipe> orders){

		//Start time
		long start = System.currentTimeMillis();
				
		//Create iterators for each arraylist
		ListIterator<Pipe> stockit = stock.listIterator();
		ListIterator<Pipe> wholeit = wholesaler.listIterator();
		ListIterator<Pipe> orderit = orders.listIterator();		
		
     	//Keep reading in orders until none remain
		while(orderit.hasNext()){			

			//Ordered pipe						
			Pipe order = orderit.next();
			//System.out.println("New Order: " + order.toString() + "\n");
			
			//Order met
			boolean finished = false;
			
			//Boolean - checks if a pipe has been picked as base
			boolean initpick = false;
					
			//Int to store index of smallest
			int index = 0;
			
			//Loop until order is met
			while(finished == false){				
				
				//Loop through stock, cut from smallest pipe when we reach the end
				while(stockit.hasNext()){			
						
				//Order in a pipe
				Pipe stockp = stockit.next();			
			
				//Compare current pipe to order - any colour
				if((stockp.getColour() == order.getColour()) || (order.getColour() == 4)){
					
					//Is current pipe large enough to fulfill order
					if(stockp.getLength() >= order.getLength()){
						
							//If this is first pipe, it is the current smallest
							if(initpick == false){
								
								index = stockit.nextIndex() - 1;
								initpick = true;
								
							}
							//Otherwise compare to current smallest and replace if possible
							if(stockp.getLength() < stock.get(index).getLength()){
								
								//If it is smaller, move index up to this pipe
								index = stockit.nextIndex() - 1;
								initpick = true;							
								
							}
						}								
					}										
					
					//Is current pipe large enough to fulfill order
					if(stockp.getLength() >= order.getLength() && (stockp.getColour() == order.getColour())){
						
							//If this is first pipe, it is the current smallest
							if(initpick == false){
								
								index = stockit.nextIndex() - 1;
								initpick = true;
								
							}
							//Otherwise compare to current smallest and replace if possible
							if(stockp.getLength() < stock.get(index).getLength()){
								
								//If it is smaller, move index up to this pipe
								index = stockit.nextIndex() - 1;
								initpick = true;								
								
							}
						}								
					}						
				
				//End of stock reached
				//If no match has been found need to order a new pipe
				//Otherwise cut the pipe
				if((stockit.hasNext() == false) && (initpick == false)){
										
					if(wholeit.hasNext()){
					stock.add(wholeit.next());				
					
					//Pipe ordered
					AlgorithmData.orderInc();
					
					//Reinit iterator, set index
					stockit = stock.listIterator(stock.size()-1);
					}
				}
				//Else, we've reached the end of stock and a match has been found
				if(initpick == true){
					
					//Cut the pipe at index
					Pipe cut = stock.get(index);
					
					
					cut.setLength(cut.getLength() - order.getLength());
					
					if(cut.getLength()<=5){
						

						AlgorithmData.recInc();
						stock.remove(index);
						
					}
					else{					
						stock.set(index, cut);				
					}

										
					finished = true;
					
					//Reset index to start
					stockit = stock.listIterator(0);					
				}	
			}	
		}
				
		//Print end time
		long end = System.currentTimeMillis();
		System.out.println("Best Fit - Time to completion (ms): "+ Long.toString(end - start));
				
		//Print out final stock	
		System.out.println(AlgorithmData.results());		


	}
}