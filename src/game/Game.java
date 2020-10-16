package game;

import java.util.LinkedList;
import java.util.List;

import engine.CephasFishNet;
import util.LineMoves;

public class Game{
	
	public CephasFishNet engine;
	
	public Board board;
	
	public Move bestmove;

	private LineMoves square;
	
	private char score = 'p';

	public char ps = '#';
	public int row = -1;
	public int col = -1;
	public List<Integer[]> moves = new LinkedList<Integer[]>();

	private boolean white = true;

	private Piece[] pieces;

	public Game(){
		board = new Board();
		engine = new CephasFishNet();
		square = new LineMoves();
		pieces = new Piece[ 6 ];
		pieces[ 0 ] = new Pawn( board );
		pieces[ 1 ] = new Queen( board );
		pieces[ 2 ] = new Knight( board );
		pieces[ 3 ] = new King( board );
		pieces[ 4 ] = new Bishop( board );
		pieces[ 5 ] = new Rook( board );
		bestmove = new Move();
	}


	public boolean move( int r, int c ){
		char selection = board.getGrid()[ r ][ c ];
		if( ps == '#' ){
			if( selection != ' ' ){
				ps = selection;
				row = r;
				col = c;
			}
		}
		else{
			if( cmove( r, c ) ){
				mkmove( row, col, r, c );
				engine.move( row, col, r, c );
				ps = '#';
				white = !white;
				gameover();
				updateMoves();
				return true;
			}
			else{
				if( selection != ' ' ){
					if( row == r && col == c ){
						ps = '#';
					}else{
						ps = selection;
						row = r;
						col = c;
						
					}
				}
				else{
					ps = '#';
				}
			}
		}
		updateMoves();
		return false;
	}
	
	public char gameStatus(){
		return score;
	}
	
	private void gameover(){
		for( int i=0; i<8; i++ ){
			for( int j=0; j<8; j++ ){
				if( board.getGrid()[ i ][ j ] != ' ' && Character.isLowerCase( board.getGrid()[ i ][ j ] ) != white ){
					if( pieceMoves( i, j ).size() > 0 ){
						return;
					}
				}
			}
		}
		int[] king = findKing( white );
		if( white ){
			score = 'b';
			if( !square.isAttackedByBlack(board, king[ 0 ], king[ 1 ] ) ){ score = 's'; }
		}
		else{
			score = 'w';
			if( !square.isAttackedByWhite(board, king[ 0 ], king[ 1 ] ) ){ score = 's'; }
		}
		
	}

	private boolean cmove( int r, int c ){
		if( Character.isLowerCase( board.getGrid()[ row ][ col ] ) == white ){
			return false;
		}
		if( board.getGrid()[ r ][ c ] != ' ' && Character.isLowerCase( board.getGrid()[ r ][ c ] ) != white ){
			return false;
		}

		if( !findMove( r, c ) ){ return false; }
		return true;
	}

	private void mkmove( int row, int col, int r, int c ){
		board.move( row, col, r, c );
	}

	private boolean findMove( int r, int c ){
		for( int i=0; i<moves.size(); i++ ){
			if( r == moves.get( i )[ 0 ] && c == moves.get( i )[ 1 ] ){
				return true;
			}
		}
		return false;
	}
	
	private void updateMoves(){
		if( ps == '#' ){
			moves.clear();
			return;
		}
		moves = pieceMoves( row, col );
	}

	private List<Integer[]> pieceMoves( int row, int col ){
		List<Integer[]> moves;
		if( board.getGrid()[ row ][ col ] == 'p' || board.getGrid()[ row ][ col ] == 'P'  ){
			moves = pieces[ 0 ].getMoves(row, col);
		}else if( board.getGrid()[ row ][ col ] == 'q' || board.getGrid()[ row ][ col ] == 'Q' ){
			moves = pieces[ 1 ].getMoves(row, col);
		}else if( board.getGrid()[ row ][ col ] == 'n' || board.getGrid()[ row ][ col ] == 'N' ){
			moves = pieces[ 2 ].getMoves(row, col);
		}else if( board.getGrid()[ row ][ col ] == 'k' || board.getGrid()[ row ][ col ] == 'K' ){
			moves = pieces[ 3 ].getMoves(row, col);
		}else if( board.getGrid()[ row ][ col ] == 'b' || board.getGrid()[ row ][ col ] == 'B' ){
			moves = pieces[ 4 ].getMoves(row, col);
		}else{
			moves = pieces[ 5 ].getMoves(row, col);
		}
		removeInvalid( row, col, moves );
		return moves;
	}

	private void removeInvalid( int row, int col, List<Integer[]> moves ){
		for( int i=0; i<moves.size(); i++ ){
			mkmove( row, col, moves.get( i )[ 0 ], moves.get( i )[ 1 ] );
			white = !white;
			int[] king;
			if( Character.isLowerCase( board.getGrid()[ moves.get( i )[ 0 ] ][ moves.get( i )[ 1 ] ] ) ){
				king = findKing( false );
				if( square.isAttackedByWhite( board, king[ 0 ], king[ 1 ] ) ){
					moves.remove( i );
					i--;
				}
			}else{
				king = findKing( true );
				if( square.isAttackedByBlack( board, king[ 0 ], king[ 1 ] ) ){
					moves.remove( i );
					i--;
				}
			}
			takeBack();
		}
	}
	
	private int[] findKing( boolean white ){
		int[] coors = new int[ 2 ];
		char king;
		if( white ){ king = 'K'; }else{ king = 'k'; }
		for( int i=0; i<8; i++ ){
			for( int j=0; j<8; j++ ){
				if( board.getGrid()[ i ][ j ] == king ){
					coors[ 0 ] = i;
					coors[ 1 ] = j;
					return coors;
				}
			}
		}
		return null;
	}

	private void takeBack(){
		if( board.record.getSize() == 0 ){ return; }
		board.takeBack();
		white = !white;
	}
	
	public void takeback(){
		if( board.record.getSize() == 0 ){ return; }
		engine.takeback();
		board.takeBack();
		white = !white;
	}
	
	public class Move{
		
		public boolean updated = false;
		
		public int fr, fc, tr, tc;

	}


}









