public class Token {
  private String row;
  private int column; 
  private int color;

  public Token (String i, int j, int c){
    this.row = i;
    this.column=j;
    this.color=c;
  }

  public String getRow(){
    return this.row;
  }

  public int getcolumn(){
    return this.column;
  }

  public int getColor(){
    return this.color;
  }
  
  public void setRow(String i){
    this.row=i;
  }

  public void setColumn(int j){
    this.column=j;
  }

  public void setColor(int c){
    this.color=c;
  }
  

}