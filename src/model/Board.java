package model;

/**
 * This class represents the "Board" or grid in a Tic-Tac-Toe game. It is interacted with by the 
 * Game class using the Player class. It was designed to be independent of Game and Player,
 * only altered by method calls. No getters or setters allowed!
 * 
 * @author Nonso Iwu
 *
 */
public class Board {
	/**Size of the board*/
	final int size;
	
	/**Character used for player 1*/ 
	char p1;
	
	/**Character used for player 2*/
	char p2;
	
	/**Filled with -1,0, and 1's. -1 means that the space is empty,
	 *0 indicates a mark from player 1 and 1 indicates a mark from
  	 *player 2
  	 */
	int[] board;
	
	/**
	 * Simple constructor for Board object
	 * <p>
	 * 'size' is defaulted to 3 for a standard Tic-Tac-Toe game, 
	 * 'p1' is defaulted to X, 
	 * 'p2' is defaulted to O,  
	 */
	public Board(){
		size = 3;
		p1 = 'X';
		p2 = 'O';
		board = new int[size*size];
		fillBoard();
	}
	
	/**
	 * Custom constructor for Board class
	 * <p>
	 * 'p1' is defaulted to X, 
	 * 'p2' is defaulted to O,  
	 * @param s int, value is set to size
	 */
	public Board(int s){
		size = s;
		p1 = 'X';
		p2 = 'O';
		board = new int[size*size];
		fillBoard();
	}
	
	/**
	 * Custom constructor for Board object
	 * @param s int, value is set to size
	 * @param c1 char, value is set to p1
	 * @param c2 char, value is set to p2
	 */
	private Board(int s, char c1, char c2){
		size = s;
		p1 = c1;
		p2 = c2;
		board = new int[size*size];
		fillBoard();
	}
	
	/**
	 * A factory method that produces a board. Asks the user for a size using standard input.
	 * @param p1 Player 1 of the game
	 * @param p2 Player 2 of the game
	 * @return a Board based on the answer and players
	 */
	public static Board getBoard(Player p1,Player p2){
		System.out.println("Choose a size for the board: 3,4, or 5");
		int boardSize = Integer.valueOf(Game.inquire("[3-5]"));
		
		return new Board(boardSize,p1.character,p2.character);
	}
	
	/**
	 * Displays board in the style of a grid that resembles a Tic-Tac-Toe board<br>
	 *  x | x | x <br>
	 * -----------<br>
	 *  x | x | x <br>
	 * -----------<br>
	 *  x | x | x 
	 *  @return a String representation of a Tic-Tac-Toe board
	 */
	public String toString(){
		//Generates an empty line  | | ....
		String line = "";
		for(int i=0; i<size;i++){
			line+="  ";
			if(i<size-1){
				line+="|";
			}
		}
		
		//Generates a border ------------....
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
		int count = 0; //Used to traverse the array
		//This loop inserts the appropriate character into the spaces of the line
		//based on the value in the index of board
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
	 * Provides a readable array of int values for {@code board}. -1 represents an empty slot
	 * while 0 and 1 mean player 1 and player 2 marks respectively
	 * @return a String representation of {@code board}
	 */
	public String rawBoard() {
		StringBuilder array = new StringBuilder();
		array.append("[");
		for(int s:board) {
			array.append(s);
			array.append(" ");
		}
		array.replace(array.length()-1, array.length(), "]"); //Replaces the " " with a bracket
		
		return array.toString();
	}
	
	/**
	 * All elements in {@code board} are set to -1 which are represented as empty slots
	 * @return Nothing
	 */
	public void fillBoard(){
		for(int i=0;i<size*size;i++){
			board[i]=-1;
		}
	}
	
	/**
	 * Indicates if the board is full, specifically if {@code board} contains any -1 values
	 * @return <p> true : {@code board} contains zero -1 values
	 * 		    <p> false : {@code board} contains one or more -1 value
	 * 		
	 */
	public boolean isFull(){
		for(int slot:board) {
			if(slot<0) return false;
		}
		return true;
	}
	
	/**
	 * Changes the board array at the {@code slot} index to the value of {@code playerInt}
	 * @param playerInt An int,  0 or 1
	 * @param slot An int (between 0 and (size^2)-1) used to index into {@code board}
	 * @throws InvalidMarkException meaning that {@code playerInt} is not 0 or 1, {@code slot} is off of the 
	 * 								board array, or if that slot has already been marked
	 * @see InvalidMarkException
	 * @return Nothing
	 */
	public void mark(int playerInt, int slot) throws InvalidMarkException{
		//This was to make sure that I or anyone does not pass a value other than
		//0 or 1 for playerInt. Added during testing.
		if((!((playerInt==0)||(playerInt==1)))||(board[slot]!=-1)){
			throw new InvalidMarkException();
		}
		board[slot] = playerInt;
	}
	
	/**
	 *  Indicates the 'priority' of a mark position based on the size of the board and returns an
	 *  integer that shows whether the it has a priority of 0,1,2 or 3
	 *  @param mark An int (between 0 and (size^2)-1) used to index into the board attribute
	 *  @return  0 : Check horizontal and vertical
	 * 		    <p> 1 : Check horizontal, vertical, and lDiagonal
	 * 		    <p> 2 : Check horizontal, vertical, and rDiagonal
	 * 			<p> 3 : Check horizontal, vertical, lDiagonal, and rDiagonal
	 */
	public int markPriority(int mark){
		boolean lDiagonal = false; //Indicates if the 'mark' lies on the left diagonal
		boolean rDiagonal = false; //Indicates if the 'mark' lies on the right diagonal
		
		if(mark%(size+1)==0){
			lDiagonal = true;		
		}
		if(mark%(size-1)==0){
			//This condition makes sure to not count index 0 and (size^2)-1 which
			//lie on the left Diagonal. The problem comes from the fact that 0 and (size^2)-1
			//will both produce 0 if modded by size-1
			if(!((mark==0)||(mark==((size*size)-1)))){
				rDiagonal = true;
			}
		}
		
		if(lDiagonal&&rDiagonal) {
			return 3;
		}else if(rDiagonal){
			return 2;
		}else if(lDiagonal){
			return 1;
		}
		return 0;
	}
	
	/**
	 * Indicates whether a row has been completed and by which player
	 * @param start Specifies which row to start on in the array (size*(row number - 1))
	 * @return  -1 : Neither player has completed the left diagonal
	 * 		    <p> 0 : Player1 has completed the left diagonal
	 * 		    <p> 1 : Player2 has completed the left diagonal
	 */
	private int checkHorizontal(int start){
		int check = board[start]; //Uses the value of the slot in order to check if all
		  						  //the slots in the row are the same
		
		for(int i=0;i<size;i++){
			if((board[start+i]==-1)||(board[start+i]!=check)){
				return -1;
			}
		}
		return check;
	}
		
	/**
	 * Indicates whether a column has been completed and by which player
	 * @param start Specifies which column to start on in the array (Between 0 and size-1 (inclusive))
	 * @return  -1 : Neither player has completed the left diagonal
	 * 		    <p> 0 : Player1 has completed the left diagonal
	 * 		    <p> 1 : Player2 has completed the left diagonal
	 */
	private int checkVertical(int start){
		int check = board[start]; //Uses the value of the slot in order to check if all
		  						  //the slots in the column are the same
		
		for(int i=0;i<size;i++){
			if((board[start+(i*size)]==-1)||(board[start+(i*size)]!=check)){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether the left diagonal (top left - bottom right) has been completed
	 * and by which player
	 * @return  -1 : Neither player has completed the left diagonal </p>
	 * 		    <p> 0 : Player1 has completed the left diagonal </p>
	 * 		    <p> 1 : Player2 has completed the left diagonal </p>
	 */
	private int checkLDiagonal(){
		int check = board[0]; //Uses the value of the slot in order to check if all
		  					  //left diagonal slots are the same
		
		//The 'slots' on the left diagonal are factors of size+1 starting at 0
		//This loop checks those slots
		for(int i=0;i<size;i++){
			if((board[i*(size+1)]==-1)||(board[i*(size+1)]!=check)){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether the right diagonal (bottom left - top right) has been completed 
	 * and by which player
	 * @return 	-1 : Neither player has completed the right diagonal
	 * 		    <p> 0 : Player1 has completed the right diagonal
	 * 		    <p> 1 : Player2 has completed the right diagonal 
	 */
	private int checkRDiagonal(){
		int check = board[size*(size-1)]; //Uses the value of the slot in order to check if all
										  //right diagonal slots are the same
		
		//The 'slots' on the right diagonal are factors of size-1 starting at size-1
		//This loop checks those slots
		for(int i=0;i<size;i++){
			if((board[((size-1)+(i*(size-1)))]==-1||(board[((size-1)+(i*(size-1)))]!=check))){
				return -1;
			}
		}
		return check;
	}
	
	/**
	 * Indicates whether the game has been completed (3,4, or 5 in a row) and who won
	 * <p>Note: Instead of checking the entirety of the board everytime, total number of 
	 * 			comparisons are reduced by only checking the necessary rows/columns/diagonals
	 * @param mark An {@code int} value (between 0 and (size^2)-1) used to index into the 
	 * 			board attribute
	 * @return 	-1 : Neither player has completed the Board
	 * 		    <p> 0 : Player1 has completed the Board
	 * 		    <p> 1 : Player2 has completed the Board
	 */
	public int checkWin(int mark){
		int win = -1; //Win indicator, defaulted to -1
		int rowStart = (mark/size)*size; //The start index for the row where the mark is
		int columnStart = mark%size; //The start index for the column where the mark is
		
		switch(markPriority(mark)){
			case 0:
				//Check only horizontal and vertical for completion
				if((checkHorizontal(rowStart)>=0)||(checkVertical(columnStart)>=0)){
					win = board[mark];
				}
				break;
			case 1:
				//Check only horizontal, vertical, and left diagonal for completion
				if((checkHorizontal(rowStart)>=0)||(checkVertical(columnStart)>=0)||(checkLDiagonal()>=0)){
					win = board[mark];
				}
				break;
			case 2:
				//Check only horizontal, vertical, and right diagonal for completion
				if((checkHorizontal(rowStart)>=0)||(checkVertical(columnStart)>=0)||(checkRDiagonal()>=0)){
					win = board[mark];
				}
				break;
			case 3:
				//Check only horizontal, vertical, left diagonal and right diagonal for completion
				if((checkHorizontal(rowStart)>=0)||(checkVertical(columnStart)>=0)||(checkLDiagonal()>=0)||(checkRDiagonal()>=0)){
					win = board[mark];
				}
				break;
		}
		return win;
	}
}