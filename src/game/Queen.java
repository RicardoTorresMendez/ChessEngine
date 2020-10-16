package game;

import java.util.List;

import util.LineMoves;

public class Queen extends Piece{
	
	private LineMoves lm;
	
	public Queen( Board board ){
		super( board );
		lm = new LineMoves();
	}
	
	@Override
	public List<Integer[]> getMoves( int r, int c ){
		moves.clear();
		moves = lm.Line(board, r, c, true, true);
		return moves;
	}
	
}
