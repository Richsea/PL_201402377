package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NodePrinter {
	private final String OUTPUT_FILENAME = "output07.txt";
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
			sb.append("( )");
			return;
		}
		if(listNode == ListNode.ENDLIST)
		{
			return;
		}
				
		sb.append("( ");
		
		while(!(listNode.car() instanceof ListNode))	// ListNode�� �ƴϸ� ���ǹ� ����
		{
			if(listNode.car() instanceof QuoteNode)
			{
				printNode((QuoteNode)listNode.car());
				listNode = listNode.cdr();
					
				return;
			}
			
			printNode(listNode.car());
			listNode = listNode.cdr();
			
			if(listNode == ListNode.ENDLIST)
				break;
		}
		
		while(listNode != ListNode.ENDLIST)				// ListNode�� �ƴ� Node�� ó���ϱ� ���� ���ǹ�
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
		
		// ���� �κ��� �־��� ��� ���Ŀ� �°� �ڵ带 �ۼ��Ͻÿ�
		sb.delete(sb.length() - 2, sb.length());
		sb.append("'");
				
		printList((ListNode)quoteNode.nodeInside());
	}
	
	private void printNode(Node node)
	{
		if(node == null)
			return;
		
		// ���� �κ��� �־��� ��� ���Ŀ� �°� �ڵ带 �ۼ��Ͻÿ�
		if(node instanceof ListNode)
		{
			printList((ListNode)node);
		}
		else if(node instanceof QuoteNode)
		{
			printNode((QuoteNode)node);
		}
		else
			sb.append("[" + node + "] ");
	}
	
	public void prettyPrint() {
		printNode(root);
		
		try(FileWriter fw = new FileWriter(OUTPUT_FILENAME);
			
				PrintWriter pw = new PrintWriter(fw)){
			pw.write(sb.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
