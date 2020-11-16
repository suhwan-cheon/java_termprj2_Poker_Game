import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;

public class HandScorer {
	
	private Card[] hand;
	private int score = -1;
	
	public HandScorer(ArrayList<Card> fullHand) {
		Collections.sort(fullHand);
		Collections.reverse(fullHand);
		hand = new Card[5];
		for (int i = 0; i < fullHand.size(); i++) {
			hand[i] = fullHand.get(i);
		}
	}
	
	public int return_Score() {
		
		
		
		
		// �׳� �����ϴ°� �����Ƽ� ���� ���� �������..
		// ������ ¥�� ���� �������ϱ� �ѵ�.. �ǹ� ����������������
		// �� �ڵ��� ���� ����! : sort�� ������������ �Ǿ�����! && a�� 1��! && getSuit() method�� � �������(������Ŭ) �˷��ִ� �޼ҵ���!
		
		// �� ���� ������ ������ 100���� �ξ��� ������ �������� ��ĥ �� ����
		// ������ �ΰ������ +50, ��������� +30 ... �̰� �� �����ϱ� ���� ¥�ٺ��ϱ� �ʿ��ؼ�
		
		//�ξ� ��Ʈ����Ʈ �÷��� �϶� - �⺻ 20000��
		if(Royal_Straight_Flush()) {
			score = 20000;
			// �ξ� ��Ʈ����Ʈ �÷����� ������! ��翡 ���ؼ��� ���� ����
			score += hand[0].getSuit();
		}
		else if(Straight_Flush()) {
			score = 18000;
			// ���� ���� ���� ����ġ �����ֱ�
			if(hand[0].getNumber() == 1) score += 1000; // �ִ� ��ġ, 
			else score += hand[4].getNumber()*50; // 50�� �����ִ� ������ ���� ���ǿ� ������ ���� �ʱ� ���� (���⼭ 1�� ���̳��� ������ 50���� ���̰� ���� ������ ���� ���ǿ� ������ ���� ����)
			// ��� ����ġ �����ֱ�
			score += hand[0].getSuit();
		}
		else if(Four_Card()) {
			score = 16000;
			// ��ī��� ������ ���ڿ� ���� ���и� �����Ƿ�. hand[2]�� ������ 4���� ī�� �� �ϳ��̴�.
			if(hand[2].getNumber() == 1) score += 1818; // 1�̸� ������ win!
			else score += hand[2].getNumber();
		}
		else if(Full_House()) {
			score = 14000;
			// Ǯ�Ͽ콺�� 3���� ī�� �� ���� �͸� ���ϸ� �� (���� �ִ밪�� ���� Ǯ�Ͽ콺�� ���� �� ����!)
			if(hand[2].getNumber() == 1) score += 1818; // 1�̸� ������ win!
			else score += hand[2].getNumber();
		}
		else if(Flush()) {
			score = 12000;
			// �÷����� ������ ���Ѵ�
			score += (hand[0].getSuit()*50);
			// ����� ���ٸ�, �ִ� ���ڸ� ������ �ִ� ����� ����!
			if(hand[4].getNumber() == 1) score += 14;
			score += hand[4].getNumber();			
		}
		else if(Straight()) {
			score = 8000;
			// ���� ����ƾ�� 1��.
			if(hand[0].getNumber()==1 && hand[4].getNumber() == 13) score += 3500;
			else if(hand[0].getNumber() == 1) score += 3000; // �������� 1�̰� ����ƾ�� �ƴ϶�� �齺Ʈ����Ʈ��
			else score += (hand[4].getNumber()*100);
			// ���� ���� ���� ���ڰ� ����� ���ٸ�, ���� ���� ���� ī���� ��糢�� ��
			score += hand[4].getSuit();
		}
		else if(Triple()) {
			score = 6000;
			// Ʈ������ ������ ���� ���� ���ڿ� ���ؼ��� ����
			if(hand[2].getNumber() == 1) score += 1818; // A Ʈ������ �׳� ��!
			else score += hand[2].getNumber();
		}
		else if(TwoPair()) {
			score = 4000;
			// �ű��ϰԵ�, ������ ���ִٸ� ��� ���� hand[1]�� ���� pair��, hand[3]�� ū pair�� ���ϰ� �ȴ�. (�׸� �׷��� �غ��ϱ� �׷����󱸿�..)
			// A �� �����Ҷ�
			if(hand[1].getNumber() == 1) {
				score += 14*100;
				score += (hand[3].getNumber()*5);
				score += hand[1].getSuit();
			}
			else { // A �� �������� ���� ��
				score += (hand[3].getNumber()*100); // �Ʒ� ���� �ִ� 13*5���̹Ƿ� *100
				score += (hand[1].getNumber()*5); // �Ʒ� ���� �ִ� 4���̹Ƿ� *5
				score += hand[3].getSuit();
			}
		}
		else if(OnePair()) {
			score = 2000;
			int [] checking_arr = new int[14];
			for(int i = 0; i < 5; i++) {
				checking_arr[hand[i].getNumber()]++;
			}
			int one = 0;
			int next_max_num = -1;
			for(int i = 1; i <= 13; i++) {
				if(checking_arr[i] == 2) one = i;
				else if(checking_arr[i] == 1)next_max_num = Math.max(next_max_num,i);
				else continue;
			}
			// one���� pair�� ������, next_max_num ���� pair�� ������ ���� ū ���� ����
			if(one == 1) score += 1818;
			else score += (one*50);
			score += next_max_num;
		}
		else {
			for(int i=0;i<5;i++) System.out.println(hand[i]);
			
			if(hand[0].getNumber()==1) {
				score = 14*50;
				score += hand[0].getSuit();
				System.out.println(hand[0].getSuit());
			}
			else {
				score = hand[4].getNumber()*50;
				score += hand[4].getSuit();
				System.out.println(hand[4].getSuit());
			}
			
		}
		
		return score;
	}
	
	// Card Ŭ������ number�� ����, suit�� ���, CardId�� 42������ ǥ���� ī��
	
	// �ξ� ��Ʈ����Ʈ �÷������� Ȯ��
	boolean Royal_Straight_Flush() {
		// ù ī���� ����� �˾ƿ´�.
		int shape = hand[0].getSuit();
		int cmp = 10; // sort �������Ƿ� ���� �ξ� ��Ʈ����Ʈ �÷������ ù ī��� �ݵ�� 10�̿��� �Ѵ�.
		for(int i = 0; i < 5; i++) {
			if(hand[i].getSuit() == shape && hand[i].getNumber() == cmp) { // ��� ����� ���ƾ� �ϰ� ������ 
				cmp++; //�� ���� ���ڴ� 11�̿��� �ϹǷ�
			}
			else return false;
		}
		return true;
	}
	
	// ��Ʈ����Ʈ �÷������� Ȯ��
	boolean Straight_Flush() {
		// �ξ�� ����ϳ� 10���� �������� �ʾƵ� �ȴ�. �� ù ���ں��� ���ʴ�� 1�� �����ϸ� �ȴ�
		// ù ī���� ����� �˾ƿ´�.
		int shape = hand[0].getSuit();
		int cmp = hand[0].getNumber();
		for(int i = 0; i < 5; i++) {
			if(hand[i].getNumber() == shape && hand[i].getNumber() == cmp) { // ��� ����� ���ƾ� �ϰ� ������ 
				cmp++;
			}
			else return false;
		}
		return true;
	}
	
	// ��ī������ Ȯ��
	boolean Four_Card() {
		// ��ī������ Ȯ���ϴ� ����� �ΰ��� ��찡 �ִ�. (���ĵǾ� �����Ƿ�)
		// 1. 4���� ���� < 1���� ����    2. 4���� ���� > 1���� ����
		if(hand[0].getNumber()==hand[1].getNumber()){ //ù ī��� �� ��° ī���� ���ڰ� ���� ��� -> 1�� ���̽�
			int cmp = hand[0].getNumber();
			if(hand[2].getNumber()== cmp && hand[3].getNumber()==cmp && hand[4].getNumber()!=cmp) { // �տ��� 4���� ī��� ����(��ī��) �������� ������ �޶�� ��! �ȱ׷��� �׳� ��Ʈ����Ʈ��!
				return true;
			}
		}
		else if(hand[0].getNumber()!=hand[1].getNumber()){//ù ī��� �� ��° ī���� ���ڰ� �ٸ� ��� -> 2�� ���̽�
			int cmp = hand[1].getNumber();
			if(hand[2].getNumber()== cmp && hand[3].getNumber()==cmp && hand[4].getNumber()==cmp) { // ���� 4���� ī��� ��� ���� ���ڸ� �ȴ�.
				return true;
			}
		}
		else return false;
		
		return false;
	}
	
	// Ǯ�Ͽ콺���� Ȯ��
	boolean Full_House() {
		// Ǯ�Ͽ콺���� Ȯ�� �ϴ� ����� �ΰ��� ��찡 �ִ�. (���ĵǾ� �����Ƿ�)
		// 1. ���� 2��, ���� 3���� ���� ī���� ��   2. ���� 2��, ���� 3���� ���� ī���� ��
		if(hand[2].getNumber()!=hand[1].getNumber()) { // 1�� ���̽�
			int cmp = hand[2].getNumber();
			if(hand[0].getNumber() == hand[1].getNumber() && hand[3].getNumber() == cmp && hand[4].getNumber() == cmp) { // 2�� 3�� ������ Ȯ��
				return true;
			}
		}
		else if(hand[2].getNumber()==hand[1].getNumber()) {// 2�� ���̽�
			int cmp = hand[2].getNumber();
			if(hand[3].getNumber() == hand[4].getNumber() && hand[0].getNumber() == cmp && hand[1].getNumber() == cmp) { // 3�� 2�� ������ Ȯ��
				return true;
			}
		}
		else return false;
		
		return false;
	}
	
	// �÷������� Ȯ��
	boolean Flush() {
		int shape = hand[0].getSuit();
		for(int i = 0; i < 5; i++) {
			if(shape != hand[i].getSuit()) return false;
		}
		return true;
	}
	
	// ��Ʈ����Ʈ���� Ȯ��
	boolean Straight() {
		int cmp = hand[0].getNumber();
		// ����ƾ�� Ư���� ��� �̹Ƿ� Ȯ������� ��!
		if(cmp == 1) {
			cmp = 10;
			for(int i = 1; i < 5; i++) {
				if(hand[i].getNumber() == cmp) {
					cmp++;
				}
				else return false;
			}
			return true;
		}
		for(int i = 0; i < 5; i++) {
			if(hand[i].getNumber() == cmp) {
				cmp++;
			}
			else return false;
		}
		return false;
	}
	
	// Ʈ�������� Ȯ��
	boolean Triple() {
		// Ʈ�������� Ȯ���ϴ� ������� 3������ �ִ�.
		// 1. *** * *    2. * *** *   3. * * ***
		int cmp = hand[2].getNumber(); // ���� ���̽��� ���� 3��° �д� �ݵ�� Ʈ���ÿ� ���������� �� �� �ִ�. (���ĵǾ������ϱ�!)
		// ù ��° ���̽�
		if(hand[0].getNumber() == cmp && hand[1].getNumber() == cmp && hand[3].getNumber() != cmp && hand[4].getNumber() != cmp && hand[3].getNumber() != hand[4].getNumber()) { // �� �ڵ� ¥�鼭 �����ǵ� �׳� �̸� ���������� �޾Ƴ�����..
			return true;
		}
		else if(hand[0].getNumber() != cmp && hand[1].getNumber() == cmp && hand[3].getNumber() == cmp && hand[4].getNumber() != cmp && hand[0].getNumber() != hand[4].getNumber()) {
			return true;
		}
		else if(hand[0].getNumber() != cmp && hand[1].getNumber() != cmp && hand[3].getNumber() == cmp && hand[4].getNumber() == cmp && hand[0].getNumber() != hand[1].getNumber()) {
			return true;
		}
		else return false;
	}
	
	// ��������� Ȯ��
	boolean TwoPair() {
		// ��.. ���ڵ� ¥�ٰ� ���ڱ� ������ �ǵ� �׳� �迭�� �ְ� ���ϸ� ���� ������ �ʳ�..? ���� �ʹ� �̷��ϰ� ®�� ��������
		// ������ �� �ڵ�� ��������� �ƴ����� �Ǵ����ָ� �Ǳ� ������
		int [] checking_arr = new int[14];
		for(int i = 0; i < 5; i++) {
			checking_arr[hand[i].getNumber()]++;
		}
		int two = 0;
		for(int i = 1; i <= 13; i++) {
			if(checking_arr[i] == 2) two++;
		}
		if(two == 2) return true;
		else return false;
	}
	
	boolean OnePair() {
		int [] checking_arr = new int[14];
		for(int i = 0; i < 5; i++) {
			checking_arr[hand[i].getNumber()]++;
		}
		int two = 0;
		for(int i = 1; i <= 13; i++) {
			if(checking_arr[i] == 2) two++;
		}
		if(two == 1) return true;
		else return false;
	}
	
}