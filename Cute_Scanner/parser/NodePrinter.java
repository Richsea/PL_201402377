package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NodePrinter {
	private final String OUTPUT_FILENAME = "output06.txt";
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
	}
	private void printNode(QuoteNode quoteNode)
	{
		if(quoteNode.nodeInside() == null)
			return;
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오
	}
	private void printNode(Node node)
	{
		if(node == null)
			return;
		// 이후 부분을 주어진 출력 형식에 맞게 코드를 작성하시오
	}
	/*
	private void printNode(Node head) {
		if(head == null)
			return;
		
		if(head instanceof ListNode) {
			ListNode ln = (ListNode)head;
			printList(ln.value);
		}else {
			sb.append("[" + head + "]");
		}
		
		printNode(head.getNext());
	}
	*/
	
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
