//Import required libraries
import java.io.IOException;
import java.awt.*;
import javax.swing.*;


/**
 * @author Alexander Clarke
 * @version 1.0
 * @since 2011-14-32
 */
public class Main{

	
	/**
	 * Main method, firstly initialises a JFrame to hold the components
	 * then creates instances of display and navigation objects and finally displays
	 * the browser
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		
		//AWT Toolkit - Used for retrieving users resolution in order to scale frame size
		Toolkit TK = Toolkit.getDefaultToolkit();		
		//Retrieves dimension of users screen
		Dimension dim = TK.getScreenSize();
		
		//History and Bookmark file locations
		String history = "UserHistory.txt";
		String bookmark = "Bookmarks.txt";
		
		//Set Homepage
		String home = "http://www.google.com";
							
		//Create new JFrame to hold components
		JFrame window = new JFrame("CSC2011 - Browser");
		
		//Create JTextField - Shared by both classes
		JTextField jtext = new JTextField(30);
		
		//Set frame to visible
		window.setVisible(true);		
		//Close program on closing window
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		
		//Set frame size		
		dim.width = (int) (dim.width * 0.67);
		window.setPreferredSize(dim);			
		
		//Setup IO - Reads/Writes to History & Bookmark text files
		IO fileIO = new IO(history, bookmark);
		
		//Create new Display (JEditorPane)
		Display pageView = new Display(window, home, jtext, fileIO);
		
		//Now set the Homepage
		pageView.setPage(home);		
		
		//Create new Navigation Bar (Buttons/URL Entry/) (JPanel)
		Navigation nav = new Navigation(window, pageView, jtext);
				
		//Setup display (sets size and adds listener) and add to main JFrame
		pageView.setupDisplay(dim);	
		
		//Setup Navigation bar - adds buttons (and their appropriate listeners) to panel and panel to main Jframe
		nav.setupNav();
				
		//Lastly - pack main window
		window.pack();						
	}	
}
