package game;

import java.util.List;

import game.Record.Notation;

public class Pawn extends Piece{
	
	private Notation n;

	public Pawn( Board board ){
		super( board );
	}

	@Override
	public List<Integer[]> getMoves( int r, int c ){
		moves.clear();
		addMoves( r, c );
		return moves;
	}
	
	private void blackPassant( int r, int c ){
		if( r != 4 ){ return; }
		n = board.lastMove();
		if( n.movingPiece == 'P' && n.torow == 4 && n.fromrow == 6 ){
			if( c - 1 == n.tocol ){ moves.add( board.coords[ r + 1 ][ c - 1 ] ); }
			else if( c + 1 == n.tocol ){ moves.add( board.coords[ r + 1 ][ c + 1 ] ); }
		}
	}
	
	private void whitePassant( int r, int c ){
		if( r != 3 ){ return; }
		n = board.lastMove();
		if( n.movingPiece == 'p' && n.torow == 3 && n.fromrow == 1 ){
			if( c - 1 == n.tocol ){ moves.add( board.coords[ r - 1 ][ c - 1 ] ); }
			else if( c + 1 == n.tocol ){ moves.add( board.coords[ r - 1 ][ c + 1 ] );  }
		}
	}

	private void addMoves( int r, int c ){
		if( Character.isLowerCase( board.getGrid()[ r ][ c ] ) ){
			blackMoves( r, c );
			blackPassant( r, c );
			
		}
		else{
			whiteMoves( r, c );
			whitePassant( r, c );
		}
	}

	private void whiteMoves( int r, int c ){
		if( board.getGrid()[ r - 1 ][ c ] == ' ' ){
			moves.add( board.coords[ r - 1 ][ c ] );
			if( r == 6 && board.getGrid()[ r - 2 ][ c ] == ' ' ){ moves.add( board.coords[ r - 2 ][ c ] ); }
		}
		if( c != 0 && Character.isLowerCase( board.getGrid()[ r - 1 ][ c - 1 ] ) ){
			moves.add( board.coords[ r - 1 ][ c - 1 ] );
		}
		if( c != 7 && Character.isLowerCase( board.getGrid()[ r - 1 ][ c + 1 ] ) ){
			moves.add( board.coords[ r - 1 ][ c + 1 ] );
		}
	}

	private void blackMoves( int r, int c ){
		if( board.getGrid()[ r + 1 ][ c ] == ' ' ){
			moves.add( board.coords[ r + 1 ][ c ] );
			if( r == 1 && board.getGrid()[ r + 2 ][ c ] == ' ' ){ moves.add( board.coords[ r + 2 ][ c ] ); }
		}
		if( c != 0 && Character.isUpperCase( board.getGrid()[ r + 1 ][ c - 1 ] ) ){
			moves.add( board.coords[ r + 1 ][ c - 1 ] );
		}
		if( c != 7 && Character.isUpperCase( board.getGrid()[ r + 1 ][ c + 1 ] ) ){
			moves.add( board.coords[ r + 1 ][ c + 1 ] );
		}
	}

}

















