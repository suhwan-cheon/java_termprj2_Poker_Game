import java.util.ArrayList;

public class Player {

	private String name;
	private Hand myHand;

	public Player(Deck sharedDeck) {
		myHand = new Hand(sharedDeck);
	}

	public Card c(int idx) {
		return myHand.c[idx];
	}

	public void drawFiveCards() {
		myHand.drawFromDeck();
	}
	
	// 플레이어 이름 설정
	public String setName(String s) {
		String ans = this.name;
		this.name = s;
		return ans;
	}

	public String getName() {
		return this.name;
	}
	
	public ArrayList<Card> getTwoCards() {
		return myHand.getHand();
	}


	private class Hand {

		private Deck sharedDeck;
		private Card[] c = new Card[5];
		private ArrayList<Card> myHand;
		private boolean hasDrawn;

		public Hand(Deck d) {
			this.sharedDeck = d;
			this.hasDrawn = false;
			myHand = new ArrayList<Card>(5);
		}

		public boolean drawFromDeck() {
			boolean wasHoldingCards = hasDrawn;
			for(int i=0; i<5; i++) c[i] = sharedDeck.draw();
			this.hasDrawn = true;
			return wasHoldingCards;
		}

		public ArrayList<Card> getHand() {
			if (!hasDrawn) {
				String msg = "Call to getHand on a hand that hasn't drawn yet!";
				throw new RuntimeException(msg);
			}
			for(int i=0; i<5; i++) myHand.add(c[i]);
			
			return myHand;
		}

	}
}
