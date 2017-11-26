package model;

import java.util.Random;
import java.util.Scanner;

public class CPUPlayer extends Player{
	
	public CPUPlayer(String n) {
		name = n;
	}
	
	@Override
	public int askMark(Board b, Scanner scanner) {
		if(AIWinMark()>=0){
			return AIWinMark();
		}
		
		if(AIDefenseMark()>=0){
			return AIDefenseMark();
		}
		
		int state = 0;
		int index = 0;
		Random rand = new Random();
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
	
	private int AIDefenseMark(){
		//returns -1 if there is no place to mark that will stop the player from winning
		return -1;
	}
	
	private int AIWinMark(){
		//returns -1 if there is no place to mark that will allow the player to win.
		return -1;
	}
	
	public String playerWon() {
		return "Congrrrrratualtionzzz bzzt "+name+". Opt-t-timal outcome achieved bzzt.";
	}
}
