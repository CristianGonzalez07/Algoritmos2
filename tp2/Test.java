import java.util.*;

public class Test{
  
  
  public Test(){}

  public static void main(String[] args){
    Problem p = new Problem();
    State estadoActual = p.initialState();
    int prof=2;
    MinMaxAlphaBetaEngine mm= new MinMaxAlphaBetaEngine(p,prof);
    while(!(p.end(estadoActual))){
      mostrarJuego(estadoActual);
      estadoActual=jugar(p,estadoActual,mm);
    }
    mostrarJuego(estadoActual);
    if(p.value(estadoActual)<0){
      System.out.println("GANO EL JUGADOR!!!");
    }else{
      System.out.println("GANO LA MAQUINA!!!");
    }
    
  }

  public static void mostrarJuego(State s){
    char [][] board;
    for (char[] row: board)
            Arrays.fill(row, '_');
    for (Token t: s.getTokens())
            board[t.getRow()][t.getColumn()]=t.getColor();
    for ( int i=0;i<7;i++) {
      for (int j =0;j<7 ;j++ ) {
        System.out.print(board[i][j]+" ");
      }
      System.out.println("");
    }
  }


    /*Esta funcion hace que, si es turno del jugador, le avise que puede hacer (si mover, insertar o eliminar) y
   * le pide la coordenada de la ficha a insertar, mover o eliminar...Si el turno es de la CPU, hace 
   * MinMax y juega el mov que mas le conviene*/
  private static State jugar(Problem problema,State estadoActual, MinMaxAlphaBetaEngine engine) {
    State res=null;
    Scanner sc = new Scanner(System.in);
    if(!estadoActual.isMax()){
      System.out.println("TENES QUE INSERTAR UNA FICHA ");
      res =ejecutarComando(problema,estadoActual);
    }else{
      res=engine.computeSuccessor(estadoActual);
    }
    return res;
  }
  /*Esta funcion ejecuta la accion del jugador*/
  
  private static State ejecutarComando(Problem prob,State estadoActual) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Ingrese la coordenada donde quiere insertar una ficha");
    int coo = sc.nextInt();
    State res= prob.insertarFicha(estadoActual,coo);

    return res;
  }




}