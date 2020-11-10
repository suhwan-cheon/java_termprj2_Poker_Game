import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI {
	
	public final static String IMAGE_FOLDER_LOCATION = "images" + File.separator;
	public final static String POKER_TABLE_IMAGE = IMAGE_FOLDER_LOCATION + "poker_table.png";
	
	private static JFrame mainFrame;
	private static JFrame playerFrame;
	private static JPanel tablePanel;
	private static JPanel cardPanel;
	private static JPanel buttonPanel;
	private static JPanel playerPanel;

	private static JLabel messageLabel;
	private static HashMap<Integer, String> cardMap;
	private static PokerTable currTable;
	private static Deck theDeck;
	
	private static int player_num = 0;
	private static String[] player = new String[5];
	
	public static void initGUI(){
		
		player_num = Integer.parseInt(JOptionPane.showInputDialog("인원 수를 적어주세요!"));
		for(int i=0; i<player_num; i++)
		{
			player[i] = JOptionPane.showInputDialog((i + 1) +"번째 플레이어의 이름을 적어주세요!");
		}
	
		mainFrame = new JFrame("Poker Game"); // creates and sets title of mainFrame
		mainFrame.setLayout(null);
		//mainFrame.setLayout(new FlowLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		mainFrame.setLocation(100, 100);
		mainFrame.setVisible(true);

		//덱 만들기
		cardMap = new HashMap<Integer, String>();
		for (int i = 1; i <= Deck.SIZE; i++) {
			cardMap.put(i, IMAGE_FOLDER_LOCATION + i + ".png");
		}
		
		//가운데 테이블 그림
		tablePanel = new JPanel();
		JLabel table = new JLabel(new ImageIcon(POKER_TABLE_IMAGE));
		tablePanel.setBounds(rect(0,0, table.getPreferredSize()));
		tablePanel.add(table);
		mainFrame.add(tablePanel);
		
		JMenu sysMenu = new JMenu("System");
		sysMenu.setMnemonic('S');
		
		JMenuItem newItem = new JMenuItem("New");
		newItem.setMnemonic('N');
		sysMenu.add(newItem);
		
		newItem.addActionListener(new NewGame());
			
		
		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setMnemonic('Q');
		sysMenu.add(quitItem);
		
		quitItem.addActionListener(new QuitGame());
		
		
		JMenu playerMenu = new JMenu("Player");
		playerMenu.setMnemonic('P');
		
		JMenuItem player1Item = new JMenuItem(player[0]);
		playerMenu.add(player1Item);
		
		player1Item.addActionListener(new player1());
		
		if(player_num > 1) {
			JMenuItem player2Item = new JMenuItem(player[1]);
			playerMenu.add(player2Item);
			
			player2Item.addActionListener(new player2());
		}
		if(player_num > 2) {
			JMenuItem player3Item = new JMenuItem(player[2]);
			playerMenu.add(player3Item);
			
			player3Item.addActionListener(new player3());
		}
		if(player_num > 3) {
			JMenuItem player4Item = new JMenuItem(player[3]);
			playerMenu.add(player4Item);
			
			player4Item.addActionListener(new player4());
		}
		
		JMenuBar bar = new JMenuBar();
		mainFrame.setJMenuBar(bar);
		bar.add(sysMenu);
		bar.add(playerMenu);
		
	
		mainFrame.repaint();
		mainFrame.validate();
	}
	
	
	private static Rectangle rect(int x, int y, Dimension size) {
		Rectangle result = new Rectangle(new Point(x,y), size);
		return result;
	}

	private static JLabel iconizeCard(int cardID){
		JLabel label = new JLabel(new ImageIcon(cardMap.get(cardID)));
//		label.setBorder(null);
		return label;
	}

	public static void fillTable(Player player){
		playerFrame = new JFrame("player"); // creates and sets title of mainFrame
		playerFrame.setLayout(new FlowLayout());
		playerFrame.setSize(400, 200);
		playerFrame.setLocation(100, 100);
		playerFrame.setVisible(true);
		
		
		ArrayList<Player> currPlayers = currTable.getPlayers();
		
	
		JPanel cardPanel = new JPanel();
		for(int i=0; i<5; i++) {
			cardPanel.add(iconizeCard(player.c(i).getCardID()));
		}
		// add randomized cards with for loop
//		cardPanel.setBounds(rect(x, y, size));
		playerFrame.add(cardPanel);
		cardPanel.validate();
		cardPanel.repaint();
		
	}
	
	
	private static class NewGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			currTable = new PokerTable();

			//TODO Users fill up players
			currTable.initDeck();
			currTable.initPlayers();
			mainFrame.validate();
		}
		
	}
	
	private static class QuitGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gameOver();
		}
		
	}
	
	private static class player1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fillTable(currTable.getPlayers().get(0));
		}
		
	}
	
	private static class player2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fillTable(currTable.getPlayers().get(1));
		}
		
	}
	
	private static class player3 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fillTable(currTable.getPlayers().get(2));
		}
		
	}
	
	private static class player4 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fillTable(currTable.getPlayers().get(3));
		}
		
	}
	
	private static void gameOver(){
		System.exit(0);
	}
}
