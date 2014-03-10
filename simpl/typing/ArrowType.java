package simpl.typing;

import java.util.HashSet;

public final class ArrowType extends Type {

    public Type t1, t2;
    private boolean poly=true;

    public ArrowType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
        this.typevars = new HashSet<TypeVar>();
        this.typevars.addAll(t1.typevars);
        this.typevars.addAll(t2.typevars);
    }
    
    public ArrowType(Type t1, Type t2,boolean p){
    	this(t1,t2);
    	poly=p;
    }
    
    public boolean is_poly()
    {
    	return poly==true;
    }
    
    @Override
    public Type newVar(){
    	return new ArrowType(t1.newVar(),t2.newVar());
    }

    @Override
    public boolean isEqualityType() {
        // TODO
    	// false
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
    	if (t instanceof TypeVar) {
            return t.unify(this);
        }
    	if (t instanceof ArrowType) {
    		ArrowType x = (ArrowType)t;
    		Substitution sub = t1.unify(x.t1);
    		sub = sub.apply(t2).unify(sub.apply(x.t2)).compose(sub);
            return sub;
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return t1.contains(tv)||t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return new ArrowType(t1.replace(a, t),t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
