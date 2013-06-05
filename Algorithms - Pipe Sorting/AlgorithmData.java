package cw2;


public class AlgorithmData {
		
	//Fields - values to be displayed after algorithm has run
	
	//New pipes ordered
	private static int ordersmade = 0;

	//Total number of pipes recycled
	private static int pipesrec = 0;

	//Total cuts made - TESTING
	private static int cutsmade = 0;
	
	//Pipe ordered - increment value
	public static void orderInc(){
		
		ordersmade++;	}
	
	//Pipe recycled - increment value
	public static void recInc(){
		
		pipesrec++;
	}
	
	public static void cutInc(){
		
		cutsmade++;
	}
	
	//Return results
	public static String results(){
	
		String output = "Orders Made: " + Integer.toString(ordersmade) + "\n" +
						"Pipes Recycled: " + Integer.toString(pipesrec) + "\n";

		reset();
		return output;		
	}
	
	//Reset variables, called after printing
	public static void reset(){
		
		ordersmade = 0;
		pipesrec = 0;
		cutsmade = 0;
	}
}
