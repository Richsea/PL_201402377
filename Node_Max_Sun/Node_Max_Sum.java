import compile.TreeFactory;
import ast.Node;
import ast.IntNode;
import ast.ListNode;

public class Node_Max_Sum {
 
	public static int max(Node node)
	{
		int max = Integer.MIN_VALUE;
		Node start = node;
		IntNode intNode;
		ListNode list;
		
		Node newNode = new ListNode(start.getType(), start);
		
		System.out.println(start.getClass().getSimpleName());
		
		
		
		// value를 확인할 방법이 필요함
		
		/*
		 * 
		 * 
		if(start.value.getClass() == ListNode.class)
		{
		max = max(start.getNext());
		}
		else if(start.value.getClass() == IntNode.class)
		{
			
		}
		 * 
		if(start.getNext().getClass() == ListNode.class)
		{
			max = max(start.getNext());
		}
		else if(start.getNext().getClass() == null)
		{
			return max;
		}
		
		while(start.getNext() != null)
		{
			// start.getNext를 모두 돌린다.
		}
		
		while(start.getNext().getClass() == ListNode.class)
		{
			max = max(start.getNext());
		}
		while(start.getClass() == ListNode.class)
		
		while(start.getNext() != null)
		{
			System.out.println(start.getType() + ", " + start.toString());
			start = start.getNext();
		}
		*/
		return 0;
	}
	
	public static int sum(Node node)
	{
		return 0;
	}
	
	public static void main(String[] args)
	{
		Node node = TreeFactory.createtTree("( 3 ( 5 2 3 ) -378 )");
		//Node node = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		
		max(node);
		
	}
}
