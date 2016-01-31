//Lou George, ligeorge PA3

public class List {
	// Private Data
	private Node cursor, back, front;
	private int length = 0, index  = -1;
	//Private Data
	
	// CONSTRUCTOR
	public List(){// Creates a new empty list.
		Node list = new Node();
		list.data = 0;
		list.prev = null;
		list.next = null;
	}//CTOR
	
	
	//NODE CLASS
	private class Node{
		
		private Node prev;
		private Node next;
		private Object data;
		
		//CTOR
		public Node(Node Prev, Node Next, Object Data){
			prev = Prev;
			next = Next;
			this.data = Data;
		}
			
		public Node(Object Data){
			this (null, null, Data);
		}
		
		public Node(){
			this (null, null, null);
		}
		
		
		public Node getPrev(){ return prev; }
		public Node getNext(){ return next; }
		public Object getData(){ return data; }
		
		public void setPrev(Node Prev){
			prev = Prev;
		}
		
		public void setNext(Node Next){
			next = Next;
		}
		
//		public void setData(Object Data){
//			data = Data;
//		}
	}//NODE CLASS
	
	
	
	//ACCESSORS
	public int length(){ return length; }			// Returns number of elements in this list.
	
	public int getIndex(){ return index; }			
	
	public Node getCursor(){ return cursor; }
		
	public Object front(){								// Returns front element in this list. Pre: length()>0
		if (length() > 0)
			return front.getData();
		return 0;
	} 
	
	public Object back(){ 								// Returns back element in this List. Pre: length()>0
		if (length() > 0)
			return back.getData(); 
		return 0;
	}												
	
	public Object getElement(){ 						// Returns cursor element in this list.
		if (length() > 0 && getIndex() >= 0 && cursor != null){
			return cursor.getData();				// Pre: length()>0, getIndex()>=0
		}
		return null;
	}
	
	
	//ACCESSORS
	public boolean equals(List L){					// Returns true if this List and L are the same integer
		boolean Equal = true;				// sequence. The cursor is ignored in both lists.
		Node A = this.front;
		L.moveTo(0);
		Node B = L.getCursor();
		
		while (Equal && A != null){
			Equal = (A.getData().equals(B.getData()));
			A = A.getNext();
			B = B.getNext();
		}
		return Equal;
	}
 
	public void clear(){		 					// Re-sets this List to the empty state
		cursor = null;
		front = null;
		back = null;
		length = 0;
		index = -1;
	}
	
	public void moveTo(int i){ 						// If 0<=i<=length()-1, moves the cursor to the element
	 	if (i >= 0 && i <= length()-1){				// at index i, otherwise the cursor becomes undefined.
	 		cursor = front;
	 		index = 0;
	 		for (int j = 0; j < i; j++){
	 			cursor = cursor.getNext();	 
	 			index++;
	 		}
	 	}else{
	 		cursor = null;
	 		index = -1;
	 	}
	}
	
	public void movePrev(){									// If 0<getIndex()<=length()-1, moves the cursor one step toward the
		if (0 < getIndex() && getIndex() <= length()-1){		// front of the list. If getIndex()==0, cursor becomes undefined.
			cursor = cursor.getPrev();						// If getIndex()==-1, cursor remains undefined. This operation is 			
			index--;
		}else{ 
			cursor = null;
			index = -1;
		}
	}
		
	public void moveNext(){					 				// If 0<=getIndex()<length()-1, moves the cursor one step toward the
		if (getIndex() >= 0 && getIndex() <= length()-1){	// back of the list. If getIndex()==length()-1, cursor becomes
			cursor = cursor.getNext();						// undefined. If index==-1, cursor remains undefined. This
			index++;
			if (cursor == null)
				index = -1;
		}
		if (getIndex() == -1)												// operation is equivalent to moveTo(getIndex()+1).
			cursor = null;
	}
	
	public void prepend(Object Data){ 							// Inserts new element before front element in this List.
		Node newPrepend = new Node (null, front, Data);
		if (length > 0)
			front.setPrev(newPrepend);
		front = newPrepend;
		if (length == 0)
			back = newPrepend;
		length++;
	}

	public void append(Object Data){							// Inserts new element after back element in this List.
		Node newAppend = new Node(back, null, Data);
		if(length > 0)
			back.setNext(newAppend);
		back = newAppend;
		if (length == 0)
			front = newAppend;
		length++;											//set back to newest Appended value
	}
	
//	private boolean isEmpty(){
//		return (length == 0);
//	}
	
	public void insertBefore(Object Data){ 					// Inserts new element before cursor element in this
		if(length() > 0 && getIndex() >= 0){				// List. Pre: length()>0, getIndex()>=0
			Node newBefore = new Node(Data);
			int cursData = getIndex();
			
			//connect Node closer to back
			moveTo(cursData);
			newBefore.setNext(cursor);
			cursor.setPrev(newBefore);			
															//connect Node closer to front
			if (getIndex() != 0){
				moveTo(cursData - 1);
				cursor.setNext(newBefore);
				newBefore.setPrev(cursor);
			}else{
				front = newBefore;
			}
			index++;
			length++;
		}
	}
	
	public void insertAfter(Object Data){ 					// Inserts new element after cursor element in this
		if (length() > 0 && getIndex() >= 0){				// List. Pre: length()>0, getIndex()>=0
			Node newAfter = new Node(Data);
			int cursData = getIndex();			

			moveTo(cursData);
			newAfter.setPrev(cursor);
			cursor.setNext(newAfter);
			
			if (getIndex() != (length - 1)){
				moveTo(cursData + 1);
				newAfter.setPrev(cursor);
				cursor.setNext(newAfter);
			}else{
				back = newAfter;
			}
			length++;
			index++;
			

		}
	}
	
	public void deleteFront(){ 								// Deletes the front element in this List. Pre: length()>0
		if (length() > 0){
			Node cursorCopy = cursor;
			cursor = front;
			moveNext();
			cursor.setPrev(null);
			cursor = cursorCopy;
			index--;
		}
		length--;
	}
	
	public void deleteBack(){ 								// Deletes the back element in this List. Pre: length()>0
		if (length() > 0){
			Node cursorCopy = cursor;
			cursor = back;
			movePrev();
			cursor.setNext(null);
			cursor = cursorCopy;
			index--;
		}
		length--;
	}
	
	public void delete(){ 									// Deletes cursor element in this List. Cursor is undefined after this
	 	if(length() > 0 && getIndex() >= 0){				// operation. Pre: length()>0, getIndex()>=0
	 		Node reConnect = null;
	 		if (cursor.getNext() == null){
	 			movePrev();
	 			cursor.setPrev(null);
	 		}else{
		 		moveNext();
		 		reConnect = cursor.getNext();
		 		movePrev();
		 		cursor.setNext(reConnect);
		 		cursor = null;
		 		length--;
		 		index = -1;
	 		}
	 	}
	}
	
	
	// Other methods
	public String toString(){ 								// Overides Object's toString method. Creates a string
		Node parse = front;
		String ret = "";
		for (int i = 0; i < length; i++){
			ret += parse.getData() + " ";					// consisting of a space separated sequence of integers
			parse = parse.getNext();						// with front on the left and back on the right. The cursor is ignored.
		}
		return(ret);
	}
	
	public List copy(){										// Returns a new list representing the same integer sequence as this
		List returnCopy = new List();						// list. The cursor in the new list is undefined, regardless 
		Node cursorCopy = cursor;
		
		cursor = front;
		for (int i = 0 ; i < length; i++){
			returnCopy.append(cursor.getData());
			cursor = cursor.getNext();
		}
		cursor = cursorCopy;
		return returnCopy;
	}
}