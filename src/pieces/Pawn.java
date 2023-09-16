package pieces;

import java.util.ArrayList;
import game.*;

public class Pawn extends Piece{
	private boolean movedTwo;

	public Pawn(Player player , int x , int y) {
		super(player, 1 , new int [] {x,y});
		this.movedTwo = false;
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		ArrayList<int []> possibleMoves = new ArrayList<>();
		int currX = getLocation()[0] , currY = getLocation()[1];
		int newX = currX+getOwner().getForward() , newY = currY;
		if(Game.isValidLocation(new int[] {newX,newY}, getOwner()) && Game.board[newX][newY] == null) {
			possibleMoves.add(new int[] {newX,newY});
		}
		newX += getOwner().getForward();
		if(Game.isValidLocation(new int[] {newX,newY}, getOwner()) 
				&& Game.board[newX][newY] == null && !movedTwo) {
			possibleMoves.add(new int[] {newX,newY});
		}
		return possibleMoves;
	}
	
	@Override
	public boolean move(int [] newLocation) {
		int [] oldLocation = this.getLocation();
		boolean movedSuccessfully = super.move(newLocation);
		if(movedSuccessfully && Math.abs(oldLocation[0] - newLocation[0]) == 2) movedTwo = true;
		return movedSuccessfully;
	}
	
}
