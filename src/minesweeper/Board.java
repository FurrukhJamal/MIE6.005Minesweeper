/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * TODO: Specification
 */
public class Board {
    private final List<List<Square>> Board = Collections.synchronizedList(new ArrayList<List<Square>>());
    private int sizeRows;
    private int sizeColumns;
    private final List<List<Square>> internalBoard = Collections.synchronizedList(new ArrayList<List<Square>>());
    private int numberOfBombs;
    private final Set<Tuple> bombs = new HashSet<>();
    //Thread safety argument
    	/*this is thread safe cuz Board and internal Board are final plus they have been encapsulated by a synchronized list
    	 * and there is no direct reference to the thread unsafe list inside the wrappers, and every mutation to the board is done via synchronized method*/
    
    public Board(int sizeColumns, int sizeRows) {
    	this.sizeRows = sizeRows;
    	this.sizeColumns = sizeColumns;
    	
    	//numberOfBombs = new Random().nextInt(this.sizeRows * this.sizeColumns);
    	numberOfBombs = (int)((this.sizeColumns * this.sizeRows) * 0.25);
    	
    	this.initailizeBombs();
    	//System.out.println("Bombs are at:" +this.bombs);
    	
    	this.initializeBoard();
    	
    	/*Test*/
    	//this.bombs.add(new Tuple(0, 1));
    	//this.bombs.add(new Tuple(2, 3));
    	/**/
    	
    	this.setupBombs();   	
    	
    	
    	for (int y = 0; y < this.sizeRows; y++)
    	{
    		for(int x = 0; x < this.sizeColumns; x++)
    		{
    			Square square = Board.get(y).get(x);
    			if(!square.hasBomb())
    			{
    				//System.out.println("Coordinates for placed bombs:" +square.getLocation());
    				this.getneighborCount(square.getX(), square.getY(), square);
    			}
    			
    		}
    	}
    	
    	//this.internalBoard = this.cloneBoard();
    	this.cloneBoard();
    	
    }
    
    
    public Board(String filename)
    {
    	
    	try
    	{
    			//File file = new File("board.txt");
    			//System.out.println(file.getAbsolutePath());
    			Path filePath = Paths.get("/Users", "furru", "eclipse-workspace", "ps2-minesweeper", "src", filename);
    			//System.out.println(filePath.toAbsolutePath());
    			//FileReader fileReader = new FileReader( filePath);
    			BufferedReader bufferedReader = Files.newBufferedReader(filePath);
    			//BufferedReader bufferedReader = new BufferedReader(fileReader);
    			String [] firstLine = bufferedReader.readLine().split(" ");
    			int sizeCols = Integer.valueOf(firstLine[0]);
    			int sizeRows = Integer.valueOf(firstLine[1]);
    			
    			String Line;
    			int rowCounter = 0;
    			List<Tuple> listofBombs = Collections.synchronizedList( new ArrayList<>());
    			while((Line = bufferedReader.readLine()) != null)
    			{
    				String [] line = Line.split(" ");
    				boolean contains = Arrays.stream(line).anyMatch("1"::equals);
    				if(contains)
    				{
    					int colCounter = 0;
    					for(String valOfSquare : line)
    					{
    						System.out.println("Square Value:" +valOfSquare);
    						if(valOfSquare.equals("1"))
    						{
    							//System.out.println("inside if");
    							//System.out.println("RowNumber: " +rowCounter+ " " + "ColNumber: " + colCounter);
    							listofBombs.add(new Tuple(rowCounter, colCounter));
    							//System.out.println("listofBombs: " +listofBombs);
    						}
    						
    						colCounter++;
    					}
    				}
    				
    				
    				rowCounter++;
    			}
    			
    			//System.out.println("listofBombs: " +listofBombs);
    			this.bombs.addAll(listofBombs);
    			
    			this.sizeColumns = sizeCols;
    			this.sizeRows = sizeRows;
    			
    			this.initializeBoard();
    			
    			this.setupBombs();
    			   			
    			
    			for (int y = 0; y < this.sizeRows; y++)
    	    	{
    	    		for(int x = 0; x < this.sizeColumns; x++)
    	    		{
    	    			Square square = Board.get(y).get(x);
    	    			if(!square.hasBomb())
    	    			{
    	    				//System.out.println("Coordinates for placed bombs:" +square.getLocation());
    	    				this.getneighborCount(square.getX(), square.getY(), square);
    	    			}
    	    			
    	    		}
    	    	}
    	    	
    	    	//this.internalBoard = this.cloneBoard();
    	    	this.cloneBoard();
    			
    			
    			bufferedReader.close();
    			
    			
    	
    	
    			
    	
    		
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private synchronized void initializeBoard() {
    	for(int i = 0; i < sizeRows; i++) {
    		List<Square> row = Collections.synchronizedList( new ArrayList<Square>());
    		for(int j = 0; j < sizeColumns; j++) {
    			Square square = new Square(i, j);
    			row.add(j, square);
    		}
    		Board.add(i, row);
    	}
    }
    
    
    private synchronized void updateNeigbourCount() {
    	for (int y = 0; y < this.sizeRows; y++)
    	{
    		for(int x = 0; x < this.sizeColumns; x++)
    		{
    			Square square = Board.get(y).get(x);
    			if(!square.hasBomb())
    			{
    				//System.out.println("Coordinates for placed bombs:" +square.getLocation());
    				this.getneighborCount(square.getX(), square.getY(), square);
    			}
    			
    		}
    	}
    }
    
    private synchronized void setupBombs() {
    	for(Tuple bombCoord : this.bombs)
    	{
    		//System.out.println(bombCoord.toString());
    		//populate the bombs
    		System.out.println("Tuple in for each of Tuple:" +bombCoord);
    		Square square = Board.get(bombCoord.getY()).get(bombCoord.getX());
    		System.out.println("Coordinates of bomb are:" +square.getLocation());
    		System.out.println("x: " +square.getY());
    		System.out.println("y: " +square.getX());
    		square.setBomb();
    	}
    }
    
    
    public synchronized void getneighborCount(int rowNumber, int colNumber, Square square) {
    	System.out.println("Square in getneighbourCOunt: " +square.getLocation());
    	System.out.println("rowNumber: " +rowNumber);
    	System.out.println("colNumber: " +colNumber);
    	int minRow = Math.max(rowNumber - 1, 0);
    	int maxRow = Math.min(rowNumber + 1, this.sizeRows - 1);
    	
    	System.out.println("maxRow :" +maxRow);
    	System.out.println("minRow :" +minRow);
    	
    	int minCol = Math.max(colNumber - 1, 0);
    	int maxCol = Math.min(colNumber + 1, this.sizeColumns - 1);
    	
    	System.out.println("maxCol :" +maxCol);
    	System.out.println("minCol :" +minCol);
    	
    	int counter = 0;
    	for (int y = minRow ; y <= maxRow; y++)
    	{
    		for(int x = minCol; x <= maxCol; x++)
    		{
    			System.out.println("Neighboring: row: " +x + " col: " +y );
    			if(Board.get(y).get(x).hasBomb())
    			{
    				System.out.println("bomb at square(row: " +y+ " col: " +x);
    				square.neighborHasBomb();
    				counter += 1;
    			}
    		}
    	}
    }
    
    
    
    
    //public List<List<Square>> cloneBoard(){
    public synchronized void cloneBoard() {
    	//List<List<Square>> duplicateBoard = Collections.synchronizedList(new ArrayList<>());
    	
    	for(int i = 0; i < this.sizeRows; i++)
    	{
    		List<Square> row = Collections.synchronizedList( new ArrayList<>());
    		for(int j = 0; j < this.sizeColumns; j++)
    		{
    			
    			row.add(j, Board.get(i).get(j).cloneSquare());
    			//setting its status to dug
    			row.get(j).diggThisSquare();
    			
    		}
    		this.internalBoard.add(i, row);
    	}
    	
    	//return duplicateBoard;
    	
    }
    
    
    
    
    
    
    public synchronized void initailizeBombs() {
    	while(true)
    	{
    		int randomRow = new Random().nextInt(sizeRows);
    		int randomColumn = new Random().nextInt(sizeColumns);
    		Tuple bombCoordinate = new Tuple(randomRow, randomColumn);
    		bombs.add(bombCoordinate);
    		if(bombs.size() == this.numberOfBombs) {
    			break;
    		}
    	}
    }
    
    
    
    /*Represent the current board as a string*/
    @Override
    public String toString() {
    	String board = "";
    	for(int i = 0; i < sizeRows; i++) {
    		
    		for(int j = 0; j < sizeColumns; j++) {
    			board += Board.get(i).get(j).toString() + " ";
    			//System.out.println("board:" +board);
    		}
    		
    		board += "\r\n";
    	}
    	
    	return board;
    }
    
    public String getinternalBoard() {
    	String board = "";
    	for(int i = 0; i < sizeRows; i++) {
    		
    		for(int j = 0; j < sizeColumns; j++) {
    			board += this.internalBoard.get(i).get(j).toString() + " ";
    			//System.out.println("board:" +board);
    		}
    		
    		board += "\n";
    	}
    	
    	return board;
    }
    
    public synchronized String digSquare(int rowNumber, int colNumber)
    {
    	
    	Square square = this.Board.get(rowNumber).get(colNumber);
    	if(square.hasBomb())
    	{
    		square.removeBomb();
    		this.updateNeigbourCount();
    		//tell user of a bomb
    		return "BOOM!\r\n"; 
    	}
    	else
    	{
    		if(square.getStatus() == "untouched")
    		{
    			//square.diggThisSquare();
    			//this.digAllNeighbors(rowNumber, colNumber);
    			this.checkAllSixNeighbors(rowNumber, colNumber);
    			
    		}
    		
    	}
    	this.cloneBoard();
    	return "";
    }
    
    
    private void checkAllSixNeighbors(int rowNum, int colNum) {
    	Square square = this.Board.get(rowNum).get(colNum);
        System.out.println("square in digallneibhors: " +square.getLocation());
        if(square.getStatus() == "dug")
        {
        	return ;
        } 
        if(!square.hasBomb())
        {
        	System.out.println("inside other condition");
        	//square.diggThisSquare();
        	int minRow = Math.max(rowNum - 1, 0);
        	int maxRow = Math.min(rowNum + 1, this.sizeRows - 1);
        	
        	int minCol = Math.max(colNum - 1, 0);
        	int maxCol = Math.min(colNum + 1, this.sizeColumns - 1);
        	
        	//for recursion
        	//int counter = 0;
        	//List<Integer> flag = new ArrayList<>();
        	boolean bombInNeighbors = false;
        	
        	for (int y = minRow ; y <= maxRow; y++)
        	{
        		for(int x = minCol; x <= maxCol; x++)
        		{
        			Square sq = this.Board.get(y).get(x);
    				System.out.println(sq.getLocation());
        			
        			if(this.Board.get(y).get(x).hasBomb())
        			{
        				
        				bombInNeighbors = true;
        			}
        			
        		}
        	}
        	
        	if(bombInNeighbors)
        	{
        		square.diggThisSquare();
        	}
        	else
        	{
        		square.diggThisSquare();
        		for (int y = minRow ; y <= maxRow; y++)
            	{
            		for(int x = minCol; x <= maxCol; x++)
            		{
            			this.checkAllSixNeighbors(y, x);            			
            		}
            	}
        	}
        }
    }
    
    
    public void digAllNeighbors(int rowNumber, int colNumber) {
    Square square = this.Board.get(rowNumber).get(colNumber);
    System.out.println("square in digallneibhors: " +square.getLocation());
    if(square.getStatus() == "dug")
    {
    	return ;
    } 
    if(!square.hasBomb())
    {
    	
    	System.out.println("rowNumber: " +rowNumber);
    	System.out.println("colNumber: " +colNumber);
    	square.diggThisSquare();
    	int minRow = Math.max(rowNumber - 1, 0);
    	int maxRow = Math.min(rowNumber + 1, this.sizeRows - 1);
    	
    	System.out.println("maxRow :" +maxRow);
    	System.out.println("minRow :" +minRow);
    	
    	int minCol = Math.max(colNumber - 1, 0);
    	int maxCol = Math.min(colNumber + 1, this.sizeColumns - 1);
    	
    	System.out.println("maxCol :" +maxCol);
    	System.out.println("minCol :" +minCol);
    	//for recursion
    	//int counter = 0;
    	//List<Integer> flag = new ArrayList<>();
    	
    	for (int y = minRow ; y <= maxRow; y++)
    	{
    		for(int x = minCol; x <= maxCol; x++)
    		{
    			//counter++;
    			this.digAllNeighbors(y, x); 
    			
    		}
    	}
    	
    	
    	
    }
    //square.diggThisSquare();
    //return 0;
    }
    
    public synchronized void toggleFlagASquare(int rowNumber, int colNumber)
    {
    	Square square = this.Board.get(rowNumber).get(colNumber);
    	square.toggleFlag();
    }
    
    
    
    
    public int getRowsSize() {
    	return this.sizeRows;
    }
    
    public int getColsSize() {
    	return this.sizeColumns;
    }
    
    public synchronized Square getSquare(int row, int col)
    {
    	return this.Board.get(row).get(col);
    }
    public static void main(String [] args) {
//    	System.out.println("Main started");
//    	Board testBoard = new Board(4, 4);
//    	System.out.println(testBoard.toString());
//    	System.out.println("-----------------");
//    	System.out.println("Internal Board");
//    	System.out.println(testBoard.getinternalBoard());
//    	testBoard.digSquare(0, 0);
//    	testBoard.flagASquare(0, 3);
//    	System.out.println("Board after one square dug");
//    	System.out.println("---------------------");
//    	System.out.println(testBoard.toString());
    	
    	/*Loading board from a file*/
    	Board board = new Board("board.txt");
    	System.out.println("hello" == "hello");
    }
    
    
    // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    
    // TODO: Specify, test, and implement in problem 2
    
}



