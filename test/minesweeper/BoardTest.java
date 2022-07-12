/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * TODO: Description
 */
public class BoardTest {
    
    // TODO: Testing strategy
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO: Tests
    
    @Test
    public void testBoardInitialization() {
    	Board board = new Board(4, 3);
    	assertEquals("Board initializing correctly", "- - - - \n- - - - \n- - - - \n", board.toString());
    }
    
    @Test
    public void testBoardWithFile()
    {
    	Board board = new Board("board.txt");
    	System.out.println(board.getinternalBoard());
    	assertEquals("bomb at the right place", "  1 1 1 \n  1 B 1 \n  1 1 1 \n",board.getinternalBoard());
    }
}
