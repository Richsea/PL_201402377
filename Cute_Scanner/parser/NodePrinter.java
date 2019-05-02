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
		
		// ���� �κ��� �־��� ��� ���Ŀ� �°� �ڵ带 �ۼ��Ͻÿ�
		//if(listNode.car() instanceof QuoteNode)
		//{
		//	
		//}
		
		sb.append("(");
		
		while(!(listNode.car() instanceof ListNode))	// list�� �ƴ�
		{
			printNode(listNode.car());
			listNode = listNode.cdr();
			
			if(listNode == ListNode.ENDLIST)
				return;
		}
		printNode(listNode.car());
		printNode(listNode.cdr().car());			
		
		sb.append(")");
	}
	private void printNode(QuoteNode quoteNode)
	{
		if(quoteNode.nodeInside() == null)
			return;
		
		// ���� �κ��� �־��� ��� ���Ŀ� �°� �ڵ带 �ۼ��Ͻÿ�
		sb.deleteCharAt(sb.length()-1);		//"("�����ϴ� �ڵ�
		sb.append("'");
		
		/*
		 * ������
		 * 1. ListNode�� ������ �� ������ �߶�� �Ѵ�.
		 * 2. "("�� ")" ���� �ʿ�
		 */
		
		Node nodeKind = ((ListNode)quoteNode).car();
		
		if(nodeKind instanceof ListNode)
		{
			//printList()
		}
		while(!(((ListNode)quoteNode).car() instanceof ListNode))
		{
		
		}
		sb.append("[" + quoteNode + "]");
		
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
			sb.append("[" + node + "]");
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
