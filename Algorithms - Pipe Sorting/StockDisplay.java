package cw2;

import java.util.ArrayList;
import java.util.ListIterator;

//Iterates through stock and prints out details
public class StockDisplay {

	public static String getStock(ArrayList<Pipe> pstock){
		
		String stockout = "";
		
		ListIterator<Pipe> stockit = pstock.listIterator();
		
		while(stockit.hasNext()){
			
			//Add pipe details to string
			stockout = stockout + stockit.next().toString() + "\n";			
		}		
		
		return stockout;				
	}
	
	public static String getStockSize(ArrayList<Pipe> pstock){
		
		String stocksize = "Pipes in stock: ";
		
		pstock.trimToSize();
		
		stocksize = stocksize + Integer.toString(pstock.size()) + "\n";
		
		return stocksize;	
		
	}	
}
