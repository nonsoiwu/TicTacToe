package test;

import static org.junit.Assert.*;
import org.junit.Test;

import model.Board;
import model.InvalidMarkException;
import model.Player;

public class BoardTest{
	Board bTest3 = new Board();
	Board bTest4 = new Board(4);
	Board bTest5 = new Board(5);
	
	@Test
	public void getBoardTest() {
		Board gb = Board.getBoard(new Player(), new Player());
		String grid = gb.toString();
		
		String g3 = "   |   |   \n"
				  + "-----------\n"
				  + "   |   |   \n"
				  + "-----------\n"
				  + "   |   |   \n";
		String g4 = "   |   |   |   \n"
				  + "---------------\n"
				  + "   |   |   |   \n"
				  + "---------------\n"
				  + "   |   |   |   \n"
				  + "---------------\n"
				  + "   |   |   |   \n";
		String g5 = "   |   |   |   |   \n"
				  + "-------------------\n"
				  + "   |   |   |   |   \n"
				  + "-------------------\n"
				  + "   |   |   |   |   \n"
				  + "-------------------\n"
				  + "   |   |   |   |   \n"
				  + "-------------------\n"
				  + "   |   |   |   |   \n";
		
		assertEquals(((grid.equals(g3))||(grid.equals(g4))||(grid.equals(g5))),true);
	}
	
	@Test
	public void markTest() {
		try {
			bTest3.mark(0, 4);
		}catch(InvalidMarkException e) {
			
		}
		String rb = bTest3.rawBoard();
		String check = "[-1 -1 -1 -1 0 -1 -1 -1 -1]";
		String check2 = "[-1 -1 -1 -1 -1 -1 -1 -1 -1]";
		assertEquals(rb.equals(check),true);
		assertEquals(rb.equals(check2),false);
	}
	
	@Test
	public void isFullTest() {
		try {
			bTest3.mark(0, 0);
			bTest3.mark(1, 1);
			bTest3.mark(0, 2);
			bTest3.mark(1, 3);
			bTest3.mark(0, 4);
			bTest3.mark(0, 5);
			bTest3.mark(1, 6);
			bTest3.mark(1, 7);
		}catch(InvalidMarkException e) {
			
		}
		assertEquals(bTest3.isFull(),false);
		
		try{
			bTest3.mark(1, 8);
		}catch(InvalidMarkException e) {
			
		}
		assertEquals(bTest3.isFull(),true);
		
	}
	
	@Test
	public void rawBoardTest() {
		String rb = bTest3.rawBoard();
		String check = "[-1 -1 -1 -1 -1 -1 -1 -1 -1]";
		String check2 = "[-1 -1 -1 -1 -1 -1 -1 -1 0]";
		assertEquals(rb.equals(check),true);
		assertEquals(rb.equals(check2),false);
	}
	
	@Test
	public void fillBoardTest() {
		try {
			bTest3.mark(0, 4);
			bTest3.mark(0, 5);
			bTest3.mark(1, 6);
			bTest3.mark(1, 7);
		}catch(InvalidMarkException e) {
			
		}
		String rbp = "[-1 -1 -1 -1 0 -1 -1 -1 -1]";
		bTest3.fillBoard();
		String rb = bTest3.rawBoard();
		String check = "[-1 -1 -1 -1 -1 -1 -1 -1 -1]";
		
		assertEquals(rb.equals(check),true);
		assertEquals(rb.equals(rbp),false);
	}
	
	@Test
	public void markPriority3Test() {
		assertEquals(bTest3.markPriority(0),1);
		assertEquals(bTest3.markPriority(1),0);
		assertEquals(bTest3.markPriority(2),2);
		assertEquals(bTest3.markPriority(3),0);
		assertEquals(bTest3.markPriority(4),3);
		assertEquals(bTest3.markPriority(5),0);
		assertEquals(bTest3.markPriority(6),2);
		assertEquals(bTest3.markPriority(7),0);
		assertEquals(bTest3.markPriority(8),1);
	}
	
	@Test
	public void markPriority4Test() {
		assertEquals(bTest4.markPriority(0),1);
		assertEquals(bTest4.markPriority(1),0);
		assertEquals(bTest4.markPriority(2),0);
		assertEquals(bTest4.markPriority(3),2);
		assertEquals(bTest4.markPriority(4),0);
		assertEquals(bTest4.markPriority(5),1);
		assertEquals(bTest4.markPriority(6),2);
		assertEquals(bTest4.markPriority(7),0);
		assertEquals(bTest4.markPriority(8),0);
		assertEquals(bTest4.markPriority(9),2);
		assertEquals(bTest4.markPriority(10),1);
		assertEquals(bTest4.markPriority(11),0);
		assertEquals(bTest4.markPriority(12),2);
		assertEquals(bTest4.markPriority(13),0);
		assertEquals(bTest4.markPriority(14),0);
		assertEquals(bTest4.markPriority(15),1);
	}
	
	@Test
	public void markPriority5Test() {
		assertEquals(bTest5.markPriority(0),1);
		assertEquals(bTest5.markPriority(1),0);
		assertEquals(bTest5.markPriority(2),0);
		assertEquals(bTest5.markPriority(3),0);
		assertEquals(bTest5.markPriority(4),2);
		assertEquals(bTest5.markPriority(5),0);
		assertEquals(bTest5.markPriority(6),1);
		assertEquals(bTest5.markPriority(7),0);
		assertEquals(bTest5.markPriority(8),2);
		assertEquals(bTest5.markPriority(9),0);
		assertEquals(bTest5.markPriority(10),0);
		assertEquals(bTest5.markPriority(11),0);
		assertEquals(bTest5.markPriority(12),3);
		assertEquals(bTest5.markPriority(13),0);
		assertEquals(bTest5.markPriority(14),0);
		assertEquals(bTest5.markPriority(15),0);
		assertEquals(bTest5.markPriority(16),2);
		assertEquals(bTest5.markPriority(17),0);
		assertEquals(bTest5.markPriority(18),1);
		assertEquals(bTest5.markPriority(19),0);
		assertEquals(bTest5.markPriority(20),2);
		assertEquals(bTest5.markPriority(21),0);
		assertEquals(bTest5.markPriority(22),0);
		assertEquals(bTest5.markPriority(23),0);
		assertEquals(bTest5.markPriority(24),1);
	}
	
	@Test
	/*
	 * Vertical Test
	 */
	public void verticalTest() {
		try {
			bTest3.mark(0, 1);
			bTest3.mark(0, 4);
			bTest3.mark(0, 7);
		}catch(InvalidMarkException e) {
			
		}
		System.out.print("Vertical test\n"+bTest3.toString());
		assertEquals(bTest3.checkWin(4),0);
	}
	
	@Test
	/*
	 * Horizontal Test
	 */
	public void horizontalTest() {
		try {
			bTest3.mark(0, 0);
			bTest3.mark(1, 1);
			bTest3.mark(0, 2);
			bTest3.mark(1, 3);
			bTest3.mark(0, 4);
			bTest3.mark(0, 5);
			bTest3.mark(1, 6);
			bTest3.mark(1, 7);
			bTest3.mark(1, 8);
		}catch(InvalidMarkException e) {
			
		}
		System.out.print("Horizontal test\n"+bTest3.toString());
		assertEquals(bTest3.checkWin(7),1);
	}
	
	@Test
	/*
	 * LDiagonal Test
	 */
	public void lDiagonalTest() {
		try {
			bTest4.mark(1, 0);
			bTest4.mark(1, 5);
			bTest4.mark(1, 10);
			bTest4.mark(1, 15);
		}catch(InvalidMarkException e) {
			
		}
		System.out.print("LDiagonal test\n"+bTest4.toString());
		assertEquals(bTest4.checkWin(5),1);
	}
	
	@Test
	/*
	 * RDiagonal Test
	 */
	public void rDiagonalTest(){
		try {
			bTest5.mark(0, 4);
			bTest5.mark(0, 8);
			bTest5.mark(0, 12);
			bTest5.mark(0, 16);
			bTest5.mark(0, 20);
		}catch(InvalidMarkException e) {
			
		}
		System.out.print("RDiagonal test\n"+bTest5.toString());
		assertEquals(bTest5.checkWin(8),0);
	}
	
	@Test
	public void checkWinTest() {
		try {
			bTest3.mark(0, 0);
			bTest3.mark(1, 1);
			bTest3.mark(0, 2);
			bTest3.mark(1, 3);
			bTest3.mark(0, 4);
			bTest3.mark(0, 5);
			bTest3.mark(1, 6);
			bTest3.mark(1, 7);
			bTest3.mark(1, 8);
		}catch(InvalidMarkException e) {
			
		}
		System.out.print("checkWin test\n"+bTest3.toString());
		assertEquals(bTest3.checkWin(0),-1);
		assertEquals(bTest3.checkWin(1),-1);
		assertEquals(bTest3.checkWin(2),-1);
		assertEquals(bTest3.checkWin(3),-1);
		assertEquals(bTest3.checkWin(4),-1);
		assertEquals(bTest3.checkWin(5),-1);
		assertEquals(bTest3.checkWin(6),1);
		assertEquals(bTest3.checkWin(7),1);
		assertEquals(bTest3.checkWin(8),1);
	}
}
