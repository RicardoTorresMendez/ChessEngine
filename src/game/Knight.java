package game;

import java.util.List;

public class Knight extends Piece{
	
	private boolean white;
	private char move;

	public Knight( Board board ){
		super( board );
	}
	
	@Override
	public List<Integer[]> getMoves( int r, int c ){
		white = Character.isUpperCase( board.getGrid()[ r ][ c ] );
		moves.clear();
		addMoves( r, c );
		return moves;
	}
	
	private void addMoves( int r, int c ){
		if( r-2 >= 0 && r-2 <= 7 && c-1 >= 0 && c-1 <= 7 ){
			move = board.getGrid()[ r-2 ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-2 ][ c-1 ] ); }
		}
		if( r-2 >= 0 && r-2 <= 7 && c+1 >= 0 && c+1 <= 7 ){
			move = board.getGrid()[ r-2 ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-2 ][ c+1 ] ); }
		}
		if( r-1 >= 0 && r-1 <= 7 && c-2 >= 0 && c-2 <= 7 ){
			move = board.getGrid()[ r-1 ][ c-2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-1 ][ c-2 ] ); }
		}
		if( r-1 >= 0 && r-1 <= 7 && c+2 >= 0 && c+2 <= 7 ){
			move = board.getGrid()[ r-1 ][ c+2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-1 ][ c+2 ] ); }
		}
		if( r+1 >= 0 && r+1 <= 7 && c-2 >= 0 && c-2 <= 7 ){
			move = board.getGrid()[ r+1 ][ c-2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+1 ][ c-2 ] ); }
		}
		if( r+1 >= 0 && r+1 <= 7 && c+2 >= 0 && c+2 <= 7 ){
			move = board.getGrid()[ r+1 ][ c+2 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+1 ][ c+2 ] ); }
		}
		if( r+2 >= 0 && r+2 <= 7 && c-1 >= 0 && c-1 <= 7 ){
			move = board.getGrid()[ r+2 ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+2 ][ c-1 ] ); }
		}
		if( r+2 >= 0 && r+2 <= 7 && c+1 >= 0 && c+1 <= 7 ){
			move = board.getGrid()[ r+2 ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+2 ][ c+1 ] ); }
		}
	}
	
}







