package simpl.interpreter;

import java.io.FileInputStream;
import java.io.InputStream;

import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.Expr;
import simpl.typing.DefaultTypeEnv;
import simpl.typing.TypeError;


public class Interpreter {
 
    public void run(String filename) {
        try (InputStream inp = new FileInputStream(filename)) {
            Parser parser = new Parser(inp);
            java_cup.runtime.Symbol parseTree = null;
            try{
            	parseTree = parser.parse();
            }catch (Exception e){
            	throw new SyntaxError("syntax",0,0);
            }
            Expr program = (Expr) parseTree.value;
            //System.err.println(program.typecheck(new DefaultTypeEnv()).t);
            program.typecheck(new DefaultTypeEnv());
            System.out.println(program.eval(new InitialState()));
        }
        catch (SyntaxError e) {
            System.out.println("syntax error");
        }
        
        catch (TypeError e) {
            System.out.println("type error");
        }
        
        catch (RuntimeError e) {
            System.out.println("runtime error");
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void interpret(String filename) {
        Interpreter i = new Interpreter();
        //System.err.println(filename);
        i.run(filename);
    }

    public static void main_test() {
    	interpret("doc/examples/test.spl");
    	interpret("doc/examples/true.spl");
        interpret("doc/examples/plus.spl");
        interpret("doc/examples/factorial.spl");
        interpret("doc/examples/gcd1.spl");
        interpret("doc/examples/gcd2.spl");
        interpret("doc/examples/max.spl");
        interpret("doc/examples/sum.spl");
        interpret("doc/examples/map.spl");
        interpret("doc/examples/pcf.sum.spl");
        interpret("doc/examples/pcf.even.spl");
        interpret("doc/examples/pcf.minus.spl");
        interpret("doc/examples/pcf.factorial.spl");
        interpret("doc/examples/pcf.fibonacci.spl");
        interpret("doc/examples/pcf.twice.spl");
        interpret("doc/examples/pcf.lists.spl");
    }
    
    public static void main(String a[]){
    	for(String i : a){
    		interpret(i);
    	}
    	//test only
    	//Interpreter.main_test();
    }
}
