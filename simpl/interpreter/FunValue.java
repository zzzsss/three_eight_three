package simpl.interpreter;

import simpl.parser.Symbol;
import simpl.parser.ast.Expr;

public class FunValue extends Value {

    public final Env E;
    public final Symbol x;
    public final Expr e;
    public final boolean spe;

    public FunValue(Env E, Symbol x, Expr e) {
        this.E = E;
        this.x = x;
        this.e = e;
        this.spe = false;
    }

    public FunValue(Env E, Symbol x, Expr e, boolean special) {
        this.E = E;
        this.x = x;
        this.e = e;
        this.spe = special;
    }
    
    public String toString() {
        return "fun";
    }

    @Override
    public boolean equals(Object other) {
        // TODO
    	//just return false, fun can not equal...
        return false;
    }
}
