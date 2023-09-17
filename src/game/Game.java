package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import pieces.*;

public class Game {
	public static int turn;
	public static Player currPlayer , otherPlayer;
	public static Piece [][] board;
	public static Piece [][] testBoard;
	public static Player playerOne , playerTwo;
	public static ArrayList<Move> history;
	public static TreeMap<String, Integer> positions; 
	
	
	// TODO : create players, change turn, check checkmate and draw
	
	public static void startGame () {
		turn = 1;
		currPlayer = playerOne;
		board = new Piece [8][8];
		testBoard = new Piece [8][8];
		history = new ArrayList<>();
		positions = new TreeMap<>();
		createFirstBoard();
	}
	
	private static void createFirstBoard() {
		createFirstRow(playerOne, 0);
		createPawns(playerOne , 1);
		createPawns(playerTwo , 6);
		createFirstRow(playerTwo, 7);
	}
	
	private static void createPawns(Player player , int row) {
		for(int i =0 ; i < 8 ; i++) {
			board[row][i] = new Pawn(player, row, i);
			testBoard[row][i] = board[row][i];
			player.getPieces().add(board[row][i]);
		}
	}
	
	private static void createFirstRow(Player player , int row) {
		board[row][0] = new Rook(player , row , 0);
		board[row][1] = new Knight(player , row , 1);
		board[row][2] = new Bishop(player , row , 2);
		board[row][3] = new Queen(player , row , 3);
		board[row][4] = new King(player , row , 4);
		board[row][5] = new Bishop(player , row , 5);
		board[row][6] = new Knight(player , row , 6);
		board[row][7] = new Rook(player , row , 7);
		for(int i = 0 ; i < 8 ; i++) testBoard[row][i] = board[row][i];
		player.getPieces().addAll(Arrays.asList(board[row]));
		player.setKingsLocation(new int[] {row , 4});
	}
	
	public static boolean sameLocation(int [] locationOne , int [] locationTwo) {
		return locationOne[0] == locationTwo[0] && locationOne[1] == locationTwo[1];
	}
	
	public static boolean isValidLocation(int [] location , Player player) {
		int row = location[0] , column = location[1];
		return isInsideTheBoard(location) &&
				(Game.board[row][column] == null || !Game.board[row][column].getOwner().equals(player));
	}
	
	public static boolean isInsideTheBoard(int [] location) {
		int row = location[0] , column = location[1];
		return row > -1 && row < 8 && column > -1 && column < 8;
	}
	
	public static boolean isCheckMate() {
		int currKingRow = currPlayer.getKingsLocation()[0],
				currKingColumn = currPlayer.getKingsLocation()[1];
		King currKing = (King) board[currKingRow][currKingColumn];
		boolean checkMate = currKing.check();
		for(Piece piece: currPlayer.getPieces()) {
			checkMate &= (piece.suggestMove()).isEmpty();
		}
		return checkMate;
	}
	
	public static boolean isDraw() {
		int currKingRow = currPlayer.getKingsLocation()[0],
				currKingColumn = currPlayer.getKingsLocation()[1];
		King currKing = (King) board[currKingRow][currKingColumn];
		boolean stalemate = !currKing.check();
		for(Piece piece: currPlayer.getPieces()) {
			stalemate &= (piece.suggestMove()).isEmpty();
		}
		boolean insufficentMaterial = checkInsufficentMaterial();
		boolean threeFoldRepition = positions.containsValue(3);
		return stalemate || insufficentMaterial || threeFoldRepition;
	}
	
	public static boolean checkInsufficentMaterial() {
		boolean insufficent = true;
		if(playerOne.getPieces().size() > 2 || playerTwo.getPieces().size() > 2) return false;
		for(Piece piece: playerOne.getPieces()) {
			if(piece instanceof King || piece instanceof Knight || piece instanceof Bishop) continue;
			insufficent = false;
		}
		for(Piece piece: playerTwo.getPieces()) {
			if(piece instanceof King || piece instanceof Knight || piece instanceof Bishop) continue;
			insufficent = false;
		}
		return insufficent;
	}
	
	public static String getBoard() {
		StringBuilder sb = new StringBuilder();
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if(board[row][col] != null) {
					Piece piece = board[row][col];
					sb.append((piece.getOwner().getColor() == 1)?"W":"B");
					String s = "";
					if(piece instanceof King) {
						s = "K";
					}else if(piece instanceof Queen) {
						s = "Q";
					}else if(piece instanceof Rook) {
						s = "R";
					}else if(piece instanceof Bishop) {
						s = "B";
					}else if(piece instanceof Knight) {
						s = "N";
					}
					sb.append(s);
					String location = (char)('a' + col) + "" + row;
					sb.append(location);
				}
			}
		}
		return sb.toString();
	}
	
	
	
			// 7 R N B Q K B N R             
			// 6 p p p p p p p p        
			// 5                
			// 4                
			// 3                
			// 2                
			// 1 p p p p p p p p               
			// 0 R N B Q K B N R 
			// _ 0 1 2 3 4 5 6 7
}
