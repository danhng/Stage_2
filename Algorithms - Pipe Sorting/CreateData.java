package cw2;

import java.util.ArrayList;
import java.util.Random;

//Creates data for testing and new pipes for order
public class CreateData {

	//Random object
	private static Random rgen = new Random();	
	
	//Add a new pipe to the stock - Initially ran n times before beginning algorithm
	public static void orderPipe(ArrayList<Pipe> pstock){
		
		//Pipe length 100m - 200m		
		//Create a new pipe of random length and colour
		Pipe p1 = new Pipe(rgen.nextInt(100) + 101, rgen.nextInt(3) + 1);
		//rgen will generate from 0 to 100, want 100-200 so add 101 to value
		
		pstock.add(p1);
		
	}
	
	//Create a new customer order
	//Orders will be a pipe that is not added to stock, but matched against
	public static void custOrder(ArrayList<Pipe> orders){		
		
		//Length  1 - 50
		//Colour 1 - 4 (4 means any colour is acceptable)
		Pipe o1 = new Pipe(rgen.nextInt(50) + 1,rgen.nextInt(4)+1);
		
		//Add to arraylist of orders
		orders.add(o1);	
		
	}	
}
