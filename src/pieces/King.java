package pieces;

import java.util.ArrayList;

import game.Game;
import game.Player;

public class King extends Piece{
	
	public King(Player player , int x , int y) {
		super(player , Integer.MAX_VALUE , new int [] {x,y});
	}
	
	public boolean check() {
		return !isLegalMove(getLocation());
	}
	
	
	public boolean move(int[] newLocation) {
		int[] oldLocation = this.getLocation();
		boolean movedSuccessfully = super.move(newLocation);
		if (movedSuccessfully) {
			this.getOwner().setKingsLocation(newLocation);
			// castling
			int newRow = newLocation[0], newCol = newLocation[1];
			if(newLocation[1] - oldLocation[1] == 2) {
				Game.board[newRow][newCol - 1] = Game.board[newRow][7];
				Game.board[newRow][7] = null;
				Game.board[newRow][newCol - 1].setLocation(new int[] {newRow, newCol - 1});
			}
			if(oldLocation[1] - newLocation[1] == 2) {
				Game.board[newRow][newCol + 1] = Game.board[newRow][0];
				Game.board[newRow][0] = null;
				Game.board[newRow][newCol + 1].setLocation(new int[] {newRow, newCol + 1});
			}
		}
		return movedSuccessfully;
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		ArrayList<int[]> possibleMoves = new ArrayList<>();
		int row = this.getLocation()[0], column = this.getLocation()[1];
		if (Game.currPlayer.equals(this.getOwner())) {
			int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 },
					{ 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
			for (int[] direction : directions) {
				int x = row + direction[0], y = column + direction[1];
				int[] location = new int[] { x, y };
				if (Game.isValidLocation(location, this.getOwner()) && isLegalMove(location))
					possibleMoves.add(location);
			}
			if(isFirstMove() && !check()) {
				// short castle
				Piece firstRook = Game.board[row][7];
				if(firstRook != null && firstRook instanceof Rook && firstRook.isFirstMove()) {
					boolean canCastle = true;
					for(int col = column + 1; col < 7; col++) {
						canCastle &= (Game.board[row][col] == null);
					}
					for(int inc = 1; inc <= 2; inc++) {
						canCastle &= isLegalMove(new int[] {row, column + inc});
					}
					if(canCastle) {
						possibleMoves.add(new int[] {row, column + 2});
					}
						
				}
				
				// long castle
				Piece secondRook = Game.board[row][0];
				if(secondRook != null && secondRook instanceof Rook && secondRook.isFirstMove()) {
					boolean canCastle = true;
					for(int col = column - 1; col > 0; col--) {
						canCastle &= (Game.board[row][col] == null);
					}
					for(int inc = 1; inc <= 2; inc++) {
						canCastle &= isLegalMove(new int[] {row, column - inc});
					}
					if(canCastle) {
						possibleMoves.add(new int[] {row, column - 2});
					}
						
				}
			}
		}
		return possibleMoves;
	}
}
