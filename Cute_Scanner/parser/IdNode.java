package parser;

public class IdNode extends Node{
	public String value;
	
	@Override
	public String toString()
	{
		return "ID: " + value;
	}
}
