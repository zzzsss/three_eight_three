package lambda;

import java.util.*;
import java.io.*;

public class Lexer {
	private char curr = ' ';					//current char
	private Hashtable words = new Hashtable();	//reserved words
	void reserve(Token a){
		words.put(a.identify,a);
	}
	public Lexer(){
		reserve(new Token(Token.LAM,"lambda"));
	}
	public Token get_token() throws IOException{
		curr = get_one();
		while(curr==' ' || curr=='\t' || curr=='\n')
			curr = get_one();
		if(Character.isLetter(curr)){
			StringBuffer b = new StringBuffer();
			do{
				b.append(curr);
				curr = get_one();
			}while(Character.isLetterOrDigit(curr));
			put_one(curr);
			
			String str = b.toString();
			Token tmp = (Token)words.get(str);
			if(tmp!=null)
				return tmp;
			else
				return new Token(Token.ID,str);
		}
		else if(curr=='(' || curr==')')
			return new Token(curr,null);
		return new Token(0,null);
	}
	
	/* buffered input -- with only one buffer*/
	private int buffer = -1;
	private boolean bufferred = false;
	private char get_one() throws IOException{
		if(!bufferred)
			return ((char) System.in.read());
		else{
			bufferred = false;
			return (char)buffer;
		}
	}
	private void put_one(char a){
		buffer = a;
		bufferred = true;
	}
	
	/* test it */
	public static void main(String[] a)  throws IOException{
		Lexer l=new Lexer();
		Token tmp;
		while((tmp=l.get_token())!=null){
			System.out.println(tmp.tag+tmp.identify);
		}
	}
}
