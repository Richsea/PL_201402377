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
		
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오
		//if(listNode.car() instanceof QuoteNode)
		//{
		//	
		//}
		
		sb.append("(");
		
		while(!(listNode.car() instanceof ListNode))	// list가 아님
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
		
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오
		sb.deleteCharAt(sb.length()-1);		//"("삭제하는 코드
		sb.append("'");
		
		/*
		 * 문제점
		 * 1. ListNode로 나왔을 때 범위를 잘라야 한다.
		 * 2. "("와 ")" 제거 필요
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
