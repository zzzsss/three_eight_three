package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.interpreter.lib.*;
import simpl.interpreter.pcf.*;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;


public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = l.typecheck(E);
    	Substitution sub = l_type.s;
    	TypeVar a = new TypeVar(true);
    	TypeVar b = new TypeVar(true);
    	sub = l_type.t.unify(new ArrowType(a,b,false)).compose(sub);
    	
    	TypeResult r_type = r.typecheck(sub.compose(E));
    	sub = r_type.s.compose(sub);
    		
    	sub = r_type.t.unify(sub.apply(a)).compose(sub);
        return TypeResult.of(sub,sub.apply(b));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	FunValue l_side = (FunValue)l.eval(s);
        Value r_side = r.eval(s);
        if(l_side.spe){
        	if(l_side instanceof fst){
        		if(!(r_side instanceof PairValue))
        			throw new RuntimeError("fst:Not pair");
        		return ((PairValue)r_side).v1;
        	}
        	else if(l_side instanceof snd){
        		if(!(r_side instanceof PairValue))
        			throw new RuntimeError("snd:Not pair");
        		return ((PairValue)r_side).v2;
        	}
        	else if(l_side instanceof hd){
        		if(!(r_side instanceof ConsValue))
        			throw new RuntimeError("hd:Not pair");
        		return ((ConsValue)r_side).v1;
        	}
        	else if(l_side instanceof tl){
        		if(!(r_side instanceof ConsValue))
        			throw new RuntimeError("tl:Not pair");
        		return ((ConsValue)r_side).v2;
        	}
        	else if(l_side instanceof iszero){
        		if(!(r_side instanceof IntValue))
        			throw new RuntimeError("iszero:Not Int");
        		return new BoolValue(((IntValue)r_side).n==0);
        	}
        	else if(l_side instanceof pred){
        		if(!(r_side instanceof IntValue))
        			throw new RuntimeError("pred:Not Int");
        		return new IntValue(((IntValue)r_side).n-1);
        	}
        	else if(l_side instanceof succ){
        		if(!(r_side instanceof IntValue))
        			throw new RuntimeError("succ:Not Int");
        		return new IntValue(((IntValue)r_side).n+1);
        	}
        	else
        		throw new RuntimeError("Unknown special function...");
        }
        else
        	return l_side.e.eval(State.of(new Env(l_side.E,l_side.x,r_side), s.M, s.p));
    }
}
