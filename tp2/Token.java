public class Token {
  private int row;
  private int column; 
  private char color;
  private boolean visited;

  /*
   * Los colores posibles son: blanco = 'b', negro = 'n' y vacio = '_'
  */

  public Token(){
    this.row = -1;
    this.column = -1;
    this.color = '_';
    this.visited=false;
  }

  public Token (int i, int j, char c,boolean v){
    this.row = i;
    this.column=j;
    this.color=c;
    this.visited=v;
  }

  public int getRow(){
    return this.row;
  }

  public int getColumn(){
    return this.column;
  }

  public char getColor(){
    return this.color;
  }
  
  public void setRow(int i){
    this.row=i;
  }

  public boolean isVisited(){
    return this.visited;
  }

  public boolean equals(Token other){
    boolean rows = (this.row==other.getRow());
    boolean columns = (this.column==other.getColumn());
    boolean colors =(this.color==other.getColor());
    boolean rvisited =(this.visited==other.isVisited());
    return (rows&&columns&&colors&&rvisited);
  }

  public void setColumn(int j){
    this.column=j;
  }

  public void setColor(char c){
    this.color=c;
  }
  
  public String toString(){
    return ("("+this.row+","+this.column+","+this.color+")");
  }

  public char getOppositeColor(){
    if(this.color == 'b'){
      return 'n';
    }else{
      return 'b';
    }
  }
}