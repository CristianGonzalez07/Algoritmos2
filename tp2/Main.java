import java.io.*;
import java.util.ArrayList;

public class Main {
  /*
   *Metodos que faltan
   +Obtener jugada
   +mostrar tablero
   +
  */

  //
   private static State tokensInBoard;

    public static Boolean insertToken(int i,int j,char color){
        Token token = new Token(i,j,color);
        if(!(tokensInBoard.ocuppied(token))){
            ArrayList<Token> tokens = new ArrayList<Token>();
            if(tokensInBoard.getTokens() != null){
                tokens = tokensInBoard.getTokens();    
            } 
            tokens.add(token);
            tokensInBoard.setTokens(tokens);
            return true;        
        }else{
            return false;
        }
    }

    public static void main(String[] args) throws Exception{
        Boolean exception = true;
        tokensInBoard = new State();
        while(exception){
            try{
                exception = false;
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                Boolean insert = false;
                int fila = 0;
                int columna = 0;
                while(!insert){
                    System.out.println("Ingrese La ficha");
                    System.out.println("Pos i :");
                    fila = Integer.parseInt(br.readLine());
                    System.out.println("Pos j :");
                    columna = Integer.parseInt(br.readLine());
                    insert = insertToken(fila,columna,'b');
                    if(!insert){
                        System.out.println("No se puede ingresar en esa posicion.Ingrese una posicion valida o no ocupada");
                    }else{
                        System.out.println("Ficha Ingresada Correctamente");
                    }
                }
            }catch(Exception e){
                exception = true;
                System.out.println("Ingreso Invalido");
            }
        }
    }
  
}