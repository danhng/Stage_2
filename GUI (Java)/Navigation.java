import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author Alexander Clarke
 * @version 1.0
 * @since 2011-14-32
 */
public class Navigation{

	//Fields\\
	
	//JFrame to hold panel
	private JFrame window;	
	//Display object - methods used by listeners
	private Display display;	
	//JPanel to hold buttons
	private JPanel panel;
	private JButton home;
	private JButton refresh;	
	//Textfield which displays current address
	private JTextField texturl;
	private JButton forwards;
	private JButton backwards;
	private JButton historybookmarks;
	private JButton bookmark;
	
	
	/**
	 * 
	 * @param frame - Jframe which buttons will be added to
	 * @param webpage - Display class, containing methods used by button listeners
	 * @param text - textbox displays current page address
	 */
	public Navigation(JFrame frame, Display webpage, JTextField text){
		
		//Set JFrame, Display and texturl to given params
		window = frame;		
		texturl = text;		
		display = webpage;
		
		//Initialise JPanel
		panel = new JPanel();
		
		//Initialise buttons, give names
		home = new JButton("Home");
		refresh = new JButton("Refresh");		
		forwards = new JButton(">");
		backwards = new JButton("<");
		historybookmarks = new JButton("Show History/Bookmarks");
		bookmark = new JButton("Bookmark This!");
	}
	
	/**
	 * This method sets up the buttons, textbox, listeners 
	 * and adds them to the main JFrame window
	 */
	public void setupNav(){	
						
		//Add listeners
		
		//This listener is for the URL textbox (when the user enters a URL)
		texturl.addKeyListener(new KeyAdapter()	{
						
			@Override
			public void keyReleased(KeyEvent e){
				//When the enter key is released
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					//Call urlGo method
					display.urlGo();
				}
			}
		});
		
		//This listener is for the Home button
		home.addMouseListener(new MouseAdapter(){
			
			//When the button is clicked
			@Override
			public void mouseClicked(MouseEvent e){
				
				//Call the home method from display class
				try {
					display.home();
				//If there's an issue - display dialogue informing user
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(display, "Invalid Homepage!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
		
		//This listener is for the Reload Button
		refresh.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e){
				
				//Call refresh method from display class
				try{
					display.refresh();					
				//Any issues - display dialogue
				} catch (IOException e1){
					JOptionPane.showMessageDialog(display, "Error Reloading!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}		
		});			
		
		//This listener is for the back button
		backwards.addMouseListener(new MouseAdapter(){
			
			//When the button is clicked
			@Override
			public void mouseClicked(MouseEvent e){
				
				//Call the back method from display class
				try {
					display.back();
				//If there's an issue - display dialogue informing user
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(display, "Invalid Operation!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
		
		//This listener is for the forwards button
		forwards.addMouseListener(new MouseAdapter(){
			
			//When the button is clicked
			@Override
			public void mouseClicked(MouseEvent e){
				
				//Call the forwards method from display class
				try {
					display.forwards();
				//If there's an issue - display dialogue informing user
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(display, "Invalid Operation!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});		
		
		//This listener is for the show history/bookmarks button
		historybookmarks.addMouseListener(new MouseAdapter(){
			
			//When the button is clicked
			@Override
			public void mouseClicked(MouseEvent e){
				
				try {
					//Call bookHistory method from display
					display.showBookHistory();
				} catch (FileNotFoundException e1) {
					//Display error dialogue if there's an exception
					JOptionPane.showMessageDialog(display, "Error Displaying Info!", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});		
		
		//This listener is for the show history/bookmarks button
		bookmark.addMouseListener(new MouseAdapter(){
			
			//When the button is clicked
			@Override
			public void mouseClicked(MouseEvent e){
				
				try {
					//Call writeBookmark from display
					display.pickBookmark(display.stringPage());
				} catch (IOException e1) {
					//Display error dialoge if there's an exception
					JOptionPane.showMessageDialog(display, "Error Bookmarking!", "Alert", JOptionPane.ERROR_MESSAGE);
				} 				
			}			
		});				
			
		//Set Text box to display homepage
		texturl.setText(display.stringPage());
				
		//JPanel is using flow layout, so add buttons in order required
		panel.add(home);
		panel.add(texturl);
		panel.add(refresh);		
		panel.add(backwards);
		panel.add(forwards);
		panel.add(historybookmarks);	
		panel.add(bookmark);
		
		//Add panel to main window at NORTH (top of frame)
		window.add(panel, BorderLayout.NORTH);
	}
	
}	
