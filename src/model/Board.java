package model;

import java.util.Scanner;

public class Board {
	final int size;
	char p1;
	char p2;
	int[] board; //Filled with -1,0, and 1's. -1 means that the space is empty,
				 //0 indicates a mark from player 1 and 1 indicates a mark from
			  	 //player 2
	
	/**
	 * Simple constructor for Board object
	 * <p>
	 * 'size' is defaulted to 3 for a standard tic-tac-toe game, 
	 * 'p1' is defaulted to x, 
	 * 'p2' is defaulted to o,  
	 */
	public Board(){
		size = 3;
		p1 = 'x';
		p2 = 'o';
		board = new int[size*size];
		fillBoard();
	}
	
	/**
	 * Custom constructor for Board class
	 * <p>
	 * 'p1' is defaulted to x, 
	 * 'p2' is defaulted to o,  
	 * @param s int, value is set to size
	 */
	public Board(int s){
		size = s;
		p1 = 'x';
		p2 = 'o';
		board = new int[size*size];
		fillBoard();
	}
	
	/**
	 * Custom constructor for Board object
	 * @param s int, value is set to size
	 * @param c1 char, value is set to p1
	 * @param c2 char, value is set to p2
	 */
	public Board(int s, char c1, char c2){
		size = s;
		p1 = c1;
		p2 = c2;
		board = new int[size*size];
		fillBoard();
	}
	
	/**
	 * Displays board in the style of a grid that resembles a Tic-Tac-Toe board
	 *  x | x | x 
	 * -----------
	 *  x | x | x 
	 * -----------
	 *  x | x | x 
	 */
	public String toString(){
		String line = "";
		for(int i=0; i<size;i++){
			line+="  ";
			if(i<size-1){
				line+="|";
			}
		}
		
		String border = "";
		for(int i=0; i<size;i++){
			border+="---";
			if(i<size-1){
				border+="-";
			}
		}
		border+="\n";
		
		StringBuilder sbRow = new StringBuilder(3*size+(size-1)+1);
		sbRow.append(line);
		StringBuilder sbGrid = new StringBuilder((2*size-1)*(3*size+(size-1)+1));
		int count = 0;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(board[count]==-1){
					sbRow.insert(4*j+1,' ');
				}else if(board[count]==0){
					sbRow.insert(4*j+1,p1);
				}else{
					sbRow.insert(4*j+1,p2);
				}
				count++;
			}
			sbRow.append('\n');
			sbGrid.append(sbRow);
			if(i<size-1){
				sbGrid.append(border);
			}
			sbRow = new StringBuilder(3*size+(size-1)+1);
			sbRow.append(line);
		}
		return sbGrid.toString();
	}
	
	/**
	 * Fills the board with empty slots
	 * <p>
	 * All elements in board array is set to -1 
	 */
	private void fillBoard(){
		for(int i=0;i<size*size;i++){
			board[i]=-1;
		}
	}
	
	public int acquireMark(){
		String rows = "abcde".substring(0, size);
		String numbers = "12345".substring(0,size);
		int state = 0;
		
		System.out.println("Where will you mark? "+"(Rows:"+rows+" and Columns:"+numbers+" in the form of 'a2')");
		Scanner scanner = new Scanner(System.in);

		while(true){
			String input = scanner.nextLine();
			if(!input.matches("["+rows+"]["+numbers+"]")){
				state = 1;
			}else{
				if(!(board[size*(rows.indexOf(input.charAt(0)))+numbers.indexOf(input.charAt(1))]==-1)){
					state = 2;
				}
			}
			switch(state){
				case 0:
					scanner.close();
					return size*(rows.indexOf(input.charAt(0)))+numbers.indexOf(input.charAt(1));
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
	
	public int AIMark(){
		return 0;
	}
	
	public int AIDefenseMark(){
		return 0;
	}
	
	public int AIWinMark(){
		return 0;
	}

	/**
	 * Indicates whether a row has been completed and by which player. start specifies which row to start on in the array (size*(row number - 1))
	 * for example: for row 1, start would be 0
	 * @param start
	 * @return int check 
	 * 			<p> -1 : Neither player has completed the left diagonal </p>
	 * 		    <p> 0 : Player1 has completed the left diagonal </p>
	 * 		    <p> 1 : Player2 has completed the left diagonal </p>
	 */
	public int checkHorizontal(int start){
		int check = board[start];
		for(int i=0;i<size;i++){
			if((board[start+i]==-1)||(board[start+i]!=check)){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether a column has been completed and by which player. start specifies which column to start on in the array (column number - 1))
	 * for example: for row 2, start would be 1
	 * @param start 
	 * @return int check
	 * 			<p> -1 : Neither player has completed the left diagonal </p>
	 * 		    <p> 0 : Player1 has completed the left diagonal </p>
	 * 		    <p> 1 : Player2 has completed the left diagonal </p>
	 */
	public int checkVertical(int start){
		int check = board[start];
		for(int i=0;i<size;i++){
			if((board[i*size]==-1)||(board[i*size]!=check)){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether a the left diagonal (top left - bottom right) has been completed
	 * and by which player
	 * @return int check 
	 * 			<p> -1 : Neither player has completed the left diagonal </p>
	 * 		    <p> 0 : Player1 has completed the left diagonal </p>
	 * 		    <p> 1 : Player2 has completed the left diagonal </p>
	 */
	public int checkLDiagonal(){
		int check = board[0];
		for(int i=0;i<size;i++){
			if((board[i*(size+1)]==-1)||(board[i*(size+1)]!=check)){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether a the right diagonal (bottom left - top right) has been completed 
	 * and by which player
	 * @return int check 
	 * 			<p> -1 : Neither player has completed the right diagonal </p>
	 * 		    <p> 0 : Player1 has completed the right diagonal </p>
	 * 		    <p> 1 : Player2 has completed the right diagonal </p>
	 */
	public int checkRDiagonal(){
		int check = board[0];
		for(int i=0;i<size;i++){
			if((board[(i*size)+(size-1)]==-1)||(board[(i*size)+(size-1)]!=check)){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether the game has been completed and who won
	 * @return int placeholder
	 * 			<p> -1 : Neither player has completed the Board </p>
	 * 		    <p> 0 : Player1 has completed the Board </p>
	 * 		    <p> 1 : Player2 has completed the Board </p>
	 */
	public int checkWin(){
		int placeholder = 7;
		return placeholder;
	}
}