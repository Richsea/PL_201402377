import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws FileNotFoundException{
		
		RecursionLinkedList list = new RecursionLinkedList();
		RecursionLinkedList list2 = new RecursionLinkedList();
		
		list.add('a');	list.add('b');
		list.add('c');	list.add('d');
		list.add('e');
		System.out.println(list);
		
		list2.add('f');	list2.add('g');
		list2.add('h');
		System.out.println(list2);
		
		list.add(0, 'z');
		System.out.println(list);
		
		System.out.println(list.get(4));
		System.out.println(list.remove(0));
		System.out.println(list);
		
		list.reverse();
		
		/*
		RecursionLinkedList list = new RecursionLinkedList();
		FileReader fr;
		try {
			fr = new FileReader("hw01.txt");
			BufferedReader br = new BufferedReader(fr);
			String inputString = br.readLine();
			for(int i = 0; i < inputString.length(); i++)
				list.add(inputString.charAt(i));
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(list.toString());
		list.add(3, 'b'); 	System.out.println(list.toString());
		list.reverse();		System.out.println(list.toString());
		// ��� ������ ��� �߰��ؼ� ���
		 * 
		 */
	}
}
