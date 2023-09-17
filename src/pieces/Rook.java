package pieces;

import java.util.ArrayList;

import game.Game;
import game.Player;

public class Rook extends Piece {

	public Rook(Player player, int[] location) {
		super(player, 5, location);
	}

	public Rook(Player player, int x, int y) {
		super(player, 5, new int[] { x, y });
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		ArrayList<int[]> possibleMoves = new ArrayList<>();
		if (Game.currPlayer.equals(this.getOwner())) {
			int[][] directions = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
			for (int[] direction : directions) {
				int counter = 1;
				while (true) {
					int x = this.getLocation()[0] + direction[0] * counter,
							y = this.getLocation()[1] + direction[1] * counter;
					int[] location = new int[] { x, y };
					if (Game.isValidLocation(location, this.getOwner())) {
						if(isLegalMove(location))
							possibleMoves.add(location);
						if (Game.board[x][y] != null)
							break;
					} else
						break;
					counter++;
				}
			}
		}
		return possibleMoves;
	}
}
