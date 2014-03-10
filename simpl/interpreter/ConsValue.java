package simpl.interpreter;

public class ConsValue extends Value {

    public final Value v1, v2;
    public int length;

    public ConsValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
        if(v2 instanceof NilValue)
        	length=1;
        else
        	length=((ConsValue)v2).length+1;
    }

    public String toString() {
        // TODO
        return "list@"+length;
    }

    @Override
    public boolean equals(Object other) {
        // TODO
    	if(other instanceof ConsValue){
    		ConsValue x = (ConsValue)other;
    		return v1==x.v1 && v2==x.v2;
    	}
        else
        	return false;
    }
}
