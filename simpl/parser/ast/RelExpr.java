package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
    	TypeResult l_type = l.typecheck(E);
    	Substitution sub = l_type.s;
    	sub = l_type.t.unify(Type.INT).compose(sub);
    	
    	TypeResult r_type = r.typecheck(sub.compose(E));
    	sub = r_type.s.compose(sub);
    	sub = r_type.t.unify(Type.INT).compose(sub);
        return TypeResult.of(sub,Type.BOOL);
    }
}
