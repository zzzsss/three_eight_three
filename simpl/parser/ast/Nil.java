package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Nil extends Expr {

    public String toString() {
        return "nil";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeVar a = new TypeVar(true);
    	return TypeResult.of(new ListType(a));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        return Value.NIL;
    }
}
