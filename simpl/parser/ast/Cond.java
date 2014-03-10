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

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult e1_type = e1.typecheck(E);
    	Substitution sub = e1_type.s;
    	sub = e1_type.t.unify(Type.BOOL).compose(sub);
    	
    	E=sub.compose(E);
    	TypeResult e2_type = e2.typecheck(E);
    	sub = e2_type.s.compose(sub);
    	E=sub.compose(E);
    	TypeResult e3_type = e3.typecheck(E);
    	sub = e3_type.s.compose(sub);
    	
    	sub = e3_type.t.unify(sub.apply(e2_type.t)).compose(sub);
        return TypeResult.of(sub,sub.apply(e2_type.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        Value predict = e1.eval(s);
        if(!(predict instanceof BoolValue))
        	throw new RuntimeError("Cond_pred_error:Not bool");
        BoolValue pp = (BoolValue)predict;
        if(pp.b)
        	return e2.eval(s);
        else
        	return e3.eval(s);
    }
}
