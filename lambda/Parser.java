package lambda;

import java.io.IOException;

public class Parser {
	/* fileds */
	private Token curr_token;
	private Lexer the_l=new Lexer();
	
	/* private methods */
	private void match(int tag) throws IOException{
		get_one();
		if(curr_token.tag == tag)
			;
		else //error -- unhandled
			;
	}
	private Token buffer;
	private boolean bufferred = false;
	private void get_one() throws IOException{
		if(bufferred==false)
			curr_token=the_l.get_token();
		else{
			bufferred=false;
			curr_token=buffer;
		}
	}
	private void put_one(Token a){
		bufferred = true;
		buffer = a;
	}
	
	/* methods */
	public Expr get_expr() throws IOException{
		get_one();
		if(curr_token.tag == Token.ID){			//expression of ID
			Expr tmp=new Expr(Expr.VAR,curr_token.identify,null,null);
			return tmp;
		}
		else if(curr_token.tag == '('){
			get_one();
			if(curr_token.tag == Token.LAM){	//lambda
				match('(');
				Expr para = get_expr();
				match(')');
				Expr proc = get_expr();
				match(')');
				return new Expr(Expr.FUN,para.var_name,proc,null);
			}
			else{								//application
				put_one(curr_token);
				Expr a = get_expr();
				Expr b = get_expr();
				match(')');
				return new Expr(Expr.APP,null,a,b);
			}
		}
		return new Expr(Expr.NUL,null,null,null);
	}
	
	/* test */
	public static void main(String[] a) throws IOException{
		Parser p = new Parser();
		Expr e=p.get_expr();
		//e.get_print();
		e.get_evaluate();
		//System.out.println();
		e.get_print();
		while((e = p.get_expr()).tag != Expr.NUL){
			System.out.println();
			//e.get_print();
			e.get_evaluate();
			//System.out.println();
			e.get_print();
		}
	}

}
