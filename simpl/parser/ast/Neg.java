package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = e.typecheck(E);
    	Substitution sub = l_type.s;
    	sub = l_type.t.unify(Type.INT).compose(sub);
    	return TypeResult.of(sub,Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        Value out = e.eval(s);
        //also check the type error...
        if(!(out instanceof IntValue))
        	throw new RuntimeError("Neg_error:Not int");
        else
        	return new IntValue(-((IntValue)out).n);
    }
}
