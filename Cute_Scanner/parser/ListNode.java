package parser;

public interface ListNode extends Node{
	
	static ListNode EMPTYLIST = new ListNode()
	{
		@Override
		public Node car() 
		{
			return null;
		}
		
		@Override
		public ListNode cdr()
		{
			return null;
		}
	};
	
	static ListNode ENDLIST = new ListNode()
	{
		@Override
		public Node car()
		{
			return null;
		}
		
		@Override
		public ListNode cdr()
		{
			return null;
		}
	};
	
	static ListNode cons(Node head, ListNode tail)
	{
		return new ListNode() 	// ���� Ŭ������ ���� Ŭ������ ����� �� ������ �����ȴ�(����)
		{
			@Override
			public Node car()
			{
				return head;	// Node data type ���� car ����(�޸� �Ҵ�)
			}
			
			@Override
			public ListNode cdr()
			{
				return tail;	// List data type ���� cdr ����(�޸� �Ҵ�)
			}
		};
	}
	Node car();					// ����� ����
	ListNode cdr();				// ����� ����
}
