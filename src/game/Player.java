package game;

import java.util.ArrayList;
import pieces.Piece;

public class Player {
	private String name;
	private int points;
	private ArrayList<Piece> pieces;
	private int [] kingsLocation;
	private int forward;
	private int color;
	
	public Player(String name) {
		this.name = name;
		this.points = 33;
		this.pieces = new ArrayList<>();
	}

	public int getForward() {
		return forward;
	}

	public void setForward(int forward) {
		this.forward = forward;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		this.forward = color == 1 ? 1 : -1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}

	public int[] getKingsLocation() {
		return kingsLocation;
	}

	public void setKingsLocation(int[] kingsLocation) {
		this.kingsLocation = kingsLocation;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Player)) return false;
		Player other = (Player) o;
		if(!this.name.equals(other.name)) return false;
		if(!this.pieces.equals(other.pieces)) return false;
		if(this.points != other.points) return false;
		if(this.kingsLocation[0] != other.kingsLocation[0] 
				|| this.kingsLocation[1] != other.kingsLocation[1]) return false;
		return true;
	}

}
