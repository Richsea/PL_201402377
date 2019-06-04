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
	
	/*
	 * table에서 define된 symbol인지 확인하고 추가하는 함수
	 */
	public Node lookupTable()
	{
		return insertTable.get(idString);
	}
	
	/*
	 * Define 내부에 Define된 symbol을 사용할 경우 변경하여 저장해주기 위해 생성한 함수
	 */
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
