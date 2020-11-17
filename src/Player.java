import java.util.ArrayList;

public class Player {

	private String name;
	public Hand myHand;
	private int score;
	private String jname;
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
	
	public int get_score() {
		return score;
	}
	public String get_jname() {
		return jname;
	}


	private class Hand {

		private Deck sharedDeck;
		private Card[] c = new Card[5];
		private ArrayList<Card> myHand;
		private boolean hasDrawn;
		public HandScorer handsc;
		
		public Hand(Deck d) {
			this.sharedDeck = d;
			this.hasDrawn = false;
			myHand = new ArrayList<Card>(5);
		}

		public boolean drawFromDeck() {
			boolean wasHoldingCards = hasDrawn;
			for(int i=0; i<5; i++) c[i] = sharedDeck.draw();
			this.hasDrawn = true;
			ArrayList<Card> tmp = getHand();
			return wasHoldingCards;
		}

		public ArrayList<Card> getHand() {
			if (!hasDrawn) {
				String msg = "nono";
				throw new RuntimeException(msg);
			}
			for(int i=0; i<5; i++) myHand.add(c[i]);
			handsc = new HandScorer(myHand);
			score = handsc.return_Score();
			jname = handsc.return_Jokbo();
			return myHand;
		}

	}
}