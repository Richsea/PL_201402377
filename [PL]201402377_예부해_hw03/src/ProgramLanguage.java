import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class ProgramLanguage {
	public static class Token{
		
	}
	
	
	
	public enum TokenType{
		ID(3), INT(2);
		private final int finalState;
		
		TokenType(int finalState)
		{
			this.finalState = finalState;
		}
	}

	public static void main(String[] args){
		FileReader fr = new FileReader("as03.txt");
		BufferedReader br = new BufferedReader(fr);
		String source = br.readLine();
		Scanner s = new Scanner(source);
		List<Token> tokens = s.tokenize();
		System.out.println(tokens);
	}
}
