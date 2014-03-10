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

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = e1.typecheck(E);
    	Substitution sub = l_type.s;
    	sub = l_type.t.unify(Type.BOOL).compose(sub);
    	
    	TypeResult r_type = e2.typecheck(sub.compose(E));
    	sub = r_type.s.compose(sub);
    	return TypeResult.of(sub,Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
    	Value predict = e1.eval(s);
        if(!(predict instanceof BoolValue))
        	throw new RuntimeError("Loop_pred_error:Not bool");
        BoolValue pp = (BoolValue)predict;
        if(pp.b){
        	Expr tmp = new Seq(e2,this);
        	return tmp.eval(s);
        }
        else
        	return Value.UNIT;
    }
}
