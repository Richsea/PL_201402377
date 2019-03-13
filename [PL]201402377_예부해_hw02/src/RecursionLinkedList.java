public class RecursionLinkedList
{
	private Node head;
	private static char UNDEF = Character.MIN_VALUE;
	
	private void linkFirst(char element) 
	{
		
	}
	
	/*
	 * List 자료구조용 class
	 */
	private class Node{
		char item;
		Node next;
		
		Node(char element, Node next)
		{
			this.item = element;
			this.next = next;
		}
	}
}