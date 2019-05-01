package parser;

public class IntNode implements ValueNode{
	private Integer value;
	
	@Override
	public String toString() {
		return "INT: " + value;
	}
	
	public IntNode(String text)
	{
		value = new Integer(text);
	}
}
