package test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import model.Board;
import model.CPUPlayer;
import model.InvalidMarkException;

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
		int size = 3; //The board's size is 3
		int playerInt = 0; //This means that the player is player 1
		int randomMark = cpu.askMark(b, new Scanner(System.in),playerInt);
		
		assertEquals(randomMark>=0&&randomMark<(size*size),true);
		assertEquals(randomMark<0&&randomMark>=(size*size),false);
		
		//Test for AIDefenseMark
		try {
			b.mark(1, 0);
			b.mark(1, 6);
		}catch(InvalidMarkException e) {
			
		}
		
		assertEquals(cpu.askMark(b, new Scanner(System.in), playerInt),3);
		
		//Test for AIWinMark
		try {
			b.mark(0, 2);
			b.mark(0, 8);
		}catch(InvalidMarkException e) {
			
		}
		
		assertEquals(cpu.askMark(b, new Scanner(System.in), playerInt),5);
	}
	
	@Test
	public void playerWonTest() {
		String winMessage = cpu.playerWon();
		assertEquals(winMessage.matches("Congrrrrratualtionzzz bzzt watt. Opt-t-timal outcome achieved bzzt."),true);
		assertEquals(winMessage.matches("You didnt win"),false);
	}
}
