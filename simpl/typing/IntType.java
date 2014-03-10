package simpl.typing;

import java.util.HashSet;

final class IntType extends Type {

    protected IntType() {
    	this.typevars = new HashSet<TypeVar>();
    }

    @Override
    public Type newVar(){
    	return Type.INT;
    }
    
    @Override
    public boolean isEqualityType() {
        // TODO
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
    	if (t instanceof TypeVar) {
            return t.unify(this);
        }
        if (t instanceof IntType) {
            return Substitution.IDENTITY;
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return Type.INT;
    }

    public String toString() {
        return "int";
    }
}
