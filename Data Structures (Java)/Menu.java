/*
 * Author: Alexander James Clarke
 * Date: 31.10.11
 * Purpose: Menu class, allows user to interact with program
 * and alter client & event objects/lists.
 */


package coursework.data.structures;

//Util for Scanner
import java.util.*;
//IO for PrintWriter
import java.io.*;

public class Menu {
	
	//Methods
	public static void menuSelect(SortedLinkedList<Client> cList, SortedLinkedList<Event> eList) throws FileNotFoundException{
		
		Scanner s1 = new Scanner(System.in);
				
		while(s1.hasNext()) {
			
			String sInput = s1.next();
			char input = sInput.charAt(0);
			
			switch (input){
		
				case 'f': 
				System.exit(0);
				
				
				case 'e': 
				System.out.println("Printing Events:");
				printList(eList); 
				break;
		
				case 'c': 
				System.out.println("Printing Clients:");
				printList(cList);
				break;
		
				//Client buys tickets
				case 'b':
				ticketUpdate(cList, eList, s1);
				break;
		
				//Client returns/cancels tickets
				case 'r': 
				ticketReturn(cList, eList, s1);
				break;
				
				default:
				System.out.println("Error - Invalid Entry.");
				
			}
			
		}		
		
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <E> void printList(SortedLinkedList list){
		
		Iterator<E> iterator = list.iterator();
		
		while(iterator.hasNext()){
			
			E element = iterator.next();
			System.out.println(element.toString());			
		}		
	}
	
	//Checks if tickets being bought are greater than what is available
	public static boolean ticketCheck(Event event, int tickets){
		
		if(event.getTickets() >= tickets){
			
			return true;
		}
		
		return false;
	}
	
	
	//Client purchases tickets
	public static void ticketUpdate(SortedLinkedList<Client> clients, SortedLinkedList<Event> events, Scanner s1) throws FileNotFoundException{
		
		s1.useDelimiter(System.getProperty("line.separator"));
		
		//Read in event name and no. tickets
		System.out.println("Enter event name.");
		String eName = s1.next();
		
		System.out.println("\nEnter tickets");
		int tickets = s1.nextInt();
		
		//Read in client's name
		System.out.println("\nEnter Client's first name.");
		String fName = s1.next();
		
		System.out.println("\nEnter Client's last name.");
		String lName = s1.next();
		
		//Search list for position of event / clients
		int e1 = getEvent(events, eName);
		int c1 = getClient(clients, fName, lName);
			
		//Check if client exists getClient returns -1 if client cannot be found.
		if(c1 != -1){
						
			//If true - check if event exists, getEvent returns -1 if event does not exist
			if(e1 != -1){
							
				//Check  if there are enough tickets
				if(ticketCheck(events.get(e1), tickets) == true){
								
					//Make sure client doesn't already have 3 registered events
					if(clients.get(c1).setEvent(eName, tickets) == 0){
												
						events.get(e1).setTickets(events.get(e1).getTickets() - tickets);	
					}
					else{
						
						System.out.println("Client is already registered for three events.");
					}
								
				}
				//If there are not enough tickets write to file.
				else{
					
					//String to hold new line 
					String newLine = System.getProperty("line.separator");
					
					//Display to user that client letter is being written
					System.out.println("Insufficient Tickets - Writing Client Letter");
					
					//New PrintWriter to write to output.txt file
					PrintWriter outFile = new PrintWriter("C:\\Users\\Alex\\Desktop\\output.txt");
					
					//Message informing client that their transaction was incomplete, not enough tickets available
					outFile.write("Dear " + fName + " " + lName + newLine + "We are sorry to inform you that your transaction was incomplete." + 
					 newLine + "There are currently " + events.get(e1).getTickets() + " tickets available for your chosen event " + eName);
					
					//Close the open file
					outFile.close();
				}
			}
			
			else{
				
				System.out.println("Invalid Event");				
			}
		}
		else{
			
			System.out.println("Invalid Client");
			
		}
			
		
		
	}
	
	public static void ticketReturn(SortedLinkedList<Client> clients, SortedLinkedList<Event> events, Scanner s1){
		
		//Read in event name and no. tickets
		System.out.println("Enter event name.");
		String eName = s1.next();
		
		System.out.println("\nEnter tickets to be returned");
		int tickets = s1.nextInt();
		
		//Read in client's name
		System.out.println("\nEnter Client's first name.");
		String fName = s1.next();
		
		System.out.println("\nEnter Client's last name.");
		String lName = s1.next();
	
		//Get event/client positions in list
		int e1 = getEvent(events, eName);
		int c1 = getClient(clients, fName, lName);
		
		
		//Check if client exists
		if(c1 != -1){
			
			//If true - check if event exists 
			if(e1 != -1){
							
				//Get event/ticket index in client array
				int arrayPos = clients.get(c1).getPos(eName);
								
				//Need to check if client is trying to return more tickets than s/he has
				if((clients.get(c1).getTickets(arrayPos) - tickets) >= 0){				
				
				//Set tickets/event
					
				//Client tickets = client tickets - tickets
				clients.get(c1).setTickets(arrayPos, clients.get(c1).getTickets(arrayPos) - tickets);
				//Event tickets = current tickets + amount being returned
				events.get(e1).setTickets(events.get(e1).getTickets() + tickets);
				
				}
				else{
					
					//If ticket amount is invalid alert user.
					System.out.println("Invalid amount of tickets - Transaction Cancelled.");
				}
				
				
			}
			else{
				
				//If event is invalid alert user.
				System.out.println("Invalid Event - Transaction Cancelled.");
				
			}			
			
		}
		else{
			
			//If client is invalid alert user.
			System.out.println("Invalid Client - Transaction Cancelled.");			
		}		
	}
		
	public static int getClient(SortedLinkedList<Client> clients, String name, String lName){
		
		//Iterate through clients list, match name and return position in list				
		Iterator<Client> iterator = clients.iterator();
		int position = 0;
		
		while(iterator.hasNext()){
			
			
			Client c1 = iterator.next();
			
			//If names match up
			if(c1.getName().equals(name) && (c1.getLName().equals(lName))){
				
				//Return position
				return position;
			}
			
			//Else increment position and continue looping
			position++;			
		}
			//If there is no match then return -1
			return -1;
	}
	
	//Returns events position in sorted list
	public static int getEvent(SortedLinkedList<Event> events, String name){
		
		Iterator<Event> iterator = events.iterator();
		int position = 0;
		
		
		while(iterator.hasNext()){
			
			Event e1 = iterator.next();
			
			//If names match up
			if(e1.getEName().equals(name)){
				
				//Return position
				return position;
			}
			//Else increment position
			position++;
		}
		//If no match then return -1
		return -1;
	}							
		
}
