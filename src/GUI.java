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
	private static String[] jname = new String[5];
	private static int player_num = 0;
	private static String[] player = new String[5];
	
	public static void initGUI(){
		
		player_num = Integer.parseInt(JOptionPane.showInputDialog("�ο� ���� �����ּ���!"));
		for(int i=0; i<player_num; i++)
		{
			player[i] = JOptionPane.showInputDialog((i + 1) +"��° �÷��̾��� �̸��� �����ּ���!");
		}
	
		mainFrame = new JFrame("Poker Game"); // creates and sets title of mainFrame
		mainFrame.setLayout(null);
		//mainFrame.setLayout(new FlowLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		mainFrame.setLocation(100, 100);
		mainFrame.setVisible(true);

		//�� �����
		cardMap = new HashMap<Integer, String>();
		for (int i = 1; i <= Deck.SIZE; i++) {
			cardMap.put(i, IMAGE_FOLDER_LOCATION + i + ".png");
		}
		
		//��� ���̺� �׸�
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
		
		JMenu judgeMenu = new JMenu("Judge");
		judgeMenu.setMnemonic('J');
		
		JMenuItem winItem = new JMenuItem("Winner");
		winItem.setMnemonic('W');
		judgeMenu.add(winItem);
		
		winItem.addActionListener(new winner());
			
		
		
		JMenuItem totalItem = new JMenuItem("Total");
		totalItem.setMnemonic('T');
		judgeMenu.add(totalItem);
		
		totalItem.addActionListener(new showTotal());
		
		JMenuBar bar = new JMenuBar();
		mainFrame.setJMenuBar(bar);
		bar.add(sysMenu);
		bar.add(playerMenu);
		bar.add(judgeMenu);
	
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

	public static void fillTable(Player player, int score, String jname){
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
		
		// ����ٰ� text (JLable add�ϸ� ��)a
		JLabel jokbo = new JLabel();
		jokbo.setText(jname);
		
		playerFrame.add(cardPanel);
		playerFrame.add(jokbo);
	}
	
	public static void totalTable(){
		playerFrame = new JFrame("Total"); // creates and sets title of mainFrame
		playerFrame.setLayout(new FlowLayout());
		playerFrame.setSize(600, 500);
		playerFrame.setLocation(100, 100);
		playerFrame.setVisible(true);
		
		boolean check[] = new boolean[5];
		for(int j=0; j<player_num; j++) {
			JPanel cardPanel = new JPanel();
			int win_idx = 0;
			int max_score = 0;
			for(int i=0; i<player_num; i++)
			{
				if(check[i]) continue;
				Player tmp = currTable.getPlayers().get(i);
				if(max_score < tmp.get_score()) {
					max_score = tmp.get_score();
					win_idx = i;
				}
			}
			check[win_idx] = true;
			Player players = currTable.getPlayers().get(win_idx);
			for(int i=0; i<5; i++) {
				cardPanel.add(iconizeCard(players.c(i).getCardID()));
				
			}
			String user_name = player[win_idx];
			// �ش� player�� ī��
			playerFrame.add(cardPanel);
			// �ش� ����
			JLabel jokbo = new JLabel();
			jokbo.setText(user_name + " : " + players.get_jname());
			
			playerFrame.add(jokbo);
			
		}
		
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
			Player p1 = currTable.getPlayers().get(0);
			fillTable(currTable.getPlayers().get(0), p1.get_score(), p1.get_jname());
		}
		
	}
	
	private static class player2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Player p2 = currTable.getPlayers().get(1);
			fillTable(currTable.getPlayers().get(1), p2.get_score(), p2.get_jname());
		}
		
	}
	
	private static class player3 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Player p3 = currTable.getPlayers().get(2);
			fillTable(currTable.getPlayers().get(2), p3.get_score(), p3.get_jname());
		}
		
	}
	
	private static class player4 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Player p4 = currTable.getPlayers().get(3);
			fillTable(currTable.getPlayers().get(3), p4.get_score(), p4.get_jname());
		}
		
	}
	
	private static class winner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int win_idx = 0;
			int max_score = 0;
			for(int i=0; i<player_num; i++)
			{
				Player tmp = currTable.getPlayers().get(i);
				if(max_score < tmp.get_score()) {
					max_score = tmp.get_score();
					win_idx = i;
				}
			}
			Player win_p = currTable.getPlayers().get(win_idx);
			fillTable(currTable.getPlayers().get(win_idx), win_p.get_score(), win_p.get_jname());
		}
		
	}
	
	private static class showTotal implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			totalTable();
		}
		
	}
	private static void gameOver(){
		System.exit(0);
	}
}