import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
    // Content of this cell (empty, cross, nought)
    Player content;
    // Row and column of this cell
    int row, col;

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row; // Initialize row
        this.col = col; // Initialize col
        clear(); // Call the method that sets the cell content to EMPTY
    }

    /** Paint itself on the graphics canvas, given the Graphics context g */
    public void paint(Graphics g) {
        Graphics2D graphic2D = (Graphics2D) g;
                                                   // Width of the stroke  // End cap style (round) // Join style (round)
        graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
     // Calculate the starting position (top-left corner) of the cell
        int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;        
        int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
     // Check the content of the cell with an if statement  
        if (content == Player.Cross) {
        	// If the content is Cross, set the color to red
            graphic2D.setColor(Color.RED);
         // Calculate the ending position (bottom-right corner) of the diagonal lines
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
         // Draw two diagonal lines that will create the Cross symbol
            graphic2D.drawLine(x1, y1, x2, y2);
            graphic2D.drawLine(x2, y1, x1, y2);
        } else if (content == Player.Nought) {
        	// If the content is Nought, set the color to blue
            graphic2D.setColor(Color.BLUE);
         // Draw a blue oval to form the Nought symbol. This was allot less work to accomplish.
            graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
        }
    }

    /** Set this cell's content to EMPTY */
    public void clear() {
        content = Player.Empty; // Set the value of content to Empty
    }
}
