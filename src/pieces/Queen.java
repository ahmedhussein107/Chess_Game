package pieces;

import java.util.ArrayList;

import game.Game;
import game.Player;

public class Queen extends Piece {
	public Queen(Player player, int x, int y) {
		super(player, 9, new int[] { x, y });
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		ArrayList<int[]> possibleMoves = new ArrayList<>();
		if (Game.currPlayer.equals(this.getOwner())) {
			Rook ifRookWasThere = new Rook(getOwner(), getLocation());
			Bishop ifBishopWasThere = new Bishop(getOwner(), getLocation());
			possibleMoves.addAll(ifBishopWasThere.suggestMove());
			possibleMoves.addAll(ifRookWasThere.suggestMove());
		}
		return possibleMoves;
	}
}