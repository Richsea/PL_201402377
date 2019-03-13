public class RecursionLinkedList
{
	private Node head;
	private static char UNDEF = Character.MIN_VALUE;
	
	private void linkFirst(char element) 
	{
		head = new Node(element, head);
	}
	/**
	 * ����(1) �־��� Node x�� ���������� ����� Node�� �������� ���Ӱ� ������ ��鸦 ����
	 * 
	 * @param 
	 * 		element	������
	 * @param 
	 * 		x			���
	 */
	private void linkLast(char element, Node x) {
		if(x.next != null)
		{
			linkLast(element, x.next);
		}
		else
		{
			Node node = new Node(element, x);
		}
	}
	/**
	 * ���� Node�� ���� Node�� ���Ӱ� ���ϵ� ��带 ����
	 * 
	 * @param 	element
	 * 			����
	 * @param	pred
	 * 			�������
	 */
	private void linkNext(char element, Node pred)
	{
		Node next = pred.next;
		pred.next = new Node(element, next);
	}
	/**
	 * ����Ʈ�� ù��° ���� ����(����)
	 * 
	 * @return ù��° ������ ������
	 */
	private char unlinkFirst() {
		Node x = head;
		char element = x.item;
		head = head.next;
		x.item = UNDEF;
		x.next = null;
		return element;
	}
	/**
	 * ���� Node �� ���� Node ���� ����(����)
	 * 
	 * @param pred
	 * 			�������
	 * @return ��������� ������
	 */
	private char unlinkNext(Node pred) {
		Node x = pred.next;
		Node next = x.next;
		char element = x.item;
		x.item = UNDEF;
		x.next = null;
		pred.next = next;
		return element;
	}
	/**
	 * ����(2) x��忡�� index��ŭ ������ Node ��ȯ
	 */
	private Node node(int index, Node x) {
		if(index == 0)
		{
			return x;
		}
		if(index < 0)
		{
			return null;
		}
		return node(index-1, x.next);
	}
	/**
	 * ����(3) ���κ��� �������� ����Ʈ�� ��� ���� ��ȯ
	 */
	private int length(Node x) {
		if(x.next == null)
			return 1;
		else
			return 1 + length(x.next);
	}
	/**
	 * ����(4) ���κ��� �����ϴ� ����Ʈ�� ���� ��ȯ
	 */
	private String toString(Node x) {
		if(x.next == null) 
			return String.valueOf(x.item);
		else
		{
			return x.item + toString(x.next);
		}
	}
	/**
	 * �߰� ���� (5) ���� ����� ���� ������ ����Ʈ�� �������� �Ųٷ� ����
	 * ex) ��尡 [s]->[t]->[r]�� ��, reverse ���� �� [r]->[t]->[s]
	 * 
	 * @param x
	 * 			���� ���
	 * @param pred
	 * 			�������� ���� ���
	 */
	private void reverse(Node x, Node pred) {
		// ä���� ���
	}
	/**
	 * ����Ʈ�� �Ųٷ� ����
	 */
	public void reverse()
	{
		reverse(head, null);
	}
	
	/**
	 * �߰� ����(6) �� ����Ʈ�� ��ħ( A+B)
	 * ex) list1 = [l] -> [o] -> [v] -> [e], list2 = [p]->[l]�� ��,
	 * 		list1.addAll(list2) ���� �� [l]->[o]->[v]->[e]->[p]->[l]
	 * 
	 * @param x
	 * 			list1�� ���
	 * @param y
	 * 			list2�� head
	 */
	private void addAll(Node x, Node y) {
		// ä���� ���
	}
	/**
	 * �� ����Ʈ�� ��ħ (this+B)
	 */
	public void addAll(RecursionLinkedList list) {
		addAll(this.head, list.head);
	}
	
	/**
	 * ���Ҹ� ����Ʈ�� �������� �߰�
	 */
	public boolean add(char element) {
		if(head == null)
		{
			linkFirst(element);
		}
		else 
		{
			linkLast(element, head);
		}
		
		return true;
	}
	/**
	 * ���Ҹ� �־��� index ��ġ�� �߰�
	 * 
	 * @param index
	 * 			����Ʈ���� �߰��� ��ġ
	 * @param element
	 * 			�߰��� ������
	 */
	public void add(int index, char element)
	{
		if(!(index >= 0 && index <= size()))
			throw new IndexOutOfBoundsException("" + index);
		
		if(index == 0)
			linkFirst(element);
		else
			linkNext(element, node(index -1, head));
	}
	/**
	 * ����Ʈ���� index ��ġ�� ���� ��ȯ
	 */
	public char get(int index) {
		if(!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException("" + index);
		return node(index, head).item;
	}
	/**
	 * ����Ʈ���� index ��ġ�� ���� ����
	 */
	public char remove(int index) {
		if(!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException("" + index);
		
		if(index == 0) {
			return unlinkFirst();
		}
		return unlinkNext(node(index -1, head));
	}
	/**
	 * ����Ʈ�� ���� ���� ��ȯ
	 */
	public int size() {
		return length(head);
	}
	@Override
	public String toString() {
		if(head == null)
			return "[]";
		return "[" + toString(head) + "]";
	}
	
	/*
	 * List �ڷᱸ���� class
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