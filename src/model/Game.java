package model;

import model.Board;
import java.util.Scanner;
import images.StringBank;

public class Game {
	Board board;
	Player[] turnManager = new Player[2]; 
	int turns = 0;
	private static final Scanner scanner = new Scanner(System.in);
	
	public Game(){	}
	
	private Game(Player p1, Player p2){
		turnManager[0] = p1;
		turnManager[1] = p2;
	}
	
	private Game(boolean AIans){
		turnManager[0] = Player.getPlayer("CPU1", true);
		turnManager[1] = Player.getPlayer("CPU 2",true);
	}
	
	/*
	 * 
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
	
	private void reset() {
		turns=0;
		board.fillBoard();
	}
	
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
		
	public static void main(String[] args){
		int gameState = 0;
		int nextState = -1;
		int index = -1;
		int won = -1;
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
					thisGame.chooseCharacters();
					nextState = 2;
					break;
				case 2:
					thisGame.board = Board.getBoard(thisGame.turnManager[0],thisGame.turnManager[1]);
					nextState = 3;
					break;
				case 3:
					System.out.println(nextState);
					System.out.println(thisGame.board.toString());
					//System.out.println(thisGame.board.toString());
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
					thisGame.reset();
					System.out.println("Game has been reset.");
					nextState = 2;
					break;
				case 5:
					System.out.println("Game has been terminated.\n");
					nextState = 0;
					break;
				case 6:
					System.out.println(nextState);
					thisGame.board.mark(thisGame.turns%2, index);
					System.out.println(thisGame.board.toString());
					System.out.println(thisGame.board.rawBoard());
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
