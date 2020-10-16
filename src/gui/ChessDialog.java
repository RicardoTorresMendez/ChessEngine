package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Node;
import game.Game;

@SuppressWarnings("serial")
public class ChessDialog extends JDialog implements Runnable{
	
	private boolean gameover = false;
	
	private Thread evaluation;

	private final static Dimension DIMENSION = new Dimension( 500, 540 );
    
    /** Button to request computer analysis move. */
    private final JButton findMove = new JButton("Find Best Move");

    /** Button to request a move take-back. */
    private final JButton takeBack = new JButton("Take Back");
    
    /** Message bar to display various messages. */
    private JLabel msgBar = new JLabel();
    
    /** Special panel to display the game board. */
    private BoardPanel boardPanel;
    
    /** Game game. */
    private Game game;
    
    public ChessDialog() {
        super((JFrame) null, "Chess");
        setSize(DIMENSION);
        configureUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        evaluation = new Thread( this );
    }
    
    /** Configure UI. */
    private void configureUI() {
        setLayout(new BorderLayout());
        add(makeControlPanel(), BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        panel.setLayout(new GridLayout(1,1));
        panel.add(makeBoardPanel());
        add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Create a control panel consisting of a play button and
     * a message bar.
     */
    private JPanel makeControlPanel() {
        JPanel content = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setBorder(BorderFactory.createEmptyBorder(5,15,0,0));
        buttons.add( findMove );
        buttons.add( takeBack );
        takeBack.setFocusPainted(false);
        takeBack.addActionListener(this::takeBackButtonClicked);
        findMove.setFocusPainted(false);
        findMove.addActionListener(this::findMoveButtonClicked);
        
        content.add(buttons, BorderLayout.NORTH);
        msgBar.setBorder(BorderFactory.createEmptyBorder(5,20,0,0));
        content.add(msgBar, BorderLayout.SOUTH);
        return content;
    }
    
    /** Create a panel to display the game board. */
    private BoardPanel makeBoardPanel(){
        game = new Game();
        boardPanel = new BoardPanel( game );
        boardPanel.setBoardClickListener(this::slotClicked);     
        return boardPanel;
    }
    
    
    /** request computer analysis event */
    private void findMoveButtonClicked(ActionEvent event){
    	if( gameover ){ return; }
    	if( evaluation.isAlive() ){return;}
    	evaluation = new Thread( this );
    	evaluation.start();
    }
    
    private void takeBackButtonClicked(ActionEvent event){
    	if( gameover ){
 
    	}
    	else{
    		game.engine.stop = true;
    		game.takeback();
    	}
    	repaint();
    }
    
    /** To be called when a slot of the board is clicked. */
    private void slotClicked( int r, int c ){
    	if( gameover ){ return; }
    	game.engine.stop = true;
    	game.move( r, c );
    	char status = game.gameStatus();
    	if( status != 'p' ){ gameover = true; addScore( status ); }
        repaint();
    }
    
    private void addScore( char s ){
    	String msg = "";
    	if( s == 'w' ){ msg = "Checkmate, white is victorious"; }
    	else if( s == 'b' ){ msg = "Checkmate, black is victorious"; }
    	else if( s == 's' ){ msg = "Stalemate, game is a draw"; }
    	else if( s == 'r' ){ msg = "Threefold repetition, game is draw"; }
    	else{ msg = ""; }
    	msgBar.setText( msg );
    }
    
    
    public static void main(String[] args){
        new ChessDialog();
    }

	@Override
	public void run(){
		copy();
		game.engine.pv = null;
		for( game.engine.maxdepth=1; ; game.engine.maxdepth++ ){
			long tStart = System.currentTimeMillis();
			game.engine.evaluate();
			if( game.engine.stop ){ return; }
			game.bestmove.updated = true;
			print( game.engine.pv );
			game.bestmove.fr = game.engine.eval.fr;
			game.bestmove.fc = game.engine.eval.fc;
			game.bestmove.tr = game.engine.eval.tr;
			game.bestmove.tc = game.engine.eval.tc;
			String value = (game.engine.eval.value/100.0) + "";
			if( game.engine.eval.value > 9000 ){
				value = "#" + (10000 - game.engine.eval.value)/2;
			}
			else if( game.engine.eval.value < -9000 ){
				value = "#" + (10000 + game.engine.eval.value)/2;
			}
			long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - tStart;
			double elapsedSeconds = tDelta;
			msgBar.setText( "Value: " + value + "  depth: " + game.engine.maxdepth +
					"                                             " +
					"nps: " + (int) ( game.engine.nodes/(elapsedSeconds) ) + "k" +
					"  nodes: " + String.format("%.1f", game.engine.nodes/1000000.0 ) + "m" +
					"  time: " + (int) (elapsedSeconds/1000) + "s"
			);
			copy();
			repaint();
		}
	}
	
	private void copy(){
		char[][] engine = game.engine.board.getGrid();
		char[][] display = game.board.getGrid();
		for( int i=0; i<display.length; i++ ){
			for( int j=0; j>display[ i ].length; j++ ){
				engine[ i ][ j ] = display[ i ][ j ];
			}
		}
	}
	
	private void print( Node pv ){
		System.out.print( "PV: " );
		while( pv != null ){
			System.out.print( "(" + pv.fr + "," + pv.fc + ") -> " + "(" + pv.tr + "," + pv.tc + ") , " );
			pv = pv.bestchild;
		}
		System.out.println();
	}
	
}
