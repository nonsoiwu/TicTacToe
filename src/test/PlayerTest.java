package test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import model.Board;
import model.CPUPlayer;
import model.Player;
import model.QuitException;
import model.ResetException;

public class PlayerTest {
	
	Player mock = new Player();
	
	@Test
	public void constructorTest() {
		String mName = mock.toString().substring(0, mock.toString().length()-3);
		char mChar = mock.toString().charAt(mock.toString().length()-1);
		assertEquals(mName.equals("mock"),true);
		assertEquals(mName.equals("notmock"),false);
		
		assertEquals(mChar=='m',true);
		assertEquals(mChar=='n',false);
	}

	@Test
	public void getPlayerTest() {
		Player humanPlayer = Player.getPlayer("thisPlayer",false);
		assertEquals(humanPlayer instanceof Player,true);
		assertEquals(humanPlayer instanceof CPUPlayer,false);
		
		Player cpuPlayer = Player.getPlayer("thatPlayer",true);
		assertEquals(cpuPlayer instanceof CPUPlayer,true);
	}

	@Test
	public void askMarkTest() {
		Board b = new Board(); //the board's size is 3
		int size = 3;
		int mark = -1;
		boolean eCaught = false;
		try {
			mark = mock.askMark(b, new Scanner(System.in));
		}catch(Exception e){
			eCaught = true;
			if(e instanceof QuitException){
				System.out.println("\nSuccessfully caught QuitException\n");
			}else if(e instanceof ResetException){
				System.out.println("\nSuccessfully caught ResetException\n");
			}
		}
			if(!eCaught){
				assertEquals(mark>=0&&mark<(size*size),true);
				assertEquals(mark<0&&mark>=(size*size),false);	
			}
	}
	
	@Test
	public void playerWonTest() {
		String winMessage = mock.playerWon();
		String name = "mock";
		assertEquals(winMessage.matches("^Congratulations "+name+ "! "),true);
		assertEquals(winMessage.matches("You didnt win"),false);
	}
}
