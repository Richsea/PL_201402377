package interpreter;

import java.io.File;

import parser.BinaryOpNode;
import parser.BooleanNode;
import parser.CuteParser;
import parser.FunctionNode;
import parser.IdNode;
import parser.ListNode;
import parser.Node;
import parser.NodePrinter;
import parser.ParserMain;
import parser.QuoteNode;

public class CuteInterpreter {
	public static void main(String[] args)
	{
		ClassLoader cloader = ParserMain.class.getClassLoader();
		File file = new File(cloader.getResource("interpreter/as08.txt").getFile());
		CuteParser cuteParser = new CuteParser(file);
		CuteInterpreter interpreter = new CuteInterpreter();
		Node parseTree = cuteParser.parseExpr();
		Node resultNode = interpreter.runExpr(parseTree);
		NodePrinter nodePrinter = new NodePrinter(resultNode);
		nodePrinter.prettyPrint();
	}
	
	private void errorLog(String err)
	{
		System.out.println(err);
	}
	
	public Node runExpr(Node rootExpr)
	{
		if(rootExpr == null)
			return null;
		if(rootExpr instanceof IdNode)
			return rootExpr;
		else if(rootExpr instanceof BooleanNode)
			return rootExpr;
		else if(rootExpr instanceof ListNode)
			return runList((ListNode) rootExpr);
		else
			errorLog("run Expr error");
		return null;
	}
	
	private Node runList(ListNode list)
	{
		if(list.equals(ListNode.EMPTYLIST))
			return list;
		if(list.car() instanceof FunctionNode)
		{
			return runFunction((FunctionNode)list.car(), (ListNode)stripList(list.cdr()));
		}
		if(list.car() instanceof BinaryOpNode)
		{
			return runBinary(list);
		}
		return list;
	}
	
	private Node runFunction(FunctionNode operator, ListNode operand)
	{
		switch(operator.funcType)
		{
		//CAR, CDR, CONS등에 대한 동작 구현
		case CAR:
		case CDR:
		case CONS:
		
		default:
			break;
		}
		return null;
	}
	
	private Node stripList(ListNode node)
	{
		if(node.car() instanceof ListNode && node.cdr() == ListNode.EMPTYLIST)
		{
			Node listNode = node.car();
			return listNode;
		}
		else
		{
			return node;
		}
	}
	
	private Node runBinary(ListNode list)
	{
		BinaryOpNode operator = (BinaryOpNode)list.car();
		
		//구현과정에서 필요한 변수 및 함수 작업 가능
		switch(operator.binType)
		{
		//+,-./ 등에 대한 바이너리 연산 동작 구현
		case PLUS:
		case MINUS:
		case TIMES:
		case DIV:
			
		default:
			break;
		}
		return null;
	}
	
	private Node runQuote(ListNode node)
	{
		return((QuoteNode) node.car()).nodeInside();
	}
}
