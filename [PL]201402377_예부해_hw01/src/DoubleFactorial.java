import java.util.Scanner;
import java.math.BigInteger;

public class DoubleFactorial {
	public static void main(String[] args) {
		long data = insertNum();
		
		if(data < 0 || data > 100)
		{
			System.out.print("1~100 사이의 숫자만 가능합니다.");
		}else
		{
			printDoubleFactResult(doDoubleFact(data));
		}
	}
	
	static BigInteger doDoubleFact(long data)
	{
		if(data <= 2)
		{
			BigInteger returnData = new BigInteger(Long.toString(data));
			
			return returnData; 
		}

		BigInteger facResult = doDoubleFact(data - 2);
		return facResult.valueOf(data).multiply(facResult);
	}
	
	static void printDoubleFactResult(BigInteger outputData)
	{	 
		System.out.println("output: " + outputData);
	}
	
	static long insertNum()
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("input: ");
		return scanner.nextLong();
	}
}
