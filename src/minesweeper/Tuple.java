package minesweeper;

import java.util.HashSet;
import java.util.Set;

public class Tuple  {
	private int x;
	private int y;
	
	public Tuple(int row, int col) {
		this.x = col;
		this.y = row;
	}
	
	public String toString() {
		String tup = "";
		tup += "(" + this.y + "," + this.x + ")";
		return tup;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public static void main(String [] args) {
		Tuple test = new Tuple(4, 90);
		
		System.out.println(test.toString());
		Tuple test2 = new Tuple(4, 90);
		System.out.println(test2.equals(test));
		
		Set<Tuple> testSet = new HashSet<>();
		testSet.add(test);
		testSet.add(test2);
		System.out.println("Set is:" +testSet);
	}
}