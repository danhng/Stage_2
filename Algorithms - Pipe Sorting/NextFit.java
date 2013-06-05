package cw2;

import java.util.ArrayList;
import java.util.ListIterator;

public class NextFit {

	//Traverses through stock fulfilling orders
	//Does not 'look back' in stock 	
	
	//Takes in current stock, wholesalers stock and customer orders
	public static void runNext(ArrayList<Pipe> stock, ArrayList<Pipe> wholesaler, ArrayList<Pipe> orders){

		//Start Time
		long start = System.currentTimeMillis();
		
		//Create iterators for each arraylist
		ListIterator<Pipe> stockit = stock.listIterator();
		ListIterator<Pipe> wholeit = wholesaler.listIterator();
		ListIterator<Pipe> orderit = orders.listIterator();
		
		Pipe stockp = stockit.next();
		
		//Order finished
		boolean fin; 	
		
		//Read in orders
		while(orderit.hasNext()){
			
			//New order, not yet complete
			fin = false;		
			
			//Current Order
			Pipe order = orderit.next();
			
			//System.out.println("ORDER: " + order.toString());
			
			while(fin == false){
					
				//Compare current pipe to order
				if ((stockp.getColour() == order.getColour()) || (order.getColour() == 4)){
					
					//If the stock pipe is >= to order then cut
					if(stockp.getLength() >= order.getLength()){						
												
						stockp.setLength(stockp.getLength()-order.getLength());
	
					}
					//Otherwise cut and update stock
					if(stockp.getLength() > 5){
							
					stockit.set(stockp);
					//Set fin to true, this order is done	
					fin = true;		
							
					}
					//Otherwise recycle
					if (stockp.getLength() <= 5){
							
						AlgorithmData.recInc();							
						stockit.remove();
						//fin remains false, next pipe is picked up
							
					}												
				}									
									
				//If we end loop without matching and there is another pipe, move to it
				if((stockit.hasNext() == true) && (fin == false)){
					
					stockp = stockit.next();					
				}				
				
				//If we end loop without matching and there is no next pipe order a new one
				else if((stockit.hasNext() == false) && (fin == false)){
					
					AlgorithmData.orderInc();
					stockit.add(wholeit.next());			
					stockit = stock.listIterator(stock.size()-1);
					stockp = stockit.next();											
					
				}			
			}			
		}					
				
		//Print out time and data
		long end = System.currentTimeMillis();
		System.out.println("Next Fit - Time to completion: " + Long.toString(end-start));
		System.out.println(AlgorithmData.results());
	
	}	
}	