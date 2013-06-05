/*
 * Author: Alexander James Clarke
 * Date: 31.10.11
 * Purpose: Main class, main method is called when the
 * program is being executed.
 */


package coursework.data.structures;

import java.io.FileNotFoundException;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//Create two empty lists
		
		//Holds event objects
		SortedLinkedList<Event> Events = new SortedLinkedList<Event>();	
		//Hold client objects
		SortedLinkedList<Client> Clients = new SortedLinkedList<Client>();
		
		//Populate the lists by reading events/clients from input file - Will throw File not Found exception if file location is incorrect
		Input.read("C:\\Users\\Alex\\Desktop\\input.txt", Events, Clients);

		//Call Menu and pass the newly populated lists as parameters.
		Menu.menuSelect(Clients, Events);
		

	}

}
