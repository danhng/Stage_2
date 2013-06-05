package cw2;

import java.util.ArrayList;
import java.util.ListIterator;

public class FirstFit {
	
	public static void runFirst(ArrayList<Pipe> stock, ArrayList<Pipe> wholesaler, ArrayList<Pipe> orders){

		//Start Time
		long start = System.currentTimeMillis();
		
		//Create iterators for each arraylist
		ListIterator<Pipe> stockit = stock.listIterator();
		ListIterator<Pipe> wholeit = wholesaler.listIterator();
		ListIterator<Pipe> orderit = orders.listIterator();
				
		//Keep reading in orders until none remain
		while(orderit.hasNext()){
					
			Pipe order = orderit.next();
			//System.out.println("New Order: " + order.toString() + "\n");
			
			//Boolean to track if order is met 
			//initially false
			boolean finished = false;
					
			//Loop until order is met
			while((finished == false) &&(stockit.hasNext())){					
				
				//Order in a pipe
				//System.out.println("Current Stock:\n" + StockDisplay.getStock(stock));
				//System.out.println("\nCurrent Pipe:\n" + stockit.next().toString());
				Pipe stockp = stockit.next();		
			
				//Compare current pipe to order - any colour
				if((stockp.getColour() == order.getColour()) || (order.getColour() == 4)){					
					
					if(stockp.getLength() >= order.getLength()){
												
						stockp.setLength(stockp.getLength() - order.getLength());
						stockit.set(stockp);
						
						//Check if <= 5 and set true
						if(stockp.getLength() <= 5){
							
							AlgorithmData.recInc();
							stockit.remove();
						}
						
						finished = true;			
						stockit = stock.listIterator();
					}				
				}
						
				//Compare current pipe to order - any colour
				if((stockp.getLength() >= order.getLength()) && (stockp.getColour() == order.getColour()) && finished == false){
										
					stockp.setLength(stockp.getLength() - order.getLength());
					stockit.set(stockp);
					
					//Check if <= 5 and set true
					if(stockp.getLength() <= 5){
						
						AlgorithmData.recInc();
						//System.out.println("Pipe too small - removing");
						stockit.remove();
					}
					
					finished = true;			
					stockit = stock.listIterator();
				}
				
				//End of loop, check if a new pipe needs to be ordered
				//Check if there is another order, no point ordering a pipe if there is no new order
				if((stockit.hasNext() == false) && (wholeit.hasNext())){
					
					AlgorithmData.orderInc();
					//Add to end of stock
					stock.add(wholeit.next());				
										
					//Reinit iterator, set index
					stockit = stock.listIterator(stock.size()-1);						
														
				}				
			}		
		}
		
		//Print end time
		long end = System.currentTimeMillis();
		System.out.println("First Fit - Time to completion (ms): "+ Long.toString(end - start));
		
		
		//Print out final stock
		System.out.println(AlgorithmData.results());		
		
	}
}
