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
		return new ListNode() 	// 무명 클래스는 무명 클래스가 실행될 때 변수가 생성된다(정의)
		{
			@Override
			public Node car()
			{
				return head;	// Node data type 변수 car 정의(메모리 할당)
			}
			
			@Override
			public ListNode cdr()
			{
				return tail;	// List data type 변수 cdr 정의(메모리 할당)
			}
		};
	}
	Node car();					// 선언된 상태
	ListNode cdr();				// 선언된 상태
}
