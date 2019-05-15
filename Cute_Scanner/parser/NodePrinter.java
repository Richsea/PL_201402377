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
			return;
		}
		
		if(listNode.car() instanceof QuoteNode)
		{
			/*
			printNode(listNode.car());
			Node node = ((QuoteNode)listNode.car()).nodeInside();
			
			printNode(node);
			
			listNode = listNode.cdr();
			*/
			
			while(listNode != ListNode.EMPTYLIST)
			{
				printNode(listNode.car());
				listNode = listNode.cdr();
			}
			return;
		}
		
		if(listNode.car() instanceof FunctionNode)
		{
			FunctionNode fNode = (FunctionNode)listNode.car();
			if(fNode.funcType == FunctionNode.FunctionType.CAR || fNode.funcType == FunctionNode.FunctionType.CDR || fNode.funcType == FunctionNode.FunctionType.COND)
			{
				listNode = listNode.cdr();
				
				//QuoteNode 출력
				printNode(listNode.car());
				listNode = listNode.cdr();
								
				printNode(listNode.car());
				return;
			}
		}
		
		
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
		
		//Node node = quoteNode.nodeInside();
		
		if(((ListNode)this.root).car() instanceof FunctionNode)
		{
			FunctionNode fNode = (FunctionNode)((ListNode)this.root).car();
			if(fNode.funcType == FunctionNode.FunctionType.CAR)
			{
				printNode(((ListNode)quoteNode.nodeInside()).car());				
			}
			else if(fNode.funcType == FunctionNode.FunctionType.CDR)
			{
				if(((ListNode)quoteNode.nodeInside()).cdr() == ListNode.EMPTYLIST)
				{
					sb.append("( ) ");
				}
				printNode(((ListNode)quoteNode.nodeInside()).cdr());
			}
			else if(fNode.funcType == FunctionNode.FunctionType.COND)
			{
				sb.append("( ");
				printNode(((ListNode)quoteNode.nodeInside()).car());
				printNode(((ListNode)quoteNode.nodeInside()).cdr());
				sb.append(") ");
			}
			return;
		}
		
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
			sb.append(node);
		}
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
