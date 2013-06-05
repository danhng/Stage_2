import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


/**
 * @author Alexander Clarke
 * @version 1.0
 * @since 2011-14-32
 */
@SuppressWarnings("serial")
public class Display extends JEditorPane{

	//Fields\\				
	private JEditorPane display;
	private JScrollPane scroll;
	private JFrame window;
	private HyperlinkListener listen;		
	private String homepage;
	private JTextField texturl;
	
	//Used to travel forwards and backwards
	private ArrayList<String> history;
	//Counts current position in the list
	private int historycount;
	
	//Instance of IO Class
	private IO fileIO;	
	
	//Constructor\\	
	
	/**
	 * 
	 * @param frame - JFrame which editor will be placed in
	 * @param home - String containing specified home page
	 * @param jt - text box, displays current pages address
	 * @param file1 - history.txt, users history
	 * @param file2 - bookmarks.txt, users chosen bookmarks
	 * @throws IOException
	 */
	public Display(JFrame frame, String home, JTextField jt, IO file) throws IOException{
		
		//Initialize JFrame, Homepage and texturl to given parameters
		window = frame;
		homepage = home;				
		texturl = jt;		
		
		//Holds current 'position' user is at in history arraylist.
		history = new ArrayList<String>();
		historycount = -1;	
				
		//Initialize editor pane and scroll pane (to display contents of JEditorPane)		
		display = new JEditorPane();		
		scroll = new JScrollPane(display);			
		
		//Initialise IO
		fileIO = file;
	}
	
	//Methods\\	
	
	/**
	 * Sets up the display by implementing hyperlink listener
	 * and adding components to the main Jframe window
	 * 
	 * @param dim Users resolution 
	 */
	public void setupDisplay(Dimension dim){
		
		//Initialize HyperlinkListener - 'links' to JTextField parameter (from navigation bar)
		listen = new HyperlinkListener() {
			
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				
				//If a hyperlink is activated
		        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        			        	
		        	try {
	        		//Display this new page
		            display.setPage(e.getURL());
		            //Change text box to display this new page
		            texturl.setText(e.getURL().toString());
		            
		            //Add this address to our current history arraylist
		            history.add(e.getURL().toString());
		            //Increment the counter by 1
		            historycount++;
		        	} 
		        	//If not then display dialog box
		        	catch (IOException ioe) {		        	  
		        	  JOptionPane.showMessageDialog(display, "Invalid URL!", "Alert", JOptionPane.ERROR_MESSAGE);
		          }
		        }
		      }
		    };
		
				
		//Prevent user from editing page
		display.setEditable(false);
		
		//Set scrollPane size		
		scroll.setSize(dim);		
		
		//Add Hyperlink Listener to the EditorPane
		display.addHyperlinkListener(listen);
		
		//Add Scroll Pane to the main JFrame
		window.add(scroll, BorderLayout.CENTER);
	}

	
	/**
	 * setPage method will call the JEditorPane setPage method which
	 * causes the pane to display the given page
	 * also calls the writeHistory method which writes the address to the
	 * history.txt file
	 * 
	 * @webpage webpage address as string
	 */
	@Override
	public void setPage(String webpage){		
			
		try {
			display.setPage(webpage);
			fileIO.writeHistory(webpage);
		} catch (IOException e) {			
			JOptionPane.showMessageDialog(display, "Invalid URL!", "Alert", JOptionPane.ERROR_MESSAGE);						
		}		
		
		//Sets textbox to this new page
		texturl.setText(webpage);
		
		//Add this new page to history arraylist			
		history.add(webpage);
		//Increment counter
		historycount++;		
	}	
	
	/**
	 * Returns the address of the page currently being displayed
	 * as a string
	 * 
	 * @return String of current pages address
	 */
	public String stringPage(){
		
		String currentPage = display.getPage().toString();
		return currentPage;		
	}
	
	/**
	 * Refreshes current page by calling the JEditorPane setPage method
	 * and passing the current pages URL as a parameter
	 * 
	 * @throws IOException
	 */
	public void refresh() throws IOException{
		
		display.setPage(display.getPage());
		texturl.setText(this.stringPage());
	}
	
	/**
	 * Sets displayed page and text box to a set homepage
	 * adds this homepage to users current history and increments counter
	 * 
	 * @throws IOException
	 */
	public void home() throws IOException{		
				
		display.setPage(homepage);
		texturl.setText(homepage);
		historycount++;
		history.add(homepage);		
	}	
	
	/**
	 * Used to allow the use to travel back to previously
	 * visited pages
	 * 
	 * @throws IOException
	 */
	public void back() throws IOException{
				
		//Check to make sure we can go back first
		if(historycount > 0){
		
		//Decrement counter
		historycount--;
		//Display this previous page
		display.setPage(history.get(historycount));
		//And Set textfield to the newpage
		texturl.setText(history.get(historycount));
		
		}
		//Display error message if user is at start of list
		else{
			
			JOptionPane.showMessageDialog(display, "Cannot go any further back!", "Alert", JOptionPane.ERROR_MESSAGE);			
		}		
	}
	
	/**
	 * Used to allow the user to travel forward between pages
	 * 
	 * @throws IOException
	 */
	public void forwards() throws IOException{
		
		//Check to make sure we can go forwards first
		if(historycount < history.size() - 1){
		
		//Increment counter
		historycount++;
		//Display this previous page
		display.setPage(history.get(historycount));
		//And Set textfield to the newpage
		texturl.setText(history.get(historycount));
		
		}
		//Display error message if user is at end of arraylist [n]
		else{
			
			JOptionPane.showMessageDialog(display, "Cannot go forwards!", "Alert", JOptionPane.ERROR_MESSAGE);			
		}		
	}
	
	/**
	 * Displays string returned from the bookHistory method in fileIO 
	 * in dialog box, allows user to see history / bookmarks
	 * 
	 * @throws FileNotFoundException
	 */
	public void showBookHistory() throws FileNotFoundException{
		
		JOptionPane.showMessageDialog(display, fileIO.bookHistory(),
									 "History and Bookmarks", 
									 JOptionPane.DEFAULT_OPTION);				
	}
	
	/**
	 * Takes a single parameter and calls the writeBookmark method from
	 * fileIO passing this parameter which is a page address to be written
	 * to the users bookmarks
	 * 
	 * @param page - page address to be written by writeBookmark method
	 * @throws IOException
	 */
	public void pickBookmark(String page) throws IOException{
		
		fileIO.writeBookmark(page);		
	}	
	
	/**
	 * This method is called when the user enters a URL and hits enter
	 */
	public void urlGo(){
		
		//Reads in the (entered) value in the text box
		String enteredURL = texturl.getText();
		//Sets JEditorPane to display this URL
		setPage(enteredURL);
		//Note - Any incorrect URL's will result in an error box
		
	}	
}
