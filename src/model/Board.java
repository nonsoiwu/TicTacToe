package model;

public class Board {
	final int size;
	char p1;
	char p2;
	int[] board;
	
	public Board(){
		size = 3;
		p1 = 'x';
		p2 = 'o';
		board = new int[size*size];
	}
	
	public Board(int s){
		size = s;
		p1 = 'x';
		p2 = 'o';
		board = new int[size*size];
	}
	
	public Board(int s, char c1, char c2){
		size = s;
		p1 = c1;
		p2 = c2;
		board = new int[size*size];
	}
	
	public void printGrid(){
		for(int s=0; s<size; s++){
			System.out.print("-");
		}
		
		
		
		for(int s=0; s<size; s++){
			System.out.print("-");
		}
	}
	
	public void askMark(){
		
	}
	
	public int checkWin(){
		//-1 for no one, 0 for player 1 and 1 for player 1
		int placeholder = 7;
		return placeholder;
	}
	
}
