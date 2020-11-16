/*
 * 번호에 따른 카드 매칭 기능
 */
import javax.swing.ImageIcon;
import java.io.File;

public class Card implements Comparable<Card>{

	private int suit;
	private int number;
	private int cardID;
	private ImageIcon myIcon;
	
	public static final int SPADE = 4;
	public static final int HEART = 2; 
	public static final int DIAMOND= 3;
	public static final int CLUB = 1;
		
	public Card(int number, int suit) {
		this.number = number;
		this.suit = suit;
		this.cardID = (suit - 1) * 13 + number;
		this.checkInput();
	}
	
	public Card(int cardNumber) {
		this.cardID = cardNumber;
		this.suit = (cardNumber - 1) / 13 + 1;
		
		this.number = cardNumber % 13;
		if (cardNumber % 13 == 0) {
			this.number = 13;
		}
		this.checkInput();

	}
	
	public void checkInput() {
		if (number < 1 || number > 13) {
			throw new IllegalArgumentException("Can't make " + cardID);
		}
		if (suit < 1 || suit > 4) {
			throw new IllegalArgumentException("Can't make " + cardID);
		}
		if (cardID == 0) {
			// Special edge case
			throw new IllegalArgumentException("Can't make " + cardID);
		}
	}
	
	
	public ImageIcon getIcon() {
		return myIcon;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getCardID(){
		return cardID;
	}
	
	public String toString() {
		String num = "" + number;
		if (number == 11) {
			num = "Jack";
		} else if (number == 12) {
			num = "Queen";
		} else if (number == 13) {
			num = "King";
		} else if (number == 1) {
			num = "Ace";
		}
		String inBetween = " of ";
		String displaySuit = "";
		switch (suit) {
		case SPADE:
			displaySuit = "Spades";
			break;
		case HEART:
			displaySuit = "Hearts";
			break;
		case DIAMOND:
			displaySuit = "Diamonds";
			break;
		case CLUB:
			displaySuit = "Clubs";
			break;
		}
		return num + inBetween + displaySuit;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Card == false) {
			return false;
		}
		Card other = (Card) o;
		if (other.suit == this.suit) {
			if (other.number == this.number) {
				return true;
			}
		}
		return false;
	}
	
	//숫자를 비교하되, 에이스인 경우 예외처리함
	public int compareTo(Card other) {
		int otherNum = other.number;
		int thisNum = this.number;
		return otherNum - thisNum;
	}
}
