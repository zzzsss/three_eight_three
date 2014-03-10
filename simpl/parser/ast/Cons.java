package simpl.parser.ast;

import simpl.interpreter.ConsValue;
import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = l.typecheck(E);
    	Substitution sub = l_type.s;
    	
    	TypeResult r_type = r.typecheck(sub.compose(E));
    	sub = r_type.s.compose(sub);
    	sub = r_type.t.unify(new ListType(sub.apply(l_type.t))).compose(sub);
        return TypeResult.of(sub,sub.apply(r_type.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	Value l_side = l.eval(s);
        Value r_side = r.eval(s);
        return new ConsValue(l_side,r_side);
    }
}
