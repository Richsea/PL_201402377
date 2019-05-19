package interpreter;

import java.io.File;

import parser.*;

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
		else if(rootExpr instanceof IntNode)
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
		//CAR, CDR, CONS� ���� ���� ����
		case CAR:
			if(operand.car() instanceof QuoteNode)
			{
				Node inNode = ((ListNode)((QuoteNode)operand.car()).nodeInside()).car();
				if(inNode instanceof ListNode)
					return new QuoteNode(inNode);
				else
					return inNode;
			}
			else	// functionNode�� ���� ��
			{
				return ((ListNode)((QuoteNode)runExpr(operand)).nodeInside()).car();
			}
			
		case CDR:
			if(operand.car() instanceof QuoteNode)
			{				
				return new QuoteNode(((ListNode)((QuoteNode)operand.car()).nodeInside()).cdr());
			}
			else	// functionNode�� ���� ��
			{
				return new QuoteNode(((ListNode)((QuoteNode)runExpr(operand)).nodeInside()).cdr());
			}
			
		case CONS:
			Node headNode = operand.car();
			Node tailNode = operand.cdr().car();
			
			/*
			 * headNode�� �Ǵ� �κ� ó��
			 */
			if(headNode instanceof ListNode)	//QuoteNode, functionNode ��� ListNode�� ����
			{
				headNode = runExpr(headNode);
				
				if(headNode instanceof QuoteNode)
				{
					headNode = ((QuoteNode)headNode).nodeInside();
				}
				else if(headNode instanceof ListNode)	// QuteNode�� �ƴ� ListNode�� ��� headNode.car()�� �ݵ�� QuoteNode�̴�.
				{
					headNode = ((QuoteNode)((ListNode)headNode).car()).nodeInside();
				}
			}
			else
			{
				headNode = runExpr(headNode);
			}
		
			/*
			 * tailNode�� �Ǵ� �κ� ó��
			 */
			if(tailNode instanceof ListNode)	//QuoteNode, functionNode ��� ListNode�� ����
			{
				tailNode = runExpr(tailNode);
				
				if(tailNode instanceof QuoteNode)
				{
					tailNode = ((QuoteNode)tailNode).nodeInside();
				}
				else if(tailNode instanceof ListNode)
				{
					tailNode = ((QuoteNode)((ListNode)tailNode).car()).nodeInside();
				}
			}
			
			/*
			 * cons ������ �� �� tailNode�� ListNode�� �ƴ� ���
			 */
			if(!(tailNode instanceof ListNode))
			{
				tailNode = ListNode.cons(runExpr(tailNode), ListNode.EMPTYLIST);
			}
			
			return new QuoteNode(ListNode.cons(headNode, (ListNode)tailNode));
			
		case NULL_Q:
			if(((ListNode)(((QuoteNode)operand.car()).nodeInside())).car() == null)
			{
				return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;
			
		case ATOM_Q:
			Node atomNode = runExpr(((QuoteNode)operand.car()).nodeInside());
			if(atomNode instanceof ListNode)	// List�� ��
			{				
				if(((ListNode)atomNode).car() == null)	
					return BooleanNode.TRUE_NODE;
				
				return BooleanNode.FALSE_NODE;
			}
			else if(atomNode instanceof QuoteNode)	// car, cdr, cons�� ���� function ������ ������ ��
			{
				Node insideNode = ((QuoteNode)atomNode).nodeInside();
				if(insideNode instanceof ListNode)
				{
					if(((ListNode)insideNode).car() == null)	
						return BooleanNode.TRUE_NODE;
					
					return BooleanNode.FALSE_NODE;
				}
				
				return BooleanNode.TRUE_NODE;
				
			}
			
			return BooleanNode.TRUE_NODE;	// ValuNode�� ���
			
		case EQ_Q:
			
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
		
		//������������ �ʿ��� ���� �� �Լ� �۾� ����
		switch(operator.binType)
		{
		//+,-./ � ���� ���̳ʸ� ���� ���� ����
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
