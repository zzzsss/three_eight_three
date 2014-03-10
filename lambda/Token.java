package lambda;

public class Token {
	/* the tag could be 0,id,lambda,(,) */
	public final static int ID=257,LAM=258;
	int tag;
	String identify;
	
	public Token(int t,String s){
		tag=t;
		identify=s;
	}
}
