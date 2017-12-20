package model;

import java.util.Random;
import java.util.Scanner;

/**
 * This CPUPlayer class is a sub class of Player. It carries its own behavior for retrieving a 
 * mark and celebrates victory to a different tune. Methods {@code askMark()} and 
 * {@code playerWon()} are polymorphic.
 * @author Nonso Iwu
 * @see Player
 */
public class CPUPlayer extends Player{
	
	/**
	 * This is a mock {@code CPUPlayer} constructor. {@code name} is set to
	 * "watt" and {@code character} is set to 'w'. Used for testing. Not useful for
	 * gameplay.
	 */
	public CPUPlayer() {
		name = "watt";
		character = 'w';
	}
	
	/**
	 * A custom {@code CPUPlayer} constructor
	 * @param n A {@code String} whose value is set to name
	 */
	protected CPUPlayer(String n) {
		name = n;
	}
	
	/**
	 * This is the {@code CPUPlayer}'s own {@code askMark} method. This method utilizes a
	 * an AI that will choose an index that can produce a victory or stop the opponents 
	 * victory rather than being ineffective
	 * @param b A {@code Board} that is to be read be the method
	 * @param scanner This has to be a scanner without its {@code close()} method acted on it
	 * @param playerInt An {@code int} associated with the {@code Player}
	 * @return an {@code int} that indicates an index given to {@code Board}
	 */
	@Override
	public int askMark(Board b, Scanner scanner, int playerInt) {
		//Winning takes priority over Defense and Random mark
		try {
			if(AIWinMark(b,playerInt)>=0) {
				//System.out.println("AIWinMark triggered at index "+AIWinMark(b,playerInt));
				return AIWinMark(b,playerInt);
				}
			} catch (InvalidMarkException e) {
				e.printStackTrace();
			}
		
		//Defending takes priority over Random mark
		try {
			if(AIDefenseMark(b,playerInt)>=0) {
				//System.out.println("AIDefenseMark triggered at index "+AIDefenseMark(b,playerInt));
				return AIDefenseMark(b,playerInt);
				}
			} catch (InvalidMarkException e) {
				e.printStackTrace();
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
	 * @param b, the {@code Board} to be scanned to get a sutiable index
	 * @param playerInt, the {@code int} associated with the {@code CPUPlayer}
	 * @return -1 : No suitable index is available 
	 * 		    <p> 0 to ((size^2)-1): an index
	 * @throws InvalidMarkException Thrown if a value other than 0 or 1 is passed as {@code playerInt}
	 */
	private int AIDefenseMark(Board b, int playerInt) throws InvalidMarkException{
		if((playerInt!=0)&&(playerInt!=1)) {
			throw new InvalidMarkException();
		}
		
		int emptySlots = 0;
		int enemySlots = 0;
		int index = -1;
		
		
		//Vertical checks
		for(int j=0; j<b.size;j++){
			for(int i=0;i<b.size;i++){
				if((b.board[j+(i*b.size)]==-1)){
					emptySlots++;
					index = j+(i*b.size);
				}else if((b.board[j+(i*b.size)]!=playerInt)) {
					enemySlots++;
				}
			}
			//System.out.println("Defense Vertical Start: "+j+" Index: "+index+", emptySlots: "+emptySlots+", enemySlots: "+enemySlots);
			if((enemySlots==(b.size-1))&&(emptySlots==1)) {
				return index;
			}
			index = -1;
			emptySlots = 0;
			enemySlots = 0;
		}
		
		//Horizontal checks
		for(int j=0; j<b.size;j++){
			for(int i=0;i<b.size;i++){
				if((b.board[(j*b.size)+i]==-1)){
					emptySlots++;
					index = (j*b.size)+i;
				}else if((b.board[(j*b.size)+i]!=playerInt)) {
					enemySlots++;
				}
			}
			//System.out.println("Defense Horizontal Start: "+j*b.size+" Index: "+index+", emptySlots: "+emptySlots+", enemySlots: "+enemySlots);
			if((enemySlots==(b.size-1))&&(emptySlots==1)) {
				return index;
			}
			index = -1;
			emptySlots = 0;
			enemySlots = 0;
		}
		
		//LeftDiagonal check
		for(int i=0;i<b.size;i++){
			if((b.board[i*(b.size+1)]==-1)){
				emptySlots++;
				index = i*(b.size+1);
			}else if(b.board[i*(b.size+1)]!=playerInt){
				enemySlots++;
			}
		}
		//System.out.println("Defense LDiagonal: Index: "+index+", emptySlots: "+emptySlots+", enemySlots: "+enemySlots);
		if((enemySlots==(b.size-1))&&(emptySlots==1)) {
			return index;
		}
		index = -1;
		emptySlots = 0;
		enemySlots = 0;
		
		//RightDiagonal
		for(int i=0;i<b.size;i++){
			if((b.board[((b.size-1)+(i*(b.size-1)))]==-1)){
				emptySlots++;
				index = (b.size-1)+(i*(b.size-1));
			}else if(b.board[(b.size-1)+(i*(b.size-1))]!=playerInt){
				enemySlots++;
			}
		}
		//System.out.println("Defense RDiagonal: Index: "+index+", emptySlots: "+emptySlots+", enemySlots: "+enemySlots);
		if((enemySlots==(b.size-1))&&(emptySlots==1)) {
			return index;
		}
		index = -1;
		return index;
	}
	
	/**
	 * This method indicates whether or not a mark will allow this {@code CPUPlayer} 
	 * to win the game
	 * @param b, the {@code Board} to be scanned to get a sutiable index
	 * @param playerInt, the {@code int} associated with the {@code CPUPlayer}
	 * @return -1 : No suitable index is available
	 * 		    <p> 0 to ((size^2)-1): an index
	 */
	private int AIWinMark(Board b, int playerInt) throws InvalidMarkException{
		if((playerInt!=0)&&(playerInt!=1)) {
			throw new InvalidMarkException();
		}
		int emptySlots = 0;
		int winSlots = 0;
		int index = -1;
		
		
		//Vertical checks
		for(int j=0; j<b.size;j++){
			for(int i=0;i<b.size;i++){
				if((b.board[j+(i*b.size)]==-1)){
					emptySlots++;
					index = j+(i*b.size);
				}else if((b.board[j+(i*b.size)]==playerInt)) {
					winSlots++;
				}
			}
			//System.out.println("Win Vertical Start "+j+" Index: "+index+", emptySlots: "+emptySlots+", winSlots: "+winSlots);
			if((winSlots==(b.size-1))&&(emptySlots==1)) {
				return index;
			}
			index = -1;
			emptySlots = 0;
			winSlots = 0;
		}
		
		//Horizontal checks
		for(int j=0; j<b.size;j++){
			for(int i=0;i<b.size;i++){
				if((b.board[(j*b.size)+i]==-1)){
					emptySlots++;
					index = (j*b.size)+i;
				}else if((b.board[(j*b.size)+i]==playerInt)) {
					winSlots++;
				}
			}
			//System.out.println("Win Horizontal Start "+j*b.size+" Index: "+index+", emptySlots: "+emptySlots+", winSlots: "+winSlots);
			if((winSlots==(b.size-1))&&(emptySlots==1)) {
				return index;
			}
			index = -1;
			emptySlots = 0;
			winSlots = 0;
		}
		
		//LeftDiagonal check
		for(int i=0;i<b.size;i++){
			if((b.board[i*(b.size+1)]==-1)){
				emptySlots++;
				index = i*(b.size+1);
			}else if(b.board[i*(b.size+1)]==playerInt){
				winSlots++;
			}
		}
		//System.out.println("Win LDiagonal: Index: "+index+", emptySlots: "+emptySlots+", winSlots: "+winSlots);
		if((winSlots==(b.size-1))&&(emptySlots==1)) {
			return index;
		}
		index = -1;
		emptySlots = 0;
		winSlots = 0;
		
		//RightDiagonal
		for(int i=0;i<b.size;i++){
			if((b.board[((b.size-1)+(i*(b.size-1)))]==-1)){
				emptySlots++;
				index = ((b.size-1)+(i*(b.size-1)));
			}else if(b.board[(b.size-1)+(i*(b.size-1))]==playerInt){
				winSlots++;
			}
		}
		//System.out.println("Win RDiagonal: Index: "+index+", emptySlots: "+emptySlots+", winSlots: "+winSlots);
		if((winSlots==(b.size-1))&&(emptySlots==1)) {
			return index;
		}
		index = -1;
		return index;
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
