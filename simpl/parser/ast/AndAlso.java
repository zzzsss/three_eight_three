package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = l.typecheck(E);
    	Substitution sub = l_type.s;
    	sub = l_type.t.unify(Type.BOOL).compose(sub);
    	
    	TypeResult r_type = r.typecheck(sub.compose(E));
    	sub = r_type.s.compose(sub);
    	sub = r_type.t.unify(Type.BOOL).compose(sub);
        return TypeResult.of(sub,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	Value l_side = l.eval(s);
    	Value r_side = r.eval(s);
        //also check the type error...
        if(!(l_side instanceof BoolValue))
        	throw new RuntimeError("Logic_error:Not bool");
        if(!(r_side instanceof BoolValue))
        	throw new RuntimeError("Logic_error:Not bool");
        BoolValue ll=(BoolValue)l_side;
        BoolValue rr=(BoolValue)r_side;
        return new BoolValue(ll.b && rr.b);
    }
}
