/*
 * Author: Alexander James Clarke
 * Date: 31.10.11
 * Purpose: SortedLinkedList class, used to hold clients and events
 * keeps these objects sorted in ascending order A-Z.
 */

package coursework.data.structures;

import java.util.*;

@SuppressWarnings("serial")
public class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E> {



	//Override Add method of LinkedList Class

	@Override
	public boolean add(E e){
		
		//Integer to hold position of iterator
		int position = 0;
				
		//First check if list is empty
		if(this.size() == 0){
			
			//If so, just add element to start of list
			this.addFirst(e);
			return true;
			
		}
		
		//If not - iterate through list and compare e to each element
		else{
			
			//Create an iterator to traverse list
			Iterator<E> iterator = this.iterator();
						
			//Whilst there is another element present
			while(iterator.hasNext()){
						
				//Compare parameter to next element
				if(e.compareTo(iterator.next())<0){
					
					//If e < element then add to position
					this.add(position, e);
					//End call
					return true;
					
				}			
				
				//If element does not fit in this position 'move' to next
				position++;
				
			}		
												
		}									
							
		//If the element has not been added in the loop
		//It must belong at the end of the list
		this.addLast(e);
		
		return true;		
	}
}

