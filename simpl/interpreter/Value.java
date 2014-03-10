package simpl.interpreter;
import simpl.interpreter.lib.hd;
import simpl.interpreter.lib.tl;
import simpl.interpreter.lib.fst;
import simpl.interpreter.lib.snd;
import simpl.interpreter.pcf.iszero;
import simpl.interpreter.pcf.pred;
import simpl.interpreter.pcf.succ;

public abstract class Value {

    public static final Value NIL = new NilValue();
    public static final Value UNIT = new UnitValue();
    
    public static final Value FST = new fst();
    public static final Value HD = new hd();
    public static final Value SND = new snd();
    public static final Value TL = new tl();
    public static final Value ISZERO = new iszero();
    public static final Value PRED = new pred();
    public static final Value SUCC = new succ();

    public abstract boolean equals(Object other);
}
