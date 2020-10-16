package game;

import game.Record.Notation;

public class Board{

	private char[][] grid = new char[ 8 ][ 8 ];
	
	public Record record = new Record();
	
	private Notation takeback;
	
	public Integer[][][] coords = new Integer[ 8 ][ 8 ][ 2 ];
	
	public Board(){
		initCoords();
		initKingPawn();
		initGrid();
	}
	
	public char[][] getGrid(){
		return grid;
	}
	
	public void move( int fr, int fc, int tr, int tc ){
		if( grid[ fr ][ fc ] == 'P' || grid[ fr ][ fc ] == 'p' ){
			if( grid[ tr ][ tc ] == ' ' && fc != tc ){ enpassant( fr, fc, tr, tc ); }
			else if( tr == 7 || tr == 0 ){ promote( fr, fc, tr, tc ); }
			else{ mkmove( fr, fc, tr, tc ); }
		}
		else if( ( grid[ fr ][ fc ] == 'k' || grid[ fr ][ fc ] == 'K' ) && ( fc == 4  ) && ( tc == 2 || tc == 6 ) ){ castle( fr, fc, tr, tc ); }
		else{ mkmove( fr, fc, tr, tc ); }
	}
	
	private void enpassant( int fr, int fc, int tr, int tc ){
		if( fr > tr ){
			grid[ tr + 1 ][ tc ] = ' ';
			record.writeMove( grid[ fr ][ fc ], fr, fc, 'e', tr, tc, 'p' );
		}
		else{
			grid[ tr - 1 ][ tc ] = ' ';
			record.writeMove( grid[ fr ][ fc ], fr, fc, 'e', tr, tc, 'P' );
		}
		grid[ tr ][ tc ] = grid[ fr ][ fc ];
		grid[ fr ][ tc ] = ' ';
		grid[ fr ][ fc ] = ' ';
	}
	
	private void promote( int fr, int fc, int tr, int tc ){
		record.writeMove( grid[ fr ][ fc ], fr, fc, 'p', tr, tc, grid[ tr ][ tc ] );
		if( tr == 0 ){
			grid[ tr ][ tc ] = 'Q';
			grid[ fr ][ fc ] = ' ';
		}
		else{
			grid[ tr ][ tc ] = 'q';
			grid[ fr ][ fc ] = ' ';
		}
	}
	
	private void castle( int fr, int fc, int tr, int tc ){
		grid[ tr ][ tc ] = grid[ fr ][ fc ];
		grid[ fr ][ fc ] = ' ';
		if( tc == 2 ){
			grid[ fr ][ fc - 1 ] = grid[ fr ][ 0 ];
			grid[ fr ][ 0 ] = ' ';
			record.writeMove( grid[ tr ][ tc ], fr, fc, 'l', tr, tc, ' ');
		}
		else{
			grid[ fr ][ fc + 1 ] = grid[ fr ][ 7 ];
			grid[ fr ][ 7 ] = ' ';
			record.writeMove( grid[ tr ][ tc ], fr, fc, 's', tr, tc, ' ');
		}
	}
	
	private void mkmove( int fr, int fc, int tr, int tc ){
		record.writeMove( grid[ fr ][ fc ], fr, fc, 'm', tr, tc, grid[ tr ][ tc ] );
		grid[ tr ][ tc ] = grid[ fr ][ fc ];
		grid[ fr ][ fc ] = ' ';
	}
	
	public void takeBack(){
		takeback = record.takeBack();
		grid[ takeback.fromrow ][ takeback.fromcol ] = grid[ takeback.torow ][ takeback.tocol ];
		if( takeback.moveType == 'm' ){
			grid[ takeback.torow ][ takeback.tocol ] = takeback.capturedPiece;
		}else if( takeback.moveType == 's' ){
			grid[ takeback.torow ][ takeback.tocol ] = takeback.capturedPiece;
			grid[ takeback.fromrow ][ 7 ] = grid[ takeback.fromrow ][ takeback.fromcol + 1 ];
			grid[ takeback.fromrow ][ takeback.fromcol + 1 ] = ' ';
			
		}else if( takeback.moveType == 'l' ){
			grid[ takeback.torow ][ takeback.tocol ] = takeback.capturedPiece;
			grid[ takeback.fromrow ][ 0 ] = grid[ takeback.fromrow ][ takeback.fromcol - 1 ];
			grid[ takeback.fromrow ][ takeback.fromcol - 1 ] = ' ';
		}else if( takeback.moveType == 'p' ){
			if( takeback.torow == 0 ){
				grid[ takeback.fromrow ][ takeback.fromcol ] = 'P';
			}else{
				grid[ takeback.fromrow ][ takeback.fromcol ] = 'p';
			}
			grid[ takeback.torow ][ takeback.tocol ] = takeback.capturedPiece;
		}else{
			if( takeback.fromrow == 3 ){
				grid[ takeback.torow + 1 ][ takeback.tocol ] = takeback.capturedPiece;
			}else{
				grid[ takeback.torow - 1 ][ takeback.tocol ] = takeback.capturedPiece;
			}
			grid[ takeback.torow ][ takeback.tocol ] = ' ';
		}
	}
	
	public Notation lastMove(){
		return record.takeLastN();
	}
	
	private void initGrid(){
		initMains();
		for( int i=0; i<8; i++ ){
			grid[ 1 ][ i ] = 'p';
			grid[ 6 ][ i ] = 'P';
		}
		for(int i=2; i<6; i++){
			for( int j=0; j<8; j++ ){
				grid[ i ][ j ] = ' ';
			}
		}
	}
	
	private void initKingPawn(){
		for(int i=0; i<8; i++){
			for( int j=0; j<8; j++ ){
				grid[ i ][ j ] = ' ';
			}
		}
		grid[ 2 ][ 3 ] = 'k'; grid[ 7 ][ 4 ] = 'K';
		grid[ 6 ][ 2 ] = 'N'; grid[ 6 ][ 5 ] = 'N'; grid[ 0 ][ 5 ] = 'N';
	}
	
	private void initMains(){
		grid[ 0 ][ 0 ] = grid[ 0 ][ 7 ] = 'r';
		grid[ 7 ][ 0 ] = grid[ 7 ][ 7 ] = 'R';
		grid[ 0 ][ 1 ] = grid[ 0 ][ 6 ] = 'n';
		grid[ 7 ][ 1 ] = grid[ 7 ][ 6 ] = 'N';
		grid[ 0 ][ 2 ] = grid[ 0 ][ 5 ] = 'b';
		grid[ 7 ][ 2 ] = grid[ 7 ][ 5 ] = 'B';
		grid[ 0 ][ 3 ] = 'q'; grid[ 7 ][ 3 ] = 'Q';
		grid[ 0 ][ 4 ] = 'k'; grid[ 7 ][ 4 ] = 'K';
	}
	
	private void initCoords(){
		for( int i=0; i<8; i++ ){
			for( int j=0; j<8; j++ ){
				coords[ i ][ j ][ 0 ] = i;
				coords[ i ][ j ][ 1 ] = j;
			}
		}
	}
	
}
