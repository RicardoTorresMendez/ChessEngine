package engine;

import java.util.HashMap;

import game.Board;

public class CephasFishNet{

	private int[][] moves;
	
	private Piece[] pieces;

	private HashMap<Character,Piece> piece;

	public Board board;
	
	private char[][] grid;
	
	private char square;

	private boolean w;
	
	public boolean stop;
	
	private int depth = 0, nummoves;
	
	public int nodes;

	public Evaluation eval;
	
	public int maxdepth;
	
	public Node pv;
	
	/** Utility variables */
	private int i, j;

	public CephasFishNet(){
		pieces = new Piece[ 6 ];
		board = new Board();
		grid = board.getGrid();
		initPieces();
		piece = new HashMap<Character,Piece>();
		initPiece();
		eval = new Evaluation();
		w = true;
		moves = Piece.moves();
		pv = null;
	}

	public void evaluate(){
		stop = false;
		depth = 0;
		nodes = 0;
		int num_of_moves = generateMoves();
		if( pv != null && num_of_moves > 1 ){
			for( i=( Piece.size() - num_of_moves ); i<Piece.size(); i++ ){
				if( moves[ i ][ 0 ] == pv.fr && moves[ i ][ 1 ] == pv.fc && moves[ i ][ 2 ] == pv.tr && moves[ i ][ 3 ] == pv.fc ){
					moves[ i ][ 0 ] = moves[ Piece.size() - num_of_moves ][ 0 ]; moves[ i ][ 1 ] = moves[ Piece.size() - num_of_moves ][ 1 ];
					moves[ i ][ 2 ] = moves[ Piece.size() - num_of_moves ][ 2 ]; moves[ i ][ 3 ] = moves[ Piece.size() - num_of_moves ][ 3 ];
					moves[ Piece.size() - num_of_moves ][ 0 ] = pv.fr; moves[ Piece.size() - num_of_moves ][ 1 ] = pv.fc;
					moves[ Piece.size() - num_of_moves ][ 2 ] = pv.tr; moves[ Piece.size() - num_of_moves ][ 3 ] = pv.tc;
					break;
				}
			}
			pv = pv.bestchild;
		}
		
		Node pv_temp;
		Node pv_best = null;
		
		int alpha = -100000, beta = 100000;
		if( w ){ eval.value = -100000; }else{ eval.value = 100000; }
		int value;
		for( int i=( Piece.size() - num_of_moves ); i<Piece.size(); i++ ){
			board.move( moves[ i ][ 0 ], moves[ i ][ 1 ], moves[ i ][ 2 ], moves[ i ][ 3 ] );
			
			pv_temp = new Node();
			pv_temp.fr = moves[ i ][ 0 ]; pv_temp.fc = moves[ i ][ 1 ];
			pv_temp.tr = moves[ i ][ 2 ]; pv_temp.tc = moves[ i ][ 3 ];
			
			w = !w;
			depth++;
			
			if( w ){ value = whiteBest( alpha, eval.value, pv_temp ); }else{ value = blackBest( eval.value, beta, pv_temp ); }
			if( w ){
				if( value < eval.value ){
					eval.value = value;
					eval.fr = moves[ i ][ 0 ]; eval.fc = moves[ i ][ 1 ];
					eval.tr = moves[ i ][ 2 ]; eval.tc = moves[ i ][ 3 ];
					pv_best = pv_temp;
				}
			}else{
				if( value > eval.value ){
					eval.value = value;
					eval.fr = moves[ i ][ 0 ]; eval.fc = moves[ i ][ 1 ];
					eval.tr = moves[ i ][ 2 ]; eval.tc = moves[ i ][ 3 ];
					pv_best = pv_temp;
				}
			}
			
			depth--;
			board.takeBack();
			w = !w;
		}
		pv = pv_best;
		Piece.remove( num_of_moves );
	}
	
	private int blackBest( int alpha, int beta, Node parent ){
		if( stop ){ return 0; }
		int num_of_moves = generateMoves();
		nodes++;
		int j = Piece.size();
		if( pv != null && num_of_moves > 1 ){
			for( i=( j - num_of_moves ); i<j; i++ ){
				if( moves[ i ][ 0 ] == pv.fr && moves[ i ][ 1 ] == pv.fc && moves[ i ][ 2 ] == pv.tr && moves[ i ][ 3 ] == pv.fc ){
					moves[ i ][ 0 ] = moves[ j - num_of_moves ][ 0 ]; moves[ i ][ 1 ] = moves[ j - num_of_moves ][ 1 ];
					moves[ i ][ 2 ] = moves[ j - num_of_moves ][ 2 ]; moves[ i ][ 3 ] = moves[ j - num_of_moves ][ 3 ];
					moves[ j - num_of_moves ][ 0 ] = pv.fr; moves[ j - num_of_moves ][ 1 ] = pv.fc;
					moves[ j - num_of_moves ][ 2 ] = pv.tr; moves[ j - num_of_moves ][ 3 ] = pv.tc;
					break;
				}
			}
			pv = pv.bestchild;
		}
		for( i=( j - num_of_moves ); i<j; i++ ){
			if( grid[ moves[ i ][ 2 ] ][ moves[ i ][ 3 ] ] == 'K' ){ Piece.remove( num_of_moves ); return -10000 + depth; }
		}
		if( depth == maxdepth ){ Piece.remove( num_of_moves ); return heuristic(); }
		int best = beta, value;
		
		Node pv_temp;
		Node pv_best = null;
		
		for( int i=( j - num_of_moves ); i<j; i++ ){
			board.move( moves[ i ][ 0 ], moves[ i ][ 1 ], moves[ i ][ 2 ], moves[ i ][ 3 ] );
			
			pv_temp = new Node();
			pv_temp.fr = moves[ i ][ 0 ]; pv_temp.fc = moves[ i ][ 1 ];
			pv_temp.tr = moves[ i ][ 2 ]; pv_temp.tc = moves[ i ][ 3 ];
			
			w = !w;
			depth++;
			
			value = whiteBest( alpha, best, pv_temp );
			if( value < best ){ best = value; pv_best = pv_temp; }
			if( best <= alpha ){
				Piece.remove( num_of_moves );
				parent.bestchild = pv_best;
				depth--;
				board.takeBack();
				w = !w;
				return best;
			}
			
			depth--;
			board.takeBack();
			w = !w;
		}
		parent.bestchild = pv_best;
		Piece.remove( num_of_moves );
		return best;
	}
	
	private int whiteBest( int alpha, int beta, Node parent ){
		if( stop ){ return 0; }
		int num_of_moves = generateMoves();
		nodes++;
		int j = Piece.size();
		if( pv != null && num_of_moves > 1 ){
			for( i=( j - num_of_moves ); i<j; i++ ){
				if( moves[ i ][ 0 ] == pv.fr && moves[ i ][ 1 ] == pv.fc && moves[ i ][ 2 ] == pv.tr && moves[ i ][ 3 ] == pv.fc ){
					moves[ i ][ 0 ] = moves[ j - num_of_moves ][ 0 ]; moves[ i ][ 1 ] = moves[ j - num_of_moves ][ 1 ];
					moves[ i ][ 2 ] = moves[ j - num_of_moves ][ 2 ]; moves[ i ][ 3 ] = moves[ j - num_of_moves ][ 3 ];
					moves[ j - num_of_moves ][ 0 ] = pv.fr; moves[ j - num_of_moves ][ 1 ] = pv.fc;
					moves[ j - num_of_moves ][ 2 ] = pv.tr; moves[ j - num_of_moves ][ 3 ] = pv.tc;
					break;
				}
			}
			pv = pv.bestchild;
		}
		
		for( i=( j - num_of_moves ); i<j; i++ ){
			if( grid[ moves[ i ][ 2 ] ][ moves[ i ][ 3 ] ] == 'k' ){ Piece.remove( num_of_moves ); return 10000 - depth; }
		}
		if( depth == maxdepth ){ Piece.remove( num_of_moves ); return heuristic(); }
		int best = alpha, value;
		
		Node pv_temp;
		Node pv_best = null;
		
		for( int i=( j - num_of_moves ); i<j; i++ ){
			board.move( moves[ i ][ 0 ], moves[ i ][ 1 ], moves[ i ][ 2 ], moves[ i ][ 3 ] );
			
			pv_temp = new Node();
			pv_temp.fr = moves[ i ][ 0 ]; pv_temp.fc = moves[ i ][ 1 ];
			pv_temp.tr = moves[ i ][ 2 ]; pv_temp.tc = moves[ i ][ 3 ];
			
			w = !w;
			depth++;
			
			value = blackBest( best, beta, pv_temp );
			if( value > best ){ best = value; pv_best = pv_temp; }
			if( beta <= best ){
				Piece.remove( num_of_moves );
				parent.bestchild = pv_best;
				depth--;
				board.takeBack();
				w = !w;
				return best;
			}
			
			depth--;
			board.takeBack();
			w = !w;
		}
		parent.bestchild = pv_best;
		Piece.remove( num_of_moves );
		return best;
	}
	
	private int generateMoves(){
		nummoves = 0;
		for( i=0; i<8; i++ ){
			for( j=0; j<8; j++ ){
				square = grid[ i ][ j ];
				if( square != ' ' && ( w == Character.isUpperCase( square ) ) ){
					nummoves = nummoves + piece.get( square ).getMoves( i, j );
				}
			}
		}
		return nummoves;
	}
	
	public void takeback(){
		board.takeBack();
		w = !w;
	}
	
	public void move( int fr, int fc, int tr, int tc ){
		board.move(fr, fc, tr, tc);
		w = !w;
	}
	
	private int heuristic(){
		int value = 0;
		for( i=0; i<8; i++ ){
			for( j=0; j<8; j++ ){
				if( grid[ i ][ j ] == 'P' ){ value = value + 130 - ( i * 5 ); if( j > 1 && j < 6 ){ value = value + 5; if( j == 3 || j == 4 ){ value = value + 5; } } }
				else if( grid[ i ][ j ] == 'p' ){ value = value - 100 - ( i * 5 ); if( j > 1 && j < 6 ){ value = value - 5; if( j == 3 || j == 4 ){ value = value - 5; } } }
				else if( grid[ i ][ j ] == 'R' ){ value = value + 500; if( j == 3 || j == 4 ){ value = value + 10; } }
				else if( grid[ i ][ j ] == 'r' ){ value = value - 500; if( j == 3 || j == 4 ){ value = value - 10; } }
				else if( grid[ i ][ j ] == 'B' ){ value = value + 310; if( i == 7 ){ value = value - 15; } }
				else if( grid[ i ][ j ] == 'b' ){ value = value - 310; if( i == 0 ){ value = value + 15; } }
				else if( grid[ i ][ j ] == 'N' ){ value = value + 300; if( j > 1 && j < 6 ){ value = value + 5; } if( i < 6 ){ value = value + 5; } if( i < 4 ){ value = value + 5; } }
				else if( grid[ i ][ j ] == 'n' ){ value = value - 300; if( j > 1 && j < 6 ){ value = value - 5; } if( i > 1 ){ value = value - 5; } if( i > 3 ){ value = value - 5; } }
				else if( grid[ i ][ j ] == 'Q' ){ value = value + 900; }
				else if( grid[ i ][ j ] == 'q' ){ value = value - 900; }
				else if( grid[ i ][ j ] == 'K' ){ if( i > 6 ){ if( j < 2 || j > 5 ){ value = value + 10; } } }
				else if( grid[ i ][ j ] == 'K' ){ if( i < 2 ){ if( j < 2 || j > 5 ){ value = value - 10; } } }
			}
		}
		return value;
	}

	private void initPiece(){
		piece.put( 'p', pieces[ 0 ] );
		piece.put( 'q', pieces[ 1 ] );
		piece.put( 'n', pieces[ 2 ] );
		piece.put( 'k', pieces[ 3 ] );
		piece.put( 'b', pieces[ 4 ] );
		piece.put( 'r', pieces[ 5 ] );
		piece.put( 'P', pieces[ 0 ] );
		piece.put( 'Q', pieces[ 1 ] );
		piece.put( 'N', pieces[ 2 ] );
		piece.put( 'K', pieces[ 3 ] );
		piece.put( 'B', pieces[ 4 ] );
		piece.put( 'R', pieces[ 5 ] );
	}

	private void initPieces(){
		pieces[ 0 ] = new Pawn( board );
		pieces[ 1 ] = new Queen( board );
		pieces[ 2 ] = new Knight( board );
		pieces[ 3 ] = new King( board );
		pieces[ 4 ] = new Bishop( board );
		pieces[ 5 ] = new Rook( board );
	}


	public class Evaluation{

		public int value;

		public int fr, fc, tr, tc;

	}


}











