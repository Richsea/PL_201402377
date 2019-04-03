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
			System.out.print("1���� 100 ������ ���ڸ� �ؾ��մϴ�");
		}
		
		FaerySequenceClass faery = new FaerySequenceClass();
		faery.startFaeryOrder(count);
	}
}

/*
 * �丮�Լ��� �����ϴ� Ŭ����
 */
class FaerySequenceClass{
	/*
	 * �丮�Լ� Ŭ���� �����ڿ��� F1�� ��츦 ó���Ѵ�
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
	 * F2~FN������ ����� ���� �ݺ����� �����ϴ� �Լ�
	 */
	void startFaeryOrder(int count)
	{
		f1ToFn(list, count, count);
	}
	
	/*
	 * ����ϱ� ���� ��͸� �����ϴ� �Լ�
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
	 * �丮�Լ��� �˰���(���)�� �����ϴ� �Լ�
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
 * print�ϴ� Ŭ����
 */
class PrintConsole{
	
	/*
	 * ��� ���Ŀ� ���� �ֿܼ� Fn�� ���
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
 * LinkedList ����� ���� Node Ŭ����
 */
class Node{
	private int denominator;	//�и�
	private int numerator;		//����
	
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