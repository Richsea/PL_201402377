package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NodePrinter {
	private final String OUTPUT_FILENAME = "output08.txt";
	private StringBuffer sb = new StringBuffer();
	private Node root;
	
	public NodePrinter(Node root)
	{
		this.root = root;
	}
	
	private void printList(ListNode listNode)
	{
		if(listNode == ListNode.EMPTYLIST)
		{
			return;
		}
		
		// QuoteNode인 경우 출력
		if(listNode.car() instanceof QuoteNode)
		{			
			while(listNode != ListNode.EMPTYLIST)
			{
				printNode(listNode.car());
				listNode = listNode.cdr();
			}
			return;
		}
		
		// QuoteNode 이외의 경우 출력
		sb.append("( ");
		while(listNode != ListNode.EMPTYLIST)
		{
			printNode(listNode.car());
			listNode = listNode.cdr();
		}
		sb.append(") ");
	}
	
	private void printNode(QuoteNode quoteNode)
	{
		if(quoteNode.nodeInside() == null)
			return;
		
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오
		sb.append("'");
		
		if(((ListNode)quoteNode.nodeInside()).car() == null)
			sb.append("( ) ");
		
		printNode(quoteNode.nodeInside());
	}
	
	private void printNode(Node node)
	{
		if(node == null)
			return;
		
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오
		if(node instanceof ListNode)
		{
			printList((ListNode)node);
		}
		else if(node instanceof QuoteNode)
		{
			printNode((QuoteNode)node);
		}
		else
		{			
			sb.append(node + " ");
		}
	}
	
	public void prettyPrint() {
		printNode(root);
		
		System.out.println("..." + sb.toString());
		try(FileWriter fw = new FileWriter(OUTPUT_FILENAME);
			
				PrintWriter pw = new PrintWriter(fw)){
			pw.write(sb.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
