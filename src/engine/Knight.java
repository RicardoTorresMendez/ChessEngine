package engine;

import game.Board;

public class Knight extends Piece{
	
	private boolean white;
	
	private char move;

	public Knight( Board board ){
		super( board );
	}
	
	@Override
	public int getMoves( int row, int col ){
		r = row; c = col;
		added = 0;
		white = Character.isUpperCase( grid[ r ][ c ] );
		addMoves();
		return added;
	}
	
	private void addMoves(){
		if( r-2 >= 0 && r-2 <= 7 && c-1 >= 0 && c-1 <= 7 ){
			move = grid[ r-2 ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 2; moves[ size ][ 3 ] = c - 1;
				size++;
				added++;
			}
		}
		if( r-2 >= 0 && r-2 <= 7 && c+1 >= 0 && c+1 <= 7 ){
			move = grid[ r-2 ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 2; moves[ size ][ 3 ] = c + 1;
				size++;
				added++;
			}
		}
		if( r-1 >= 0 && r-1 <= 7 && c-2 >= 0 && c-2 <= 7 ){
			move = grid[ r-1 ][ c-2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c - 2;
				size++;
				added++;
			}
		}
		if( r-1 >= 0 && r-1 <= 7 && c+2 >= 0 && c+2 <= 7 ){
			move = grid[ r-1 ][ c+2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r - 1; moves[ size ][ 3 ] = c + 2;
				size++;
				added++;
			}
		}
		if( r+1 >= 0 && r+1 <= 7 && c-2 >= 0 && c-2 <= 7 ){
			move = grid[ r+1 ][ c-2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c - 2;
				size++;
				added++;
			}
		}
		if( r+1 >= 0 && r+1 <= 7 && c+2 >= 0 && c+2 <= 7 ){
			move = grid[ r+1 ][ c+2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 1; moves[ size ][ 3 ] = c + 2;
				size++;
				added++;
			}
		}
		if( r+2 >= 0 && r+2 <= 7 && c-1 >= 0 && c-1 <= 7 ){
			move = grid[ r+2 ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 2; moves[ size ][ 3 ] = c - 1;
				size++;
				added++;
			}
		}
		if( r+2 >= 0 && r+2 <= 7 && c+1 >= 0 && c+1 <= 7 ){
			move = grid[ r+2 ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){
				moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = r + 2; moves[ size ][ 3 ] = c + 1;
				size++;
				added++;
			}
		}
	}
	
}







