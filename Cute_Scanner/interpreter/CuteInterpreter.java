package interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import parser.*;

public class CuteInterpreter {
	public static void main(String[] args)
	{
		while(true) {
			InputStreamReader input = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(input);
			StringBuffer sb = new StringBuffer();
			
			System.out.print("> ");
			try {
				sb.append(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			CuteParser cuteParser = new CuteParser(sb);
			CuteInterpreter interpreter = new CuteInterpreter();
			Node parseTree = cuteParser.parseExpr();
			Node resultNode = interpreter.runExpr(parseTree);
			NodePrinter nodePrinter = new NodePrinter(resultNode);
			nodePrinter.prettyPrint();
		}
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
		{
			Node definedList = ((IdNode)rootExpr).lookupTable();
			if(definedList != null)
			{
				return definedList;
						//this.runExpr(definedList);
			}
			return rootExpr;
		}
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
		Node headNode = operand.car();
		Node tailNode = operand.cdr().car();
		
		if(headNode instanceof IdNode)
		{
			headNode = runExpr(headNode);
			
			if(headNode instanceof ListNode)
				headNode = ((ListNode)runExpr(headNode)).car();
		}
		
		if(tailNode instanceof IdNode)
		{
			tailNode = runExpr(tailNode);
			
			if(tailNode instanceof ListNode)
				tailNode = ((ListNode)runExpr(tailNode)).car();
		}
		
		switch(operator.funcType)
		{
		//CAR, CDR, CONS등에 대한 동작 구현
		case CAR:			
			if(headNode instanceof QuoteNode)
			{
				Node inNode = runExpr(((QuoteNode)headNode).nodeInside());
								
				/*
				 * runExpr의 결과로 QuoteNode 내부의 List가 그대로 반환된 경우 ( car() 처리 작업 필요)
				 */
				if(inNode instanceof ListNode)
				{
					inNode = ((ListNode)inNode).car();
				}
				
				/*
				 * 결과 출력을 위한 process
				 */
				if(inNode instanceof ListNode)
					return new QuoteNode(inNode);
				else if(inNode instanceof IdNode)
				{
					return new QuoteNode(inNode);
				}
				else
					return inNode;
			}
			else	// functionNode가 나올 때
			{
				return ((ListNode)((QuoteNode)runExpr(operand)).nodeInside()).car();
			}
			
		case CDR:		
			if(headNode instanceof QuoteNode)
			{
				Node inNode = runExpr(((QuoteNode)headNode).nodeInside());
				
				if(inNode instanceof ListNode)
				{
					return new QuoteNode(((ListNode)inNode).cdr());
				}
				else
					return new QuoteNode(ListNode.cons(ListNode.EMPTYLIST, ListNode.EMPTYLIST));
			}
			else	// cdr 내부에  cdr이 선언될 때
			{
				return new QuoteNode(((ListNode)((QuoteNode)runExpr(operand)).nodeInside()).cdr());
			}
			
		case CONS:			
			/*
			 * headNode가 되는 부분 처리
			 */
			if(headNode instanceof ListNode)	//QuoteNode, functionNode 모두 ListNode로 구성된 경우
			{
				headNode = runExpr(headNode);
				
				if(headNode instanceof QuoteNode)
				{
					headNode = ((QuoteNode)headNode).nodeInside();
				}
				else if(headNode instanceof ListNode)	// QuteNode가 아닌 ListNode일 경우 headNode.car()은 반드시 QuoteNode이다.
				{
					headNode = ((QuoteNode)((ListNode)headNode).car()).nodeInside();
				}
			}
			else if(headNode instanceof QuoteNode)	// QuoteNode로 구성된 경우
			{
				headNode = ((QuoteNode)headNode).nodeInside();
			}
			else
			{
				headNode = runExpr(headNode);
			}
		
			/*
			 * tailNode가 되는 부분 처리
			 */
			if(tailNode instanceof ListNode)	//QuoteNode, functionNode 모두 ListNode로 구성
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
			else if(tailNode instanceof QuoteNode)
			{
				tailNode = ((QuoteNode)tailNode).nodeInside();
			}
			
			/*
			 * cons 과정을 할 때 tailNode가 ListNode가 아닐 경우
			 */
			if(!(tailNode instanceof ListNode))
			{
				tailNode = ListNode.cons(runExpr(tailNode), ListNode.EMPTYLIST);
			}
			
			return new QuoteNode(ListNode.cons(headNode, (ListNode)tailNode));
			
		case NULL_Q:
			if(!(headNode instanceof QuoteNode))
			{
				return BooleanNode.FALSE_NODE;
			}
			
			if(((ListNode)(((QuoteNode)headNode).nodeInside())).car() == null)
			{
				return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;
			
		case ATOM_Q:			
			Node atomNode;
			if(headNode instanceof QuoteNode)
			{
				atomNode = runExpr(((QuoteNode)headNode).nodeInside());
			}
			else
			{
				atomNode = headNode;
			}
			
			if(atomNode instanceof ListNode)	// List일 때
			{				
				if(((ListNode)atomNode).car() == null)	
					return BooleanNode.TRUE_NODE;
				
				return BooleanNode.FALSE_NODE;
			}
			else if(atomNode instanceof QuoteNode)	// car, cdr, cons와 같은 function 연산이 존재할 때
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
			
			return BooleanNode.TRUE_NODE;	// ValuNode일 경우
			
		case EQ_Q:			
			/*
			 * ValueNode일 경우 바로 비교
			 */
			if(headNode instanceof ValueNode && tailNode instanceof ValueNode)
			{
				if(headNode.toString().equals(tailNode.toString()))
					return BooleanNode.TRUE_NODE;
				return BooleanNode.FALSE_NODE;
			}
			
			/*
			 * ListNode일 경우 처리
			 */
			if(headNode instanceof ListNode)
			{				
				if(((ListNode)headNode).car() instanceof QuoteNode)
					headNode = runExpr(((QuoteNode)((ListNode)headNode).car()).nodeInside());
				else
					headNode = runExpr(((ListNode)headNode));
			}
			if(tailNode instanceof ListNode)
			{
				if(((ListNode)tailNode).car() instanceof QuoteNode)
					tailNode = runExpr(((QuoteNode)((ListNode)tailNode).car()).nodeInside());
				else
					tailNode = runExpr(((ListNode)tailNode));	
			}
									
			/*
			 * 진행 결과가 ValueNode인지 확인
			 */
			if(headNode instanceof ValueNode && tailNode instanceof ValueNode)
			{
				if(headNode.toString().equals(tailNode.toString()))
					return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;
			
		case NOT:		
			if(headNode instanceof BinaryOpNode)
				headNode = runExpr(operand);
			
			if(headNode == BooleanNode.TRUE_NODE)
				return BooleanNode.FALSE_NODE;
			
			return BooleanNode.TRUE_NODE;
			
		case COND:	
			if(runExpr(((ListNode)headNode).car()) == BooleanNode.TRUE_NODE)
			{
				return runExpr(((ListNode)headNode).cdr().car());
			}
			
			if(runExpr(((ListNode)tailNode).car()) == BooleanNode.TRUE_NODE)
			{
				return runExpr(((ListNode)tailNode).cdr().car());
			}
			
			return null;
			
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
		Node firstItem;
		Node secondItem;
	
		firstItem = runExpr((list.cdr().car()));
		secondItem = runExpr((list.cdr().cdr().car()));
		
		int data1 = Integer.parseInt(firstItem.toString());
		int data2 = Integer.parseInt(secondItem.toString());
		
		//구현과정에서 필요한 변수 및 함수 작업 가능
		switch(operator.binType)
		{
		//+,-./ 등에 대한 바이너리 연산 동작 구현
		
		case PLUS:	
			return new IntNode(String.valueOf(data1+data2));
			
		case MINUS:
			return new IntNode(String.valueOf(data1-data2));
			
		case TIMES:
			return new IntNode(String.valueOf(data1*data2));
			
		case DIV:
			return new IntNode(String.valueOf(data1/data2));
			
		case GT:
			if(data1 > data2)
				return BooleanNode.TRUE_NODE;
			return BooleanNode.FALSE_NODE;
			
		case LT:
			if(data1 < data2)
				return BooleanNode.TRUE_NODE;
			return BooleanNode.FALSE_NODE;
			
		case EQ:
			if(data1 == data2)
				return BooleanNode.TRUE_NODE;
			return BooleanNode.FALSE_NODE;
			
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
