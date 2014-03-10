package simpl.typing;

import java.util.*;
import simpl.parser.Symbol;

public abstract class Type {

    public abstract boolean isEqualityType();

    public abstract Type replace(TypeVar a, Type t);

    public abstract boolean contains(TypeVar tv);

    public abstract Substitution unify(Type t) throws TypeError;

    public static final Type INT = new IntType();
    public static final Type BOOL = new BoolType();
    public static final Type UNIT = new UnitType();
    
    //newVar
    public abstract Type newVar();
    protected Set<TypeVar> typevars ;
    public Type get_newVar(){
    	Type x = this;
    	for(TypeVar k : typevars){
    		x = x.replace(k, new TypeVar(k.isEqualityType()));
    	}
    	return x;
    }
}
