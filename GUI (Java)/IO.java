import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Alexander Clarke
 * @version 1.0
 * @since 2011-14-32
 */
public class IO {
	
	//Fields\\

	//String containing location of users history.txt file
	private String historyfile;
	//String containing location of users bookmarks.txt file
	private String bookmarkfile;	
	//Scanner for reading the history.txt file
	private Scanner inHistory;
	//Printwriter for writing updates to the history.txt file
	private PrintWriter outHistory;
	//Scanner for reading the bookmarks.txt file
	private Scanner inBook;
	//Printwriter for writing updates to the bookmarks.txt file
	private PrintWriter outBook;	
	
	
	/**
	 * Constructor
	 * 
	 * @param file1 - String, storing location of users history.txt file 
	 * @param file2 - String, storing location of users bookmarks.txt file
	 * @throws IOException
	 */
	public IO(String file1, String file2) throws IOException{
				
		//Initialize file strings
		historyfile = file1;
		bookmarkfile = file2;
		
		//Scanners and Printwriters for I/O between Bookmark & History text files
		inHistory = new Scanner(new FileReader(historyfile));
		outHistory = new PrintWriter(new FileWriter(historyfile, true));			
		
		inBook = new Scanner(new FileReader(bookmarkfile));
		outBook = new PrintWriter(new FileWriter(bookmarkfile, true));				
		
	}
	
	/**
	 * Iterates through UserHistory.txt and Bookmarks.txt
	 * and stores each entry in a string
	 * this string is then displayed in a dialog box, showing
	 * the user their bookmarks and history
	 * 
	 * @throws FileNotFoundException
	 */
	public String bookHistory() throws FileNotFoundException{
		
	
	//Reset the scanners, so they are at the start of the file
	inHistory = new Scanner(new FileReader(historyfile));
	inBook = new Scanner(new FileReader(bookmarkfile));
	
	//String to store bookmarks and history
	String info = "Bookmarks:\n";
	
	//Bookmarks iterated through first
	while(inBook.hasNext()){
		
		//Add each line to string
		info = info + inBook.next() + "\n";				
	}		
	
	//Then History			
	info = info + "\nHistory:\n";
	while(inHistory.hasNext()){		
		
		//Add each line to string
		info = info + inHistory.next() +"\n";	
	}
	
	//Close both scanners after using them
	inHistory.close();
	inBook.close();
	
	//Return this string
	return info;				
}	
	
	/**
	 * Writes the page address to UserHistory.txt	 * 
	 * @param page - string to be written to text file
	 * @throws IOException
	 */
	public void writeHistory(String page) throws IOException{
		
		//Reset writer after closing
		outHistory = new PrintWriter(new FileWriter(historyfile, true));
		//Write to text file
		outHistory.println(page);
		//Close writer after use
		outHistory.close();
	}
	
	/**
	 * Writes the chosen page address to Bookmarks.txt
	 * @param page - string to be written to text file
	 * @throws IOException
	 */
	public void writeBookmark(String page) throws IOException{
		
		//Reset writer after closing
		outBook = new PrintWriter(new FileWriter(bookmarkfile, true));
		//Write to textfile
		outBook.println(page);
		//Close writer after use
		outBook.close();	
	}
}