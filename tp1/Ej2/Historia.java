// Cada Tripla almacena los datos de una actividad del backlog

public class Historia {
  private String descripcion;
  private int prioridad; // Su valor estara comprendido entre [1..5].El mismo hace referencia a la prioridad de la acividad
  private int costo; //tiempo que cuesta realizar la actividad

  public Historia (String d, int p, int c){
    this.descripcion = d;
    this.prioridad=p;
    this.costo=c;
  }

  // Retorna la descripcion de la actividad    
  public String getDescripcion(){
    return this.descripcion;
  }

  // Retorna el valor de la actividad
  public int getPrioridad(){
    return this.prioridad;
  }

  // Retorna el valor de la actividad
  public int getCosto(){
    return this.costo;
  }
  
  // Modifica la descripcion de la actividad
  public void setDescripcion(String d){
    this.descripcion=d;
  }

  // Modifica el valor de la actividad
  public void setPrioridad(int p){
    this.prioridad=p;
  }

  // Modifica el costo de la actividad
  public void setCosto(int c){
    this.costo=c;
  }
  

}