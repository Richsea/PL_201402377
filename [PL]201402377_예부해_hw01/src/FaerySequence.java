import java.util.LinkedList;
import java.util.Scanner;

public class FaerySequence {
	public static void main(String args[])
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("input: ");
		int count = scanner.nextInt();
		
		if(count < 1 || count > 100)
		{
			System.out.print("1에서 100 사이의 숫자를 해야합니다");
		}
		
		FaerySequenceClass faery = new FaerySequenceClass();
		faery.startFaeryOrder(count);
	}
}

/*
 * 페리함수를 실행하는 클래스
 */
class FaerySequenceClass{
	/*
	 * 페리함수 클래스 생성자에서 F1일 경우를 처리한다
	 */
	FaerySequenceClass()
	{
		print = new PrintConsole();
		list = new LinkedList<Node>();
		
		Node node1 = new Node(0, 1);
		Node node2 = new Node(1, 1);
		list.add(node1);
		list.add(node2);
		
		print.printFn(list, 0);
	}
	
	PrintConsole print;
	LinkedList<Node> list;
	
	
	/*
	 * F2~FN까지의 출력을 위한 반복문을 실행하는 함수
	 */
	void startFaeryOrder(int count)
	{
		f1ToFn(list, count, count);
	}
	
	/*
	 * 출력하기 위한 재귀를 실행하는 함수
	 */
	private LinkedList<Node> f1ToFn(LinkedList<Node> list, int currentStart, int count)
	{
		if(currentStart == 1)
		{
			return list;
		}
		
		list = f1ToFn(list, currentStart-1, count);
		list = doFaerySequence(list, currentStart, 0, list.getLast());
		
		print.printFn(list, currentStart-1);
		
		return list;
	}
	
	/*
	 * 페리함수의 알고리즘(재귀)을 실행하는 함수
	 */
	private LinkedList<Node> doFaerySequence(LinkedList<Node> list, int number, int start, Node lastNode) {
		
		if(list.get(start) == lastNode)
		{
			return list;
		}
		
		LinkedList<Node> Fn = new LinkedList<Node>();
		
		Node startNode = list.get(start);
		Node endNode = list.get(start+1);
		
		Fn = doFaerySequence(list, number, start+1, lastNode);
		
		if(startNode.getDenom() + endNode.getDenom() <= number)
		{
			Node newNode = new Node();
			newNode.setNumer(startNode.getNumer() + endNode.getNumer());
			newNode.setDenom(startNode.getDenom() + endNode.getDenom());
			Fn.add(start+1, newNode);
		}	

		return Fn;
	}
}

/*
 * print하는 클래스
 */
class PrintConsole{
	
	/*
	 * 출력 형식에 맞춰 콘솔에 Fn을 출력
	 */
	void printFn(LinkedList<Node> list, int number)
	{
		Node node;
		System.out.print("f" + (number+1) + ":[");
		
		int i = 0;
		
		while(i < list.size()-1)
		{
			node = list.get(i);
			System.out.print(node.getNumer() + "/" + node.getDenom() + ", ");
			i++;
		}
		node = list.get(i);
		System.out.println(node.getNumer() + "/" + node.getDenom() + "]");
	}
	
}

/*
 * LinkedList 사용을 위한 Node 클래스
 */
class Node{
	private int denominator;	//분모
	private int numerator;		//분자
	
	Node(){
		
	}
	
	Node(int numer, int denom)
	{
		this.denominator = denom;
		this.numerator = numer;
	}
	
	void setNumer(int num)
	{
		numerator = num;
	}
	void setDenom(int num)
	{
		denominator = num;
	}
	int getNumer()
	{
		return numerator;
	}
	int getDenom()
	{
		return denominator;
	}
}