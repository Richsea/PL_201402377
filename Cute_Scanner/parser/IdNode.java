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
	void addDefine(Node node)
	{
		for(String key : insertTable.keySet())
		{
			insertTable.put(idString, node);
		}
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
