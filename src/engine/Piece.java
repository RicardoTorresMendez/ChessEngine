package engine;

import game.Board;

public class Piece{
	
	protected Board board;
	
	protected static int[][] moves;
	
	protected static int size = 0;
	
	protected static char[][] grid;
	
	protected int added;
	
	protected int r, c;
	
	public Piece( Board board ){
		this.board = board;
		moves = new int[ 1000 ][ 4 ];
		grid = board.getGrid();
	}
	
	public int getMoves( int r, int c ){
		return 0;
	}
	
	public static int[][] moves(){
		return moves;
	}
	
	public static int size(){
		return size;
	}
	
	public static void remove( int n ){
		size = size - n;
	}
	
	
}
