//Sort class which will contain a range    
// of sorting methods.                     
// Initially contains methods for reading   
// and displaying an array of integers.     
//                                          
// Initial Author: Jason Steggles 20/09/11  
// Extended by: Alexander James Clarke 02.11.11


import java.io.*;
import java.text.*;
import java.util.*;

public class Sort{

//Fields	
	
//Size of array
private int size;

//Number of used elements in array
private int usedSize;

//Array of integers
private int[] A;

//Global variables for counting sort comparisons
public int compIS;
public int compQS;

//Constructor
Sort(int max){
	
    //Initialize global count variables
    compIS = 0;
    compQS = 0;
    
    //Initialize size variables
    usedSize = 0;
    size = max;
    
    //Create Array of Integers
    A = new int[size];
}


//Read a file of integers into an array
public void readIn(String file){
	
	try{
	   
       //Initialise loop variable
       usedSize = 0;
       
       //Set up file for reading
       FileReader reader = new FileReader(file);
       Scanner in = new Scanner(reader);
       
       //Loop round reading in data while array not full
       while(in.hasNextInt() && (usedSize < size)) {
    	   
         A[usedSize] = in.nextInt();
         usedSize++;
       }
       
    }
    catch (IOException e){
    	
       System.out.println("Error processing file " + file);
    }
}

//Display Array
public void display(int line, String header){
	
    //Integer Formatter - three digits
    NumberFormat FI = NumberFormat.getInstance();
    FI.setMinimumIntegerDigits(3);

    //Print header string
    System.out.print("\n"+header);

    //Display array data
    for (int i=0;i<usedSize;i++){
    	
        //Check if new line is needed
        if (i%line == 0){ 
        	
            System.out.println(); 
        }
        
        //Display an array element
        System.out.print(FI.format(A[i])+" ");
    }
}


	//Insertion Sort - takes in two values to 'sort between'
	public void iSort(int start, int end){
		
		//From A[1] to A[N-1] (<)
		
		//+1 to start, don't sort first element
		for(int i = start + 1; i<=end; i++){
			
			//Value to insert
			int key = A[i];
			
			//Position of key in array
			int j = i;
			
			//j > 0 prevents index error
			//key < A[j-1] ensures that key can be place earlier in the array
			while((j>0) && (key < A[j-1])){
				
				//If loop executes a comparison has been made
				compIS++;				
				
				//Move down the array
				A[j] = A[j-1];
				
				//Initialize j to this new position
				j = j - 1;				
			}
			
			//If key > A[j-1] it belongs before it at A[j]
			A[j] = key;
			
			//Even if while loop hasn't executed - a comparison has still been made
			compIS++;						
		}		
	}


	//Takes in int L and R (0 and A.length - 1)
	public void qSort(int L, int R){
		
		//Make sure more than one element is present in the array
		if(R > L){			
			
			//Partition array
			int p = partition(L,R);
			//Sort Left of partition A[0] to A[p-1]
			qSort(L, p-1);
			//Sort Right of partition A[p+1] to A[n]
			qSort(p+1, R);						
		}		
	}


	public int partition(int l, int r) {
		
		//Select rightmost element as pivot
		int pivot = A[r];
		int pl = l;
		int pr = r;
		
		//Loop until pointers cross
		while(pl < pr){		
			
			//While pl is less than pivot - increment pl
			while(A[pl] < pivot){			
				
				//Increment comparisons - comparing Array values
				compQS++;
				
				pl++;							
			}
			
			//While pr is greater than pivot - decrease pr
			//pr must be greater than left to prevent error (A[-1])
			while((A[pr] >= pivot) && (pr > l)){
				
				//Increment comparisons - comparing Array values
				compQS++;
				
				pr--;				
			}
			
						
			if(pl < pr){
				//Swaps the values at pointers
				swap(pl, pr);			
			}						
			
			//Account for the two while loop conditions that were checked and return false
			//A comparison is made, but loop isn't executed - so the increment is missed.
			compQS+=2;
			
		}
		
		swap(pl, r);
		
		return pl;		
	}	
	
	public void swap(int pl, int pr) {		
		
		//Store each element in a temporary variable
		int lVal = A[pl];
		int rVal = A[pr];
		
		//Swap variables
		A[pl] = rVal;
		A[pr] = lVal;		
	}
	
	//Takes in parameter few - when this is < specified value perform Insertion sort.
	public void improvedQSort(int few, int L, int R){
		
		//If Size (0 to nth index, size is N + 1) of array is <= than few
		if((R + 1) - L <= few){
		
			//Perform insertion sort
			//Take in L - R indexes to sort between, rest should already be sorted by quicksort.
			iSort(L, R);
			
			
		}
		//Otherwise call quick sort
		else{
			
			
			//Partition Array
			int p = partition(L, R);
			
			//Sort left partition
			improvedQSort(few, L, p - 1);
			
			//Sort right partition
			improvedQSort(few, p + 1, R);
			
				
		}		
	}
					
	//Accessor	
	public int getSize(){
	
		return this.A.length;
	}	

}//End of Sort Class