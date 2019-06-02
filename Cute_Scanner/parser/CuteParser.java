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
			
		// FunctionNode에 대하여 작성
		// 키워드가 FunctionNode에 해당
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
			// 내용 채우기
			
		//BooleanNode에 대하여 작성
		case FALSE:
			return BooleanNode.FALSE_NODE;
		case TRUE:
			return BooleanNode.TRUE_NODE;
			
		// case L_PAREN일 경우와 case R_PAREN일 경우에 대해서 작성
		// L_PAREN일 경우 parseExprList()를 호출하여 처리
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
	
	//List의 value를 생성하는 메소드
	private ListNode parseExprList() {
		Node head = parseExpr();
		
		if(head == null)
			return null;
		if(head instanceof FunctionNode)
		{
			if(((FunctionNode)head).funcType == FunctionNode.FunctionType.DEFINE)
			{
				//define에 심벌테이블 추가
				ListNode define = parseExprList();
				// head가 DEFINE임
				// tail로 온데이터가 , a 1 같은 list 데이터 존재
				//define = ListNode.cons(head, define);	?? head는 DEFINE
				
				Node symbolName = define.car();
				Node symbolDefine = define.cdr();	// .cdr().car()로 하면 define plus1 (lambda (x) (+ x 1) 과 같이 정의될 때 문제 발생
				
				if(!(symbolName instanceof IdNode))
				{
					System.out.println("Parsing Error!");
					return null;
				}
				
				((IdNode)symbolName).addDefine(symbolDefine);
				
				
				/*
				 *  idNode에 다음에 오는 데이터 추가
				 *  symbolName이 Hash의 key
				 *  symbolDeinfe이 hash의 elements
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
