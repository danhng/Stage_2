/*
 * Author: Alexander James Clarke
 * Date: 31.10.11
 * Purpose: Input class, takes in two SortedLinkedLists and a text file
 * and then populate the lists with the information from the file
 */


package coursework.data.structures;

import java.util.*;
import java.io.*;

public class Input {
	
	/**
	 * Description: Takes in two SortedLinkedLists and populates them using a given text file
	 * @param file - text file, contents to be read and added into appropriate lists as objects
	 * @param Eventlist - (empty) list of events
	 * @param Clientlist - (empty) list of clients
	 * @throws FileNotFoundException
	 */

	public static void read(String file, SortedLinkedList<Event> Eventlist, SortedLinkedList<Client> Clientlist) throws FileNotFoundException{
		
		 //Event name and Tickets
		 String eName = "";
		 int eTickets;
		 
		 //Create FileReader / Scanner	
		 FileReader reader = new FileReader(file);
	     Scanner in = new Scanner(reader);
	       
	       //Read in first value
	       int eventNo = in.nextInt();	       
	       
	       //Array of size 1 to hold current event
	       Event[] eventArray = new Event[1];
	       
	       //Apply delimiter - reads each line, prevent events with a space in name being cut off.
	       in.useDelimiter(System.getProperty("line.separator"));
	       
	       //Events
	       for(int i = 0; i < eventNo*2; i++){    	   
	    	   
	    	   //File is read in the order name / tickets, name on even values of i
	    	   
	    	   if(i%2 == 0){
	    		   
	    		   //Trim trailing whitespace " "
	    		   eName = in.next().trim();	    	 		   
	    	   }
	    	   
	    	   else{
	    		   
	    		   //Tickets are read in after name - so create new event here
	    		   eTickets = in.nextInt();
	    		   	    		   
	    		   //Hold newly created event in array then add to the list.
	    		   eventArray[0] = new Event(eName, eTickets);
	    		   Eventlist.add(eventArray[0]);	    		   
	    	   }	     
	    	   
	       }		
	       
	       //Clients
	       
	       //Store the first integer before client strings
	       int clientNo = in.nextInt();
	       
	       //Array of size 1 to hold current client
	       Client[] clientArray = new Client[1];	                  
	       
	       //Client first and last name
	       String fName = "";
	       String lName = "";
	       
	       //Alter delimiter to split at spaces (whitespace \\s + modifier - matches one or more occurence)
	       in.useDelimiter("\\s+");
	       
	       for(int i = 0; i < clientNo*2; i++){
	    	   
	    	   
	    	   //File is read first name / last name, when i is even, first name is read
	    	   if(i%2 == 0){
	    		   
	    		   fName = in.next();
	    		   
	    	   }
	    	   else{
	    		   
	    		   lName = in.next();	    		   
	    		   
	    		   //Now Create new client, last name read in.	    		   
	    		   //Hold in array and then pass to list
	    		   clientArray[0] = new Client(fName, lName);
	    		   Clientlist.add(clientArray[0]);
	    	   } 
	    	   
	       }		
	}
	
}
