import java.util.Arrays;
import java.util.ArrayList;

public class Problem implements AdversarySearchProblem{ 
    
    
    public S initialState(){
        char [][] board=new int [7][7];
        // Llenamos el arreglos con todos los espacios vacios
        for (char[] row: arr)
            Arrays.fill(row, '_');
        ArrayList<Token> tokens= new ArrayList<Token>();
        State initial= new State(tokens,false,board);
        return initial;
    }

}