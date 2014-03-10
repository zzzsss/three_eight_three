package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = e.typecheck(E);
        return TypeResult.of(l_type.s,new RefType(l_type.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	int p_init = s.p.get();
    	s.p.set(s.p.get()+1);
        Value tmp = e.eval(s);
        s.M.put(p_init, tmp);
        return new RefValue(p_init);
    }
}
