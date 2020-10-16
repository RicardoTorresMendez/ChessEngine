package game;

import java.util.List;

public class King extends Piece{

	private boolean white;
	private char move;
	private char[][] grid;
	private int i;

	public King( Board board ){
		super( board );
	}

	@Override
	public List<Integer[]> getMoves( int r, int c ){
		grid = board.getGrid();
		white = Character.isUpperCase( grid[ r ][ c ] );
		moves.clear();
		addMoves( r, c );
		addCastles( r, c );
		return moves;
	}

	private void addCastles( int r, int c ){
		if( white ){ whiteCastle( r, c ); }
		else{ blackCastle( r, c ); }
	}

	private void whiteCastle( int r, int c ){
		if( r != 7 || c != 4 || grid[ 5 ][ 4 ] == 'n' || grid[ 6 ][ 4 ] == 'p' ){ return; }
		if( !e1() ){ return; }
		if( grid[ 7 ][ 3 ] == ' ' && grid[ 7 ][ 2 ] == ' ' && grid[ 7 ][ 1 ] == ' ' ){
			if( board.record.canCastle( true, true ) ){
				if( bottLeft() ){
					moves.add( board.coords[ r ][ c-2 ] );
				}
			}
		}
		if( grid[ 7 ][ 5 ] == ' ' && grid[ 7 ][ 6 ] == ' ' ){
			if( board.record.canCastle( true, false ) ){
				if( bottRight() ){
					moves.add( board.coords[ r ][ c+2 ] );
				}
			}
		}
	}

	private void blackCastle( int r, int c ){
		if( r != 0 || c != 4 || grid[ 2 ][ 4 ] == 'N' || grid[ 1 ][ 4 ] == 'P' ){ return; }
		if( !e8() ){ return; }
		if( grid[ 0 ][ 3 ] == ' ' && grid[ 0 ][ 2 ] == ' ' && grid[ 0 ][ 1 ] == ' ' ){
			if( board.record.canCastle( false, true ) ){
				if( topLeft() ){
					moves.add( board.coords[ r ][ c-2 ] );
				}
			}
		}
		if( grid[ 0 ][ 5 ] == ' ' && grid[ 0 ][ 6 ] == ' ' ){
			if( board.record.canCastle( false, false ) ){
				if( topRight() ){
					moves.add( board.coords[ r ][ c+2 ] );
				}
			}
		}
	}

	private boolean topLeft(){
		if( grid[ 1 ][ 2 ] == 'P' || grid[ 1 ][ 2 ] == 'K' ){ return false; }
		return d8();
	}

	private boolean topRight(){
		if( grid[ 1 ][ 6 ] == 'P' || grid[ 1 ][ 6 ] == 'K' ){ return false; }
		return f8();
	}

	private boolean bottLeft(){
		if( grid[ 6 ][ 2 ] == 'p' || grid[ 6 ][ 2 ] == 'k' ){ return false; }
		return d1();
	}

	private boolean bottRight(){
		if( grid[ 6 ][ 6 ] == 'p' || grid[ 6 ][ 6 ] == 'k' ){ return false; }
		return f1();
	}

	private void addMoves( int r, int c ){
		if( r != 0 ){
			move = board.getGrid()[ r-1 ][ c ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-1 ][ c ] ); }
		}
		if( r != 7 ){
			move = board.getGrid()[ r+1 ][ c ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+1 ][ c ] ); }
		}
		if( c != 0 ){
			move = board.getGrid()[ r ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r ][ c-1 ] ); }
		}
		if( c != 7 ){
			move = board.getGrid()[ r ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r ][ c+1 ] ); }
		}
		if( r != 0 && c != 0 ){
			move = board.getGrid()[ r-1 ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-1 ][ c-1 ] ); }
		}
		if( r != 7 && c != 7 ){
			move = board.getGrid()[ r+1 ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+1 ][ c+1 ] ); }
		}
		if( c != 0 && r != 7 ){
			move = board.getGrid()[ r+1 ][ c-1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r+1 ][ c-1 ] ); }
		}
		if( c != 7 && r != 0 ){
			move = board.getGrid()[ r-1 ][ c+1 ];
			if( move == ' ' || white != Character.isUpperCase( move ) ){ moves.add( board.coords[ r-1 ][ c+1 ] ); }
		}
	}
	
	private boolean d1(){
		if( grid[ 6 ][ 1 ] == 'n' || grid[ 5 ][ 2 ] == 'n' || grid[ 6 ][ 5 ] == 'n' ){ return false; }
		
		for( i=0; i<3; i++ ){
			if( grid[ 6 - i ][ 2 - i ] != ' ' ){
				if( grid[ 6 - i ][ 2 - i ] == 'q' || grid[ 6 - i ][ 2 - i ] == 'b' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<4; i++ ){
			if( grid[ 6 - i ][ 4 + i ] != ' ' ){
				if( grid[ 6 - i ][ 4 + i ] == 'q' || grid[ 6 - i ][ 4 + i ] == 'b' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<7; i++ ){
			if( grid[ 6 - i ][ 3 ] != ' ' ){
				if( grid[ 6 - i ][ 3 ] == 'q' || grid[ 6 - i ][ 3 ] == 'r' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		return true;
	}
	
	private boolean f1(){
		if( grid[ 6 ][ 7 ] == 'n' || grid[ 5 ][ 6 ] == 'n' || grid[ 6 ][ 3 ] == 'n' ){ return false; }
		
		for( i=0; i<5; i++ ){
			if( grid[ 6 - i ][ 4 - i ] != ' ' ){
				if( grid[ 6 - i ][ 4 - i ] == 'q' || grid[ 6 - i ][ 4 - i ] == 'b' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<2; i++ ){
			if( grid[ 6 - i ][ 6 + i ] != ' ' ){
				if( grid[ 6 - i ][ 6 + i ] == 'q' || grid[ 6 - i ][ 6 + i ] == 'b' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<7; i++ ){
			if( grid[ 6 - i ][ 5 ] != ' ' ){
				if( grid[ 6 - i ][ 5 ] == 'q' || grid[ 6 - i ][ 5 ] == 'r' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		return true;
	}
	
	private boolean d8(){
		if( grid[ 1 ][ 1 ] == 'N' || grid[ 2 ][ 2 ] == 'N' || grid[ 1 ][ 5 ] == 'N' ){ return false; }
		
		for( i=0; i<3; i++ ){
			if( grid[ i + 1 ][ 2 - i ] != ' ' ){
				if( grid[ i + 1 ][ 2 - i ] == 'Q' || grid[ i + 1 ][ 2 - i ] == 'B' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<4; i++ ){
			if( grid[ i + 1 ][ 4 + i ] != ' ' ){
				if( grid[ i + 1 ][ 4 + i ] == 'Q' || grid[ i + 1 ][ 4 + i ] == 'B' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<7; i++ ){
			if( grid[ i + 1 ][ 3 ] != ' ' ){
				if( grid[ i + 1 ][ 3 ] == 'Q' || grid[ i + 1 ][ 3 ] == 'R' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		return true;
	}
	
	private boolean f8(){
		if( grid[ 1 ][ 7 ] == 'N' || grid[ 2 ][ 6 ] == 'N' || grid[ 1 ][ 3 ] == 'N' ){ return false; }
		
		for( i=0; i<5; i++ ){
			if( grid[ i + 1 ][ 4 - i ] != ' ' ){
				if( grid[ i + 1 ][ 4 - i ] == 'Q' || grid[ i + 1 ][ 4 - i ] == 'B' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<2; i++ ){
			if( grid[ i + 1 ][ 6 + i ] != ' ' ){
				if( grid[ i + 1 ][ 6 + i ] == 'Q' || grid[ i + 1 ][ 6 + i ] == 'B' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<7; i++ ){
			if( grid[ i + 1 ][ 5 ] != ' ' ){
				if( grid[ i + 1 ][ 5 ] == 'Q' || grid[ i + 1 ][ 5 ] == 'R' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		return true;
	}
	
	private boolean e1(){
		if( grid[ 6 ][ 2 ] == 'n' || grid[ 5 ][ 3 ] == 'n' || grid[ 5 ][ 5 ] == 'n' || grid[ 6 ][ 6 ] == 'n' ){ return false; }
		for( i=0; i<4; i++ ){
			if( grid[ 6 - i ][ 3 - i ] != ' ' ){
				if( grid[ 6 - i ][ 3 - i ] == 'q' || grid[ 6 - i ][ 3 - i ] == 'b' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<3; i++ ){
			if( grid[ 6 - i ][ 5 + i ] != ' ' ){
				if( grid[ 6 - i ][ 5 + i ] == 'q' || grid[ 6 - i ][ 5 + i ] == 'b' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<7; i++ ){
			if( grid[ 6 - i ][ 4 ] != ' ' ){
				if( grid[ 6 - i ][ 4 ] == 'q' || grid[ 6 - i ][ 4 ] == 'r' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		return true;
	}
	
	private boolean e8(){
		if( grid[ 1 ][ 7 ] == 'N' || grid[ 2 ][ 3 ] == 'N' || grid[ 2 ][ 5 ] == 'N' || grid[ 1 ][ 6 ] == 'N' ){ return false; }
		for( i=0; i<4; i++ ){
			if( grid[ 1 + i ][ 3 - i ] != ' ' ){
				if( grid[ 1 + i ][ 3 - i ] == 'Q' || grid[ 1 + i ][ 3 - i ] == 'B' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<3; i++ ){
			if( grid[ 1 + i ][ 5 + i ] != ' ' ){
				if( grid[ 1 + i ][ 5 + i ] == 'Q' || grid[ 1 + i ][ 5 + i ] == 'B' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		for( i=0; i<7; i++ ){
			if( grid[ 1 + i ][ 4 ] != ' ' ){
				if( grid[ 1 + i ][ 4 ] == 'Q' || grid[ 1 + i ][ 4 ] == 'R' ){
					return false;
				}
				else{
					break;
				}
			}
		}
		
		return true;
	}

}















