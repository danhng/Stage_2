/*
 * Author: Alexander James Clarke
 * Date: 31.10.11
 * Purpose: Client class, used to hold information about clients
 * and alter/return that information for use elsewhere.
 */

package coursework.data.structures;

public class Client implements Comparable<Client> {

	// Fields

	// First and Last names
	private String name;
	private String lastName;

	// Event names and tickets stored in array e[0] is event 1 and t[0] are the
	// corresponding tickets
	String[] events = new String[3];
	int[] tickets = new int[3];

	// Constructors

	public Client(String s1, String s2) {

		name = s1;
		lastName = s2;

	}

	// Methods

	@Override
	public int compareTo(Client o) {

		// If last names are equal, compare first names
		if (this.lastName.compareTo(o.lastName) == 0) {

			if (this.name.compareTo(o.name) < 0) {

				return -1;
			}
		}

		// Else compare last names
		if (this.lastName.compareTo(o.lastName) < 0) {

			return -1;
		}

		// If no matches are made return +1
		return 1;
	}

	// 'Converts' fields to string, allows for printin
	@Override
	public String toString() {

		String details = "";

		details = "\nFirst Name: " + this.name + "\nLast Name: "
				+ this.lastName;

		for (int i = 0; i < this.events.length; i++) {

			// Assume that if event[i] is empty then ticket[i] is too
			if (this.events[i] != null) {

				details = details + "\nEvent Name " + (i + 1) + ": "
						+ this.events[i] + "\nTickets: " + this.tickets[i];

			}

		}

		return details;
	}

	// Set tickets at a given index
	public void setTickets(int i, int tickets) {

		this.tickets[i] = tickets;

	}

	// Return tickets at given index
	public int getTickets(int i) {

		return this.tickets[i];

	}

	// Adds event and tickets to client
	public int setEvent(String eventName, int pTickets) {

		// Loops through event/ticket arrays, both of same length
		for (int i = 0; i < this.events.length; i++) {

			// If there is no event at index i
			if (this.events[i] == null) {

				// Set to event & ticket parameter
				this.events[i] = eventName;
				this.tickets[i] = pTickets;
				// End loop
				return 0;
			}
			// If the event is already present
			if (this.events[i].equals(eventName)) {

				// Add newly bought tickets to current amount
				this.tickets[i] = this.tickets[i] + pTickets;
				return 0;

			}
			// If index 2 has a value stored then the client has registered for
			// three events (maximum)
			if (this.events[2] != null) {

				// return -1. end loop.
				return -1;

			}
		}
		return -1;
	}

	// Returns the array index of a specified event
	public int getPos(String name) {

		for (int i = 0; i < events.length; i++) {

			if (events[i].equals(name) == true) {

				return i;
			}

		}
		// Returns -1 if not found.
		return -1;
	}

	// Accessors

	// Return first name
	public String getName() {

		return this.name;
	}

	// Return last name
	public String getLName() {

		return this.lastName;
	}
}