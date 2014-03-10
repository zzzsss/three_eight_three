package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = e.typecheck(E);
    	Substitution sub = l_type.s;
    	sub = l_type.t.unify(Type.BOOL).compose(sub);
    	return TypeResult.of(sub,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	Value out = e.eval(s);
        //also check the type error...
        if(!(out instanceof BoolValue))
        	throw new RuntimeError("Logic_error:Not bool");
        else
        	return new BoolValue(!((BoolValue)out).b);
    }
}
