package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Div extends ArithExpr {

    public Div(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " / " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	Value l_side = l.eval(s);
    	Value r_side = r.eval(s);
        //also check the type error...
        if(!(l_side instanceof IntValue))
        	throw new RuntimeError("Arith_error:Not int");
        if(!(r_side instanceof IntValue))
        	throw new RuntimeError("Arith_error:Not int");
        IntValue ll = (IntValue)l_side;
        IntValue rr = (IntValue)r_side;
        if(rr.n==0)
        	throw new RuntimeError("Arith_error:Divide 0");
        return new IntValue(ll.n/rr.n);
    }
}
