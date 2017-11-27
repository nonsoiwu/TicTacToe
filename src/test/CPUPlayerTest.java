package test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import model.Board;
import model.CPUPlayer;

public class CPUPlayerTest {
	
	CPUPlayer cpu = new CPUPlayer();
	Board b = new Board();
	
	@Test
	public void constructorTest() {
		String mName = cpu.toString().substring(0, cpu.toString().length()-3);
		char mChar = cpu.toString().charAt(cpu.toString().length()-1);
		assertEquals(mName.equals("watt"),true);
		assertEquals(mName.equals("notwatt"),false);
		
		assertEquals(mChar=='w',true);
		assertEquals(mChar=='v',false);
	}

	@Test
	public void askMarkTest() {
		Board b = new Board(); //the board's size is 3
		int size = 3;
		int randomMark = cpu.askMark(b, new Scanner(System.in));
		
		assertEquals(randomMark>=0&&randomMark<(size*size),true);
		assertEquals(randomMark<0&&randomMark>=(size*size),false);
		
		//int smartMark = 0;
	}
	
	@Test
	public void playerWonTest() {
		String winMessage = cpu.playerWon();
		assertEquals(winMessage.matches("Congrrrrratualtionzzz bzzt watt. Opt-t-timal outcome achieved bzzt."),true);
		assertEquals(winMessage.matches("You didnt win"),false);
	}
}
