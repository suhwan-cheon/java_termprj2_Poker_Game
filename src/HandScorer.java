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
		
		
		
		
		// 그냥 생각하는게 귀찮아서 족보 전부 만들었어..
		// 새벽에 짜서 조금 지저분하긴 한데.. 의미 없겠지ㅋㅋㅋㅋㅋ
		// 내 코드의 전제 조건! : sort가 오름차순으로 되어있음! && a가 1임! && getSuit() method는 어떤 모양인지(스다하클) 알려주는 메소드임!
		
		// 각 족보 사이의 점수를 100점씩 두었기 때문에 족보끼리 겹칠 일 없음
		// 조건이 두가지라면 +50, 세가지라면 +30 ... 이건 좀 설명하기 어려운데 짜다보니까 필요해서
		
		//로얄 스트레이트 플러쉬 일때 - 기본 20000점
		if(Royal_Straight_Flush()) {
			score = 20000;
			// 로얄 스트레이트 플러쉬는 무조건! 모양에 의해서만 순위 갈림
			score += hand[0].getSuit();
		}
		else if(Straight_Flush()) {
			score = 18000;
			// 가장 높은 숫자 가중치 더해주기
			if(hand[0].getNumber() == 1) score += 1000; // 최대 수치, 
			else score += hand[4].getNumber()*50; // 50을 곱해주는 이유는 다음 조건에 영향을 받지 않기 위해 (여기서 1만 차이나도 점수가 50점이 차이가 나기 때문에 다음 조건에 영향을 받지 않음)
			// 모양 가중치 더해주기
			score += hand[0].getSuit();
		}
		else if(Four_Card()) {
			score = 16000;
			// 포카드는 무조건 숫자에 의해 승패를 가리므로. hand[2]는 무조건 4장의 카드 중 하나이다.
			if(hand[2].getNumber() == 1) score += 1818; // 1이면 무조건 win!
			else score += hand[2].getNumber();
		}
		else if(Full_House()) {
			score = 14000;
			// 풀하우스는 3장의 카드 중 높은 것만 비교하면 됨 (절대 최대값이 같은 풀하우스가 나올 수 없음!)
			if(hand[2].getNumber() == 1) score += 1818; // 1이면 무조건 win!
			else score += hand[2].getNumber();
		}
		else if(Flush()) {
			score = 12000;
			// 플러쉬는 모양부터 비교한다
			score += (hand[0].getSuit()*50);
			// 모양이 같다면, 최대 숫자를 가지고 있는 사람이 승자!
			if(hand[4].getNumber() == 1) score += 14;
			score += hand[4].getNumber();			
		}
		else if(Straight()) {
			score = 8000;
			// 먼저 마운틴이 1등.
			if(hand[0].getNumber()==1 && hand[4].getNumber() == 13) score += 3500;
			else if(hand[0].getNumber() == 1) score += 3000; // 시작점이 1이고 마운틴이 아니라면 백스트레이트임
			else score += (hand[4].getNumber()*100);
			// 만약 가장 높은 숫자가 상대방과 같다면, 가장 높은 숫자 카드의 모양끼리 비교
			score += hand[4].getSuit();
		}
		else if(Triple()) {
			score = 6000;
			// 트리플은 무조건 가장 높은 숫자에 의해서만 결정
			if(hand[2].getNumber() == 1) score += 1818; // A 트리플은 그냥 승!
			else score += hand[2].getNumber();
		}
		else if(TwoPair()) {
			score = 4000;
			// 신기하게도, 정렬이 되있다면 어떠한 경우든 hand[1]은 작은 pair에, hand[3]은 큰 pair에 속하게 된다. (그림 그려서 해보니까 그렇더라구요..)
			// A 페어가 존재할때
			if(hand[1].getNumber() == 1) {
				score += 14*100;
				score += (hand[3].getNumber()*5);
				score += hand[1].getSuit();
			}
			else { // A 페어가 존재하지 않을 때
				score += (hand[3].getNumber()*100); // 아래 조건 최대 13*5점이므로 *100
				score += (hand[1].getNumber()*5); // 아래 조건 최대 4점이므로 *5
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
			// one에는 pair의 정보가, next_max_num 에는 pair를 제외한 가장 큰 수가 저장
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
	
	// Card 클래스의 number는 숫자, suit은 모양, CardId는 42까지로 표현한 카드
	
	// 로얄 스트레이트 플러쉬인지 확인
	boolean Royal_Straight_Flush() {
		// 첫 카드의 모양을 알아온다.
		int shape = hand[0].getSuit();
		int cmp = 10; // sort 되있으므로 만약 로얄 스트레이트 플러쉬라면 첫 카드는 반드시 10이여야 한다.
		for(int i = 0; i < 5; i++) {
			if(hand[i].getSuit() == shape && hand[i].getNumber() == cmp) { // 모든 모양이 같아야 하고 순서가 
				cmp++; //그 다음 숫자는 11이여야 하므로
			}
			else return false;
		}
		return true;
	}
	
	// 스트레이트 플러쉬인지 확인
	boolean Straight_Flush() {
		// 로얄과 비슷하나 10부터 시작하지 않아도 된다. 즉 첫 숫자부터 차례대로 1씩 증가하면 된다
		// 첫 카드의 모양을 알아온다.
		int shape = hand[0].getSuit();
		int cmp = hand[0].getNumber();
		for(int i = 0; i < 5; i++) {
			if(hand[i].getNumber() == shape && hand[i].getNumber() == cmp) { // 모든 모양이 같아야 하고 순서가 
				cmp++;
			}
			else return false;
		}
		return true;
	}
	
	// 포카드인지 확인
	boolean Four_Card() {
		// 포카드인지 확인하는 방법은 두가지 경우가 있다. (정렬되어 있으므로)
		// 1. 4장의 숫자 < 1장의 숫자    2. 4장의 숫자 > 1장의 숫자
		if(hand[0].getNumber()==hand[1].getNumber()){ //첫 카드와 두 번째 카드의 숫자가 같은 경우 -> 1번 케이스
			int cmp = hand[0].getNumber();
			if(hand[2].getNumber()== cmp && hand[3].getNumber()==cmp && hand[4].getNumber()!=cmp) { // 앞에서 4개의 카드는 같고(포카드) 나머지는 무조건 달라야 함! 안그러면 그냥 스트레이트임!
				return true;
			}
		}
		else if(hand[0].getNumber()!=hand[1].getNumber()){//첫 카드와 두 번째 카드의 숫자가 다른 경우 -> 2번 케이스
			int cmp = hand[1].getNumber();
			if(hand[2].getNumber()== cmp && hand[3].getNumber()==cmp && hand[4].getNumber()==cmp) { // 뒤의 4개의 카드는 모두 같은 숫자면 된다.
				return true;
			}
		}
		else return false;
		
		return false;
	}
	
	// 풀하우스인지 확인
	boolean Full_House() {
		// 풀하우스인지 확인 하는 방법은 두가지 경우가 있다. (정렬되어 있으므로)
		// 1. 앞의 2장, 뒤의 3장이 같은 카드일 때   2. 뒤의 2장, 앞의 3장이 같을 카드일 때
		if(hand[2].getNumber()!=hand[1].getNumber()) { // 1번 케이스
			int cmp = hand[2].getNumber();
			if(hand[0].getNumber() == hand[1].getNumber() && hand[3].getNumber() == cmp && hand[4].getNumber() == cmp) { // 2개 3개 같은지 확인
				return true;
			}
		}
		else if(hand[2].getNumber()==hand[1].getNumber()) {// 2번 케이스
			int cmp = hand[2].getNumber();
			if(hand[3].getNumber() == hand[4].getNumber() && hand[0].getNumber() == cmp && hand[1].getNumber() == cmp) { // 3개 2개 같은지 확인
				return true;
			}
		}
		else return false;
		
		return false;
	}
	
	// 플러쉬인지 확인
	boolean Flush() {
		int shape = hand[0].getSuit();
		for(int i = 0; i < 5; i++) {
			if(shape != hand[i].getSuit()) return false;
		}
		return true;
	}
	
	// 스트레이트인지 확인
	boolean Straight() {
		int cmp = hand[0].getNumber();
		// 마운틴은 특별한 경우 이므로 확인해줘야 됨!
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
	
	// 트리플인지 확인
	boolean Triple() {
		// 트리플인지 확인하는 방법에는 3가지가 있다.
		// 1. *** * *    2. * *** *   3. * * ***
		int cmp = hand[2].getNumber(); // 위의 케이스를 통해 3번째 패는 반드시 트리플에 속해있음을 알 수 있다. (정렬되어있으니까!)
		// 첫 번째 케이스
		if(hand[0].getNumber() == cmp && hand[1].getNumber() == cmp && hand[3].getNumber() != cmp && hand[4].getNumber() != cmp && hand[3].getNumber() != hand[4].getNumber()) { // 이 코드 짜면서 느낀건데 그냥 미리 전역변수로 받아놓을걸..
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
	
	// 투페어인지 확인
	boolean TwoPair() {
		// 음.. 이코드 짜다가 갑자기 떠오른 건데 그냥 배열에 넣고 비교하면 가장 편하지 않나..? 위에 너무 미련하게 짰다 ㅋㅋㅋㅋ
		// 어차피 이 코드는 투페어인지 아닌지만 판단해주면 되기 때문에
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