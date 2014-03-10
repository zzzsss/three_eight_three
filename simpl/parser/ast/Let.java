package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Let extends Expr {

    public Symbol x;
    public Expr e1, e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = e1.typecheck(E);
    	E = E.of(E, x, l_type.t);
    	
    	TypeResult l2_type = e2.typecheck(E);
        return TypeResult.of(l2_type.s,l2_type.t);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        Value let_v = e1.eval(s);
        return e2.eval(State.of(new Env(s.E,x,let_v), s.M, s.p));
    }
}
