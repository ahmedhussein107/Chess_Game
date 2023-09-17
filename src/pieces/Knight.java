package pieces;

import java.util.ArrayList;

import game.Game;
import game.Player;

public class Knight extends Piece {
	public Knight(Player player, int x, int y) {
		super(player, 3, new int[] { x, y });
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		ArrayList<int[]> possibleMoves = new ArrayList<>();
		if (Game.currPlayer.equals(this.getOwner())) {
			int[][] directions = { { -2, -1 }, { -2, 1 }, { 2, -1 }, { 2, 1 },
					{ -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 } };
			for (int[] direction : directions) {
				int x = this.getLocation()[0] + direction[0], y = this.getLocation()[1] + direction[1];
				int[] location = new int[] { x, y };
				if (Game.isValidLocation(location, this.getOwner()) && isLegalMove(location))
					possibleMoves.add(location);
			}
		}
		return possibleMoves;
	}
}
