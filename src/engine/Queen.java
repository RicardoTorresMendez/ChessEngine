package engine;

import game.Board;

public class Queen extends Piece{
	
	private boolean white;
	
	public Queen( Board board ){
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
		line( 1, 0 );
		line( 0, 1 );
		line( 1, 1 );
		line( -1, 1 );
	}
	
	private void line( int dy, int dx ){
		consecutive( dy, dx );
		consecutive( -dy, -dx );
	}
	
	private void consecutive( int dy, int dx ){
		int i = r + dy ; int j = c + dx;
		while( !done( i, j ) ){
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = i; moves[ size ][ 3 ] = j;
			size++;
			added++;
			i = i + dy; j = j + dx;
		}
	}
	
	private boolean done( int i, int j ){
		if( i < 0 || j < 0 ){ return true; }
		if( i >= grid.length ){ return true; }
		if( j >= grid[0].length ){ return true; }
		if( grid[ i ][ j ] != ' ' ){
			if( white == Character.isUpperCase( grid[ i ][ j ] ) ){ return true; }
			moves[ size ][ 0 ] = r; moves[ size ][ 1 ] = c; moves[ size ][ 2 ] = i; moves[ size ][ 3 ] = j;
			size++;
			added++;
			return true; 
		}
		return false;
	}
	
	
}










