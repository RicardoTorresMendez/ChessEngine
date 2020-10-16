package engine;

import game.Board;
import game.Record.Notation;

public class Pawn extends Piece{
	
	private Notation n;

	public Pawn( Board board ){
		super( board );
	}

	@Override
	public int getMoves( int row, int col ){
		r = row; c = col;
		added = 0;
		addMoves();
		return added;
	}
	
	private void blackPassant(){
		if( r != 4 ){ return; }
		n = board.lastMove();
		if( n.movingPiece == 'P' && n.torow == 4 && n.fromrow == 6 ){
			if( c - 1 == n.tocol ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c - 1;
				size++;
				added++;
			}
			else if( c + 1 == n.tocol ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c + 1;
				size++;
				added++;
			}
		}
	}
	
	private void whitePassant(){
		if( r != 3 ){ return; }
		n = board.lastMove();
		if( n.movingPiece == 'p' && n.torow == 3 && n.fromrow == 1 ){
			if( c - 1 == n.tocol ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c - 1;
				size++;
				added++;
			}
			else if( c + 1 == n.tocol ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c + 1;
				size++;
				added++;
			}
		}
	}

	private void addMoves(){
		if( Character.isLowerCase( grid[ r ][ c ] ) ){
			blackMoves();
			blackPassant();
			
		}
		else{
			whiteMoves();
			whitePassant();
		}
	}

	private void whiteMoves(){
		if( grid[ r - 1 ][ c ] == ' ' ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c;
			size++;
			added++;
			if( r == 6 && grid[ r - 2 ][ c ] == ' ' ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 2; moves[ size ][ 3 ] = c;
				size++;
				added++;
			}
		}
		if( c != 0 && Character.isLowerCase( grid[ r - 1 ][ c - 1 ] ) ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c - 1;
			size++;
			added++;
		}
		if( c != 7 && Character.isLowerCase( grid[ r - 1 ][ c + 1 ] ) ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c + 1;
			size++;
			added++;
		}
	}

	private void blackMoves(){
		if( grid[ r + 1 ][ c ] == ' ' ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c;
			size++;
			added++;
			if( r == 1 && grid[ r + 2 ][ c ] == ' ' ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 2; moves[ size ][ 3 ] = c;
				size++;
				added++;
			}
		}
		if( c != 0 && Character.isUpperCase( grid[ r + 1 ][ c - 1 ] ) ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c - 1;
			size++;
			added++;
		}
		if( c != 7 && Character.isUpperCase( grid[ r + 1 ][ c + 1 ] ) ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c + 1;
			size++;
			added++;
		}
	}

}

















