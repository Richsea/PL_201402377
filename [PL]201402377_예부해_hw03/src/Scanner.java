import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class Scanner {
	/**
	 * Token 표현하기
	 * @author 예부해
	 *
	 */
	public enum TokenType{
		ID(3), INT(2);
		private final int finalState;
		
		TokenType(int finalState)
		{
			this.finalState = finalState;
		}
	}
	
	
	/**
	 * Programming-TM
	 */
	public Scanner(String source) {
		this.transM = new int[4][128];
		this.source = source == null ? "" : source;
		this.st = new StringTokenizer(this.source, " ");
		initTM();
	}
	private int transM[][];
	private String source;
	private StringTokenizer st;
	
	private void initTM() {
		//init 함수 제작 제대로 됬는지 확인 필요
		for(int i = 0; i < 128; i++)
		{
			if(i == 45) 
			{
				transM[0][45] = 1;
			}
			else if(i > 47 && i < 58)
			{
				transM[0][i] = 2;
			}
			else if((i > 64 && i < 91) || (i > 96 && i < 123))
			{
				transM[0][i] = 3;
			}
			else
			{
				transM[0][i] = -1;
			}
		}
		
		for(int i = 1; i < 3; i++)
		{
			for(int j = 0; j < 128; j++)
			{
				if(i > 47 && i < 58)
					transM[i][j] = 2;
				else
					transM[i][j] = -1;
			}
		}
		
		for(int i = 0; i < 128; i++)
		{
			if((i > 47 && i < 58) || (i > 64 && i < 91) || (i > 96 && i < 123))
			{
				transM[3][i] = 3;
			}
			else
			{
				transM[3][i] = -1;
			}
		}
	}
	private Token nextToken() {
		int stateOld = 0, stateNew;
		
		//토큰이 더 있는지 검사
		if(!st.hasMoreTokens()) return null;
		
		//그 다음 토큰을 받음
		String temp = st.nextToken();
		
		Token result = null;
		for(int i = 0; i < temp.length(); i++) {
			//문자열의 문자를 하나씩 가져와 현재상태와 TransM를 이용하여 다음 상태를 판별
			//만약 입력된 문자의 상태가 reject 이면 에러메세지 출력 후 return함
			//새로 얻은 상태를 현재 상태로 저장
			
			stateNew = transM[stateOld][temp.charAt(i)];
			
			if(stateNew == -1)
			{
				System.out.println("Error 발생: 잘못된 syntax 입니다.");
				return result;
			}
			
			stateOld = stateNew;			
		}
		for(TokenType t : TokenType.values()) {
			if(t.finalState == stateOld) {
				result = new Token(t, temp);
				break;
			}
		}
		return result;
	}
	
	public List<Token> tokenize(){
		//입력으로 들어온 모든 token에 대해
		//nextToken() 이용해 식별한 후 list에 추가해 반환
		List<Token> list = new ArrayList<Token>();
		list.add(this.nextToken());
		
		
		return ...
	}
	
	/**
	 * Data type
	 */
	public static class Token{
		public final TokenType type;
		public final String lexme;
		
		Token(TokenType type, String lexme)
		{
			this.type = type;
			this.lexme = lexme;
		}
		
		@Override
		public String toString() {
			return String.format("[%s: %s]", type.toString(), lexme);
		}
	}

	public static void main(String[] args) throws IOException{
		FileReader fr = new FileReader("as03.txt");
		BufferedReader br = new BufferedReader(fr);
		String source = br.readLine();
		Scanner s = new Scanner(source);
		List<Token> tokens = s.tokenize();
		System.out.println(tokens);
	}
}
