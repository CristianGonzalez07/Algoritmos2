public class Token {
  private int row;
  private int column; 
  private char color;

  /*
   * Los colores posibles son: blanco = 'b', negro = 'n' y vacio = '_'
  */
  
  public Token (int i, int j, char c){
    this.row = i;
    this.column=j;
    this.color=c;
  }

  public int getRow(){
    return this.row;
  }

  public int getcolumn(){
    return this.column;
  }

  public char getColor(){
    return this.color;
  }
  
  public void setRow(int i){
    this.row=i;
  }

  public void setColumn(int j){
    this.column=j;
  }

  public void setColor(char c){
    this.color=c;
  }
  

}