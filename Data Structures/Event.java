/*
 * Author: Alexander James Clarke
 * Date: 31.10.11
 * Purpose: Event class, used to hold information about events
 * and alter/return that information for use elsewhere.
 */


package coursework.data.structures;

public class Event implements Comparable<Event> {

	//Fields
	private String name;
	private int tickets;
	
	//Constructors
	public Event(String test, int ptickets){
		
		name = test;
		tickets = ptickets;
	}
	
	//Methods	
		
	@Override
	public int compareTo(Event o) {
		
		// Return -1 if this is less than object
		if(this.name.compareTo(o.name)<0){
					
		return -1;
		}		
		else{
			
		//Return  +1 if this is greater than o
		return 1;
		}
	}
	
	//'Converts' Event fields to a single string, for printing
	@Override
	public String toString(){
		
		String details = "";
		
		details = "\nEvent Name: " + this.name + "\n" + "Tickets Available: " + this.tickets;		
				
		return details;		
	}
	
	
	//Accessors / Mutators
	
	//Return tickets
	public int getTickets(){
		
		return this.tickets;
	}
	
	//return name
	public String getEName(){
		
		return this.name;
	}
	
	//Set tickets
	public void setTickets(int pTickets){
		
		this.tickets = pTickets;
	}
}
