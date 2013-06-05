
//Simple test class for Sort class       
//Author: Alexander James Clarke 2.11.11          


import java.io.*;
import java.text.*;

@SuppressWarnings("unused")
public class TestSort
{
      public static void main(String[] args) 
      {
          Sort sortTest = new Sort(100);
          
                   
          //Read in test data into array
          sortTest.readIn("test4.txt");
          
          //Perform Insertion Sort
          System.out.println("\n\nPerforming Insertion Sort.");
          sortTest.iSort(0, sortTest.getSize() - 1);
          
          //Print Insertion Sort comparisons.
         System.out.println("\n\nInsertion sort comparison counter: " + sortTest.compIS);
          
          //Display array
          sortTest.display(10,"\nArray of Integers");
          
          //Read in test data again, otherwise already sorted array will be sorted.
          sortTest.readIn("test4.txt");
          
          //Perform Quick Sort
          System.out.println("\n\nPerforming Quick Sort");
          sortTest.qSort(0, sortTest.getSize() - 1);
          
          //Print Quick Sort comparisons
        System.out.println("\n\nQuick sort comparison counter: " + sortTest.compQS);
          
          //Display array
         sortTest.display(10, "\nArray of Integers");
          
          //Read in test data once again, otherwise already sorted array will be sorted.
          sortTest.readIn("test3.txt");
          
          //Reset comparisons - reusing Quick Sort and Insertion Sort.
          sortTest.compIS = 0;
          sortTest.compQS = 0;
          
          //Perform Quick Sort
          System.out.println("\n\nPerforming Improved Quick Sort");
          sortTest.improvedQSort(13, 0, sortTest.getSize()- 1);
          
          //Print Imp. Quick Sort comparisons
          System.out.println("\n\nImproved Quick sort comparison counter: " + (sortTest.compQS + sortTest.compIS));
          
          //Display array
          sortTest.display(10, "Array of Integers");
                    
         
      }
    
} /** End of Test class **/