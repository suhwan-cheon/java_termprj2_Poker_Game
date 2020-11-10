import java.util.ArrayList;

public class PokerTable {
	private Deck theDeck;
	private ArrayList<Player> players;
	
	public void initDeck() {
		theDeck = Deck.makeShuffledDeck();
	}

	public void initPlayers() {
		players = new ArrayList<Player>();
		addPlayer(theDeck);
		addPlayer(theDeck);
		addPlayer(theDeck);
		addPlayer(theDeck);
		for (Player player : players) {
			player.drawFiveCards();
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Deck sharedDeck) {
		players.add(new Player(sharedDeck));
	}
}