package model;

import model.Board;
import java.util.Scanner;
import images.StringBank;

public class Game {
	Board board;
	Player[] turnManager = new Player[2]; 
	int turns = 0;
	private static final Scanner scanner = new Scanner(System.in);
	
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
	public void gameOver() {
		
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
		String answer = Game.inquire("[y/n]");
		if(answer.equals("n")){
			turnManager[0].character = 'X';
			turnManager[1].character = 'O';
		}else if(answer.equals("y")){
			for(Player p:turnManager){
				System.out.println("Choose a character for "+p.name+": (1 character/Uppercase/Lowercase/Alphabet");
				p.character = Game.inquire("[a-zA-Z]").charAt(0);
				System.out.println();
			}
		}
		return;
	}
		
	public static void main(String[] args){
		int gameState = 0;
		StringBank.opening();
		
		//Player chooses what type of game they want to play
		Game thisGame = Game.getGame();
		thisGame.chooseCharacters();
		thisGame.board = Board.getBoard(thisGame.turnManager[0],thisGame.turnManager[1]);
		System.out.println(thisGame.board.toString());
		scanner.close();
		while(true){
			switch(gameState){
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				
			}
		}
	}
	
	
}
