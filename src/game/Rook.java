package game;

import java.util.List;

import util.LineMoves;

public class Rook extends Piece{

	LineMoves lm;
	
	public Rook( Board board ){
		super( board );
		lm =  new LineMoves();
	}
	
	@Override
	public List<Integer[]> getMoves( int r, int c ){
		moves.clear();
		moves = lm.Line(board, r, c, true, false);
		return moves;
	}
	
}
