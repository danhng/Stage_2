package cw2;

public class Pipe {
	
	//Fields
	private int length;
	//1 for Red; 2 for Green; 3 for Yellow
	private int colour;
		
	//Constructors
	public Pipe(int plength, int pcolour){
		
		length = plength;
		
		colour = pcolour;
	}
	
	//Accessors and Mutators
	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getColour() {
		return colour;
	}


	public void setColour(int colour) {
		this.colour = colour;
	}	
	
	//Put pipe details into a string
	public String toString(){
		
		String pipeinf = "Length: " + getLength() + " Colour: "; 
		
		
		
		if (this.colour == 1){
			
			pipeinf = pipeinf + "Red";
			
		}
		else if (this.colour == 2){
			
			pipeinf = pipeinf + "Green";
			
		}
		else if (this.colour == 3){
			
			pipeinf = pipeinf + "Yellow";
			
		}
		else{
			
			pipeinf = pipeinf +"Any Colour";
			
		}						
		return pipeinf;
		
	}	
}
