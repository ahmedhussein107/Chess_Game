package pieces;
import java.util.ArrayList;

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
				this.firstMove = false;
				return true;
			}
		}
		return false;
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
	
}
