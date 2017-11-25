package model;

import java.util.Scanner;

public class Player {
	String name;
	char character;
	
	public Player(){
		name = "mock";
		character = 'm';
	}
	
	private Player(String n){
		name = n;
		}
	
	public static Player getPlayer(String n, boolean cpu){
		if(cpu){
			return new CPUPlayer(n);
			}
		return new Player(n);
		}
	
	public Player(boolean cpu){
		name = "Player";
		if(cpu){
			name = "CPU";
			}
		}
	
	public int askMark(Board b, Scanner scanner){
		String rows = "abcde".substring(0, b.size);
		String numbers = "12345".substring(0, b.size);
		int state = 0;
		
		System.out.println("Where will you mark? "+"(Rows:"+rows+" and Columns:"+numbers+" in the form of 'a2')");

		while(true){
			String input = scanner.nextLine();
			if(!input.matches("["+rows+"]["+numbers+"]")){
				state = 1;
			}else{
				if(!(b.board[b.size*(rows.indexOf(input.charAt(0)))+numbers.indexOf(input.charAt(1))]==-1)){
					state = 2;
				}
			}
			switch(state){
				case 0:
					return b.size*(rows.indexOf(input.charAt(0)))+numbers.indexOf(input.charAt(1));
				case 1:
					System.out.println("That is not a valid input.");
					state = 0;
					break;
				case 2:
					System.out.println("That slot is already filled.");
					state = 0;
					break;
			}
		}
	}
}
