package simpl.typing;

import java.util.HashSet;

public final class RefType extends Type {

    public Type t;

    public RefType(Type t) {
        this.t = t;
        this.typevars = new HashSet<TypeVar>();
        this.typevars.addAll(t.typevars);
    }
    
    @Override
    public Type newVar(){
    	return new RefType(t.newVar());
    }

    @Override
    public boolean isEqualityType() {
        // TODO
        return t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
    	if (t instanceof TypeVar) {
            return t.unify(this);
        }
    	if (t instanceof RefType) {
    		RefType x = (RefType)t;
    		Substitution sub = this.t.unify(x.t);
            return sub;
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return new RefType(t.replace(a, t));
    }

    public String toString() {
        return t + " ref";
    }
}
