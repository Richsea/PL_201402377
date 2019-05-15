package parser;

import java.util.HashMap;
import java.util.Map;

import lexer.TokenType;

public class FunctionNode implements ValueNode{
	public enum FunctionType{
		ATOM_Q	{ TokenType tokenType() { return TokenType.ATOM_Q;} },
		CAR		{ TokenType tokenType() { return TokenType.CAR;} },
		CDR		{ TokenType tokenType() { return TokenType.CDR;} },
		COND	{ TokenType tokenType() { return TokenType.COND;} },
		DEFINE	{ TokenType tokenType() { return TokenType.DEFINE;} },
		CONS	{ TokenType tokenType() { return TokenType.CONS;} },
		EQ_Q	{ TokenType tokenType() { return TokenType.EQ_Q;} },
		LAMBDA	{ TokenType tokenType() { return TokenType.LAMBDA;} },
		NOT		{ TokenType tokenType() { return TokenType.NOT;} },
		NULL_Q	{ TokenType tokenType() { return TokenType.NULL_Q;} },
		QUOTE	{ TokenType tokenType() { return TokenType.QUOTE;} };
		
		private static Map<TokenType, FunctionType> fromTokenType = new HashMap<TokenType, FunctionType>();
	
		static {
			for(FunctionType fType : FunctionType.values())
			{
				fromTokenType.put(fType.tokenType(), fType);
			}
		}
	
		static FunctionType getFunctionType(TokenType tType) {
			return fromTokenType.get(tType);
		}
		
		abstract TokenType tokenType();
	}
	//binaryOPNode 클래스를 보고 참고하여 작성
	public FunctionType funcType;
	
	@Override
	public String toString()
	{
		return funcType.name();
	}
	
	public void setValue(TokenType tType)
	{
		FunctionType fType = FunctionType.getFunctionType(tType);
		funcType = fType;
	}
}
