package util;

import java.util.LinkedList;
import java.util.List;
import game.Board;

public class LineMoves{

	/** last move ( given move ) coordinates */
	private int h, w;

	/** List of moves */
	List<Integer[]> moves;

	public LineMoves(){
		moves = new LinkedList<Integer[]>();
	}

	public List<Integer[]> Line( Board board, int y, int x, boolean normal, boolean diagonal ){
		h = y; w = x;
		moves.clear();

		if( normal ){
			line( board, 1, 0 );//vertical
			line( board, 0, 1 );//horizontal
		}
		if( diagonal ){
			line( board, 1, 1 );//doDiagonal
			line( board, -1, 1 );//upDiagonal
		}

		return moves;
	}
	
	public boolean isAttackedByBlack( Board board, int x, int y ){
		h = x; w = y;
		char[][] grid = board.getGrid();
		
		if( h != 0 ){
			if( grid[ h-1 ][ w ] == 'k' ){ return true; }
			if( w != 0 ){ if( grid[ h-1 ][ w-1 ] == 'p' || grid[ h-1 ][ w-1 ] == 'k' ){ return true; } }
			if( w != 7 ){ if( grid[ h-1 ][ w+1 ] == 'p' || grid[ h-1 ][ w+1 ] == 'k' ){ return true; } }
			if( w > 1 ){ if( grid[ h-1 ][ w-2 ] == 'n' ){ return true; }
			if( w < 6 ){ if( grid[ h-1 ][ w+2 ] == 'n' ){ return true; } }
			}
		}
		if( w != 0 ){ if( grid[ h ][ w-1 ] == 'k' ){ return true; } }
		if( w != 7 ){ if( grid[ h ][ w+1 ] == 'k' ){ return true; } }
		if( h > 1 ){
			if( w != 0 ){ if( grid[ h-2 ][ w-1 ] == 'n' ){ return true; } }
			if( w != 7 ){ if( grid[ h-2 ][ w+1 ] == 'n' ){ return true; } }
		}
		if( h < 6 ){
			if( w != 0 ){ if( grid[ h+2 ][ w-1 ] == 'n' ){ return true; } }
			if( w != 7 ){ if( grid[ h+2 ][ w+1 ] == 'n' ){ return true; } }
		}
		if( h !=7 ){
			if( grid[ h+1 ][ w ] == 'k' ){ return true; }
			if( w != 0 ){ if( grid[ h+1 ][ w-1 ] == 'k' ){ return true; } }
			if( w != 7 ){ if( grid[ h+1 ][ w+1 ] == 'k' ){ return true; } }
			if( w > 1 ){ if( grid[ h+1 ][ w-2 ] == 'n' ){ return true; } }
			if( w < 6 ){ if( grid[ h+1 ][ w+2 ] == 'n' ){ return true; } }
		}
		
		if( lineBlack( board, 1, 0, false ) ){ return true; }
		if( lineBlack( board, 0, 1, false ) ){ return true; }
		if( lineBlack( board, 1, 1, true ) ){ return true; }
		if( lineBlack( board, -1, 1, true ) ){ return true; }
		
		return false;
	}
	
	public boolean isAttackedByWhite( Board board, int x, int y ){
		h = x; w = y;
		char[][] grid = board.getGrid();
		if( h != 0 ){
			if( grid[ h-1 ][ w ] == 'K' ){ return true; }
			if( w != 0 ){ if( grid[ h-1 ][ w-1 ] == 'K' ){ return true; } }
			if( w != 7 ){ if( grid[ h-1 ][ w+1 ] == 'K' ){ return true; } }
			if( w > 1 ){ if( grid[ h-1 ][ w-2 ] == 'N' ){ return true; }
			if( w < 6 ){ if( grid[ h-1 ][ w+2 ] == 'N' ){ return true; } }
			}
		}
		if( w != 0 ){ if( grid[ h ][ w-1 ] == 'K' ){ return true; } }
		if( w != 7 ){ if( grid[ h ][ w+1 ] == 'K' ){ return true; } }
		if( h > 1 ){
			if( w != 0 ){ if( grid[ h-2 ][ w-1 ] == 'N' ){ return true; } }
			if( w != 7 ){ if( grid[ h-2 ][ w+1 ] == 'N' ){ return true; } }
		}
		if( h < 6 ){
			if( w != 0 ){ if( grid[ h+2 ][ w-1 ] == 'N' ){ return true; } }
			if( w != 7 ){ if( grid[ h+2 ][ w+1 ] == 'N' ){ return true; } }
		}
		if( h !=7 ){
			if( grid[ h+1 ][ w ] == 'K' ){ return true; }
			if( w != 0 ){ if( grid[ h+1 ][ w-1 ] == 'P' || grid[ h+1 ][ w-1 ] == 'K' ){ return true; } }
			if( w != 7 ){ if( grid[ h+1 ][ w+1 ] == 'P' || grid[ h+1 ][ w+1 ] == 'K' ){ return true; } }
			if( w > 1 ){ if( grid[ h+1 ][ w-2 ] == 'N' ){ return true; } }
			if( w < 6 ){ if( grid[ h+1 ][ w+2 ] == 'N' ){ return true; } }
		}
		
		if( lineWhite( board, 1, 0, false ) ){ return true; }
		if( lineWhite( board, 0, 1, false ) ){ return true; }
		if( lineWhite( board, 1, 1, true ) ){ return true; }
		if( lineWhite( board, -1, 1, true ) ){ return true; }
		
		return false;
	}

	/** checks consecutive in both ways of the line starting from last move */
	private void line( Board board, int dy, int dx ){
		consecutive( board, dy, dx );
		consecutive( board, -dy, -dx );
	}

	//counts consecutive marks
	private void consecutive( Board board, int dy, int dx ){
		int i = h + dy ; int j = w + dx;
		while( !done( board, i, j ) ){
			moves.add( board.coords[ i ][ j ] );
			i = i + dy; j = j + dx;
		}
	}

	/** To determine when done */
	private boolean done( Board board, int i, int j ){
		if( i < 0 || j < 0 ){ return true; }
		if( i >= board.getGrid().length ){ return true; }
		if( j >= board.getGrid()[0].length ){ return true; }
		if( board.getGrid()[ i ][ j ] != ' ' ){
			if( Character.isUpperCase( board.getGrid()[ h ][ w ] ) == Character.isUpperCase( board.getGrid()[ i ][ j ] ) ){ return true; }
			moves.add( board.coords[ i ][ j ] );
			return true; 
		}
		return false;
	}
	
	private boolean lineBlack( Board board, int dy, int dx, boolean dia ){
		if( consecutiveBlack( board, dy, dx, dia ) ){ return true; }
		if( consecutiveBlack( board, -dy, -dx, dia ) ){ return true; }
		return false;
	}
	
	private boolean consecutiveBlack( Board board, int dy, int dx, boolean dia ){
		int i = h + dy ; int j = w + dx;
		while( !doneBlack( board, i, j, dia ) ){
			if( board.getGrid()[ i ][ j ] == 'q' ){ return true; }
			if( dia ){ if( board.getGrid()[ i ][ j ] == 'b' ){ return true; } }
			else{ if( board.getGrid()[ i ][ j ] == 'r' ){ return true; } }
			i = i + dy; j = j + dx;
		}
		return false;
	}
	
	private boolean doneBlack( Board board, int i, int j, boolean dia ){
		if( i < 0 || j < 0 ){ return true; }
		if( i >= board.getGrid().length ){ return true; }
		if( j >= board.getGrid()[0].length ){ return true; }
		if( board.getGrid()[ i ][ j ] != ' ' ){
			if( board.getGrid()[ i ][ j ] == 'p' || board.getGrid()[ i ][ j ] == 'n' || board.getGrid()[ i ][ j ] == 'k' ){
				return true;
			}
			if( dia ){ if( board.getGrid()[ i ][ j ] == 'r' ){ return true; } }
			else{ if( board.getGrid()[ i ][ j ] == 'b' ){ return true; } }
			if( Character.isUpperCase( board.getGrid()[ i ][ j ] ) ){
				return true;
			}
		}
		return false;
	}
	
	private boolean lineWhite( Board board, int dy, int dx, boolean dia ){
		if( consecutiveWhite( board, dy, dx, dia ) ){ return true; }
		if( consecutiveWhite( board, -dy, -dx, dia ) ){ return true; }
		return false;
	}
	
	private boolean consecutiveWhite( Board board, int dy, int dx, boolean dia ){
		int i = h + dy ; int j = w + dx;
		while( !doneWhite( board, i, j, dia ) ){
			if( board.getGrid()[ i ][ j ] == 'Q' ){ return true; }
			if( dia ){ if( board.getGrid()[ i ][ j ] == 'B' ){ return true; } }
			else{ if( board.getGrid()[ i ][ j ] == 'R' ){ return true; } }
			i = i + dy; j = j + dx;
		}
		return false;
	}
	
	private boolean doneWhite( Board board, int i, int j, boolean dia ){
		if( i < 0 || j < 0 ){ return true; }
		if( i >= board.getGrid().length ){ return true; }
		if( j >= board.getGrid()[0].length ){ return true; }
		if( board.getGrid()[ i ][ j ] != ' ' ){
			if( board.getGrid()[ i ][ j ] == 'P' || board.getGrid()[ i ][ j ] == 'N' || board.getGrid()[ i ][ j ] == 'K' ){
				return true;
			}
			if( dia ){ if( board.getGrid()[ i ][ j ] == 'R' ){ return true; } }
			else{ if( board.getGrid()[ i ][ j ] == 'B' ){ return true; } }
			if( Character.isLowerCase( board.getGrid()[ i ][ j ] ) ){
				return true;
			}
		}
		return false;
	}

}
