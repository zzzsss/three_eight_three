package simpl.typing;

import java.util.HashSet;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
        this.typevars = new HashSet<TypeVar>();
        this.typevars.add(this);
    }
    
    @Override
    public Type newVar(){
    	return this;
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        // TODO
    	if (t instanceof TypeVar) {
    		if(((TypeVar)t).name == name)
    			return Substitution.IDENTITY;
    		else
    			return Substitution.of(this, t);
        }
    	else if(t.contains(this))
    		throw new TypeCircularityError();
    	else
    		return Substitution.of(this, t);
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return ((TypeVar)tv).name == name;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
    	if(((TypeVar)a).name == name)
    		return t;
    	else
    		return this;
    }
}
