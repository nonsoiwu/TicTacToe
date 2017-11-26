package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Board;
import model.Player;

public class BoardTest{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	Board bTest = new Board();
	Board bTest4 = new Board(4);
	Board bTest5 = new Board(5);
	
	@Test
	public void test() {
	}

	@Test
	/*
	 * Vertical Test
	 */
	public void verticalTest() {
		bTest.mark(0, 0);
		bTest.mark(0, 1);
		bTest.mark(1, 2);
		bTest.mark(1, 3);
		bTest.mark(0, 4);
		bTest.mark(1, 5);
		bTest.mark(0, 6);
		bTest.mark(0, 7);
		bTest.mark(1, 8);
		System.out.print("Vertical test\n"+bTest.toString());
		assertEquals(bTest.checkWin(4),0);
	}
	
	@Test
	/*
	 * Horizontal Test
	 */
	public void horizontalTest() {
		bTest.mark(0, 0);
		bTest.mark(1, 1);
		bTest.mark(0, 2);
		bTest.mark(1, 3);
		bTest.mark(0, 4);
		bTest.mark(0, 5);
		bTest.mark(1, 6);
		bTest.mark(1, 7);
		bTest.mark(1, 8);
		System.out.print("Horizontal test\n"+bTest.toString());
		assertEquals(bTest.checkWin(7),1);
	}
	
	@Test
	/*
	 * LDiagonal Test
	 */
	public void lDiagonalTest() {
		bTest4.mark(1, 0);
		bTest4.mark(1, 5);
		bTest4.mark(1, 10);
		bTest4.mark(1, 15);
		System.out.print("LDiagonal test\n"+bTest4.toString());
		assertEquals(bTest4.checkWin(5),1);
	}
	
	@Test
	/*
	 * RDiagonal Test
	 */
	public void rDiagonalTest(){
		bTest5.mark(0, 4);
		bTest5.mark(0, 8);
		bTest5.mark(0, 12);
		bTest5.mark(0, 16);
		bTest5.mark(0, 20);
		System.out.print("RDiagonal test\n"+bTest5.toString());
		assertEquals(bTest5.checkWin(8),0);
	}
}
