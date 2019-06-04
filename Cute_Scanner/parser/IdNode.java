package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IdNode implements ValueNode{
	private String idString;
	
	private static Map<String, Node> insertTable = new HashMap<String, Node>();
	
	/*
	 * define�� �߰��ϱ� ���� �Լ�
	 */
	void addDefine(Node node)
	{
		if(node == null) return;
		insertTable.put(idString, node);
	}
	
	/*
	 * table���� define�� symbol���� Ȯ���ϰ� �߰��ϴ� �Լ�
	 */
	public Node lookupTable()
	{
		return insertTable.get(idString);
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
