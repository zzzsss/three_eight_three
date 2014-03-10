package simpl.interpreter;

import static simpl.parser.Symbol.symbol;


public class InitialState extends State {

    public InitialState() {
        super(initialEnv(Env.empty), new Mem(), new Int(0));
    }

    private static Env initialEnv(Env E) {
        // TODO
    	E = new Env(E,symbol("fst"),Value.FST);
    	E = new Env(E,symbol("hd"),Value.HD);
    	E = new Env(E,symbol("snd"),Value.SND);
    	E = new Env(E,symbol("tl"),Value.TL);
    	E = new Env(E,symbol("iszero"),Value.ISZERO);
    	E = new Env(E,symbol("pred"),Value.PRED);
    	E = new Env(E,symbol("succ"),Value.SUCC);
        return E;
    }
}
