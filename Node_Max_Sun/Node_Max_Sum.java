import compile.TreeFactory;
import ast.Node;
import ast.IntNode;
import ast.ListNode;

public class Node_Max_Sum {
 
	public static int max(Node node)
	{
		int max = Integer.MIN_VALUE;
		IntNode intNode = null;
		ListNode listNode = null;
		
		if(node.getClass() == ListNode.class)
		{
			listNode = (ListNode) node;
			
			if(listNode.value != null)
			{
				int temp = max(listNode.value);
				max = Math.max(temp, max);
			}
			
			if(listNode.getNext() != null)
			{
				int temp = max(listNode.getNext());
				max = Math.max(temp, max);
			}
		}
		else if(node.getClass() == IntNode.class)
		{
			intNode = (IntNode) node;
			
			max = intNode.value;
			
			if(intNode.getNext() != null)
			{
				int temp = max(intNode.getNext());
				max = Math.max(max, temp);
			}
		}		
		
		return max;
	}
	
	public static int sum(Node node)
	{
		IntNode intNode = null;
		ListNode listNode = null;
		int totalValue = 0;
		
		if(node.getClass() == ListNode.class)
		{
			listNode = (ListNode) node;
			
			if(listNode.value != null)
			{
				totalValue += sum(listNode.value);
			}
			
			if(listNode.getNext() != null)
			{
				totalValue += sum(listNode.getNext());
			}
		}
		else if(node.getClass() == IntNode.class)
		{
			intNode = (IntNode) node;
			totalValue = intNode.value;
			
			if(intNode.getNext() != null)
			{
				totalValue += sum(intNode.getNext());
			}
		}
		
		return totalValue;
	}
	
	public static void main(String[] args)
	{
		Node node = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		
		System.out.println("max°ª: " + max(node));
		System.out.println("sum°ª: " + sum(node));
		
	}
}
