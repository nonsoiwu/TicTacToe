package model;

import java.util.Random;
import java.util.Scanner;

/**
 * The Player class represents a Player for the Tic-Tac-Toe game. This class is more or less
 * a means of retrieving 'marks' and referencing characters onto the board 
 * @author Nonso Iwu
 *
 */
public class Player {
	/**Name of the player*/
	String name;
	
	/**The character that is marked for the player*/
	char character;
	
	/**
	 * This is a mock {@code Player} constructor. {@code name} is set to
	 * "mock" and {@code character} is set to 'm'. Used for testing. Not useful for
	 * gameplay.
	 */
	public Player(){
		name = "mock";
		character = 'm';
	}
	
	/**
	 * A custom {@code Player} constructor that sets the parameter {@code n} is set to name
	 * @param n A {@code String} whose value is set to name
	 */
	private Player(String n){
		name = n;
		}
	
	/**
	 * A factory method that produces a {@code Player} or {@code CPUPlayer}
	 * @param name A {@code String} that will become the name of the {@code Player}
	 * @param cpu A {@code boolean} that determines which player class is returned
	 * @return If {@code cpu} is true, a {@code CPUPlayer} is returned, a {@code Player}
	 * 		   is returned otherwise
	 */
	public static Player getPlayer(String name, boolean cpu){
		if(cpu){
			return new CPUPlayer(name);
			}
		return new Player(name);
		}
		
	/**
	 * Produces a string containing the player's name and character
	 * @return A {@code String} representation of the Player
	 */
	public String toString() {
		return name+", "+character;
	}
	
	/**
	 * Asks the {@code Player} for an input (through an Input Stream) and converts that to an int.
	 * The input is two characters, the first alphabetical and the other numerical that show the
	 *  rows and the column respectively.
	 * <p> For example: a Tic-Tac-Toe game of size 3, has rows a,b,c and columns 1,2,3. In order
	 * to choose the second slot on the first row, the input would be 'a2'.
	 * @param board The {@code Player}'s options for an answer are limited based on this {@code Board}
	 * @param scanner This has to be a scanner without its {@code close()} method acted on it
	 * @param PlayerInt An {@code int} associated with the {@code Player}
	 * @return An {@code int} that changes a correct input into an index used by the {@code board}
	 * 		   attribute in the {@code Board} class
	 * @throws QuitException Thrown if quit is passed as an input
	 * @throws ResetException Thrown if reset is passed as an input
	 */
	public int askMark(Board board, Scanner scanner, int PlayerInt) throws QuitException, ResetException{
		String rows = "abcde".substring(0, board.size); //Used to get the row input requirement
		String columns = "12345".substring(0, board.size); //Used to get the column input requirement
		String input;
		int state = 0;
		
		System.out.println("Where will you mark? "+"(Rows:"+rows+" and Columns:"+columns+" in the form of 'a2')");

		while(true){
			input = scanner.nextLine();
			
			if(input.matches("quit")){
				//Indicates that the player 'quit'
				throw new QuitException();
			}else if(input.matches("reset")){
				//Indicates that the player 'reset'
				throw new ResetException();
			}else if(!(input.matches("["+rows+"]["+columns+"]"))){
				state = 1;
			}else{
				//That value represents the slot that was chosen by the player
				if(!(board.board[board.size*(rows.indexOf(input.charAt(0)))+columns.indexOf(input.charAt(1))]==-1)){
					state = 2;
				}
			}
			
			switch(state){
				case 0:
					return board.size*(rows.indexOf(input.charAt(0)))+columns.indexOf(input.charAt(1));
				case 1:
					//Invalid Input
					System.out.println("That is not a valid input.");
					break;
				case 2:
					//Slot is already filled
					System.out.println("That slot is already filled.");
					break;
			}
			state = 0;
		}
	}
	
	/**
	 * Provides a string that celebrates the player's victory. The message is random!
	 * @return A String saying "Congratulations 'Player' " followed by a random compliment
	 */
	public String playerWon() {
		String message = "Congratulations "+name+ "! ";
		Random rand = new Random();
		switch(rand.nextInt(3)) {
			case 0:
				message+="Can you stop now? You're too good at this!";
				break;
			case 1:
				message+="You OBLITERATED the opponent!";
				break;
			case 2:
				message+="You sent them to the Shadow Realm";
		}
		return message;
	}
}
