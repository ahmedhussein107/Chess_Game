package pieces;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import game.Game;
import game.Move;
import game.Player;

abstract public class Piece {
	private Player owner;
	private int points;
	private int [] location;
	private boolean firstMove;
	

	public Piece(Player player , int points , int [] location) {
		this.owner = player;
		this.points = points;
		this.location = location;
		this.firstMove = true;
	}
	
	abstract public ArrayList<int []> suggestMove();
	
	public boolean move(int [] newLocation) {
		int [] oldLocation = this.location;
		for(int [] location : suggestMove()) {
			if(Game.sameLocation(location, newLocation)) {
				int oldX = oldLocation[0] , oldY = oldLocation[1] ,
						newX = newLocation[0] , newY = newLocation[1];
				Game.board[oldX][oldY] = null;
				if(Game.board[newX][newY] != null) {
					Game.otherPlayer.setPoints(Game.otherPlayer.getPoints() - Game.board[newX][newY].points);
					Game.otherPlayer.getPieces().remove(Game.board[newX][newY]);
				}
				Game.board[newX][newY] = this;
				Game.history.add(new Move(this, oldLocation, newLocation));
				Game.positions.put(Game.getBoard(), Game.positions.getOrDefault(Game.getBoard(), 0)+1);
				Game.board[newX][newY].setLocation(newLocation);
				this.firstMove = false;
				return true;
			}
		}
		return false;
	}
	
	public boolean isLegalMove(int [] location) {
		int oldRow = getLocation()[0] , oldColumn = getLocation()[1];
		int newRow = location[0] , newColumn = location[1];
		Piece oldPiece = Game.testBoard[newRow][newColumn];
		int kingRow = this.getOwner().getKingsLocation()[0],
			kingColumn = this.getOwner().getKingsLocation()[1];
		// try to move the piece and see what happens
		Game.testBoard[oldRow][oldColumn] = null;
		Game.testBoard[newRow][newColumn] = this;
		boolean isLegal = true;
		isLegal &= checkDiagonals(kingRow, kingColumn);
		isLegal &= checkRowsAndColumns(kingRow , kingColumn);
		isLegal &= checkKnights(kingRow , kingColumn);
		isLegal &= checkPawns(kingRow , kingColumn);
		//reset everything before returning
		Game.testBoard[oldRow][oldColumn] = this;
		Game.testBoard[newRow][newColumn] = oldPiece;
		return isLegal;
	}
	
	private boolean checkDiagonals(int kingRow , int kingColumn) {
		int[][] directions = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
		for (int[] direction : directions) {
			int counter = 1;
			while (true) {
				int x = kingRow + direction[0] * counter,
						y = kingColumn + direction[1] * counter;
				int[] location = new int[] { x, y };
				if (Game.isInsideTheBoard(location)) {
					Piece currPiece = Game.testBoard[x][y];
					if(currPiece != null) {
						if(currPiece.getOwner().equals(this.getOwner())) break;
						else {
							if((currPiece instanceof Bishop) || (currPiece instanceof Queen)) return false;
						}
					}
				} else
					break;
				counter++;
			}
		}
		return true;
	}
	
	private boolean checkRowsAndColumns(int kingRow, int kingColumn) {
		int[][] directions = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
		for (int[] direction : directions) {
			int counter = 1;
			while (true) {
				int x = kingRow + direction[0] * counter,
						y = kingColumn + direction[1] * counter;
				int[] location = new int[] { x, y };
				if (Game.isInsideTheBoard(location)) {
					Piece currPiece = Game.testBoard[x][y];
					if(currPiece != null) {
						if(currPiece.getOwner().equals(this.getOwner())) break;
						else {
							if((currPiece instanceof Rook) || (currPiece instanceof Queen)) return false;
						}
					}
				} else
					break;
				counter++;
			}
		}
		return true;
	}
	
	private boolean checkKnights(int kingRow, int kingColumn) {
		int[][] directions = { { -2, -1 }, { -2, 1 }, { 2, -1 }, { 2, 1 },
				{ -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 } };
		for(int[] direction: directions) {
			int x = kingRow + direction[0], y = kingColumn + direction[1];
			int[] location = new int[] { x, y };
			if (Game.isInsideTheBoard(location)) {
				Piece currPiece = Game.testBoard[x][y];
				if(currPiece != null) {
					if(!currPiece.getOwner().equals(this.getOwner()) && (currPiece instanceof Knight))
						return false;
				}
			}
		}
		return true;
	}
	
	private boolean checkPawns(int kingRow, int kingColumn) {
		int forward = Game.testBoard[kingRow][kingColumn].getOwner().getForward();
		int[] directions = {1,-1};
		for(int direction: directions) {
			int x = kingRow + forward, y = kingColumn + direction;
			int[] location = new int[] {x,y};
			if(Game.isInsideTheBoard(location)) {
				Piece currPiece = Game.testBoard[x][y];
				if(currPiece != null) {
					if(!currPiece.getOwner().equals(this.getOwner()) && (currPiece instanceof Pawn)) 
						return false;
				}
			}
		}
		return true;
	}
	
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player player) {
		this.owner = player;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int[] getLocation() {
		return location;
	}
	public void setLocation(int[] location) {
		this.location = location;
	}
	public boolean isFirstMove() {
		return firstMove;
	}
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Piece)) {
			return false;
		}
		Piece other = (Piece) obj;
		return firstMove == other.firstMove && Arrays.equals(location, other.location)
				&& Objects.equals(owner, other.owner) && points == other.points;
	}
	
	
}
