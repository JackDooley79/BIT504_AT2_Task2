import java.awt.*;

public class Board {
	// grid line width constant
	public static final int GRID_WIDTH = 8;
	// grid line half width constant
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;
	
	//2D array of ROWS-by-COLS Cell instances
	Cell [][] cells;
	
	/** Constructor to create the game board */
	public Board() {
		 // Initialize the cells array using ROWS and COLS constants from GameMain class.
        cells = new Cell[GameMain.ROWS][GameMain.COLS]; // Initialize the cells array
		
     // Fill the cells array with Cell instances
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                // Create a new Cell instance at the specified row and column
                cells[row][col] = new Cell(row, col);
            }
        }
    }
	

	 /** Return true if it is a draw (i.e., no more EMPTY cells) */ 
	public boolean isDraw() {
		 
		// Check if there are any empty cells left
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                // If any cell is empty, it's not a draw yet
                if (cells[row][col].content == Player.Empty) {
                    return false; // Not a draw
                }
            }
        }
        // All cells are filled, so it's a draw
        return true; 
    }
		
	
	/** Return true if the current player "thePlayer" has won after making their move  */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
		 // check if player has 3-in-that-row
		if(cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer && cells[playerRow][2].content == thePlayer )
			return true; 
		// Check if the player has 3 in a column
        if (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer && cells[2][playerCol].content == thePlayer) {
            return true;
        }		
		 // 3-in-the-diagonal from top left to bottom right
		if( cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer)
			return true;
		 
		
		//  Check the diagonal from top right to bottom left
		if (playerRow + playerCol == 2 && cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer) {
	            return true;
	        }		
		//if no winner condition, return false and keep playing
		return false;
	}
	
	/**
	 * Draws the grid (rows then columns) using constant sizes, then calls on the
	 * Cells to paint themselves into the grid
	 */
	public void paint(Graphics g) {
	    // Set the color for drawing the grid lines
		g.setColor(Color.gray);
		for (int row = 1; row < GameMain.ROWS; ++row) {
            // Drawing horizontal grid lines
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,                
					GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,                
					GRID_WIDTH, GRID_WIDTH);       
			}
		for (int col = 1; col < GameMain.COLS; ++col) { 
            // Draw vertical grid lines
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,                
					GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,                
					GRID_WIDTH, GRID_WIDTH);
		}
		
		//Draw the cells
		for (int row = 0; row < GameMain.ROWS; ++row) {          
			for (int col = 0; col < GameMain.COLS; ++col) { 
                // Ask each cell from the GameMain's associated board  to paint itself on the grid
				cells[row][col].paint(g);
			}
		}
	}
	

}

