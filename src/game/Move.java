package game;
import pieces.*;

public class Move {
	private Piece piece;
	private int [] oldLocation;
	private int [] newLocation;
	public static int numberOfMoves = 0;
	
	public Move(Piece piece , int [] oldLocation , int [] newLocation) {
		this.piece = piece;
		this.oldLocation = oldLocation;
		this.newLocation = newLocation;
		numberOfMoves++;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public int[] getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(int[] oldLocation) {
		this.oldLocation = oldLocation;
	}

	public int[] getNewLocation() {
		return newLocation;
	}

	public void setNewLocation(int[] newLocation) {
		this.newLocation = newLocation;
	}
	
	
}
