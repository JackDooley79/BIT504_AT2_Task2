import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class GameMain extends JPanel implements MouseListener{
	//Constants for game 
	// number of ROWS by COLS cell constants 
	public static final int ROWS = 3;     
	public static final int COLS = 3;  
	public static final String TITLE = "Tic Tac Toe";

	//constants for dimensions used for drawing
	//cell width and height
	public static final int CELL_SIZE = 100;
	//drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	//Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;    
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	/*declare game object variables*/
	// the game board called form the Board class.
	private Board board;
	// Enumeration to represent the game states
    private enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }

	
	private GameState currentState; 
	// the current player
	private Player currentPlayer; 
	// for displaying game status message
	private JLabel statusBar;       
	

	/** Constructor to setup the UI and game components on the panel */
	public GameMain() {   
					    
		// Setup the status bar (JLabel) to display status message       
		statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);
        //layout of the panel is in border layout
		setLayout(new BorderLayout());       
		add(statusBar, BorderLayout.SOUTH);
		// account for statusBar height in overall height
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));		        
         // Create game board
        board = new Board();
        // Initialize the game board content
        initGame();
        // Add mouse listener to this panel
        addMouseListener(this);

	}
	
	/** Main method to launch the game */
	public static void main(String[] args) {
		    // Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
				//create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);								
				// new game main panel
				GameMain gameMain = new GameMain();
				// added the new game panel to the frame
                frame.add(gameMain);
                // set close operation to the close window
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);						
				frame.pack();             
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
	         }
		 });
	}
	/** Custom painting codes on this JPanel */
	public void paintComponent(Graphics g) {
		//fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		//ask the game board (Board class) to paint itself
		board.paint(g);
		
	    // Set the status bar message based on the current game state using an If-else statement.
		if (currentState == GameState.Playing) {
			// If the game is currently being played
			statusBar.setForeground(Color.BLACK); 
			// If it's the X player's turn status bar which is the message at the bottom of the game panel will say X's turn. the current player is check and Cross is called from Player class enum
			if (currentPlayer == Player.Cross) { 				
				 statusBar.setText("X's Turn");				
			// If its O player's turn to play, the status bar 	will say O's turn. the current player is check and Naught is called from Player class enumerator
			} else {    				
				statusBar.setText("O's Turn");				
			} 
			// If the game ends in a draw the status bar will appear saying it is a draw shown in black font.
			} else if (currentState == GameState.Draw) {          
				statusBar.setForeground(Color.BLACK);          
				statusBar.setText("It's a Draw! Click to play again.");  
			// If the game ends with X winning, the status bar will show X won! click to play again shown in red.
			} else if (currentState == GameState.Cross_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'X' Won! Click to play again."); 
			// If the game ends with O winning, the status bar will show O won! click to play again shown in blue.
			} else if (currentState == GameState.Nought_won) {          
				statusBar.setForeground(Color.BLUE);          
				statusBar.setText("'O' Won! Click to play again.");       
			}
		}
		
	
	  /** Initialize the game-board contents and the current status of GameState and Player) */
		public void initGame() {
			for (int row = 0; row < ROWS; ++row) {          
				for (int col = 0; col < COLS; ++col) {  
		            // Set all cells to be empty using the Cell class from the Board object
					board.cells[row][col].content = Player.Empty;           
				}
			}
			 currentState = GameState.Playing;
			 currentPlayer = Player.Cross;
			 // Reset the game's state and current player
		}
		
		
		/**After each turn check to see if the current player hasWon by putting their symbol in that position, 
		 * If they have the GameState is set to won for that player
		 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
		 *   
		 */
		public void updateGame(Player thePlayer, int row, int col) {
		    // Check for win after play using the hasWon method from the Board class
			if (board.hasWon(thePlayer, row, col)) {
	            if (thePlayer == Player.Cross) {
	                currentState = GameState.Cross_won;
	            } else {
	                currentState = GameState.Nought_won;
	            }
	        } else if (board.isDraw()) {
	           // If no winner, check for a draw using the isDraw method from the Board class
	            currentState = GameState.Draw;
	        }
			//otherwise no change to current state of playing
		}
		
				
	
		/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
		 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
		 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears*/
	
	public void mouseClicked(MouseEvent e) {  
	    // get the coordinates of where the click event happened            
		int mouseX = e.getX();             
		int mouseY = e.getY();             
		// covert the coordinates to row and column             
		int rowSelected = mouseY / CELL_SIZE;             
		int colSelected = mouseX / CELL_SIZE;               			
		if (currentState == GameState.Playing) {                
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
				// when a player makes a move the row and column is assigned to which player they are (X or O)  
				board.cells[rowSelected][colSelected].content = currentPlayer; 
				// update currentState that will display on the cell an X or an O depending on the player state when repaint() is called.                
				updateGame(currentPlayer, rowSelected, colSelected); 
				// Switch player once the game has updated
				if (currentPlayer == Player.Cross) {
					currentPlayer =  Player.Nought;
				}
				else {
					currentPlayer = Player.Cross;
				}
				repaint(); // Redraw the graphics on the UI showing the X or O on the cell the player has clicked on
			}             
		} else {        
			// game over and restart              
			initGame(); 
			repaint(); // Redraw the graphics on the UI the cells are cleared and the game has restarted
		}   
		
		         
           
	}
		
	
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used
		
	}

}
