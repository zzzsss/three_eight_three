package lambda;
import java.util.*;
import java.io.*;

/* the expression of the lambda calculus */
public class Expr {
	/* expr can be 0,VAR,FUN,APP */
	public final static int NUL=0,VAR=1,FUN=2,APP=3;
	
	/* the fields --- VAR:tag FUN:tag,e1 APP:e1,e2 */
	int tag;
	String var_name;
	Expr e1,e2;
	Set<String> all_var = new HashSet<String>();
	Set<String> free_var = new HashSet<String>();
	
	/* construct methods */
	public Expr(int t,String v,Expr ex1,Expr ex2){
		tag=t; var_name=v; e1=ex1; e2=ex2;
		//then get all_var and free_var
		get_var();
	}
	public Expr(){
		tag=NUL;
	}
	/* private */
	private void get_var(){
		all_var.clear();
		free_var.clear();
		if(tag==VAR){
			all_var.add(var_name);
			free_var.add(var_name);
		}
		else if(tag==FUN){
			all_var.addAll(e1.all_var);
			all_var.add(var_name);
			free_var.addAll(e1.free_var);
			free_var.remove(var_name);
		}
		else if(tag==APP){
			all_var.addAll(e1.all_var);
			all_var.addAll(e2.all_var);
			free_var.addAll(e1.free_var);
			free_var.addAll(e2.free_var);
		}
	}
	private void get_replace_by(Expr x){
		all_var.clear();
		free_var.clear();
		
		tag = x.tag;
		if(x.var_name!=null)
			var_name = x.var_name.substring(0);
		if(x.e1!=null)
			e1 = x.e1.get_clone();
		if(x.e2!=null)
			e2 = x.e2.get_clone();
		all_var.addAll(x.all_var);
		free_var.addAll(x.free_var);
	}
	
	/* methods */
	//get_clone...
	public Expr get_clone(){
		Expr tmp = new Expr();
		tmp.tag = tag;
		if(var_name!=null)
			tmp.var_name = var_name.substring(0);
		if(e1!=null)
			tmp.e1 = e1.get_clone();
		if(e2!=null)
			tmp.e2 = e2.get_clone();
		tmp.all_var.addAll(all_var);
		tmp.free_var.addAll(free_var);
		return tmp;
	}
	//print the expression
	public void get_print(){
		if(tag==NUL);
		else if(tag==VAR)
			System.out.print(var_name);
		else if(tag==FUN){
			System.out.print("(lambda ("+var_name+") ");
			e1.get_print();
			System.out.print(")");
		}
		else if(tag==APP){
			System.out.print("(");
			e1.get_print();
			System.out.print(" ");
			e2.get_print();
			System.out.print(")");
		}
	}
	//get_substitute
	public void get_substitude(String x,Expr v){
		if(tag==VAR){
			if(!var_name.equals(x));	//do nothing
			else						//replace
				get_replace_by(v.get_clone());
		}
		else if(tag==APP){
			e1.get_substitude(x, v);
			e2.get_substitude(x, v);
		}
		else if(tag==FUN){ 
			if(var_name.equals(x));		//do nothing because of bounded var
			else if(!v.free_var.contains(var_name)){
				e1.get_substitude(x, v);
			}
			else{						//alpha-eq first --- adding z
				StringBuffer tmp = new StringBuffer();
				tmp.append(var_name);
				tmp.append('z');
				while(all_var.contains(tmp) || v.free_var.contains(tmp))
					tmp.append('z');
				e1.get_substitude(var_name,new Expr(VAR,tmp.toString(),null,null));
				var_name = tmp.toString();
				e1.get_substitude(x, v);
			}
		}
		get_var();
	}
	public void get_evaluate(){
		if(tag==APP){	//call-by-value: only evaluate with application
			e1.get_evaluate();
			e2.get_evaluate();
			if(e1.tag == FUN && (e2.tag==FUN || e2.tag==VAR)){
				e1.e1.get_substitude(e1.var_name, e2);
				get_replace_by(e1.e1);
				get_evaluate();
			}			
		}
	}
	
}
