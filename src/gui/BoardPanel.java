package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Game;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{

	/** Callback interface to listen to board click events. */
    public interface BoardClickListener {

        /** Called when a slot is clicked. */
        void slotClicked(int r, int c);
    }
    
    /** Background dark color of the board. */
    private final Color boardColor1 = new Color(198, 156, 57);
    
    /** Background bright color of the board. */
    private final Color boardColor2 = new Color(234, 204, 133);
    
    /** Listen to board click events. */
    private BoardClickListener listener;
    
    /** Game to be played */
    private Game game;
    
    /** Create a new board panel. */
    public BoardPanel(Game game) {
        this.game = game;
        addMouseListener(new MouseAdapter() {          
            public void mouseClicked(MouseEvent e) {
            	int row = row( e.getY() );
            	int col = col( e.getX() );
            	if( row >= 8 || col >= 8 ){ return; }
            	listener.slotClicked( row, col );
            }
        });
        
    }
    
    private void drawSuggestion( Graphics g ){
    	if( !game.bestmove.updated ){ return; }
    	drawMark(g, true, game.bestmove.fr, game.bestmove.fc );
    	drawCircleReducedBy(g, true, 2.9, game.bestmove.tr, game.bestmove.tc );
    	game.bestmove.updated = false;
    }
    
    //draws available moves
    private void drawMoves( Graphics g ){
    	Integer[] move;
    	for( int i=0; i<game.moves.size(); i++ ){
    		move = game.moves.get( i );
    		if( game.board.getGrid()[ move[ 0 ] ][ move[ 1 ] ] == ' ' ){
    			drawCircleReducedBy( g, false, 2.9, move[ 0 ], move[ 1 ] );
    		}else{
    			drawMark( g, false, move[ 0 ], move[ 1 ] );
    		}
    	}
    }

    
    //draws special marker for spaces with pieces on them
    private void drawMark( Graphics g, boolean red, int row, int col ){
    	if( red ){ g.setColor( new Color( 183, 29, 29 ) ); }
    	else{ g.setColor( new Color( 181, 182, 183 ) ); }
    	int[] x1 = { ( getWidth()/8 )*col, ( getWidth()/8 )*col + getWidth()/(8*5), ( getWidth()/8 )*col };
    	int[] y1 = { ( getHeight()/8 )*row, ( getHeight()/8 )*row, ( getHeight()/8 )*row + getHeight()/(8*5) };
    	int[] x2 = { ( getWidth()/8 )*(col+1), ( getWidth()/8 )*(col+1) - getWidth()/(8*5) - 2, ( getWidth()/8 )*(col+1) };
    	int[] y2 = { ( getHeight()/8 )*(row+1), ( getHeight()/8 )*(row+1), ( getHeight()/8 )*(row+1) - getHeight()/(8*5) - 2 };
    	int[] x3 = { ( getWidth()/8 )*col, ( getWidth()/8 )*col + getWidth()/(8*5), ( getWidth()/8 )*col };
    	int[] y3 = { ( getHeight()/8 )*(row+1), ( getHeight()/8 )*(row+1), ( getHeight()/8 )*(row+1) - getHeight()/(8*5) - 2 };
    	int[] x4 = { ( getWidth()/8 )*(col+1), ( getWidth()/8 )*(col+1) - getWidth()/(8*5) - 2, ( getWidth()/8 )*(col+1) };
    	int[] y4 = { ( getHeight()/8 )*row, ( getHeight()/8 )*row, ( getHeight()/8 )*row + getHeight()/(8*5) };
    	//each is for one corner
    	g.fillPolygon( x1, y1, 3 );
    	g.fillPolygon( x2, y2, 3 );
    	g.fillPolygon( x3, y3, 3 );
    	g.fillPolygon( x4, y4, 3 );
    }
    
    /** Draws Grid */
    private void drawGrid( Graphics g ){
		for( int row=0; row<8; row++ ){
			for( int col = 0; col<8; col++ ){
				if( ( col%2 == 0 ^ row%2 == 0 ) ){ g.setColor( boardColor1 ); }
				else{ g.setColor( boardColor2 ); }
				if( game.ps != '#' && game.row == row && game.col == col ){
					g.setColor( g.getColor().darker() );
				}
				drawRectangle( g, row, col );
				drawPictureReducedBy( g, 1, row, col );
			}
		}
	}
    
    /** Draws rectangle */
    private void drawRectangle( Graphics g, int row, int col ){
		g.fillRect( 
				0 + ( getWidth()/8 )*col,
				0 + ( getHeight()/8 )*row,
				getWidth()/8 - (0*2),
				getHeight()/8 - (0*2)
		);
	}
    
    //draws circle reduced by space
    private void drawCircleReducedBy( Graphics g, boolean red, double factor, int row, int col ){
    	if( red ){ g.setColor( new Color( 183, 29, 29 ) ); }
    	else{ g.setColor( new Color( 181, 182, 183 ) ); }
    	int widthSpace = (int) ( getWidth()/(8*factor) );
    	int heightSpace = (int) ( getHeight()/(8*factor) );
    	g.fillOval( 
				widthSpace + (getWidth() )/8*col,
				heightSpace + (getHeight() )/8*row,
				getWidth()/8 - (widthSpace*2),
				getHeight()/8 - (heightSpace*2)
		);
    }
    
    /** Draws Piece */
    private void drawPictureReducedBy( Graphics g, int space, int row, int col ){
		String dir = getImageAt( row, col );
		if( dir == null ){ return; }
		BufferedImage img = null;
		try{
			img = ImageIO.read( new File( dir ) );
		}catch( Exception e ){  }
		g.drawImage(
				img,
				space + (getWidth() )/8*col,
				space + (getHeight() )/8*row,
				getWidth()/8 - (space*2),
				getHeight()/8 - (space*2),
				this
		);
	}
    
    private String getImageAt( int r, int c ){
    	char[][] grid = game.board.getGrid();
    	//char[][] grid = game.engine.board.getGrid();
    	if( grid[ r ][ c ] == 'p' ){ return "BlackPawn.png"; }
    	else if( grid[ r ][ c ] == 'P' ){ return "WhitePawn.png"; }
    	else if( grid[ r ][ c ] == 'r' ){ return "BlackRook.png"; }
    	else if( grid[ r ][ c ] == 'R' ){ return "WhiteRook.png"; }
    	else if( grid[ r ][ c ] == 'n' ){ return "BlackKnight.png"; }
    	else if( grid[ r ][ c ] == 'N' ){ return "WhiteKnight.png"; }
    	else if( grid[ r ][ c ] == 'b' ){ return "BlackBishop.png"; }
    	else if( grid[ r ][ c ] == 'B' ){ return "WhiteBishop.png"; }
    	else if( grid[ r ][ c ] == 'q' ){ return "BlackQueen.png"; }
    	else if( grid[ r ][ c ] == 'Q' ){ return "WhiteQueen.png"; }
    	else if( grid[ r ][ c ] == 'k' ){ return "BlackKing.png"; }
    	else if( grid[ r ][ c ] == 'K' ){ return "WhiteKing.png"; }
    	return null;
    }
    
    /** Register the given board click listener. */
    public void setBoardClickListener(BoardClickListener listener) {
        this.listener = listener;
    }
    
    /** Given( clicked ) a screen pixels x and y, return corresponding row and col for board */
	private int row( int row ) {
		return row/( getHeight()/8 );
	}
	
	private int col( int col ) {
		return col/( getWidth()/8 );
	}
	
    /** Overridden here to draw the board along with placed Pieces. */
    @Override
    public void paint(Graphics g) {
        super.paint(g);// clear the background
        drawGrid( g );
        drawMoves( g );
        drawSuggestion( g );
    }
	
}
