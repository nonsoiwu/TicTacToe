package model;

import java.util.Random;
import java.util.Scanner;

/**
 * This CPUPlayer class is a sub class of Player. It carries its own behavior for retrieving a 
 * mark and celebrates victory to a different tune. Methods {@code askMark()} and 
 * {@code playerWon()} are polymorphic.
 * @author Nonso Iwu
 *
 */
public class CPUPlayer extends Player{
	
	/**
	 * A custom {@code CPUPlayer} constructor
	 * @param n A {@code String} whose value is set to name
	 */
	public CPUPlayer(String n) {
		name = n;
	}
	
	/**
	 * This is the {@code CPUPlayer}'s own {@code askMark} method. This method utilizes a
	 * an AI that will choose an index that can produce a victory or stop the opponents 
	 * victory rather than being ineffective
	 * @param b a {@code Board} that is to be read be the method
	 * @param scanner This has to be a scanner without its {@code close()} method acted on it
	 * @return an {@code int} that indicates an index given to {@code Board}
	 */
	@Override
	public int askMark(Board b, Scanner scanner) {
		//Winning takes priority over Defense and Random mark
		if(AIWinMark(b)>=0){
			return AIWinMark(b);
		}
		
		//Defending takes priority over Random mark
		if(AIDefenseMark(b)>=0){
			return AIDefenseMark(b);
		}
		
		int state = 0;
		int index = 0;
		Random rand = new Random();
		
		//This while loop makes sure that the slot generated hits an 'empty' slot
		while(true){
			switch(state){
				case 0:
					index = rand.nextInt((b.size*b.size));
					if(b.board[index]<0){
						state = 2;
						System.out.println(index);
					}
					break;
				case 1:
					state = 0;
					break;
				case 2:
					return index;
			}
		}
	}
	
	/**
	 * This method indicates whether or not a mark will prevent the opposing {@code Player}
	 * from winning the game
	 * @return -1 : No suitable index is available 
	 * 		    <p> 0 to ((size^2)-1): an index
	 */
	private int AIDefenseMark(Board b){
		//returns -1 if there is no place to mark that will stop the player from winning
		return -1;
	}
	
	/**
	 * This method indicates whether or not a mark will allow this {@code CPUPlayer} 
	 * to win the game
	 * @return -1 : No suitable index is available
	 * 		    <p> 0 to ((size^2)-1): an index
	 */
	private int AIWinMark(Board b){
		//returns -1 if there is no place to mark that will allow the player to win.
		return -1;
	}
	
	/**
	 * This method overrides the {@code Player} method
	 * @return A {@code String} that celebrates the {@code CPUPlayer} victory
	 */
	@Override
	public String playerWon() {
		return "Congrrrrratualtionzzz bzzt "+name+". Opt-t-timal outcome achieved bzzt.";
	}
}
