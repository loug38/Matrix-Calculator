public class ListTest{
	public static void main(String[] args){
		List A = new List();
		List B = new List();

		String a = new String("HELLO");
		String b = new String("WORLD");
		String c = new String ("!");

		String d = new String("One");
		String e = new String("Two");
		String f = new String("Three");

		B.append(d);
		B.append(e);
		B.append(f);

		A.append(a);
		A.append(b);
		A.append(c);

		System.out.println(A);
		System.out.println(B);

		for(A.moveTo(0); A.getIndex()>=0; A.moveNext()){
		 	System.out.print(A.getElement()+" ");
		}
		System.out.println();
		for(B.moveTo(B.length()-1); B.getIndex()>=0; B.movePrev()){
		 	System.out.print(B.getElement()+" ");
		}
		System.out.println();

		List C = A.copy();
		System.out.println(A.equals(B));
		System.out.println(B.equals(C));
		System.out.println(C.equals(A));

		A.moveTo(2);
		A.insertBefore(-1);;
		A.moveTo(0);
		System.out.println(A);
		A.clear();
		System.out.println(A.length());
	}
	  
}