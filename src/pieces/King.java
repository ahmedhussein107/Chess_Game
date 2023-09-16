package pieces;

import java.util.ArrayList;

import game.Player;

public class King extends Piece{
	public King(Player player , int x , int y) {
		super(player , Integer.MAX_VALUE , new int [] {x,y});
	}

	@Override
	public ArrayList<int[]> suggestMove() {
		// TODO Auto-generated method stub
		return null;
	}
}
