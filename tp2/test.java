import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class test{
	public static void main(String[] args) {
		 Problem p = new Problem();
        ArrayList<Token> tokens = new ArrayList<Token>();
    	Token t1 = new Token(0,0,'b');
        Token t2 = new Token(0,1,'b');
    	Token t3 = new Token(0,2,'b');
    	Token t4 = new Token(0,3,'b');
    	Token t5 = new Token(0,4,'b');
    	Token t6 = new Token(0,5,'b');
    	Token t7 = new Token(0,6,'b');
    	tokens.add(t1);
    	tokens.add(t2);
    	tokens.add(t3);
    	tokens.add(t4);
    	tokens.add(t5);
    	tokens.add(t6);
    	tokens.add(t7);
        System.out.println("pase====================");
        State state = new State(tokens,true,7,0,null);

        ArrayList<State> successors= p.getSuccessors(state);
        Problem problem = new Problem();
		MinMaxAlphaBetaEngine<Problem,State>minMaxEngine = new MinMaxAlphaBetaEngine<Problem,State>(problem,2);
        state = minMaxEngine.computeSuccessor(state);
        System.out.println("E1: "+state.toString());
        state = minMaxEngine.computeSuccessor(state);
        System.out.println("E2: "+state.toString());


	}
}