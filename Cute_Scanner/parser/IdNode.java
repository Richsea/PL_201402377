package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import interpreter.CuteInterpreter;
import lexer.TokenType;
import parser.BinaryOpNode.BinType;

public class IdNode implements ValueNode{
	private String idString;
	
	private static Map<String, Node> insertTable = new HashMap<String, Node>();
	
	/*
	 * define을 추가하기 위한 함수
	 */
	void addDefine(ListNode node)
	{
		node = stringToDefine(node);
		
		if(node == null) return;
		insertTable.put(idString, node.car());
	}
	public Node lookupTable()
	{
		return insertTable.get(idString);
	}
	
	private ListNode stringToDefine(ListNode node)
	{
		if(node == ListNode.EMPTYLIST) return ListNode.EMPTYLIST;
		
		Node headNode = node.car();
		ListNode tailNode = node.cdr();
		
		tailNode = stringToDefine(tailNode);	// tailNode의 EmptyNode가 나오면 종료
		
		if(headNode instanceof IdNode)
		{
			headNode = ((IdNode)headNode).lookupTable();
			
			if(headNode == null)
			{
				return null;
			}
		}
		
		if(headNode instanceof ListNode)
		{
			headNode = stringToDefine((ListNode)headNode);
			
			if(headNode == null) return null;
		}
		
		return ListNode.cons(headNode, tailNode);
	}
	
	public IdNode(String text)
	{
		idString= text;
	}
	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof IdNode)) return false;
		IdNode idNode = (IdNode) o;
		return Objects.equals(idString, idNode.idString);
	}
	
	@Override
	public String toString()
	{
		return idString;
	}
}
