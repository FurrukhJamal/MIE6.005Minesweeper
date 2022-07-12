package minesweeper;

public class Square {
	private String state = "untouched";
	private int x;
	private int y;
	private boolean hasBomb = false;
	private int numberOfNeighboringBombs = 0;
	private boolean isFlagged = false;
	
	public Square(int xCoord, int yCoord) {
		this.x = xCoord;
		this.y = yCoord;
	}
	
	
	public Square cloneSquare() {
		Square square = new Square(this.getX(), this.getY());
		if(this.hasBomb)
		{
			square.setBomb();
		}
		
		if(this.numberOfNeighboringBombs > 0)
		{
			square.setNumberOfNeighbouringBombs(this.numberOfNeighboringBombs);
		}
		
		return square;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Tuple getLocation() {
		return new Tuple(this.x, this.y);
	}
	
	public void neighborHasBomb() {
		this.numberOfNeighboringBombs++;
	}
	
	
	public boolean hasBomb() {
		return this.hasBomb;
	}
	
	public void setBomb() {
		this.hasBomb = true;
	}
	
	public void removeBomb() {
		this.hasBomb = false;
	}
	public void toggleFlag() {
		this.isFlagged = !this.isFlagged;
	}
	
	public void diggThisSquare() {
		this.state = "dug";
	}
	
	public boolean checkFlagged()
	{
		return this.isFlagged;
	}
	
	public String getStatus() {
		return this.state;
	}
	
	
	public int getNumberOfNeighborBombs() {
		return this.numberOfNeighboringBombs;
	}
	@Override
	public String toString() {
		String square = "";
		if(this.state == "untouched" && !this.isFlagged)
		{
			square += "-";
		}
		else if(this.state == "untouched" && this.isFlagged)
		{
			square += "F";
		}
		else if(this.state == "dug" && this.hasBomb() && this.numberOfNeighboringBombs == 0)
		{
			//delete this condition later
			square += "B";
		}
		else if(this.state== "dug" && this.numberOfNeighboringBombs == 0)
		{
			square += " ";
		}
		else if(this.state == "dug" && this.numberOfNeighboringBombs != 0)
		{
			square += String.valueOf(numberOfNeighboringBombs);
		}
		
		
		return square;
	}
	
	public void setNumberOfNeighbouringBombs(int val)
	{
		this.numberOfNeighboringBombs = val;
	}
	
	@Override
	public boolean equals(Object that)
	{
		if(!(that instanceof Square))
		{
			return false;
		}
		
		Square thatSquare = (Square)that;
		return thatSquare.getLocation().equals(this.getLocation());
	}
	
	@Override
	public int hashCode() {
	   final int prime = 31;
	   int result = 1;
	   result = prime * result + this.x;
	   result = prime * result + this.y;
	   return result;
	}
	
}
