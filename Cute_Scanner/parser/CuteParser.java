package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import lexer.Scanner;
import lexer.Token;
import lexer.TokenType;

public class CuteParser {
	private Iterator<Token> tokens;
	public static Node END_OF_LIST = new Node() {};
	
	public CuteParser(File file)
	{
		try {
			tokens = Scanner.scan(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public CuteParser(StringBuffer sb)
	{
		try {
			tokens = Scanner.scan(sb);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	private Token getNextToken() {
		if(!tokens.hasNext())
			return null;
		return tokens.next();
	}
	
	public Node parseExpr()
	{
		Token t = getNextToken();
		if(t == null)
		{
			System.out.println("No more token");
			return null;
		}
		TokenType tType = t.type();
		String tLexeme = t.lexme();
		
		switch(tType)
		{
		case ID:
			return new IdNode(tLexeme);
			
		case INT:
			if(tLexeme == null)
				System.out.println("???");
			return new IntNode(tLexeme);
			
		case DIV:
		case EQ:
		case MINUS:
		case GT:
		case PLUS:
		case TIMES:
		case LT:
			BinaryOpNode biNode = new BinaryOpNode();
			if(tLexeme == null)
				System.out.println("???");
			biNode.setValue(tType);
			return biNode;
			
		// FunctionNode�� ���Ͽ� �ۼ�
		// Ű���尡 FunctionNode�� �ش�
		case ATOM_Q:
		case CAR:
		case CDR:
		case COND:
		case CONS:
		case DEFINE:
		case EQ_Q:
		case LAMBDA:
		case NOT:
		case NULL_Q:
			FunctionNode fNode = new FunctionNode();
			if(tLexeme == null)
				System.out.println("???");
			fNode.setValue(tType);
			return fNode;
			// ���� ä���
			
		//BooleanNode�� ���Ͽ� �ۼ�
		case FALSE:
			return BooleanNode.FALSE_NODE;
		case TRUE:
			return BooleanNode.TRUE_NODE;
			
		// case L_PAREN�� ���� case R_PAREN�� ��쿡 ���ؼ� �ۼ�
		// L_PAREN�� ��� parseExprList()�� ȣ���Ͽ� ó��
		case L_PAREN:
			return parseExprList();
			
		case R_PAREN:
			return END_OF_LIST;
			
		case APOSTROPHE:
			QuoteNode quoteNode = new QuoteNode(parseExpr());
			ListNode listNode = ListNode.cons(quoteNode, ListNode.EMPTYLIST);
			return listNode;
			
		case QUOTE:
			return new QuoteNode(parseExpr());
			
		default:
			System.out.println("Parsing Error!");
			return null;
		}
	}
	
	//List�� value�� �����ϴ� �޼ҵ�
	private ListNode parseExprList() {
		Node head = parseExpr();
		
		if(head == null)
			return null;
		if(head instanceof FunctionNode)
		{
			if(((FunctionNode)head).funcType == FunctionNode.FunctionType.DEFINE)
			{
				//define�� �ɹ����̺� �߰�
				ListNode define = parseExprList();
				// head�� DEFINE��
				// tail�� �µ����Ͱ� , a 1 ���� list ������ ����
				//define = ListNode.cons(head, define);	?? head�� DEFINE
				
				Node symbolName = define.car();
				Node symbolDefine = define.cdr();	// .cdr().car()�� �ϸ� define plus1 (lambda (x) (+ x 1) �� ���� ���ǵ� �� ���� �߻�
				
				if(!(symbolName instanceof IdNode))
				{
					System.out.println("Parsing Error!");
					return null;
				}
				
				((IdNode)symbolName).addDefine(symbolDefine);
				
				
				/*
				 *  idNode�� ������ ���� ������ �߰�
				 *  symbolName�� Hash�� key
				 *  symbolDeinfe�� hash�� elements
				 */
				
				return ListNode.EMPTYLIST;
			}
		}
		if(head == END_OF_LIST)	// if next token is R_PAREN
			return ListNode.EMPTYLIST;
		ListNode tail = parseExprList();
		
		if(tail == null)
			return null;
		
		return ListNode.cons(head, tail);
	}
}
