package pieces;

import java.util.ArrayList;
import game.*;

public class Pawn extends Piece {
	private boolean movedTwo;

	public Pawn(Player player, int x, int y) {
		super(player, 1, new int[] { x, y });
		this.movedTwo = false;
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		ArrayList<int[]> possibleMoves = new ArrayList<>();
		if (Game.currPlayer.equals(this.getOwner())) {
			int currX = getLocation()[0], currY = getLocation()[1];
			int forward = getOwner().getForward();
			int newX = currX + forward, newY = currY;
			if (Game.isValidLocation(new int[] { newX, newY }, getOwner()) && Game.board[newX][newY] == null
					&& isLegalMove(new int[] { newX, newY })) {
				possibleMoves.add(new int[] { newX, newY });
			}
			newX += forward;
			if (Game.isValidLocation(new int[] { newX, newY }, getOwner()) && Game.board[newX][newY] == null
					&& !movedTwo && isLegalMove(new int[] { newX, newY })) {
				possibleMoves.add(new int[] { newX, newY });
			}
			// TODO attacking methods
			int[] directions = { 1, -1 };
			for (int direction : directions) {
				newX = currX + forward;
				newY = currY + direction;
				int[] location = new int[] { newX, newY };
				if (Game.isInsideTheBoard(location)) {
					Piece currPiece = Game.testBoard[newX][newY];
					if (currPiece != null && !currPiece.getOwner().equals(this.getOwner())
							&& isLegalMove(location)) {
						possibleMoves.add(new int[] { newX, newY });
					}
				}
				
				newX = currX;
				location = new int[] { newX, newY };
				if (Game.isInsideTheBoard(location)) {
					Piece currPiece = Game.testBoard[newX][newY];
					if (currPiece != null && !currPiece.getOwner().equals(this.getOwner())) {
						if(currPiece instanceof Pawn) {
							Move move = Game.history.get(Game.history.size() - 1);
							if(move.getPiece().equals(currPiece) &&
									Math.abs(move.getOldLocation()[1] - move.getNewLocation()[1]) == 2
									&& isLegalMove(location)) {
								possibleMoves.add(new int[] { newX, newY });
							}
						}
					}
				}
			}

		}
		return possibleMoves;
	}

	@Override
	public boolean move(int[] newLocation) {
		int[] oldLocation = this.getLocation();
		boolean movedSuccessfully = super.move(newLocation);
		if (movedSuccessfully) {
			if(Math.abs(oldLocation[0] - newLocation[0]) == 2)
				movedTwo = true;
			if(newLocation[1] == 0 || newLocation[1] == 7) 
				promote();
		}
		return movedSuccessfully;
	}
	
	public void promote() {
		//TODO: handle promotion
	}

}
