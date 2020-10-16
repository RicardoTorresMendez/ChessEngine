package game;

public class Record{


	/** size of record */
	private int size;
	
	/** implementation choice */
	private char k;
	
	
	/** utility variable */
	private int i, r, c;

	/** the actual list of records */
	private Notation[] record;
	
	private Notation move;


	public Record(){
		size = 0;
		record = new Notation[ 1000 ];
		for( i=0; i<record.length; i++ ){
			record[ i ] = new Notation( '#', -1, -1, '#', -1, -1, '#' );
		}
	}

	public int getSize(){
		return size;
	}

	public String[] getRecord(){
		String[] rcd = new String[ size ];
		for( i=0; i<size; i++ ){
			rcd[ i ] = stringRecord( i );
		}
		return rcd;
	}

	public boolean canCastle( boolean white, boolean queenC ){//this only guarantees that neither king or rook have moved
		if( white ){ k = 'K'; r = 7; }else{ k = 'k'; r = 0; }
		if( queenC ){ c = 0; }else{ c = 7; }
		for( i=0; i<size; i++ ){
			move = record[ i ];
			if( move.movingPiece == k ){ return false; }
			if( move.fromrow == r && move.fromcol == c ){ return false; }
			if( move.torow == r && move.tocol == c ){ return false; }
			
		}
		return true;
	}

	public void writeMove( char piece, int row, int col, char moveType, int ro, int co, char capturedPiece ){
		move = record[ size ];
		move.movingPiece = piece;
		move.fromrow = row;
		move.fromcol = col;
		move.moveType = moveType;
		move.torow = ro;
		move.tocol = co;
		move.capturedPiece = capturedPiece;
		size++;
	}

	private String stringRecord( int ith ){
		String rcd = "";
		rcd =  ( rcd + "("+ record[ ith ].fromrow + ", " + record[ ith ].fromcol + ") " );
		rcd = ( rcd + record[ ith ].movingPiece + " " );
		rcd = ( rcd + move( record[ ith ].moveType ) + " " );
		rcd = ( rcd + "(" + record[ ith ].torow + ", " + record[ ith ].tocol + ") " );
		rcd = ( rcd + record[ ith ].capturedPiece );
		return rcd;
	}

	private String move( char move ){
		if( move == 'm' ){ return "moves"; }
		if( move == 'l' ){ return "long castles"; }
		if( move == 's' ){ return "short castles"; }
		if( move == 'p' ){ return "promotes"; }
		if( move == 'e' ){ return "en passant"; }
		return "";
	}

	public Notation takeBack(){
		if( size == 0 ){ return null; }
		size--;
		return record[ size ];
	}

	public String takeLastS(){
		if( size == 0 ){ return ""; }
		return stringRecord( size - 1 );
	}

	public Notation takeLastN(){
		if( size == 0 ){ return null; }
		return record[ size - 1 ];
	}

	public class Notation{

		public char movingPiece;

		public char moveType;

		public int fromrow;

		public int fromcol;

		public int torow;

		public int tocol;

		public char capturedPiece;

		public Notation( char piece, int row, int col, char moveT, int ro, int co, char cpiece ){
			movingPiece = piece;
			moveType = moveT;
			fromrow = row;
			fromcol = col;
			torow = ro;
			tocol = co;
			capturedPiece = cpiece;
		}

	}


}
