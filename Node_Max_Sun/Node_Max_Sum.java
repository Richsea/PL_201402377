import compile.TreeFactory;
import ast.Node;
import ast.IntNode;
import ast.ListNode;

public class Node_Max_Sum {
 
	public static int max(Node node)
	{
		int max = Integer.MIN_VALUE;
		Node start = node;
		IntNode intNode = null;
		ListNode listNode = null;
		
		if(node.getClass() == ListNode.class)
		{
			listNode = (ListNode) node;
			
			if(listNode.value.getClass() != null)
			{
				int temp = max(listNode.value);
				max = Math.max(temp, max);
			}
			
			if(listNode.getNext() == null)
			{
				return max;
			}
			else
			{
				int temp = max(listNode.getNext());
				max = Math.max(temp, max);
			}
		}
		else if(node.getClass() == IntNode.class)
		{
			intNode = (IntNode) node;
			
			max = intNode.value;
			
			if(intNode.getNext() == null)
			{
				return max;
			}
			else
			{
				int temp = max(intNode.getNext());
				max = Math.max(max, temp);				
			}
		}		
		
		return max;
	}
	
	public static int sum(Node node)
	{
		return 0;
	}
	
	public static void main(String[] args)
	{
		Node node = TreeFactory.createtTree("( 3 ( 5 2 3 ) -378 )");
		//Node node = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		
		System.out.println(max(node));
		
	}
}
