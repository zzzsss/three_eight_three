package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	Type the_t = E.get(x);
    	if(the_t==null)
    		return TypeResult.of(new TypeVar(true));
    	else if(!(the_t instanceof simpl.typing.ArrowType))
    		return TypeResult.of(the_t);
    	else{
    		if(((simpl.typing.ArrowType)the_t).is_poly())
    			return TypeResult.of(the_t.get_newVar());
    		else
    			return TypeResult.of(the_t);
    	}
    		
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        Env e = s.E;
        Value v_out = e.get(x);
        if(v_out==null)
        	throw new RuntimeError("Name_error:No such name "+x);
        else if(v_out instanceof RecValue){
        	RecValue vv = (RecValue)v_out;
        	Expr tmp = new Rec(vv.x,vv.e);
        	return tmp.eval(State.of(vv.E, s.M, s.p));
        }
        else
        	return v_out;
    }
}
