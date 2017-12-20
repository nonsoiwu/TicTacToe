package model;

import model.Board;
import java.util.Scanner;
import images.StringBank;

public class Game {
	/**The {@code Board} class that belongs to {@code Game}*/
	Board board;
	
	/**An array that holds the two {@code Player} classes of the game*/
	Player[] turnManager = new Player[2]; 
	
	/**An int that indicates what turn the Tic-Tac-Toe is on*/
	int turns = 0;
	
	/**{@code Scanner} that is meant to always be open.
	 * It was made to accommodate {@code Game.inquire()}
	 * */
	private static final Scanner scanner = new Scanner(System.in);
	
	/**
	 * No arg constructor for class {@code Game}.
	 * <p>  {code board} is uninitialized.<br>
	 * 		{code turnManager} is uninitialized.<br>
	 * 		{code turns} value is set to 0.<br>
	 * 		{code scanner} is a new Scanner.
	 */
	public Game(){
		
	}
	
	/**
	 * Custom constructor for class {@code Game}
	 * @param p1 A {@code Player} that is added to {@code turnManager} as Player 1
	 * @param p2 A {@code Player} that is added to {@code turnManager} as Player 2
	 */
	private Game(Player p1, Player p2){
		turnManager[0] = p1;
		turnManager[1] = p2;
	}
	
	/**
	 * Custom constructor for class {@code Game}
	 * Based on parameter AIans, creates an automated game played by AI
	 * @param AIans A {@code boolean}. If true, a new {@code Game} is created with two {@code CPUPlayers}
	 * 				else, it will act as the No-Arg constructor for {@code Game}
	 */
	private Game(boolean AIans){
		if(AIans) {
			turnManager[0] = Player.getPlayer("CPU1", true);
			turnManager[1] = Player.getPlayer("CPU 2",true);
			return;
		}
	}
	
	/**
	 * A factory method that uses a {@code scanner} in order to retrieve a {@code Game}
	 * @return Based on the answer to the inquiry a {@code Game} with varying players is created
	 * 		   <p> 0 : Player vs. Player
	 * 		   <p> 1 : Player vs. CPUPlayer
	 * 		   <p> 2 : CPUPlayer vs. CPUPlayer
	 */
	public static Game getGame() {
		int state = 0;
		
		System.out.println("Choose a game type you would like to play(input):");
		System.out.println("0: 1Player vs. 2Player\n"
				+ "1: 1Player vs. CPU\n"
				+ "2: CPU vs. CPU");
		
		while(true){
			String input = scanner.nextLine();
			if(!input.matches("[0-2]")){
				state = 1;
			}
			
			switch(state){
				case 0:
					switch(Integer.valueOf(input)){
						case 0:
							return new Game(Player.getPlayer("Player 1",false), Player.getPlayer("Player 2",false));
						case 1:
							return new Game(Player.getPlayer("Player 1", false), Player.getPlayer("CPU",true));
						case 2:
							return new Game(true);
					}
				case 1:
					System.out.println("'"+input+"' is not a valid input.");
					state = 0;
					break;
			}
		}
	}
	
	/**
	 * 'Resets' the {@code Game} as if it were new.
	 * <p> {@code turns} is set to 0;
	 * <p> {@code board} is 'cleared' (All values are set to -1)
	 */
	private void reset() {
		turns=0;
		board.fillBoard();
	}
	
	/**
	 * Makes sure the user inputs an expression that is specified by {@code regex}
	 * @param regex A regular expression to which the input is to be matched
	 * @return A {@code String} that matches {@code regex}
	 * @see java.lang.String.matches
	 */
	public static String inquire(String regex){
		int state = 0;
		while(true){
			String input = scanner.nextLine();
			if(!input.matches(regex)){
				state = 1;
			}
			
			switch(state){
				case 0:
					return input;
				case 1:
					System.out.println("'"+input+"' is not a valid input.");
					state = 0;
					break;
			}
		}
	}
	
	/**
	 * Asks the user if they would like to choose characters for each player.
	 * <p> If input is 'n', the player's characters are set to X and O.
	 * <p> If input is 'y', the user is allowed to choose a custom character for each player
	 */
	public void chooseCharacters() {
		System.out.println("Would you like to choose custom characters? (y or n)\n"
				+ "(Choosing n results in default X and O)");
		String answer = Game.inquire("[yn]");
		if(answer.equals("n")){
			turnManager[0].character = 'X';
			turnManager[1].character = 'O';
		}else if(answer.equals("y")){
			for(Player p:turnManager){
				System.out.println("Choose a character for "+p.name+": (1 character/Uppercase/Lowercase/Alphabetical)");
				p.character = Game.inquire("[a-zA-Z]").charAt(0);
				System.out.println();
			}
		}
		return;
	}
		
	/**
	 * This is the main method for class {@code Game}
	 * It plays the game of TicTacToe on console for the user
	 * @param args
	 */
	public static void main(String[] args){
		int gameState = 0; //The state that the game is in
		int nextState = -1; //Although this could just be gameState, it is made for readability
		int index = -1; //Where the Player marks on the board
		int won = -1; //Indicates if, and who won the game
		Game thisGame = new Game();
		StringBank.opening();
		
		while(true){
			switch(gameState){
				case 0:
					//Player chooses what type of game they want to play
					thisGame = Game.getGame();
					nextState = 1;
					break;
				case 1:
					//Choose the characters that each Player has
					thisGame.chooseCharacters();
					nextState = 2;
					break;
				case 2:
					//Create the Board
					thisGame.board = Board.getBoard(thisGame.turnManager[0],thisGame.turnManager[1]);
					nextState = 3;
					break;
				case 3:
					//Asks the user for an input. The user can mark the board, reset or quit the game
					//System.out.println(nextState);
					System.out.println(thisGame.board.toString());
					System.out.println(thisGame.turnManager[thisGame.turns%2].name+"'s turn!");
					try {
						index = thisGame.turnManager[thisGame.turns%2].askMark(thisGame.board, scanner);
					}catch(Exception e){
						if(e instanceof ResetException){
							nextState = 4;
							break;
						}else if(e instanceof QuitException){
							nextState = 5;
							break;
						}
					}
					nextState = 6;
					break;
				case 4:
					//Resets the game, sends the user back Board creation
					thisGame.reset();
					System.out.println("Game has been reset.");
					nextState = 2;
					break;
				case 5:
					//Quits the game, exits the main function
					System.out.println("Game has been terminated.\n");
					nextState = 0;
					return;
				case 6:
					//The Player marks the board
					try {
						thisGame.board.mark(thisGame.turns%2, index);
					}catch(InvalidMarkException e) {
						e.printStackTrace();
					}
					System.out.println(thisGame.board.toString());
					won = thisGame.board.checkWin(index);
					if(won>=0){
						nextState = 7;
					}else{
						if(!thisGame.board.isFull()){
							nextState = 3;
							thisGame.turns++;
						}else{
							nextState = 8;
						}
					}
					break;
				case 7:
					//Some one Friggin won
					System.out.println(thisGame.turnManager[thisGame.turns%2].playerWon());
					nextState = 9;
					break;
				case 8:
					//No one won
					System.out.println("The board is full. No one won :(");
					nextState = 9;
					break;
				case 9:
					//Play Again
					System.out.println("Would you like to play again? (y/n)");
					String answer = Game.inquire("[yn]");
					if(answer.equals("y")){
						nextState = 2;
						break;
					}else if(answer.equals("n")){
						nextState = 5;
						break;
					}
			}
			gameState = nextState;
		}
	}
	
	
}
