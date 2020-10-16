package game;

import java.util.LinkedList;
import java.util.List;

public class Piece{
	
	protected Board board;
	
	protected List<Integer[]> moves = new LinkedList<Integer[]>();
	
	public Piece( Board board ){
		this.board = board;
		
	}
	
	public List<Integer[]> getMoves( int r, int c ){
		return null;
	}
	
}
